package chess.logic.pieces;

import chess.Color;
import chess.logic.board.Board;
import chess.logic.board.BoardData;
import chess.logic.board.Move;
import chess.logic.board.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a queen chess piece.
 */
public class Queen extends Piece {
    private final static int[] PRESET_VECTOR_COORDS = {-9, -8, -7, -1, 1, 7, 8, 9};

    /**
     * Initializes a queen piece with the specified coordinate and color.
     *
     * @param pieceCoord The coordinate of the queen on the board.
     * @param pieceColor The color of the queen (BLACK or WHITE).
     */
    public Queen(final int pieceCoord, final Color pieceColor) {
        super(PieceType.QUEEN, pieceCoord, pieceColor, true);
    }

    /**
     * Initializes a queen piece with the specified coordinate, color, and initial move status.
     *
     * @param pieceCoord The coordinate of the queen on the board.
     * @param pieceColor The color of the queen (BLACK or WHITE).
     * @param isFirstMove True if it's the queen's first move, false otherwise.
     */
    public Queen(final int pieceCoord, final Color pieceColor, final boolean isFirstMove) {
        super(PieceType.QUEEN, pieceCoord, pieceColor, isFirstMove);
    }

    /**
     * Retrieves the legal moves available for the queen on the given board.
     *
     * @param board The board on which the queen is located.
     * @return A list of legal moves for the queen.
     */
    @Override
    public List<Move> getLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentOffset : PRESET_VECTOR_COORDS) {
            int possibleDestinationCoord = this.pieceCoord;
            while (BoardData.isValidSquareCoord(possibleDestinationCoord)) {
                if (isFirstCol(possibleDestinationCoord, currentOffset) || isEighthCol(possibleDestinationCoord, currentOffset)) {
                    break;
                }
                possibleDestinationCoord += currentOffset;
                if (BoardData.isValidSquareCoord(possibleDestinationCoord)) {
                    final Square possibleDestinationSquare = board.getSquare(possibleDestinationCoord);
                    if (!possibleDestinationSquare.isOccupied()) {
                        legalMoves.add(new Move.MajorPieceRegularMove(board, this, possibleDestinationCoord));
                    } else {
                        final Piece pieceAtDestination = possibleDestinationSquare.getPiece();
                        final Color pieceColor = pieceAtDestination.getPieceColor();
                        if (this.pieceColor != pieceColor) {
                            legalMoves.add(new Move.MajorPieceAttackMove(board, this, pieceAtDestination, possibleDestinationCoord));
                        }
                        break;
                    }
                }
            }
        }

        return legalMoves;
    }

    /**
     * Checks if the current coordinate is in the first column of the board.
     *
     * @param currentCoord The current coordinate.
     * @param offset       The offset value.
     * @return True if the current coordinate is in the first column, false otherwise.
     */
    private static boolean isFirstCol(final int currentCoord, final int offset) {
        return BoardData.FIRST_COL[currentCoord] && ((offset == -9) || (offset == 7) || (offset == -1));
    }

    /**
     * Checks if the current coordinate is in the eighth column of the board.
     *
     * @param currentCoord The current coordinate.
     * @param offset       The offset value.
     * @return True if the current coordinate is in the eighth column, false otherwise.
     */
    private static boolean isEighthCol(final int currentCoord, final int offset) {
        return BoardData.EIGHTH_COL[currentCoord] && ((offset == 9) || (offset == -7) || (offset == 1));
    }

    /**
     * Moves the queen piece to the destination coordinate.
     *
     * @param move The move to be executed.
     * @return The queen piece after the move.
     */
    @Override
    public Piece movePiece(final Move move) {
        return new Queen(move.getDestinationCoord(), move.getMovedPiece().getPieceColor());
    }

    /**
     * Converts the queen piece to its string representation.
     *
     * @return The string representation of the queen piece.
     */
    @Override
    public String toString() {
        return PieceType.QUEEN.toString();
    }
}
