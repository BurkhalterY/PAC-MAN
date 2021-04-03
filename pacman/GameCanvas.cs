using System;
using System.Windows;
using System.Windows.Media;

namespace pacman
{
    public class GameCanvas : MyCanvas
    {
        protected override void OnRender(DrawingContext dc)
        {
            ratio = Math.Min(ActualWidth / screenWidth, ActualHeight / screenHeight);

            Entity baseEntity = Game.entities.Find(e => e is Player);
            (double offsetX, double offsetY) = baseEntity != null ? baseEntity.GetXY() : (0, 0);

            offsetX -= screenWidth / 2;
            offsetY -= screenHeight / 2;

            offsetX = Math.Max(0, Math.Min(offsetX, Game.map.tiles.GetLength(0) - screenWidth));
            offsetY = Math.Max(0, Math.Min(offsetY, Game.map.tiles.GetLength(1) - screenHeight + 3));

            offsetY -= 3;

            Point offset = new Point(offsetX, offsetY);

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
