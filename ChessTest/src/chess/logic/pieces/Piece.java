package chess.logic.pieces;

import chess.Color;
import chess.logic.board.Board;
import chess.logic.board.Move;

import java.util.List;
/**
 * Represents a chess piece on the game board.
 */
public abstract class Piece {

    // Piece type, coordinate, color, and first move status
    final PieceType pieceType;
    final int pieceCoord;
    final Color pieceColor;
    final boolean isFirstMove;
    final int hashCode;

    /**
     * Constructs a new Piece object with the specified parameters.
     *
     * @param pieceType   The type of the piece.
     * @param pieceCoord  The coordinate of the piece.
     * @param pieceColor  The color of the piece.
     * @param isFirstMove True if it's the piece's first move, false otherwise.
     */
    Piece(final PieceType pieceType, final int pieceCoord, final Color pieceColor, final boolean isFirstMove) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        this.pieceCoord = pieceCoord;
        this.isFirstMove = isFirstMove;
        this.hashCode = calchashCode();
    }

    // Calculate the hash code for the piece
    private int calchashCode() {
        int res = pieceType.hashCode();
        res = 31 * res + pieceColor.hashCode();
        res = 31 * res + pieceCoord;
        res = 31 * res + (isFirstMove ? 1 : 0);
        return res;
    }

    /**
     * Gets the color of the piece.
     *
     * @return The color of the piece.
     */
    public Color getPieceColor() {
        return this.pieceColor;
    }

    /**
     * Gets the coordinate of the piece.
     *
     * @return The coordinate of the piece.
     */
    public int getPieceCoord() {
        return pieceCoord;
    }

    /**
     * Checks if it's the first move of the piece.
     *
     * @return True if it's the first move, false otherwise.
     */
    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    /**
     * Gets the type of the piece.
     *
     * @return The type of the piece.
     */
    public PieceType getPieceType() {
        return this.pieceType;
    }

    /**
     * Gets the value of the piece.
     *
     * @return The value of the piece.
     */
    public int getPieceValue() {
        return this.pieceType.getPieceValue();
    }

    /**
     * Represents the type of the piece.
     */
    public enum PieceType {

        KING(10000, "K") {
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        QUEEN(900, "Q") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK(500, "R") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        BISHOP(300, "B") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT(300, "N") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        PAWN(100, "P") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        };

        private final String name;
        private int pieceValue;

        // Constructor
        PieceType(final int pieceValue, final String name) {
            this.name = name;
            this.pieceValue = pieceValue;
        }

        /**
         * Gets the string representation of the piece type.
         *
         * @return The string representation of the piece type.
         */
        @Override
        public String toString() {
            return this.name;
        }

        /**
         * Gets the value of the piece.
         *
         * @return The value of the piece.
         */
        public int getPieceValue() {
            return this.pieceValue;
        }

        /**
         * Checks if the piece type is a king.
         *
         * @return True if the piece type is a king, false otherwise.
         */
        public abstract boolean isKing();

        /**
         * Checks if the piece type is a rook.
         *
         * @return True if the piece type is a rook, false otherwise.
         */
        public abstract boolean isRook();
    }

    /**
     * Determines the legal moves for the piece on the given board.
     *
     * @param board The board on which the piece is located.
     * @return A list of legal moves for the piece.
     */
    public abstract List<Move> getLegalMoves(final Board board);

    /**
     * Moves the piece according to the given move.
     *
     * @param move The move to be performed.
     * @return The piece after the move.
     */
    public abstract Piece movePiece(Move move);

    /**
     * Checks if the current piece is equal to another object.
     *
     * @param object The object to compare.
     * @return True if the current piece is equal to the object, false otherwise.
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Piece)) {
            return false;
        }
        final Piece other = (Piece) object;
        return pieceCoord == other.getPieceCoord() && pieceType == other.getPieceType() && pieceColor == other.getPieceColor() && isFirstMove == other.isFirstMove();
    }

    /**
     * Gets the hash code of the piece.
     *
     * @return The hash code of the piece.
     */
    public int gethashCode() {
        return this.hashCode;
    }
}
