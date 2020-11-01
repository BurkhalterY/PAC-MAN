using System;
using System.Windows.Media;

namespace pacman
{
    public enum GhostMode
    {
        Chase,
        Scatter,
        Eaten,
        Frightened
    }

    public class Ghost : Entity
    {
        public GhostMode mode = GhostMode.Scatter;
        protected int targetX = 0, targetY = 0;
        protected int scatterX = 0, scatterY = 0;
        protected Brush color;

        public Ghost(Node nodeFrom, Direction direction, double distance = 0) : base(nodeFrom, direction, distance)
        {

        }

        public override void BeforeMove()
        {
            
        }

        public override void CalculateDirection()
        {
            if ((mode == GhostMode.Chase || mode == GhostMode.Scatter) && mode != Game.ghostMode)
            {
                nextDirection = DirectionHelper.Opposite(direction);
                mode = Game.ghostMode;
            }
            else
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
                            if ((distanceBetween < minDistance || minDistance == -1) && !DirectionHelper.IsOpposite(direction, key))
                            {
                                minDistance = distanceBetween;
                                nextDirection = key;
                            }
                        }
                    }
                }
            }
        }

        public void CalculateTarget()
        {
            switch (mode)
            {
                case GhostMode.Chase:
                    CalculateTargetChaseMode();
                    break;
                case GhostMode.Scatter:
                    targetX = scatterX;
                    targetY = scatterY;
                    break;
                case GhostMode.Eaten:
                    // Ghost house
                    break;
                case GhostMode.Frightened:
                    (targetX, targetY) = RandomTarget();
                    break;
            }
        }

        public virtual void CalculateTargetChaseMode()
        {

        }

        public (int, int) RandomTarget()
        {
            (double x2, double y2) = GetXY();
            int x = (int)(x2 + .5);
            int y = (int)(y2 + .5);

            switch (Game.random.Next(4))
            {
                case 0:
                    x++;
                    break;
                case 1:
                    x--;
                    break;
                case 2:
                    y++;
                    break;
                case 3:
                    y--;
                    break;
            }
            return (x, y);
        }

        public override void LoadTextures()
        {
            LoadTexture("up", 2);
            LoadTexture("down", 2);
            LoadTexture("left", 2);
            LoadTexture("right", 2);

            LoadTexture("scarred_up", 2, "ghosts\\scarred\\up");
            LoadTexture("scarred_down", 2, "ghosts\\scarred\\down");
            LoadTexture("scarred_left", 2, "ghosts\\scarred\\left");
            LoadTexture("scarred_right", 2, "ghosts\\scarred\\right");

            LoadTexture("scarred_white_up", 2, "ghosts\\white-scarred\\up");
            LoadTexture("scarred_white_down", 2, "ghosts\\white-scarred\\down");
            LoadTexture("scarred_white_left", 2, "ghosts\\white-scarred\\left");
            LoadTexture("scarred_white_right", 2, "ghosts\\white-scarred\\right");

            LoadTexture("eyes_up", 1, "ghosts\\eyes\\up");
            LoadTexture("eyes_down", 1, "ghosts\\eyes\\down");
            LoadTexture("eyes_left", 1, "ghosts\\eyes\\left");
            LoadTexture("eyes_right", 1, "ghosts\\eyes\\right");
        }

        public override void SetSprite()
        {
            base.SetSprite();
            if (mode == GhostMode.Frightened)
            {
                CurrentSprite = "scarred_" + CurrentSprite;
            }
            else if (mode == GhostMode.Eaten)
            {
                CurrentSprite = "eyes_" + CurrentSprite;
            }
        }

        public override void Draw(DrawingContext dc, double ratio)
        {
            base.Draw(dc, ratio);
            //dc.DrawRectangle(Brushes.Transparent, new Pen(color, ratio/8), new Rect(targetX * ratio, targetY * ratio, ratio, ratio));
        }
    }
}
