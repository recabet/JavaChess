package chess.logic.player;

import chess.Color;
import chess.logic.pieces.*;
import chess.logic.board.Board;
import chess.logic.board.Move;

import java.util.Collection;

/**
 * Represents a player with black pieces in a chess game.
 */
public class BlackPlayer extends Player {
    /**
     * Initializes a black player with the given board and legal moves.
     *
     * @param board                The board associated with the player.
     * @param legalBlackStdLegalMoves  Legal moves available for black pieces.
     * @param legalWhiteStdLegalMoves  Legal moves available for white pieces.
     */
    public BlackPlayer(Board board, Collection<Move> legalBlackStdLegalMoves, Collection<Move> legalWhiteStdLegalMoves) {
        super(board, legalBlackStdLegalMoves, legalWhiteStdLegalMoves);
    }

    /**
     * Retrieves the active black pieces controlled by this player.
     *
     * @return Collection of active black pieces.
     */
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    /**
     * Gets the color associated with this player.
     *
     * @return The color of the player (BLACK).
     */
    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    /**
     * Retrieves the opponent player.
     *
     * @return The player controlling the white pieces.
     */
    @Override
    public Player getEnemy() {
        return this.board.whitePlayer();
    }
}
