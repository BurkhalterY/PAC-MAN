using System.Windows.Media;

namespace pacman
{
    public class Pinky : Ghost
    {
        public Pinky(Node nodeFrom, Direction direction, double distance = 0) : base(nodeFrom, direction, distance)
        {
            color = Brushes.Pink;
        }

        public override void LoadTextures()
        {
            currentSprite = "right";
            AddSprites("right", @"..\..\..\res\ghosts\pinky\right.png", 2);
        }

        public override void CalculateTarget()
        {
            (double x, double y) = Game.player.GetXY();
            targetX = (int)(x + .5);
            targetY = (int)(y + .5);

            switch (Game.player.direction)
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
