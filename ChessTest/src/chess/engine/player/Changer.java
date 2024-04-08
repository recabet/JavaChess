package chess.engine.player;

import chess.engine.board.Board;
import chess.engine.board.Move;
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

    public Board getChangedBoard() {
        return this.changedBoard;
    }
}
