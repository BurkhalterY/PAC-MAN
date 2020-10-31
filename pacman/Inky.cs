using System.Windows.Media;

namespace pacman
{
    public class Inky : Ghost
    {
        public Inky(Node nodeFrom, Direction direction, double distance = 0) : base(nodeFrom, direction, distance)
        {
            color = Brushes.Cyan;
        }

        public override void LoadTextures()
        {
            currentSprite = "right";
            AddSprites("right", @"..\..\..\res\ghosts\inky\right.png", 2);
        }

        public override void CalculateTarget()
        {
            (double x, double y) = Game.player.GetXY();
            int pacmanX = (int)(x + .5);
            int pacmanY = (int)(y + .5);

            (x, y) = Game.ghosts[0].GetXY();
            int blinkyX = (int)(x + .5);
            int blinkyY = (int)(y + .5);

            switch (Game.player.direction)
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
