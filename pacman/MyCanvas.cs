using System;
using System.Collections.Generic;
using System.Windows;
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
            double screenWidth = 28;
            double screenHeight = 36;

            double ratio = Math.Min(ActualWidth / screenWidth, ActualHeight / screenHeight);

            (double offsetX, double offsetY) = Game.entities.Find(e => e is Player).GetXY();

            offsetX -= screenWidth / 2;
            offsetY -= screenHeight / 2;

            offsetX = Math.Max(0, Math.Min(offsetX, Game.map.tiles.GetLength(0) - screenWidth));
            offsetY = Math.Max(0, Math.Min(offsetY, Game.map.tiles.GetLength(1) - screenHeight));

            offsetY -= 3;

            Point offset = new Point(offsetX, offsetY);

            dc.DrawRectangle(Brushes.Black, new Pen(Brushes.Black, 1), new Rect(0, 0, screenWidth * ratio, (screenHeight + 5) * ratio));

            for (int i = toDraw.Length-1; i >= 0; i--)
            {
                foreach (Drawable obj in toDraw[i])
                {
                    obj.Draw(dc, ratio, offset);
                }
            }

            dc.DrawRectangle(Brushes.Black, new Pen(Brushes.Black, 1), new Rect(screenWidth * ratio, 0, ActualWidth, ActualHeight));
            dc.DrawRectangle(Brushes.Black, new Pen(Brushes.Black, 1), new Rect(0, (screenHeight + 3) * ratio, ActualWidth, ActualHeight));
        }
    }
}
