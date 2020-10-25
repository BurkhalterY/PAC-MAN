using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace pacman
{
    public class Game
    {
        public delegate void Refresh();
        public event Refresh OnRefresh;

        public List<Node> nodes = new List<Node>();
        public Pacman player;

        public Game()
        {
            Timer timer = new Timer();
            timer.Tick += new EventHandler(onTick);
            timer.Interval = 16;
            timer.Start();


            Node nodeA = new Node(2, 2);
            Node nodeB = new Node(4, 2);
            Node nodeC = new Node(2, 4);
            Node nodeD = new Node(4, 4);
            Node nodeE = new Node(6, 4);
            Node nodeF = new Node(2, 8);
            Node nodeG = new Node(6, 8);
            nodeA.neighbors[Direction.Right] = nodeB;
            nodeA.neighbors[Direction.Down] = nodeC;
            nodeB.neighbors[Direction.Left] = nodeA;
            nodeB.neighbors[Direction.Down] = nodeD;
            nodeC.neighbors[Direction.Up] = nodeA;
            nodeC.neighbors[Direction.Right] = nodeD;
            nodeC.neighbors[Direction.Down] = nodeF;
            nodeD.neighbors[Direction.Up] = nodeB;
            nodeD.neighbors[Direction.Left] = nodeC;
            nodeD.neighbors[Direction.Right] = nodeE;
            nodeE.neighbors[Direction.Left] = nodeD;
            nodeE.neighbors[Direction.Down] = nodeG;
            nodeF.neighbors[Direction.Up] = nodeC;
            nodeF.neighbors[Direction.Right] = nodeG;
            nodeG.neighbors[Direction.Up] = nodeE;
            nodeG.neighbors[Direction.Left] = nodeF;
            nodes.Add(nodeA);
            nodes.Add(nodeB);
            nodes.Add(nodeC);
            nodes.Add(nodeD);
            nodes.Add(nodeE);
            nodes.Add(nodeF);
            nodes.Add(nodeG);

            nodeA.neighbors[Direction.Up] = nodeG;
            player = new Pacman(nodeA, Direction.Down, 0);
        }

        public void pressKey(Direction direction)
        {
            player.direction = direction;
        }

        private void onTick(object sender, EventArgs e)
        {
            player.distance += 0.01;
            OnRefresh();
        }
    }
}
