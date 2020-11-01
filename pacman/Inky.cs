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

        public override void LoadTextures()
        {
            currentSprite = "right";
            AddSprites("right", @"..\..\..\res\ghosts\inky\right.png", 2);
        }

        public override void CalculateTargetChaseMode()
        {
            (int pacmanX, int pacmanY) = Game.players[0].GetIntXY();
            (int blinkyX, int blinkyY) = Game.ghosts[0].GetIntXY();

            switch (Game.players[0].direction)
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
