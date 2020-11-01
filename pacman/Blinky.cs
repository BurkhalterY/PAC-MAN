using System.Windows.Media;

namespace pacman
{
    public class Blinky : Ghost
    {
        public Blinky(Node nodeFrom, Direction direction, double distance = 0) : base(nodeFrom, direction, distance)
        {
            color = Brushes.Red;
            scatterX = Game.map.tiles.GetLength(0) - 3;
            scatterY = 0;
        }

        public override void LoadTextures()
        {
            currentSprite = "right";
            AddSprites("right", @"..\..\..\res\ghosts\blinky\right.png", 2);
        }

        public override void CalculateTargetChaseMode()
        {
            (targetX, targetY) = Game.players[0].GetIntXY();
        }
    }
}
