namespace pacman
{
    public class MsPacman : Player
    {
        public MsPacman(Node nodeFrom, Direction direction, double distance = 0) : base(nodeFrom, direction, distance)
        {
            
        }

        protected override void Init()
        {
            name = "players/mspacman";
        }
    }
}
