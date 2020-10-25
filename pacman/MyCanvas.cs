using System;
using System.Collections.Generic;
using System.Windows.Controls;
using System.Windows.Media;

namespace pacman
{
    public class MyCanvas : Canvas
    {
        public static List<Drawable> toDraw = new List<Drawable>();
        protected override void OnRender(DrawingContext dc)
        {
            double ratio = Math.Min(ActualWidth, ActualHeight) / 16;

            foreach (Drawable obj in toDraw)
            {
                obj.Draw(dc, ratio);
            }
        }
    }
}
