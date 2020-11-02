using System.Windows;
using System.Windows.Media;

namespace pacman
{
    public interface Drawable
    {
        void Draw(DrawingContext dc, double ratio, Point offset);
    }
}
