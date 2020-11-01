using System;
using System.Windows.Media;

namespace pacman
{
    public class Sue : Ghost
    {
        public Sue(Node nodeFrom, Direction direction, double distance = 0) : base(nodeFrom, direction, distance)
        {
            color = Brushes.Purple;
            scatterX = 0;
            scatterY = Game.map.tiles.GetLength(1) - 1;
        }

        public override void LoadTextures()
        {
            currentSprite = "right";
            AddSprites("right", @"..\..\..\res\ghosts\sue\right.png", 2);
        }

        public override void CalculateTargetChaseMode()
        {
            (targetX, targetY) = RandomTarget();
        }
    }
}
