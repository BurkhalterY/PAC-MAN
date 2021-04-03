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
            (int x, int y) = GetIntXY();
            if (Game.map.IsNotOut(x, y))
            {
                Tile tile = Game.map.tiles[x, y];

                switch (tile.type)
                {
                    case TileType.Pellet:
                        tile.SetTile(TileType.None);
                        break;
                    case TileType.SuperPellet:
                        tile.SetTile(TileType.None);
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
