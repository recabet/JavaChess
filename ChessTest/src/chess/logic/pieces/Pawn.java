package chess.logic.pieces;

import chess.Color;
import chess.logic.board.Board;
import chess.logic.board.BoardData;
import chess.logic.board.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a pawn piece in the chess game.
 */
public class Pawn extends Piece {

    // Offsets representing possible moves for the pawn
    private final static int[] PRESET_OFFSET = {7, 8, 9, 16};

    /**
     * Creates a new Pawn object with the given coordinates and color.
     *
     * @param pieceCoord The coordinate of the pawn.
     * @param pieceColor The color of the pawn.
     */
    public Pawn(final int pieceCoord, final Color pieceColor) {
        super(PieceType.PAWN, pieceCoord, pieceColor, true);
    }

    /**
     * Creates a new Pawn object with the given coordinates, color, and move status.
     *
     * @param pieceCoord  The coordinate of the pawn.
     * @param pieceColor  The color of the pawn.
     * @param isFirstMove True if it's the pawn's first move, false otherwise.
     */
    public Pawn(final int pieceCoord, final Color pieceColor, final boolean isFirstMove) {
        super(PieceType.PAWN, pieceCoord, pieceColor, isFirstMove);
    }

    /**
     * Determines the legal moves for the pawn piece on the given board.
     * <p>
     * This method overrides the {@link Piece#getLegalMoves(Board)} method from the superclass {@link Piece}.
     * The logic of this method is specific to the behavior of a pawn piece.
     * </p>
     *
     * @param board The board on which the pawn is located.
     * @return A list of legal moves for the pawn.
     * @inheritDoc
     */
    @Override
    public List<Move> getLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        // Iterate over preset offsets to determine possible moves
        for (final int currentOffset : PRESET_OFFSET) {
            int possibleDestinationCoord = this.pieceCoord + (this.pieceColor.getDirection() * currentOffset);

            // Check if the possible destination coordinate is valid
            if (!BoardData.isValidSquareCoord(possibleDestinationCoord)) {
                continue;
            }

            // Regular pawn move
            if (currentOffset == 8 && !board.getSquare(possibleDestinationCoord).isOccupied()) {
                // If pawn reaches the promotion square, add promotion move
                if (this.pieceColor.isPawnPromotionSquare(possibleDestinationCoord)) {
                    legalMoves.add(new Move.PawnPromotion(new Move.MinorPieceRegularMove(board, this, possibleDestinationCoord),
                            PieceData.INSTANCE.getMovedQueen(this.pieceColor, possibleDestinationCoord)));
                } else {
                    legalMoves.add(new Move.MinorPieceRegularMove(board, this, possibleDestinationCoord));
                }
            }
            // Pawn initial two-square move
            else if (currentOffset == 16 && this.isFirstMove() &&
                    ((BoardData.SEVENTH_RANK[this.pieceCoord] && this.pieceColor == Color.BLACK) ||
                            (BoardData.SECOND_RANK[this.pieceCoord] && this.pieceColor == Color.WHITE))) {
                final int behindPossibleDestinationCoord = this.pieceCoord + (this.pieceColor.getDirection() * 8);
                if (!board.getSquare(possibleDestinationCoord).isOccupied() &&
                        !board.getSquare(behindPossibleDestinationCoord).isOccupied()) {
                    legalMoves.add(new Move.PawnJump(board, this, possibleDestinationCoord));
                }
            }
            // Pawn capture moves
            else if ((currentOffset == 7 || currentOffset == 9) &&
                    !((BoardData.EIGHTH_COL[this.pieceCoord] && this.pieceColor == Color.WHITE) ||
                            (BoardData.FIRST_COL[this.pieceCoord] && this.pieceColor == Color.BLACK))) {
                if (board.getSquare(possibleDestinationCoord).isOccupied()) {
                    final Piece pieceAtDestination = board.getSquare(possibleDestinationCoord).getPiece();
                    if (this.pieceColor != pieceAtDestination.pieceColor) {
                        if (this.pieceColor.isPawnPromotionSquare(possibleDestinationCoord)) {
                            legalMoves.add(new Move.PawnPromotion(new Move.MinorPieceRegularMove(board, this, possibleDestinationCoord),
                                    PieceData.INSTANCE.getMovedQueen(this.pieceColor, possibleDestinationCoord)));
                        } else {
                            legalMoves.add(new Move.MinorPieceAttackMove(board, this, pieceAtDestination, possibleDestinationCoord));
                        }
                    }
                }
            }
        }
        return legalMoves;
    }

    /**
     * Creates a new pawn piece after performing a move.
     *
     * @param move The move to be performed.
     * @return A new Pawn object after the move.
     */
    @Override
    public Piece movePiece(final Move move) {
        return PieceData.INSTANCE.getMovedPawn(move.getMovedPiece().getPieceColor(), move.getDestinationCoord());
    }

    /**
     * Returns a string representation of the pawn piece.
     *
     * @return The string representation of the pawn piece.
     */
    @Override
    public String toString() {
        return this.pieceType.toString();
    }
}
