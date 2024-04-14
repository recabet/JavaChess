package chess.logic.player;

import chess.Color;
import chess.logic.pieces.*;
import chess.logic.board.Board;
import chess.logic.board.Move;

import java.util.Collection;

/**
 * Represents a white player in a chess game.
 */
public class WhitePlayer extends Player {

    /**
     * Constructs a WhitePlayer object.
     *
     * @param board                 The chess board.
     * @param legalWhiteStdLegalMoves    Collection of legal moves for the white player.
     * @param legalBlackStdLegalMoves    Collection of legal moves for the black player.
     */
    public WhitePlayer(Board board, Collection<Move> legalWhiteStdLegalMoves, Collection<Move> legalBlackStdLegalMoves) {
        super(board, legalWhiteStdLegalMoves, legalBlackStdLegalMoves);
    }

    /**
     * Gets the active pieces for the white player.
     *
     * @return Collection of active pieces for the white player.
     */
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    /**
     * Gets the color of the player.
     *
     * @return The color of the player.
     */
    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    /**
     * Gets the enemy player (black player).
     *
     * @return The enemy player (black player).
     */
    @Override
    public Player getEnemy() {
        return this.board.blackPlayer();
    }
}
