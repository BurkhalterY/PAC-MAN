using System.Windows;
using System.Windows.Input;
using static pacman.Game;

namespace pacman
{
    public partial class MainWindow : Window
    {
        public Game controller = new Game();

        public MainWindow()
        {
            InitializeComponent();
            controller.OnRefresh += new Refresh(OnRefresh);
        }

        public void OnRefresh()
        {
            scene.InvalidateVisual();
        }

        private void Window_KeyDown(object sender, KeyEventArgs e)
        {
            switch (e.Key)
            {
                case Key.Up:
                case Key.W:
                    controller.pressKey(Direction.Up);
                    break;
                case Key.Right:
                case Key.D:
                    controller.pressKey(Direction.Right);
                    break;
                case Key.Down:
                case Key.S:
                    controller.pressKey(Direction.Down);
                    break;
                case Key.Left:
                case Key.A:
                    controller.pressKey(Direction.Left);
                    break;
            }
        }
    }
}
