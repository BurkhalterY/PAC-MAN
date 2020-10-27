using System;
using System.Collections.Generic;
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
        public double speed = 0.05;//11d / 60d;
        public bool move = true;
        protected Dictionary<string, CroppedBitmap[]> sprites = new Dictionary<string, CroppedBitmap[]>();
        protected string currentSprite;

        public Entity(Node nodeFrom, Direction direction, double distance = 0)
        {
            this.nodeFrom = nodeFrom;
            this.direction = this.nextDirection = direction;
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
            Node a = nodeFrom;
            Node b = nodeFrom.neighbors[direction];
            double d = Math.Sqrt(Math.Pow(a.x - b.x, 2) + Math.Pow(a.y - b.y, 2));

            if (DirectionHelper.isOpposite(direction, nextDirection))
            {
                nodeFrom = nodeFrom.neighbors[direction];
                direction = nextDirection;
                distance = d - distance;
                move = true;
            }

            if (move)
            {
                distance += speed;
            }

            if (Math.Abs(distance - d) < speed)
            {
                if (nodeFrom.neighbors[direction].neighbors[nextDirection] != null)
                {
                    nodeFrom = nodeFrom.neighbors[direction];
                    direction = nextDirection;
                    distance -= d;
                    move = true;
                }
                else if (nodeFrom.neighbors[direction].neighbors[direction] != null)
                {
                    nodeFrom = nodeFrom.neighbors[direction];
                    distance -= d;
                    move = true;
                }
                else
                {
                    move = false;
                    distance = d;
                }
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

            dc.DrawImage(sprites[currentSprite][Game.ticks / 4 % sprites[currentSprite].Length], new Rect(x * ratio, y * ratio, ratio, ratio));
        }
    }
}
