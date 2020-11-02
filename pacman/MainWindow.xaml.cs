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
            /*controller.player.Move();
            OnRefresh();*/
            switch (e.Key)
            {
                case Key.Up:
                    controller.pressKey(Direction.Up);
                    break;
                case Key.W:
                    controller.pressKey(Direction.Up, 1);
                    break;
                case Key.Right:
                    controller.pressKey(Direction.Right);
                    break;
                case Key.D:
                    controller.pressKey(Direction.Right, 1);
                    break;
                case Key.Down:
                    controller.pressKey(Direction.Down);
                    break;
                case Key.S:
                    controller.pressKey(Direction.Down, 1);
                    break;
                case Key.Left:
                    controller.pressKey(Direction.Left);
                    break;
                case Key.A:
                    controller.pressKey(Direction.Left, 1);
                    break;
            }
        }
    }
}
