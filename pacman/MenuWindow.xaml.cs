using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace pacman
{
    /// <summary>
    /// Interaction logic for MenuWindow.xaml
    /// </summary>
    public partial class MenuWindow : Window
    {
        public MenuWindow()
        {
            InitializeComponent();
        }

        private void BtnDebug(object sender, RoutedEventArgs e)
        {
            Game.debug = true;
            new MainWindow().Show();
            Close();
        }

        private void Btn1Player(object sender, RoutedEventArgs e)
        {
            Game.debug = false;
            new MainWindow().Show();
            Close();
        }

        private void BtnEditor(object sender, RoutedEventArgs e)
        {
            new EditorWindow().Show();
            Close();
        }
    }
}
