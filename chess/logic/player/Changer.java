package chess.logic.player;

import chess.logic.board.Board;
import chess.logic.board.Move;

/**
 * Represents a change in the game state caused by a move.
 */
public record Changer(Board changedBoard, Move move, MoveSt moveSt) {
    /**
     * Initializes a changer with the given changed board, move, and move state.
     *
     * @param changedBoard The board after the move has been applied.
     * @param move         The move that caused the change.
     * @param moveSt       The state of the move (e.g., NORMAL, CASTLE, PROMOTION).
     */
    public Changer
    {
    }

    /**
     * Retrieves the state of the move.
     *
     * @return The state of the move.
     */
    @Override
    public MoveSt moveSt()
    {
        return this.moveSt;
    }

    /**
     * Retrieves the changed board.
     *
     * @return The board after the move has been applied.
     */
    @Override
    public Board changedBoard()
    {
        return this.changedBoard;
    }

    /**
     * Retrieves the move that caused the change.
     *
     * @return The move that caused the change.
     */
    @Override
    public Move move()
    {
        return this.move;
    }
}
