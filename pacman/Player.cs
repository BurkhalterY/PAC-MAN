using System;
using System.Linq;

namespace pacman
{
    public class Player : Entity
    {
        public Player(Node nodeFrom, Direction direction, double distance = 0) : base(nodeFrom, direction, distance)
        {

        }

        public override void AfterMove()
        {
            (int x, int y) = Game.entities.Find(e => e is Player).GetIntXY();
            if (x >= 0 && x < Game.map.tiles.GetLength(0) && y >= 0 && y < Game.map.tiles.GetLength(1))
            {
                Tile tile = Game.map.tiles[x, y];

                switch (tile.type)
                {
                    case TileType.Pellet:
                        tile.type = TileType.None;
                        break;
                    case TileType.SuperPellet:
                        tile.type = TileType.None;
                        Game.entities.FindAll(e => e is Ghost).ForEach(e => ((Ghost)e).mode = GhostMode.Frightened);
                        break;
                }
            }
        }

        public override void OnCollision(Entity entity)
        {
            if (entity is Ghost)
            {
                Ghost ghost = (Ghost)entity;
                if (ghost.mode == GhostMode.Chase || ghost.mode == GhostMode.Scatter)
                {
                    GameOver();
                }
                else if(ghost.mode == GhostMode.Frightened)
                {
                    ghost.mode = GhostMode.Eaten;
                }
            }
        }

        public void GameOver()
        {
            Console.WriteLine("Game Over!");
        }

        public override void LoadTextures()
        {
            LoadTexture("up", 3);
            LoadTexture("down", 3);
            LoadTexture("left", 3);
            LoadTexture("right", 3);
        }
    }
}
