using System;
using System.Windows;
using System.Windows.Media;

namespace pacman
{
    public class Ghost : Entity
    {
        protected int targetX = 0, targetY = 0;
        protected int scatterX = 0, scatterY = 0;
        protected Brush color;

        public Ghost(Node nodeFrom, Direction direction, double distance = 0) : base(nodeFrom, direction, distance)
        {

        }

        public override void CalculateDirection()
        {
            double d = 0;
            if (nodeFrom.neighbors[direction] != null)
            {
                d = Helper.CalculateDistance(nodeFrom.x, nodeFrom.y, nodeFrom.neighbors[direction].x, nodeFrom.neighbors[direction].y);
            }

            if (Math.Abs(distance - d) < speed)
            {
                CalculateTarget();
                double minDistance = -1;

                Node baseNode = nodeFrom;
                if (baseNode.neighbors[direction] != null)
                {
                    baseNode = baseNode.neighbors[direction];
                }
                foreach (Direction key in baseNode.neighbors.Keys)
                {
                    if (baseNode.neighbors[key] != null)
                    {
                        double distanceBetween = Helper.CalculateDistance(baseNode.neighbors[key].x, baseNode.neighbors[key].y, targetX, targetY);
                        if ((distanceBetween < minDistance || minDistance == -1) && !DirectionHelper.isOpposite(direction, key))
                        {
                            minDistance = distanceBetween;
                            nextDirection = key;
                        }
                    }
                }
            }
        }

        public virtual void CalculateTarget()
        {

        }

        public override void Draw(DrawingContext dc, double ratio)
        {
            base.Draw(dc, ratio);
            dc.DrawRectangle(Brushes.Transparent, new Pen(color, ratio/8), new Rect(targetX * ratio, targetY * ratio, ratio, ratio));
        }
    }
}
