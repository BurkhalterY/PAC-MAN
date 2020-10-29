﻿using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace pacman
{
    public class Game
    {
        public delegate void Refresh();
        public event Refresh OnRefresh;
        public static int ticks;

        public Pacman player;

        public Game()
        {
            Timer timer = new Timer();
            timer.Tick += new EventHandler(onTick);
            timer.Interval = 16;
            timer.Start();


            /*Node nodeA = new Node(6, 6);
            Node nodeB = new Node(12, 6);
            Node nodeC = new Node(6, 12);
            Node nodeD = new Node(12, 12);
            Node nodeE = new Node(18, 12);
            Node nodeF = new Node(6, 24);
            Node nodeG = new Node(18, 24);
            nodeA.neighbors[Direction.Right] = nodeB;
            nodeA.neighbors[Direction.Down] = nodeC;
            nodeB.neighbors[Direction.Left] = nodeC;
            nodeB.neighbors[Direction.Down] = nodeD;
            nodeC.neighbors[Direction.Up] = nodeB;
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
            player = new Pacman(nodeA, Direction.Down);*/

            Map map = new Map();
            map.LoadMap();
            player = new Pacman(map.tiles[6, 8].node, Direction.Down);
        }

        public void pressKey(Direction direction)
        {
            player.nextDirection = direction;
        }

        private void onTick(object sender, EventArgs e)
        {
            player.Move();
            ticks++;
            OnRefresh();
        }
    }
}
