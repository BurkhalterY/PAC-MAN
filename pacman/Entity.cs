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
        public double speed = 11d / 60d;
        protected bool move = false;
        protected string name;
        protected Dictionary<string, CroppedBitmap[]> sprites = new Dictionary<string, CroppedBitmap[]>();
        private string currentSprite = "left";
        protected string CurrentSprite
        {
            get {
                return currentSprite;
            }
            set {
                if (value != currentSprite)
                {
                    currentSprite = value;
                    frame = 0;
                    loop = false;
                }
            }
        }
        protected int frame;
        protected bool loop;

        public Entity(Node nodeFrom, Direction direction, double distance = 0)
        {
            Init();
            this.nodeFrom = nodeFrom;
            this.direction = nextDirection = direction;
            this.distance = distance;
            MyCanvas.toDraw[2].Add(this);
            LoadTextures();
        }

        protected virtual void Init()
        {

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

        public void LoadTexture(string textureName, int frames, string path = "")
        {
            if (path == string.Empty) {
                path = name + "\\" + textureName;
            }
            AddSprites(textureName, Game.texturePack + "\\" + path + ".png", frames);
        }

        public virtual void BeforeMove()
        {

        }

        public virtual void AfterMove()
        {

        }

        public virtual void CalculateDirection()
        {

        }

        public void Move()
        {
            BeforeMove();
            CalculateDirection();
            move = false;
            double d = 0;
            if (nodeFrom.neighbors[direction] != null)
            {
                d = Helper.CalculateDistance(nodeFrom.x, nodeFrom.y, nodeFrom.neighbors[direction].node.x, nodeFrom.neighbors[direction].node.y);
            }

            if (DirectionHelper.IsOpposite(direction, nextDirection))
            {
                if (d != 0)
                {
                    nodeFrom = nodeFrom.neighbors[direction].node;
                }
                distance = d - distance;
                move = true;
                direction = nextDirection;
            }
            else if (Math.Abs(distance - d) < speed)
            {
                if (nodeFrom.neighbors[direction] != null)
                {
                    nodeFrom = nodeFrom.neighbors[direction].node;
                }
                if (nodeFrom.neighbors[nextDirection] != null)
                {
                    if (nodeFrom.neighbors[nextDirection].tp)
                    {
                        nodeFrom = nodeFrom.neighbors[nextDirection].node;
                    }
                    distance -= d;
                    move = true;
                    direction = nextDirection;
                }
                else if (nodeFrom.neighbors[direction] != null)
                {
                    if (nodeFrom.neighbors[direction].tp)
                    {
                        nodeFrom = nodeFrom.neighbors[direction].node;
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
            AfterMove();
        }

        public virtual void OnCollision(Entity entity)
        {

        }

        public (double, double) GetXY()
        {
            Node a = nodeFrom;
            NodeConfig b = nodeFrom.neighbors[direction];
            double angle = 0;
            if (b != null)
            {
                angle = Math.Atan2(b.node.y - a.y, b.node.x - a.x);
            }
            double x = nodeFrom.x + Math.Cos(angle) * distance;
            double y = nodeFrom.y + Math.Sin(angle) * distance;

            return (x, y);
        }

        public (int, int) GetIntXY()
        {
            (double x2, double y2) = GetXY();
            int x = (int)(x2 + .5);
            int y = (int)(y2 + .5);
            return (x, y);
        }

        public virtual void SetSprite()
        {
            CurrentSprite = DirectionHelper.ToString(direction);
        }

        public virtual void Draw(DrawingContext dc, double ratio, Point offset)
        {
            SetSprite();

            (double x, double y) = GetXY();

            if (sprites[CurrentSprite].Length > 1 && ((move && Game.ticks % 4 == 0) || (!move && frame == sprites[CurrentSprite].Length - 1)))
            {
                frame += loop ? -1 : 1;
                if (frame == sprites[CurrentSprite].Length - 1 || frame == 0)
                {
                    loop = !loop;
                }
            }

            dc.DrawImage(sprites[CurrentSprite][frame], new Rect((x - offset.X - .5) * ratio, (y - offset.Y - .5) * ratio, ratio * 2, ratio * 2));
        }
    }
}
