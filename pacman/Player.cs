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
    }
}
