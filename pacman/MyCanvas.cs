using System.Collections.Generic;
using System.Windows.Controls;

namespace pacman
{
    public abstract class MyCanvas : Canvas
    {
        public static List<Drawable>[] toDraw = new List<Drawable>[10];
        public static double ratio;

        public static double screenWidth = 28;
        public static double screenHeight = 36;

        public static void Init()
        {
            for (int i = 0; i < toDraw.Length; i++)
            {
                toDraw[i] = new List<Drawable>();
            }
        }
    }
}
