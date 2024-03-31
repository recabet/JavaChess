package chess.player;

import chess.board.Board;
import chess.board.Move;
public class Changer
{
    private final Board changedBoard;
    private final Move move;
    private final MoveSt moveSt;
    public Changer(final Board changedBoard,final Move move,final MoveSt moveSt)
    {
        this.changedBoard=changedBoard;
        this.move=move;
        this.moveSt=moveSt;
    }
    public MoveSt getMoveSt()
    {
        return this.moveSt;
    }

}
