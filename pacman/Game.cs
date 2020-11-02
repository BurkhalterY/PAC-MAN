using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;

namespace pacman
{
    public class Game
    {
        public delegate void Refresh();
        public event Refresh OnRefresh;
        public static int ticks;
        public static Random random;

        public static Map map;
        public static List<Entity> entities = new List<Entity>();
        public static GhostMode ghostMode = GhostMode.Scatter;
        public static List<int> modeIntervals = new List<int>() { 7 * 60, 20 * 60, 7 * 60, 20 * 60, 5 * 60, 20 * 60, 5 * 60 };
        public static int ticksMode;
        public static string texturePack = @"..\..\..\res";
        public static bool debug = false;

        public Game()
        {
            Timer timer = new Timer();
            timer.Tick += new EventHandler(onTick);
            timer.Interval = 16;
            timer.Start();
            random = new Random();
            MyCanvas.Init();

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

            map = new Map();
            map.LoadMap("PAC-MAN");
            //map.LoadMap("Jr. PAC-MAN (2)");
            /*entities[5] = new Pacman(map.tiles[6, 8].node, Direction.Down);
            entities[0] = new Blinky(map.tiles[6, 8].node, Direction.Left);
            entities[1] = new Inky(map.tiles[6, 8].node, Direction.Left);
            entities[2] = new Pinky(map.tiles[6, 8].node, Direction.Left);
            entities[3] = new Clyde(map.tiles[6, 8].node, Direction.Left);*/
        }

        public void pressKey(Direction direction)
        {
            entities.Find(e => e is Player).nextDirection = direction;
        }

        private void onTick(object sender, EventArgs e)
        {
            CalculateGhostMode();
            foreach (Entity entity in entities)
            {
                entity.Move();
                foreach (Entity entityCollision in entities)
                {
                    if (entity.GetIntXY() == entityCollision.GetIntXY())
                    {
                        entity.OnCollision(entityCollision);
                    }
                }
            }
            ticks++;
            OnRefresh();
        }

        private void CalculateGhostMode()
        {
            ticksMode++;
            if (modeIntervals.Count() > 0 && ticksMode > modeIntervals.First())
            {
                modeIntervals.RemoveAt(0);
                ticksMode = 0;

                ghostMode = ghostMode == GhostMode.Scatter ? GhostMode.Chase : GhostMode.Scatter;
            }
        }
    }
}
