namespace pacman
{
    public class JrPacman : Player
    {
        public JrPacman(Node nodeFrom, Direction direction, double distance = 0) : base(nodeFrom, direction, distance)
        {

        }

        protected override void Init()
        {
            name = "players/jrpacman";
        }
    }
}
