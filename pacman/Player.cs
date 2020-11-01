namespace pacman
{
    public class Player : Entity
    {
        public Player(Node nodeFrom, Direction direction, double distance = 0) : base(nodeFrom, direction, distance)
        {

        }

        public override void LoadTextures()
        {
            currentSprite = "move";
            AddSprites("move", @"..\..\..\res\players\pac-man\move.png", 3);
        }

        public override void AfterMove()
        {
            (int x, int y) = Game.players[0].GetIntXY();
            Tile tile = Game.map.tiles[x, y];

            switch (tile.type)
            {
                case TileType.Pellet:
                    tile.type = TileType.None;
                    break;
                case TileType.SuperPellet:
                    tile.type = TileType.None;
                    break;
            }
        }
    }
}
