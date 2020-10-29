using System.Windows;
using System.Windows.Media;

namespace pacman
{
    public enum TileType
    {
        Wall = 'X',
        None = ' ',
        Pellet = '.',
        SuperPellet = 'O'
    }

    public class Tile : Drawable
    {
        public int x, y;
        public TileType type;
        public Node node;

        public Tile(int x, int y, TileType type)
        {
            this.x = x;
            this.y = y;
            this.type = type;
            this.node = new Node(x, y);
            MyCanvas.toDraw.Add(this);
        }

        public Tile(int x, int y, char type) : this(x, y, (TileType)type)
        {

        }

        public void Draw(DrawingContext dc, double ratio)
        {
            Brush brush = Brushes.Transparent;

            switch (type)
            {
                case TileType.Wall:
                    brush = Brushes.Blue;
                    break;
                case TileType.None:
                    brush = Brushes.Black;
                    break;
                case TileType.Pellet:
                    brush = Brushes.Beige;
                    break;
                case TileType.SuperPellet:
                    brush = Brushes.Orange;
                    break;
                default:
                    brush = Brushes.Green;
                    break;
            }

            dc.DrawRectangle(brush, new Pen(brush, 1), new Rect(x * ratio, y * ratio, ratio, ratio));
        }
    }
}
