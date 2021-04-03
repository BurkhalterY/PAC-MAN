using System;

namespace pacman
{
    public class Editor
    {
        public static Map map;

        public Editor()
        {
            map = new Map("PAC-MAN");
            map.LoadMap();
            MyCanvas.screenWidth = map.tiles.GetLength(0);
            MyCanvas.screenHeight = map.tiles.GetLength(1);
        }

        public void Press(int x, int y)
        {
            if (map.IsNotOut(x, y))
            {
                map.tiles[x, y].SetTile(TileType.Wall);
            }
        }
    }
}
