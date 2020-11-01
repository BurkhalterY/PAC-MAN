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

        protected override void Init()
        {
            name = "ghosts/clyde";
        }

        public override void CalculateTargetChaseMode()
        {
            (targetX, targetY) = Game.entities.Find(e => e is Player).GetIntXY();
            (int myX, int myY) = GetIntXY();

            if (Helper.CalculateDistance(targetX, targetY, myX, myY) <= 8)
            {
                targetX = scatterX;
                targetY = scatterY;
            }
        }
    }
}
