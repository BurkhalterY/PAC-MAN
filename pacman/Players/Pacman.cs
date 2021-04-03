namespace pacman
{
    public class Pacman : Player
    {
        public Pacman(Node nodeFrom, Direction direction, double distance = 0) : base(nodeFrom, direction, distance)
        {
            
        }

        protected override void Init()
        {
            name = "players/pacman";
        }
    }
}
