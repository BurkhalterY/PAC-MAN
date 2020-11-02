using Newtonsoft.Json.Linq;
using System.IO;

namespace pacman
{
    public class Map
    {
        public Tile[,] tiles;

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

                    if (tiles[x, y].type != TileType.Wall)
                    {
                        if (x - 1 >= 0 && tiles[x - 1, y].type != TileType.Wall)
                        {
                            tiles[x, y].node.neighbors[Direction.Left] = new NodeConfig(tiles[x - 1, y].node);
                            tiles[x - 1, y].node.neighbors[Direction.Right] = new NodeConfig(tiles[x, y].node);
                        }
                        if (y - 1 >= 0 && tiles[x, y - 1].type != TileType.Wall)
                        {
                            tiles[x, y].node.neighbors[Direction.Up] = new NodeConfig(tiles[x, y - 1].node);
                            tiles[x, y - 1].node.neighbors[Direction.Down] = new NodeConfig(tiles[x, y].node);
                        }
                    }
                }

                if (tiles[0, y].type != TileType.Wall && tiles[tiles.GetLength(0) - 1, y].type != TileType.Wall)
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
    }
}
