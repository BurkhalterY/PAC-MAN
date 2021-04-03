using System.Windows;
using System.Windows.Media;

namespace pacman
{
    public class Inky : Ghost
    {
        public Inky(Node nodeFrom, Direction direction, double distance = 0) : base(nodeFrom, direction, distance)
        {
            color = Brushes.Cyan;
            scatterX = Game.map.tiles.GetLength(0) - 1;
            scatterY = Game.map.tiles.GetLength(1) - 1;
        }

        protected override void Init()
        {
            name = "ghosts/inky";
        }

        public override void CalculateTargetChaseMode()
        {
            (int pacmanX, int pacmanY) = Game.entities.Find(e => e is Player).GetIntXY();
            (int blinkyX, int blinkyY) = Game.entities.Find(e => e is Blinky).GetIntXY();

            switch (Game.entities.Find(e => e is Player).direction)
            {
                case Direction.Right:
                    pacmanX += 2;
                    break;
                case Direction.Left:
                    pacmanX -= 2;
                    break;
                case Direction.Down:
                    pacmanY += 2;
                    break;
                case Direction.Up:
                    pacmanY -= 2;
                    pacmanX -= 2;
                    break;
            }

            targetX = pacmanX - (blinkyX - pacmanX);
            targetY = pacmanY - (blinkyY - pacmanY);
        }

        public override void Draw(DrawingContext dc, double ratio, Point offset)
        {
            base.Draw(dc, ratio, offset);
            if (Game.debug)
            {
                (int pacmanX, int pacmanY) = Game.entities.Find(e => e is Player).GetIntXY();
                (int blinkyX, int blinkyY) = Game.entities.Find(e => e is Blinky).GetIntXY();

                switch (Game.entities.Find(e => e is Player).direction)
                {
                    case Direction.Right:
                        pacmanX += 2;
                        break;
                    case Direction.Left:
                        pacmanX -= 2;
                        break;
                    case Direction.Down:
                        pacmanY += 2;
                        break;
                    case Direction.Up:
                        pacmanY -= 2;
                        pacmanX -= 2;
                        break;
                }

                int targetX = pacmanX - (blinkyX - pacmanX);
                int targetY = pacmanY - (blinkyY - pacmanY);

                dc.DrawLine(new Pen(color, ratio / 8), new Point((blinkyX + .5 - offset.X) * ratio, (blinkyY + .5 - offset.Y) * ratio), new Point((targetX + .5 - offset.X) * ratio, (targetY + .5 - offset.Y) * ratio));
            }
        }
    }
}
