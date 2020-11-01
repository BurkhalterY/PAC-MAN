namespace pacman
{
    public class PacmanJr : Player
    {
        public PacmanJr(Node nodeFrom, Direction direction, double distance = 0) : base(nodeFrom, direction, distance)
        {

        }

        protected override void Init()
        {
            name = "players/jrpacman";
        }
    }
}
