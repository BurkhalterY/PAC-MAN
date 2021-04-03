using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Windows;
using System.Windows.Media.Imaging;

namespace pacman
{
    public class Map
    {
        private string name;
        public Tile[,] tiles;
        private Node[,] nodes;
        public (string, CroppedBitmap)[] tileset;

        public Map(string name)
        {
            this.name = name;
        }

        public void LoadMap()
        {
            string[] lines = File.ReadAllLines($@"..\..\..\res\maps\{name}\map.txt");

            LoadTileset();
            tiles = new Tile[lines[0].Length, lines.Length];
            nodes = new Node[lines[0].Length, lines.Length];

            for (int y = 0; y < lines.Length; y++)
            {
                for (int x = 0; x < lines[y].Length; x++)
                {
                    char tile = lines[y][x];
                    tiles[x, y] = new Tile(x, y, this, tile);
                    nodes[x, y] = new Node(x, y);

                    if (!Tile.TileIsSolid(tiles[x, y].type))
                    {
                        if (x - 1 >= 0 && !Tile.TileIsSolid(tiles[x - 1, y].type))
                        {
                            nodes[x, y].neighbors[Direction.Left] = new NodeConfig(nodes[x - 1, y]);
                            nodes[x - 1, y].neighbors[Direction.Right] = new NodeConfig(nodes[x, y]);
                        }
                        if (y - 1 >= 0 && !Tile.TileIsSolid(tiles[x, y - 1].type))
                        {
                            nodes[x, y].neighbors[Direction.Up] = new NodeConfig(nodes[x, y - 1]);
                            nodes[x, y - 1].neighbors[Direction.Down] = new NodeConfig(nodes[x, y]);
                        }
                    }
                }

                if (!Tile.TileIsSolid(tiles[0, y].type) && !Tile.TileIsSolid(tiles[tiles.GetLength(0) - 1, y].type))
                {
                    Node bridgeLeft = new Node(-3, y);
                    Node bridgeRight = new Node(tiles.GetLength(0) + 2, y);
                    Node nodeLeft = nodes[0, y];
                    Node nodeRight = nodes[tiles.GetLength(0) - 1, y];

                    nodeLeft.neighbors[Direction.Left] = new NodeConfig(bridgeLeft);
                    bridgeLeft.neighbors[Direction.Left] = new NodeConfig(bridgeRight) { tp = true };
                    bridgeRight.neighbors[Direction.Left] = new NodeConfig(nodeRight);

                    nodeRight.neighbors[Direction.Right] = new NodeConfig(bridgeRight);
                    bridgeRight.neighbors[Direction.Right] = new NodeConfig(bridgeLeft) { tp = true };
                    bridgeLeft.neighbors[Direction.Right] = new NodeConfig(nodeLeft);
                }
            }

            for (int x = 0; x < tiles.GetLength(0); x++)
            {
                for (int y = 0; y < tiles.GetLength(1); y++)
                {
                    AutoTiling(x, y);
                }
            }

            /*Dictionary<Direction, List<Node>>[,] subNodes = new Dictionary<Direction, List<Node>>[nodes.GetLength(0), nodes.GetLength(1)];

            for (int x = 0; x < nodes.GetLength(0); x++)
            {
                for (int y = 0; y < nodes.GetLength(1); y++)
                {
                    subNodes[x, y] = new Dictionary<Direction, List<Node>>();
                    subNodes[x, y].Add(Direction.Up, new List<Node>());
                    subNodes[x, y].Add(Direction.Down, new List<Node>());
                    subNodes[x, y].Add(Direction.Left, new List<Node>());
                    subNodes[x, y].Add(Direction.Right, new List<Node>());

                    foreach (Direction key in nodes[x, y].neighbors.Keys)
                    {
                        if (nodes[x, y].neighbors[key] != null)
                        {
                            switch (key)
                            {
                                case Direction.Up:
                                    for (int i = 1; i <= 4; i++)
                                    {
                                        subNodes[x, y][Direction.Up].Add(new Node(x, y - i / 8d));
                                    }
                                    break;
                                case Direction.Down:
                                    for (int i = 1; i <= 3; i++)
                                    {
                                        subNodes[x, y][Direction.Down].Add(new Node(x, y + i / 8d));
                                    }
                                    break;
                                case Direction.Left:
                                    for (int i = 1; i <= 3; i++)
                                    {
                                        subNodes[x, y][Direction.Left].Add(new Node(x - i / 8d, y));
                                    }
                                    break;
                                case Direction.Right:
                                    for (int i = 1; i <= 4; i++)
                                    {
                                        subNodes[x, y][Direction.Right].Add(new Node(x + i / 8d, y));
                                    }
                                    break;
                            }
                        }
                    }

                    List<Node> subNodesX = subNodes[x, y][Direction.Left].Concat(subNodes[x, y][Direction.Right]).ToList();
                    List<Node> subNodesY = subNodes[x, y][Direction.Up].Concat(subNodes[x, y][Direction.Down]).ToList();

                    for (int i = 0; i < subNodesX.Count; i++)
                    {
                        for (int j = 0; j < subNodes[x, y][Direction.Up].Count; j++)
                        {
                            subNodesX[i].neighbors[Direction.Up] = new NodeConfig(subNodes[x, y][Direction.Up][j]) { red = true };
                        }
                        for (int j = 0; j < subNodes[x, y][Direction.Down].Count; j++)
                        {
                            subNodesX[i].neighbors[Direction.Down] = new NodeConfig(subNodes[x, y][Direction.Down][j]) { red = true };
                        }
                    }
                    for (int i = 0; i < subNodesY.Count; i++)
                    {
                        for (int j = 0; j < subNodes[x, y][Direction.Left].Count; j++)
                        {
                            subNodesY[i].neighbors[Direction.Left] = new NodeConfig(subNodes[x, y][Direction.Left][j]) { red = true };
                        }
                        for (int j = 0; j < subNodes[x, y][Direction.Right].Count; j++)
                        {
                            subNodesY[i].neighbors[Direction.Right] = new NodeConfig(subNodes[x, y][Direction.Right][j]) { red = true };
                        }
                    }
                }
            }*/
        }

        public void LoadEntities()
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
                        Game.entities.Add(new Pacman(nodes[x, y], direction, distance));
                        break;
                    case "mspacman":
                        Game.entities.Add(new MsPacman(nodes[x, y], direction, distance));
                        break;
                    case "jrpacman":
                        Game.entities.Add(new JrPacman(nodes[x, y], direction, distance));
                        break;
                    case "blinky":
                        Game.entities.Add(new Blinky(nodes[x, y], direction, distance));
                        break;
                    case "inky":
                        Game.entities.Add(new Inky(nodes[x, y], direction, distance));
                        break;
                    case "pinky":
                        Game.entities.Add(new Pinky(nodes[x, y], direction, distance));
                        break;
                    case "clyde":
                        Game.entities.Add(new Clyde(nodes[x, y], direction, distance));
                        break;
                    case "sue":
                        Game.entities.Add(new Sue(nodes[x, y], direction, distance));
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

        public void AutoTiling(int x1, int y1)
        {
            for (int x = x1 - 1; x <= x1 + 1; x++)
            {
                for (int y = y1 - 1; y <= y1 + 1; y++)
                {
                    if (IsNotOut(x, y))
                    {
                        string pattern = "";
                        for (int j = -1; j <= 1; j++)
                        {
                            for (int i = -1; i <= 1; i++)
                            {
                                if (IsNotOut(x + i, y + j))
                                {
                                    pattern += (char)tiles[x + i, y + j].type;
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
            }
        }

        public bool IsNotOut(int x, int y)
        {
            return x >= 0 && x < tiles.GetLength(0)
                && y >= 0 && y < tiles.GetLength(1);
        }
    }
}
