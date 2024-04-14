package chess.logic.player;

import chess.logic.board.Board;
import chess.logic.board.Move;

/**
 * Represents a change in the game state caused by a move.
 */
public class Changer {
    private final Board changedBoard;
    private final Move move;
    private final MoveSt moveSt;

    /**
     * Initializes a changer with the given changed board, move, and move state.
     *
     * @param changedBoard The board after the move has been applied.
     * @param move         The move that caused the change.
     * @param moveSt       The state of the move (e.g., NORMAL, CASTLE, PROMOTION).
     */
    public Changer(final Board changedBoard, final Move move, final MoveSt moveSt) {
        this.changedBoard = changedBoard;
        this.move = move;
        this.moveSt = moveSt;
    }

    /**
     * Retrieves the state of the move.
     *
     * @return The state of the move.
     */
    public MoveSt getMoveSt() {
        return this.moveSt;
    }

    /**
     * Retrieves the changed board.
     *
     * @return The board after the move has been applied.
     */
    public Board getChangedBoard() {
        return this.changedBoard;
    }

    /**
     * Retrieves the move that caused the change.
     *
     * @return The move that caused the change.
     */
    public Move getMove() {
        return this.move;
    }
}
