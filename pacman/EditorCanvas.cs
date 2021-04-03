using System;
using System.Windows;
using System.Windows.Media;

namespace pacman
{
    public class EditorCanvas : MyCanvas
    {
        protected override void OnRender(DrawingContext dc)
        {
            ratio = Math.Min(ActualWidth / screenWidth, ActualHeight / screenHeight);

            Entity baseEntity = Game.entities.Find(e => e is Player);

            Point offset = new Point(0, 0);

            dc.DrawRectangle(Brushes.Black, new Pen(Brushes.Black, 1), new Rect(0, 0, screenWidth * ratio, (screenHeight + 5) * ratio));

            for (int i = toDraw.Length - 1; i >= 0; i--)
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
