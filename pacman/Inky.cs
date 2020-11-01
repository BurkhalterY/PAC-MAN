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
    }
}
