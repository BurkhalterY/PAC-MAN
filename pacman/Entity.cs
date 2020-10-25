using System;
using System.Windows;
using System.Windows.Media;
using System.Windows.Media.Imaging;

namespace pacman
{
    public class Entity : Drawable
    {
        public Node nodeFrom;
        public Direction direction;
        public double distance;
        
        public Entity(Node nodeFrom, Direction direction, double distance = 0)
        {
            this.nodeFrom = nodeFrom;
            this.direction = direction;
            this.distance = distance;
            MyCanvas.toDraw.Add(this);
        }

        public void Draw(DrawingContext dc, double ratio)
        {
            Node a = nodeFrom;
            Node b = nodeFrom.neighbors[direction];
            double angle = 0;
            if (b != null)
            {
                angle = Math.Atan2(b.y - a.y, b.x - a.x);
            }
            double x = nodeFrom.x + Math.Cos(angle) * distance;
            double y = nodeFrom.y + Math.Sin(angle) * distance;

            dc.DrawImage(new BitmapImage(new Uri(@"..\..\..\res\players\pac-man\move.png", UriKind.Relative)), new Rect(x * ratio, y * ratio, ratio, ratio));
        }
    }
}
