using System.Windows;
using System.Windows.Media;
using System.Windows.Media.Imaging;

namespace pacman
{
    public enum TileType
    {
        Wall = 'X',
        None = ' ',
        Pellet = '.',
        SuperPellet = 'O',
        External = 'E'
    }

    public class Tile : Drawable
    {
        public int x, y;
        private TileType type;
        public TileType Type
        {
            get
            {
                return type;
            }
            set
            {
                type = value;
                Game.map.AutoTiling(x, y);
            }
        }

        public Node node;
        public CroppedBitmap texture;

        public Tile(int x, int y, TileType type)
        {
            this.x = x;
            this.y = y;
            Type = type;
            node = new Node(x, y);
            MyCanvas.toDraw[8].Add(this);
        }

        public Tile(int x, int y, char type) : this(x, y, (TileType)type)
        {

        }

        public void Draw(DrawingContext dc, double ratio, Point offset)
        {
            Rect rect = new Rect((x - offset.X) * ratio, (y - offset.Y) * ratio, ratio, ratio);

            if (rect.X + rect.Width >= 0 && rect.X <= MyCanvas.screenWidth * ratio && rect.Y + rect.Height >= 0 && rect.Y <= MyCanvas.screenHeight * ratio)
            {
                if (texture != null)
                {
                    dc.DrawImage(texture, rect);
                }
                else
                {
                    Brush brush;

                    switch (Type)
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
                            brush = Brushes.Red;
                            break;
                    }

                    dc.DrawRectangle(brush, null, rect);
                }
            }
        }
    }
}
