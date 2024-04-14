package chess.logic.board;

import chess.logic.pieces.*;
import chess.logic.pieces.Piece;


import java.util.Objects;
/**
 * Represents a move in the chess game.
 */

public abstract class Move {
    public static AttackMove.MoveCreator MoveCreator;
    /** The board on which the move is made. */
    final Board board;

    /** The piece being moved. */
    final Piece movedPiece;

    /** The destination coordinate of the move. */
    final int destinationCoord;

    /** Flag indicating if it's the first move of the piece. */
    final boolean isFirstMove;

    /** An invalid move object. */
    public static final Move INVALID_MOVE = new AttackMove.InvalidMove();

    /**
     * Constructs a move object with the specified board, piece, and destination coordinate.
     *
     * @param board The board on which the move is made.
     * @param movedPiece The piece being moved.
     * @param destinationCoord The destination coordinate of the move.
     */
    private Move(final Board board, final Piece movedPiece, final int destinationCoord) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoord = destinationCoord;
        this.isFirstMove = movedPiece.isFirstMove();
    }
    /**
     * Constructs a move object with the specified board and destination coordinate.
     *
     * @param board The board on which the move is made.
     * @param destinationCoord The destination coordinate of the move.
     */
    private Move(final Board board, final int destinationCoord) {
        this.board = board;
        this.destinationCoord = destinationCoord;
        this.movedPiece = null;
        this.isFirstMove = false;
    }
    /**
     * Checks if the move is an attack move.
     *
     * @return True if the move is an attack move, false otherwise.
     */
    public boolean isAttack() {
        return false;
    }
    /**
     * Gets the destination coordinate of the move.
     *
     * @return The destination coordinate.
     */
    public int getDestinationCoord() {
        return destinationCoord;
    }
    /**
     * Gets the piece being attacked, if any.
     *
     * @return The attacked piece, or null if no piece is being attacked.
     */
    public Piece getAttackedPiece() {
        return null;
    }
    /**
     * Checks if the move is a castle move.
     *
     * @return True if it is a castle move, false otherwise.
     */
    public boolean isCastle() {
        return false;
    }
    /**
     * Gets the piece that is being moved.
     *
     * @return The piece being moved.
     */
    public Piece getMovedPiece() {
        return this.movedPiece;
    }
    /**
     * Gets the color of the piece making the move.
     *
     * @return The color of the piece, or null if not applicable.
     */
    public Piece getColor() {
        return null;
    }
    /**
     * Gets the board on which the move is made.
     *
     * @return The board.
     */
    public Board getBoard() {
        return this.board;
    }
    /**
     * Gets the current coordinate of the moved piece.
     *
     * @return The current coordinate.
     */
    public int getCurrentCoord() {
        return this.movedPiece.getPieceCoord();
    }
    /**
     * Computes a hash code for the move.
     * The hash code is computed using the destination coordinate,
     * the hash code of the moved piece, the current coordinate of the moved piece,
     * and whether it is the first move or not.
     *
     * @return The hash code value for the move.
     */
    @Override
    public int hashCode() {
        int res = 1;
        res = 31 * res + this.destinationCoord;
        res = 31 * res + this.movedPiece.hashCode();
        res = 31 * res + this.movedPiece.getPieceCoord();
        res = res + (isFirstMove ? 1 : 0);
        return res;
    }
    /**
     * Indicates whether some other object is "equal to" this one.
     * Two moves are considered equal if they have the same current coordinate,
     * destination coordinate, and moved piece.
     *
     * @param object The reference object with which to compare.
     * @return True if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Move)) {
            return false;
        }
        final Move otherMove = (Move) object;
        return  this.getCurrentCoord() == otherMove.getCurrentCoord() &&
                this.destinationCoord == otherMove.destinationCoord
                && this.movedPiece.equals(otherMove.movedPiece);
    }
    /**
     * Applies the move to the board and returns the resulting board.
     * This method creates a new board based on the current board state
     * and the move being made, updating the pieces accordingly.
     *
     * @return The board after applying the move.
     */
    public Board make() {
        final Board.Builder builder = new Board.Builder();
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for (final Piece piece : this.board.getCurrentPlayer().getEnemy().getActivePieces()) {
            builder.setPiece(piece);
        }
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.getCurrentPlayer().getEnemy().getColor());
        builder.setTransitionMove(this);
        return builder.build();
    }

    /**
     * Represents a promotion move, which is a type of move where a pawn is promoted to another piece upon reaching the last rank.
     * It utilizes the Decorator design pattern.
     */

    public static final class PawnPromotion extends Move {
        //Decorator Design pattern
        final Move decoratedMove;
        final Pawn promotedPawn;
        final Piece promotionPiece;
        /**
         * Constructs a PawnPromotion object with the specified decorated move and promotion piece.
         *
         * @param decoratedMove The decorated move.
         * @param promotionPiece The piece to which the pawn is promoted.
         */
        public PawnPromotion(Move decoratedMove, final Piece promotionPiece) {
            super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getDestinationCoord());
            this.decoratedMove = decoratedMove;
            this.promotedPawn = (Pawn) decoratedMove.getMovedPiece();
            this.promotionPiece = promotionPiece;
        }
        @Override
        public boolean equals(Object other) {
            //return this==other || other instanceof PawnPromotion && (super.equals(other));
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;
            if (!super.equals(other)) return false;
            final PawnPromotion that = (PawnPromotion) other;
            return Objects.equals(decoratedMove, that.decoratedMove) &&  Objects.equals(promotedPawn, that.promotedPawn) && Objects.equals(promotionPiece, that.promotionPiece);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), decoratedMove, promotedPawn, promotionPiece);
            //return 0;
        }

        @Override
        public Board make() {
            final Board pawnMovedBoard = this.decoratedMove.make();
            final Board.Builder builder = new Board.Builder();
            for (final Piece piece : pawnMovedBoard.getCurrentPlayer().getActivePieces()) {
                if (!(this.promotedPawn.equals(piece)))
                {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece : pawnMovedBoard.getCurrentPlayer().getEnemy().getActivePieces()) {
                builder.setPiece(piece);
            }
            builder.setPiece(this.promotionPiece.movePiece(this));
            builder.setMoveMaker(pawnMovedBoard.getCurrentPlayer().getColor());
            builder.setTransitionMove(this);
            return builder.build();
        }

        @Override
        public boolean isAttack() {
            return this.decoratedMove.isAttack();
        }

        @Override
        public Piece getAttackedPiece() {
            return this.decoratedMove.getAttackedPiece();
        }

        @Override
        public String toString() {
            return BoardData.getPositionAtCoord(this.movedPiece.getPieceCoord()) + "-" +
                    BoardData.getPositionAtCoord(this.destinationCoord) + "=" + this.promotionPiece.getPieceType();
        }

    }
    /**
     * Represents a regular move for a major piece (rook, bishop, queen, or king).
     */
    public static final class MajorPieceRegularMove extends Move {
        /**
         * Constructs a MajorPieceRegularMove object with the specified board, moved piece, and destination coordinate.
         *
         * @param board The board on which the move is made.
         * @param movedPiece The piece being moved.
         * @param destinationCoord The destination coordinate of the move.
         */
        public MajorPieceRegularMove(Board board, Piece movedPiece, int destinationCoord) {
            super(board, movedPiece, destinationCoord);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MajorPieceRegularMove && super.equals(other);
        }

        @Override
        public String toString() {
            return movedPiece.getPieceType().toString() + BoardData.getPositionAtCoord(this.destinationCoord);
        }

    }

    /**
     * Represents an attack move made by a major piece (Queen, Rook, or Bishop) on a chess board.
     */
    public static class MajorPieceAttackMove extends AttackMove {

        /**
         * Constructs a new MajorPieceAttackMove object with the given parameters.
         *
         * @param board            The chess board on which the move is made.
         * @param movedPiece       The major piece making the attack move.
         * @param attackedPiece    The piece being attacked.
         * @param destinationCoord The destination coordinate of the attack move.
         */
        public MajorPieceAttackMove(Board board, Piece movedPiece, Piece attackedPiece, int destinationCoord) {
            super(board, movedPiece, attackedPiece, destinationCoord);
        }

        /**
         * Compares this MajorPieceAttackMove with another object for equality.
         *
         * @param other The object to compare with this MajorPieceAttackMove.
         * @return {@code true} if the two objects are the same (reference equality),
         *         or if {@code other} is an instance of MajorPieceAttackMove and
         *         the superclass's equals method returns {@code true}; {@code false} otherwise.
         */
        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MajorPieceAttackMove && super.equals(other);
        }

        /**
         * Returns a string representation of this MajorPieceAttackMove.
         * The string contains the type of the moved piece followed by the position of the destination coordinate.
         *
         * @return A string representation of this MajorPieceAttackMove.
         */
        @Override
        public String toString() {
            return movedPiece.getPieceType() + BoardData.getPositionAtCoord(this.destinationCoord);
        }

    }

    /**
     * Represents a regular move made by a minor piece (pawn).
     */
    public static class MinorPieceRegularMove extends Move {
        /**
         * Constructs a MinorPieceRegularMove object with the specified parameters.
         *
         * @param board The board on which the move is made.
         * @param movedPiece The minor piece making the move.
         * @param destinationCoord The destination coordinate of the move.
         */
        public MinorPieceRegularMove(Board board, Piece movedPiece, int destinationCoord) {
            super(board, movedPiece, destinationCoord);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MinorPieceRegularMove && super.equals(other);
        }

        @Override
        public String toString() {
            return BoardData.getPositionAtCoord(this.destinationCoord);
        }
    }

    /**
     * Represents a move made by a minor piece (pawn) to attack an opponent's piece.
     */
    public static class MinorPieceAttackMove extends AttackMove {
        /**
         * Constructs a MinorPieceAttackMove object with the specified parameters.
         *
         * @param board The board on which the move is made.
         * @param movedPiece The minor piece making the move.
         * @param attackedPiece The piece being attacked.
         * @param destinationCoord The destination coordinate of the move.
         */
        public MinorPieceAttackMove(Board board, Piece movedPiece, Piece attackedPiece, int destinationCoord) {
            super(board, movedPiece, attackedPiece, destinationCoord);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MinorPieceAttackMove && super.equals(other);
        }

        @Override
        public String toString() {
            return BoardData.getPositionAtCoord(this.movedPiece.getPieceCoord()).substring(0, 1) + "x" + BoardData.getPositionAtCoord(this.destinationCoord);
        }
    }

    /**
     * Represents a pawn's jump move, typically occurring when a pawn moves two squares forward from its starting position.
     */
    public static final class PawnJump extends Move {
        /**
         * Constructs a PawnJump object with the specified parameters.
         *
         * @param board The board on which the move is made.
         * @param movedPiece The pawn making the move.
         * @param destinationCoord The destination coordinate of the move.
         */
        public PawnJump(Board board, Piece movedPiece, int destinationCoord) {
            super(board, movedPiece, destinationCoord);
        }

        @Override
        public Board make() {
            final Board.Builder builder = new Board.Builder();
            for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
                if (!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece : this.board.getCurrentPlayer().getEnemy().getActivePieces()) {
                builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setMoveMaker(this.board.getCurrentPlayer().getEnemy().getColor());
            builder.setTransitionMove(this);
            return builder.build();
        }

        @Override
        public String toString() {
            return BoardData.getPositionAtCoord(this.destinationCoord);
        }
    }

    /**
     * Represents a move made by a piece to attack an opponent's piece on the board.
     */
    public static class AttackMove extends Move {
        /**
         * Constructs an AttackMove object with the specified parameters.
         *
         * @param board The board on which the move is made.
         * @param movedPiece The piece making the move.
         * @param attackedPiece The piece being attacked.
         * @param destinationCoord The destination coordinate of the move.
         */
        final private Piece attackedPiece;

        public AttackMove(final Board board, final Piece movedPiece, final Piece attackedPiece, final int destinationCoord) {
            super(board, movedPiece, destinationCoord);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public boolean isAttack() {
            return true;
        }

        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }

        @Override
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof AttackMove)) {
                return false;
            }
            final AttackMove otherAttack = (AttackMove) object;
            return super.equals(otherAttack) && getAttackedPiece().equals(otherAttack.getAttackedPiece());
        }

        /**
         * Represents an invalid move, typically used to indicate an illegal move.
         */

        static class InvalidMove extends Move {
            private InvalidMove() {
                super(null, -1);
            }

            @Override
            public Board make() {
                throw new RuntimeException("Can you even play?");
            }

            @Override
            public int getCurrentCoord() { return -1; }

            @Override
            public int getDestinationCoord() {
                return -1;
            }
        }
        /**
         * Creates moves based on the current board position and the coordinates of the source and destination squares.
         */

        public static class MoveCreator {
            public static Move moveCreate(final Board board, final int currentCoord, final int destinationCoord) {
                for (final Move move : board.getAllLegalMoves()) {
                    if (move.getCurrentCoord() == currentCoord && move.getDestinationCoord() == destinationCoord) {
                        return move;
                    }
                }
                return INVALID_MOVE;
            }
        }
    }
}