package chess;

import chess.logic.board.BoardData;
import chess.logic.player.BlackPlayer;
import chess.logic.player.Player;
import chess.logic.player.WhitePlayer;

public enum Color {
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public int getOppositeDirection() {
            return 1;
        }

        @Override
        public boolean isPawnPromotionSquare(int squareId) {
            return BoardData.EIGHTH_RANK[squareId];
        }

        @Override
        public Player selectPlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return whitePlayer;
        }
    },
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public int getOppositeDirection() {
            return -1;
        }

        @Override
        public boolean isPawnPromotionSquare(int squareId) {
            return BoardData.FIRST_RANK[squareId];
        }

        @Override
        public Player selectPlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return blackPlayer;
        }
    };

    /**
     * Gets the direction of movement for this color.
     *
     * @return The direction of movement (-1 for white, 1 for black).
     */
    public abstract int getDirection();

    /**
     * Checks if the color is white.
     *
     * @return True if the color is white, false otherwise.
     */
    public abstract boolean isWhite();

    /**
     * Checks if the color is black.
     *
     * @return True if the color is black, false otherwise.
     */
    public abstract boolean isBlack();

    /**
     * Gets the opposite direction of movement for this color.
     *
     * @return The opposite direction of movement (1 for white, -1 for black).
     */
    public abstract int getOppositeDirection();

    /**
     * Checks if a given square is a pawn promotion square for this color.
     *
     * @param squareId The ID of the square to check.
     * @return True if the square is a pawn promotion square, false otherwise.
     */
    public abstract boolean isPawnPromotionSquare(int squareId);

    /**
     * Selects the player associated with this color.
     *
     * @param whitePlayer The white player.
     * @param blackPlayer The black player.
     * @return The selected player (white player for WHITE, black player for BLACK).
     */
    public abstract Player selectPlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
}
