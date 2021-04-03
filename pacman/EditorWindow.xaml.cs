using System;
using System.Windows;
using System.Windows.Input;
using System.Windows.Media;

namespace pacman
{
    public partial class EditorWindow : Window
    {
        public Editor controller;
        private Point previousPosition = new Point(-1, -1);
        private double zoom = 1;

        public EditorWindow()
        {
            InitializeComponent();
            MyCanvas.Init();
            controller = new Editor();
        }

        private void scene_MouseDown(object sender, MouseButtonEventArgs e)
        {
            if (e.LeftButton == MouseButtonState.Pressed)
            {
                int x = (int)(e.GetPosition(scene).X / MyCanvas.ratio);
                int y = (int)(e.GetPosition(scene).Y / MyCanvas.ratio);
                controller.Press(x, y);
                scene.InvalidateVisual();
            }
        }

        private void scene_MouseUp(object sender, MouseButtonEventArgs e)
        {
            previousPosition.X = -1;
            previousPosition.Y = -1;
            Matrix matrix = scene.RenderTransform.Value;
            Console.WriteLine(matrix.OffsetX);
        }

        private void scene_MouseMove(object sender, MouseEventArgs e)
        {
            if (e.LeftButton == MouseButtonState.Pressed)
            {
                int x = (int)(e.GetPosition(scene).X / MyCanvas.ratio);
                int y = (int)(e.GetPosition(scene).Y / MyCanvas.ratio);
                controller.Press(x, y);
                scene.InvalidateVisual();
            }
            if (e.MiddleButton == MouseButtonState.Pressed)
            {
                Point mousePos = e.GetPosition(scene);
                /*mousePos.X /= MyCanvas.ratio;
                mousePos.Y /= MyCanvas.ratio;
                mousePos.X *= 8;
                mousePos.Y *= 8;*/

                if (previousPosition.X != -1 && previousPosition.Y != -1)
                {
                    double deltaX = mousePos.X - previousPosition.X;
                    double deltaY = mousePos.Y - previousPosition.Y;

                    deltaX *= zoom;
                    deltaY *= zoom;

                    Matrix matrix = scene.RenderTransform.Value;
                    matrix.Translate(deltaX, deltaY);

                    matrix.OffsetX = Math.Min(0, matrix.OffsetX);
                    matrix.OffsetY = Math.Min(0, matrix.OffsetY);

                    scene.RenderTransform = new MatrixTransform(matrix);
                }

                previousPosition = mousePos;
            }
        }

        private void scene_MouseWheel(object sender, MouseWheelEventArgs e)
        {
            double deltaZoom = e.Delta >= 0 ? 1.1 : 1 / 1.1;
            zoom *= deltaZoom;
            double x = e.GetPosition(scene).X;
            double y = e.GetPosition(scene).Y;

            Matrix matrix = scene.RenderTransform.Value;
            matrix.ScaleAtPrepend(deltaZoom, deltaZoom, x, y);

            matrix.OffsetX = Math.Min(0, matrix.OffsetX);
            matrix.OffsetY = Math.Min(0, matrix.OffsetY);

            matrix.M11 = Math.Max(1, matrix.M11);
            matrix.M22 = Math.Max(1, matrix.M22);

            scene.RenderTransform = new MatrixTransform(matrix);
            scene.InvalidateVisual();
        }
    }
}
