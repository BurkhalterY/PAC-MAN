using System.Windows;
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
            (double myX, double myY) = GetXY();

            if (Helper.CalculateDistance(targetX, targetY, myX, myY) <= 8)
            {
                targetX = scatterX;
                targetY = scatterY;
            }
        }

        public override void Draw(DrawingContext dc, double ratio, Point offset)
        {
            base.Draw(dc, ratio, offset);
            if (Game.debug)
            {
                (double x, double y) = GetXY();
                dc.DrawEllipse(Brushes.Transparent, new Pen(color, ratio / 8), new Point((x - offset.X) * ratio, (y - offset.Y) * ratio), 8 * ratio, 8 * ratio);
            }
        }
    }
}
