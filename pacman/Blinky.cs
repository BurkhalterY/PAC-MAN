using System.Windows.Media;

namespace pacman
{
    public class Blinky : Ghost
    {
        public Blinky(Node nodeFrom, Direction direction, double distance = 0) : base(nodeFrom, direction, distance)
        {
            color = Brushes.Red;
        }

        public override void LoadTextures()
        {
            currentSprite = "right";
            AddSprites("right", @"..\..\..\res\ghosts\blinky\right.png", 2);
        }

        public override void CalculateTarget()
        {
            (double x, double y) = Game.player.GetXY();
            targetX = (int)(x + .5);
            targetY = (int)(y + .5);
        }
    }
}
