using System;
using System.Collections.Generic;
using System.Globalization;
using System.Windows;
using System.Windows.Media;
using System.Windows.Media.Imaging;

namespace pacman
{
    public class Entity : Drawable
    {
        public Node nodeFrom;
        public Direction direction;
        public Direction nextDirection;
        public double distance;
        public double speed = 11d / 60d;
        private bool move = false;
        protected Dictionary<string, CroppedBitmap[]> sprites = new Dictionary<string, CroppedBitmap[]>();
        protected string currentSprite;
        private int frame;
        private bool loop;

        public Entity(Node nodeFrom, Direction direction, double distance = 0)
        {
            this.nodeFrom = nodeFrom;
            this.direction = nextDirection = direction;
            this.distance = distance;
            MyCanvas.toDraw.Add(this);
            LoadTextures();
        }

        public void AddSprites(string name, string path, int frames)
        {
            BitmapImage spritesheet = new BitmapImage(new Uri(path, UriKind.Relative));
            CroppedBitmap[] arr = new CroppedBitmap[frames];

            int w = spritesheet.PixelWidth / frames;
            int h = spritesheet.PixelHeight;

            for (int i = 0; i < frames; i++)
            {
                arr[i] = new CroppedBitmap(spritesheet, new Int32Rect(i % frames * w, 0, w, h));
            }
            sprites.Add(name, arr);
        }

        public virtual void LoadTextures()
        {

        }

        public void Move()
        {
            move = false;
            double d = 0;
            if (nodeFrom.neighbors[direction] != null) {
                d = Node.CalculateDistance(nodeFrom, nodeFrom.neighbors[direction]);
            }

            if (DirectionHelper.isOpposite(direction, nextDirection))
            {
                if (d != 0)
                {
                    nodeFrom = nodeFrom.neighbors[direction];
                }
                distance = d - distance;
                move = true;
                direction = nextDirection;
            }
            else if (Math.Abs(distance - d) < speed)
            {
                if (nodeFrom.neighbors[direction] != null)
                {
                    nodeFrom = nodeFrom.neighbors[direction];
                }
                if (nodeFrom.neighbors[nextDirection] != null)
                {
                    if (nodeFrom.tp)
                    {
                        nodeFrom = nodeFrom.neighbors[nextDirection];
                    }
                    distance -= d;
                    move = true;
                    direction = nextDirection;
                }
                else if (nodeFrom.neighbors[direction] != null)
                {
                    if (nodeFrom.tp)
                    {
                        nodeFrom = nodeFrom.neighbors[direction];
                    }
                    distance -= d;
                    move = true;
                }
            }
            else
            {
                move = true;
            }
            

            if (move)
            {
                distance += speed;
            }
            else
            {
                distance = 0;
            }
        }

        public void Draw(DrawingContext dc, double ratio)
        {
            Node a = nodeFrom;
            Node b = nodeFrom.neighbors[direction];
            double angle = 0;
            if (b != null)
            {
                angle = Math.Atan2(b.y - a.y, b.x - a.x);
            }
            double x = nodeFrom.x + Math.Cos(angle) * distance;
            double y = nodeFrom.y + Math.Sin(angle) * distance;

            if (Game.ticks % 4 == 0)
            {
                frame += loop ? -1 : 1;
                if (frame == sprites[currentSprite].Length - 1 || frame == 0)
                {
                    loop = !loop;
                }
            }

            dc.DrawImage(sprites[currentSprite][frame], new Rect(x * ratio - ratio / 2, y * ratio - ratio / 2, ratio * 2, ratio * 2));

            dc.DrawText(new FormattedText(nodeFrom.x + " ; " + nodeFrom.y, CultureInfo.InvariantCulture, FlowDirection.LeftToRight, new Typeface("arial"), 40, Brushes.Red), new Point());
            dc.DrawText(new FormattedText(distance+"", CultureInfo.InvariantCulture, FlowDirection.LeftToRight, new Typeface("arial"), 40, Brushes.Red), new Point(0, 45));
        }
    }
}
