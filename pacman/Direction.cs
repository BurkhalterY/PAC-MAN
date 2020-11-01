namespace pacman
{
    public enum Direction
    {
        None,
        Up,
        Left,
        Down,
        Right
    }

    public static class DirectionHelper
    {
        public static Direction Opposite(Direction direction)
        {
            switch (direction)
            {
                case Direction.Left:
                    return Direction.Right;
                case Direction.Right:
                    return Direction.Left;
                case Direction.Up:
                    return Direction.Down;
                case Direction.Down:
                    return Direction.Up;
                default:
                    return Direction.None;
            }
        }

        public static bool IsOpposite(Direction a, Direction b)
        {
            return a == Direction.Left && b == Direction.Right
                || a == Direction.Right && b == Direction.Left
                || a == Direction.Up && b == Direction.Down
                || a == Direction.Down && b == Direction.Up;
        }

        public static Direction FromString(string direction)
        {
            switch (direction)
            {
                case "left":
                    return Direction.Left;
                case "right":
                    return Direction.Right;
                case "up":
                    return Direction.Up;
                case "down":
                    return Direction.Down;
                default:
                    return Direction.None;
            }
        }
    }
}
