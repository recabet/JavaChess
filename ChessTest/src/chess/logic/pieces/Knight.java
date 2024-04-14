package chess.logic.pieces;

import chess.Color;
import chess.logic.board.Board;
import chess.logic.board.BoardData;
import chess.logic.board.Move;
import chess.logic.board.Square;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents a Knight piece in the chess game.
 * Extends the abstract Piece class.
 */
public class Knight extends Piece
{
    private final static int[] PRESET_OFFSET = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final int pieceCoord, final Color pieceColor) { super(PieceType.KNIGHT,pieceCoord, pieceColor, true); }
    public Knight(final int pieceCoord, final Color pieceColor, final boolean isFirstMove) { super(PieceType.KNIGHT,pieceCoord, pieceColor, isFirstMove); }
    /**
     * Generates a list of legal moves for the knight piece on the board.
     *
     * @param board The current board state.
     * @return A list of legal moves for the knight.
     */
    @Override
    public List<Move> getLegalMoves(final Board board) {
        // Initialize variables
        int possibleDestinationCoord;
        final List<Move> legalMoves = new ArrayList<>();

        // Iterate through preset offset positions
        for (final int currentOffset : PRESET_OFFSET) {
            possibleDestinationCoord = this.pieceCoord + currentOffset;

            // Check if the possible destination coordinate is valid
            if (BoardData.isValidSquareCoord(possibleDestinationCoord)) {

                // Check if the knight is positioned on the edge columns
                if (isFirstCol(this.pieceCoord, currentOffset) || isSecondCol(this.pieceCoord, currentOffset) ||
                        isSeventhCol(this.pieceCoord, currentOffset) || isEighthCol(this.pieceCoord, currentOffset)) {
                    continue; // Skip if on edge columns
                }

                // Get the square at the possible destination coordinate
                final Square possibleDestinationSquare = board.getSquare(possibleDestinationCoord);

                // Check if the destination square is unoccupied
                if (!possibleDestinationSquare.isOccupied()) {
                    // Add regular move to legal moves list
                    legalMoves.add(new Move.MajorPieceRegularMove(board, this, possibleDestinationCoord));
                } else {
                    // Get the piece at the destination square
                    final Piece pieceAtDestination = possibleDestinationSquare.getPiece();
                    final Color pieceColor = pieceAtDestination.getPieceColor();

                    // Check if the piece at the destination square is an opponent's piece
                    if (this.pieceColor != pieceColor) {
                        // Add attack move to legal moves list
                        legalMoves.add(new Move.AttackMove(board, this, pieceAtDestination, possibleDestinationCoord));
                    }
                }
            }
        }
        return legalMoves;
    }

    /**
     * Checks if the current coordinate is in the first column of the board and if the offset
     * corresponds to valid knight moves in the first column.
     *
     * @param currentCoord The current coordinate of the knight.
     * @param offset       The offset representing the move direction.
     * @return True if the knight is in the first column and the offset corresponds to valid moves, otherwise false.
     */
    private static boolean isFirstCol(final int currentCoord, final int offset) {
        return BoardData.FIRST_COL[currentCoord] && ((offset == -17) || (offset == -10) || (offset == 6) || (offset == 15));
    }

    /**
     * Checks if the current coordinate is in the second column of the board and if the offset
     * corresponds to valid knight moves in the second column.
     *
     * @param currentCoord The current coordinate of the knight.
     * @param offset       The offset representing the move direction.
     * @return True if the knight is in the second column and the offset corresponds to valid moves, otherwise false.
     */
    private static boolean isSecondCol(final int currentCoord, final int offset) {
        return BoardData.SECOND_COL[currentCoord] && ((offset == -10) || (offset == 6));
    }

    /**
     * Checks if the current coordinate is in the seventh column of the board and if the offset
     * corresponds to valid knight moves in the seventh column.
     *
     * @param currentCoord The current coordinate of the knight.
     * @param offset       The offset representing the move direction.
     * @return True if the knight is in the seventh column and the offset corresponds to valid moves, otherwise false.
     */
    private static boolean isSeventhCol(final int currentCoord, final int offset) {
        return BoardData.SEVENTH_COL[currentCoord] && ((offset == -6) || (offset == 10));
    }

    /**
     * Checks if the current coordinate is in the eighth column of the board and if the offset
     * corresponds to valid knight moves in the eighth column.
     *
     * @param currentCoord The current coordinate of the knight.
     * @param offset       The offset representing the move direction.
     * @return True if the knight is in the eighth column and the offset corresponds to valid moves, otherwise false.
     */
    private static boolean isEighthCol(final int currentCoord, final int offset) {
        return BoardData.EIGHTH_COL[currentCoord] && ((offset == -15) || (offset == -6) || (offset == 10) || (offset == 17));
    }

    /**
     * Creates a new knight piece after it has made a move.
     *
     * @param move The move made by the knight.
     * @return A new knight piece with updated position.
     */
    @Override
    public Piece movePiece(final Move move) {
        return new Knight(move.getDestinationCoord(), move.getMovedPiece().getPieceColor());
    }

    /**
     * Returns the string representation of the knight piece.
     *
     * @return The string representation of the knight piece.
     */
    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }

}
