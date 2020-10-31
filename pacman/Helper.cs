using System;

namespace pacman
{
    public static class Helper
    {
        public static double CalculateDistance(double ax, double ay, double bx, double by)
        {
            return Math.Sqrt(Math.Pow(ax - bx, 2) + Math.Pow(ay - by, 2));
        }
    }
}
