using System;
using System.Windows.Media;

namespace pacman
{
    public class Clyde : Ghost
    {
        public Clyde(Node nodeFrom, Direction direction, double distance = 0) : base(nodeFrom, direction, distance)
        {
            color = Brushes.Orange;
            scatterX = 0;
            scatterY = Game.map.tiles.GetLength(1) - 1;
        }

        public override void LoadTextures()
        {
            currentSprite = "right";
            AddSprites("right", @"..\..\..\res\ghosts\clyde\right.png", 2);
        }

        public override void CalculateTarget()
        {
            (double x, double y) = Game.player.GetXY();
            targetX = (int)(x + .5);
            targetY = (int)(y + .5);

            (x, y) = GetXY();
            int myX = (int)(x + .5);
            int myY = (int)(y + .5);

            if (Helper.CalculateDistance(targetX, targetY, myX, myY) <= 8)
            {
                targetX = scatterX;
                targetY = scatterY;
            }
        }
    }
}
