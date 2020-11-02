using System.Collections.Generic;
using System.Diagnostics;
using System.Windows;
using System.Windows.Media;

namespace pacman
{
    public class Node : Drawable
    {
        public double x, y;
        public Dictionary<Direction, NodeConfig> neighbors = new Dictionary<Direction, NodeConfig>() {
            { Direction.None, null },
            { Direction.Up, null },
            { Direction.Left, null },
            { Direction.Down, null },
            { Direction.Right, null }
        };

        public Node(double x, double y)
        {
            this.x = x;
            this.y = y;
            MyCanvas.toDraw[5].Add(this);
        }

        public void Draw(DrawingContext dc, double ratio, Point offset)
        {
            if (Game.debug)
            {
                //dc.DrawEllipse(Brushes.Red, new Pen(Brushes.Red, 1), new Point((x - offset.X + .5) * ratio, (y - offset.Y + .5) * ratio), ratio / 4, ratio / 4);
                foreach (var neighbor in neighbors)
                {
                    if (neighbor.Value != null && !neighbor.Value.tp)
                    {
                        dc.DrawLine(new Pen(Brushes.White, ratio / 8), new Point((x - offset.X + .5) * ratio, (y - offset.Y + .5) * ratio), new Point((neighbor.Value.node.x - offset.X + .5) * ratio, (neighbor.Value.node.y - offset.Y + .5) * ratio));
                    }
                }
            }
        }
    }
}
