namespace pacman
{
    public enum Direction
    {
        None,
        Up,
        Right,
        Down,
        Left
    }

    public static class DirectionHelper
    {
        public static bool isOpposite(Direction a, Direction b)
        {
            return a == Direction.Left && b == Direction.Right
                || a == Direction.Right && b == Direction.Left
                || a == Direction.Up && b == Direction.Down
                || a == Direction.Down && b == Direction.Up;
        }
    }
}
