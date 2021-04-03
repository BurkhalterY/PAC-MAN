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
        private Map map;
        public TileType type;

        public CroppedBitmap texture;

        public Tile(int x, int y, Map map, TileType type)
        {
            this.x = x;
            this.y = y;
            this.map = map;
            this.type = type;
            MyCanvas.toDraw[8].Add(this);
        }

        public Tile(int x, int y, Map map, char type) : this(x, y, map, (TileType)type)
        {

        }

        public void SetTile(TileType type)
        {
            this.type = type;
            map.AutoTiling(x, y);
        }

        public void Draw(DrawingContext dc, double ratio, Point offset)
        {
            Rect rect = new Rect((x - offset.X) * ratio, (y - offset.Y) * ratio, ratio, ratio);

            if (rect.X + rect.Width >= 0 && rect.X <= GameCanvas.screenWidth * ratio && rect.Y + rect.Height >= 0 && rect.Y <= GameCanvas.screenHeight * ratio)
            {
                if (texture != null)
                {
                    dc.DrawImage(texture, rect);
                }
                else
                {
                    Brush brush;

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
                            brush = Brushes.Red;
                            break;
                    }

                    dc.DrawRectangle(brush, null, rect);
                }
            }
        }

        public static bool TileIsSolid(TileType tile)
        {
            return tile == TileType.Wall || tile == TileType.External;
        }
    }
}
