using Newtonsoft.Json.Linq;
using System;
using System.IO;
using System.Windows;
using System.Windows.Media.Imaging;

namespace pacman
{
    public class Map
    {
        public Tile[,] tiles;
        public (string, CroppedBitmap)[] tileset;
        public bool isLoaded;

        public void LoadMap(string name)
        {
            string[] lines = File.ReadAllLines($@"..\..\..\res\maps\{name}\map.txt");

            tiles = new Tile[lines[0].Length, lines.Length];

            for (int y = 0; y < lines.Length; y++)
            {
                for (int x = 0; x < lines[y].Length; x++)
                {
                    char tile = lines[y][x];
                    tiles[x, y] = new Tile(x, y, tile);

                    if (tiles[x, y].Type != TileType.Wall)
                    {
                        if (x - 1 >= 0 && tiles[x - 1, y].Type != TileType.Wall)
                        {
                            tiles[x, y].node.neighbors[Direction.Left] = new NodeConfig(tiles[x - 1, y].node);
                            tiles[x - 1, y].node.neighbors[Direction.Right] = new NodeConfig(tiles[x, y].node);
                        }
                        if (y - 1 >= 0 && tiles[x, y - 1].Type != TileType.Wall)
                        {
                            tiles[x, y].node.neighbors[Direction.Up] = new NodeConfig(tiles[x, y - 1].node);
                            tiles[x, y - 1].node.neighbors[Direction.Down] = new NodeConfig(tiles[x, y].node);
                        }
                    }
                }

                if (tiles[0, y].Type != TileType.Wall && tiles[tiles.GetLength(0) - 1, y].Type != TileType.Wall)
                {
                    Node bridgeLeft = new Node(-3, y);
                    Node bridgeRight = new Node(tiles.GetLength(0) + 2, y);
                    Node tileLeft = tiles[0, y].node;
                    Node tileRight = tiles[tiles.GetLength(0) - 1, y].node;

                    tileLeft.neighbors[Direction.Left] = new NodeConfig(bridgeLeft);
                    bridgeLeft.neighbors[Direction.Left] = new NodeConfig(bridgeRight) { tp = true };
                    bridgeRight.neighbors[Direction.Left] = new NodeConfig(tileRight);

                    tileRight.neighbors[Direction.Right] = new NodeConfig(bridgeRight);
                    bridgeRight.neighbors[Direction.Right] = new NodeConfig(bridgeLeft) { tp = true };
                    bridgeLeft.neighbors[Direction.Right] = new NodeConfig(tileLeft);


                    //tiles[0, y].node.neighbors[Direction.Left] = new NodeConfig(tiles[tiles.GetLength(0) - 1, y].node) { tp = true };
                    //tiles[tiles.GetLength(0) - 1, y].node.neighbors[Direction.Right] = new NodeConfig(tiles[0, y].node) { tp = true };
                }
            }
            isLoaded = true;

            LoadTileset();
            for (int x = 0; x < tiles.GetLength(0); x++)
            {
                for (int y = 0; y < tiles.GetLength(1); y++)
                {
                    AutoTiling(x, y);
                }
            }
            LoadEntities(name);
        }

        public void LoadEntities(string name)
        {
            JObject obj = JObject.Parse(File.ReadAllText($@"..\..\..\res\maps\{name}\elements.json"));
            JArray arr = obj.Value<JArray>("elements");
            foreach (JToken element in arr)
            {
                int x = element.Value<int>("x");
                int y = element.Value<int>("y");
                double distance = element.Value<double>("distance");
                Direction direction = DirectionHelper.FromString(element.Value<string>("direction"));

                switch (element.Value<string>("type"))
                {
                    case "pacman":
                        Game.entities.Add(new Pacman(Game.map.tiles[x, y].node, direction, distance));
                        break;
                    case "mspacman":
                        Game.entities.Add(new MsPacman(Game.map.tiles[x, y].node, direction, distance));
                        break;
                    case "jrpacman":
                        Game.entities.Add(new JrPacman(Game.map.tiles[x, y].node, direction, distance));
                        break;
                    case "blinky":
                        Game.entities.Add(new Blinky(Game.map.tiles[x, y].node, direction, distance));
                        break;
                    case "inky":
                        Game.entities.Add(new Inky(Game.map.tiles[x, y].node, direction, distance));
                        break;
                    case "pinky":
                        Game.entities.Add(new Pinky(Game.map.tiles[x, y].node, direction, distance));
                        break;
                    case "clyde":
                        Game.entities.Add(new Clyde(Game.map.tiles[x, y].node, direction, distance));
                        break;
                    case "sue":
                        Game.entities.Add(new Sue(Game.map.tiles[x, y].node, direction, distance));
                        break;
                }
            }
        }

        public void LoadTileset()
        {
            BitmapImage spritesheet = new BitmapImage(new Uri(Game.texturePack + @"\tilesets\original.png", UriKind.Relative));

            int w = spritesheet.PixelWidth / 16;
            int h = spritesheet.PixelHeight / 4;


            JObject obj = JObject.Parse(File.ReadAllText(Game.texturePack + @"\tilesets\original.json"));
            JArray arr = obj.Value<JArray>("tiles");
            tileset = new (string, CroppedBitmap)[arr.Count];

            for (int i = 0; i < arr.Count; i++)
            {
                int x = arr[i].Value<int>("x");
                int y = arr[i].Value<int>("y");
                string pattern = arr[i].Value<string>("pattern");

                tileset[i] = (pattern, new CroppedBitmap(spritesheet, new Int32Rect(x * w, y * h, w, h)));
            }
        }

        public void AutoTiling(int x, int y)
        {
            if (isLoaded)
            {
                string pattern = "";
                for (int j = -1; j <= 1; j++)
                {
                    for (int i = -1; i <= 1; i++)
                    {
                        if (IsNotOut(x + i, y + j))
                        {
                            pattern += (char)tiles[x + i, y + j].Type;
                        }
                        else
                        {
                            pattern += 'E';
                        }
                    }
                }

                foreach (var texture in tileset)
                {
                    bool match = true;
                    for (int i = 0; i < pattern.Length; i++)
                    {
                        if (!(texture.Item1[i] == '?' || texture.Item1[i] == pattern[i]
                        || texture.Item1[i] == '_' && " .O".Contains(pattern[i] + "")
                        || texture.Item1[i] == 'S' && "XE".Contains(pattern[i] + "")))
                        {
                            match = false;
                            break;
                        }
                    }

                    if (match)
                    {
                        tiles[x, y].texture = texture.Item2;
                    }
                }
            }
        }

        public bool IsNotOut(int x, int y)
        {
            return x >= 0 && x < tiles.GetLength(0)
                && y >= 0 && y < tiles.GetLength(1);
        }
    }
}
