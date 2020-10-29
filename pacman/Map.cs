using System;
using System.Collections.Generic;
using System.IO;

namespace pacman
{
    public class Map
    {
        public Tile[,] tiles;
        public List<Node> nodes = new List<Node>();

        public void LoadMap()
        {
            string[] lines = File.ReadAllLines(@"..\..\..\res\maps\PAC-MAN\map.txt");

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
                            tiles[x, y].node.neighbors[Direction.Left] = tiles[x - 1, y].node;
                            tiles[x - 1, y].node.neighbors[Direction.Right] = tiles[x, y].node;
                        }
                        if (y - 1 >= 0 && tiles[x, y - 1].type != TileType.Wall)
                        {
                            tiles[x, y].node.neighbors[Direction.Up] = tiles[x, y - 1].node;
                            tiles[x, y - 1].node.neighbors[Direction.Down] = tiles[x, y].node;
                        }

                    }
                }

                if (tiles[0, y].type != TileType.Wall && tiles[tiles.GetLength(0) - 1, y].type != TileType.Wall)
                {
                    tiles[0, y].node.tp = true;
                    tiles[tiles.GetLength(0) - 1, y].node.tp = true;

                    tiles[0, y].node.neighbors[Direction.Left] = tiles[tiles.GetLength(0) - 1, y].node;
                    tiles[tiles.GetLength(0) - 1, y].node.neighbors[Direction.Right] = tiles[0, y].node;
                }
            }
        }
    }
}
