using System;
using System.Collections.Generic;
using System.Windows;
using System.Windows.Media;

namespace pacman
{
    public class Node : Drawable
    {
        public double x, y;
        public Dictionary<Direction, Node> neighbors = new Dictionary<Direction, Node>() {
            { Direction.None, null },
            { Direction.Up, null },
            { Direction.Left, null },
            { Direction.Down, null },
            { Direction.Right, null }
        };
        public bool tp = false;

        public Node(double x, double y)
        {
            this.x = x;
            this.y = y;
            MyCanvas.toDraw.Add(this);
        }

        public void Draw(DrawingContext dc, double ratio)
        {
            /*dc.DrawEllipse(Brushes.Red, new Pen(Brushes.Red, 1), new Point(x * ratio, y * ratio), ratio / 4, ratio / 4);
            foreach (var neighbor in neighbors)
            {
                if (neighbor.Value != null)
                {
                    dc.DrawLine(new Pen(Brushes.Black, 2), new Point(x * ratio, y * ratio), new Point(neighbor.Value.x * ratio, neighbor.Value.y * ratio));
                }
            }*/
        }
    }
}
