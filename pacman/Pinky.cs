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

        public override void LoadTextures()
        {
            currentSprite = "right";
            AddSprites("right", @"..\..\..\res\ghosts\pinky\right.png", 2);
        }

        public override void CalculateTargetChaseMode()
        {
            (targetX, targetY) = Game.players[0].GetIntXY();

            switch (Game.players[0].direction)
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
