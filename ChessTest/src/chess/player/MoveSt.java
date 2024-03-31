package chess.player;

public enum MoveSt
{
    DONE
            {
                @Override
                boolean isDone()
                {
                    return true;
                }
            },
    ILLEGAL_MOVE
            {
                @Override
                boolean isDone()
                {
                    return false;
                }
            },
    IN_CHECK
            {
                @Override
                boolean isDone()
                {
                    return false;
                }
            };

    abstract boolean isDone();
}
