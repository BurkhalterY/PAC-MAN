using System.Collections.Generic;
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
                foreach (var neighbor in neighbors)
                {
                    if (neighbor.Value != null && !neighbor.Value.tp)
                    {
                        Point p1 = new Point((x - offset.X + .5) * ratio, (y - offset.Y + .5) * ratio);
                        Point p2 = new Point((neighbor.Value.node.x - offset.X + .5) * ratio, (neighbor.Value.node.y - offset.Y + .5) * ratio);
                        if (p1.X >= 0 && p1.X < GameCanvas.screenWidth * ratio && p1.Y >= 0 && p1.Y < GameCanvas.screenHeight * ratio
                         || p2.X >= 0 && p2.X < GameCanvas.screenWidth * ratio && p2.Y >= 0 && p2.Y < GameCanvas.screenHeight * ratio)
                        {
                            dc.DrawLine(new Pen(neighbor.Value.red ? Brushes.Red : Brushes.White, ratio / 8), p1, p2);
                        }
                    }
                }
            }
        }
    }
}
