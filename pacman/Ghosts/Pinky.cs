using System.Windows.Media;

namespace pacman
{
    public class Pinky : Ghost
    {
        public Pinky(Node nodeFrom, Direction direction, double distance = 0) : base(nodeFrom, direction, distance)
        {
            color = Brushes.Pink;
            scatterX = 2;
            scatterY = 0;
        }

        protected override void Init()
        {
            name = "ghosts/pinky";
        }

        public override void CalculateTargetChaseMode()
        {
            (targetX, targetY) = Game.entities.Find(e => e is Player).GetIntXY();

            switch (Game.entities.Find(e => e is Player).direction)
            {
                case Direction.Right:
                    targetX += 4;
                    break;
                case Direction.Left:
                    targetX -= 4;
                    break;
                case Direction.Down:
                    targetY += 4;
                    break;
                case Direction.Up:
                    targetY -= 4;
                    targetX -= 4;
                    break;
            }
        }
    }
}
