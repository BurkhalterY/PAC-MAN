using System.Linq;
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

        protected override void Init()
        {
            name = "ghosts/blinky";
        }

        public override void CalculateTargetChaseMode()
        {
            (targetX, targetY) = Game.entities.Find(e => e is Player).GetIntXY();
        }
    }
}
