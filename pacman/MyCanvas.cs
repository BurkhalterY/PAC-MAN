using System;
using System.Collections.Generic;
using System.Windows.Controls;
using System.Windows.Media;

namespace pacman
{
    public class MyCanvas : Canvas
    {
        public static List<Drawable>[] toDraw = new List<Drawable>[10];

        public static void Init()
        {
            for (int i = 0; i < toDraw.Length; i++)
            {
                toDraw[i] = new List<Drawable>();
            }
        }

        protected override void OnRender(DrawingContext dc)
        {
            double ratio = Math.Min(ActualWidth / 28, ActualHeight / 36);

            for (int i = toDraw.Length-1; i >= 0; i--)
            {
                foreach (Drawable obj in toDraw[i])
                {
                    obj.Draw(dc, ratio);
                }
            }
        }
    }
}
