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

        protected override void Init()
        {
            name = "ghosts/sue";
        }

        public override void CalculateTargetChaseMode()
        {
            (targetX, targetY) = RandomTarget();
        }
    }
}
