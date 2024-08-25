package chess.logic.pieces;

import chess.Color;
import chess.logic.board.Board;
import chess.logic.board.BoardData;
import chess.logic.board.Move;
import chess.logic.board.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Bishop piece in the chess game.
 * Extends the abstract Piece class.
 */
public class Bishop extends Piece {
    /**
     * Predefined movement vectors for the bishop.
     */
    private final static int[] PRESET_VECTOR_COORDS = {-9, -7, 7, 9};

    /**
     * Constructs a Bishop object with the specified piece coordinate and color.
     *
     * @param pieceCoord The coordinate of the bishop on the board.
     * @param pieceColor The color of the bishop (BLACK or WHITE).
     */
    public Bishop(final int pieceCoord, final Color pieceColor)
    {
        super(PieceType.BISHOP, pieceCoord, pieceColor, true);
    }

    /**
     * Gets the legal moves for the bishop on the given board.
     *
     * @param board The current state of the chess board.
     * @return A list of legal moves for the bishop.
     */
    @Override
    public List<Move> getLegalMoves(final Board board)
    {
        final List<Move> legalMoves = new ArrayList<>();

        // Iterate through predefined movement vectors for the bishop
        for(final int currentOffset : PRESET_VECTOR_COORDS)
        {
            int possibleDestinationCoord = this.pieceCoord;

            // Loop to calculate possible destination coordinates
            while(BoardData.isValidSquareCoord(possibleDestinationCoord))
            {
                // Check if the bishop is blocked by piece or out of bounds
                if(isFirstCol(possibleDestinationCoord, currentOffset) || isEighthCol(possibleDestinationCoord, currentOffset))
                {
                    break;
                }

                possibleDestinationCoord += currentOffset;

                // Check if the possible destination is within the board
                if(BoardData.isValidSquareCoord(possibleDestinationCoord))
                {
                    final Square possibleDestinationSquare = board.getSquare(possibleDestinationCoord);

                    // If the destination square is unoccupied, add a regular move
                    if(!possibleDestinationSquare.isOccupied())
                    {
                        legalMoves.add(new Move.MajorPieceRegularMove(board, this, possibleDestinationCoord));
                    } else
                    {
                        // If the destination square is occupied, add an attack move if it's an enemy piece
                        final Piece pieceAtDestination = possibleDestinationSquare.getPiece();
                        final Color pieceColor = pieceAtDestination.getPieceColor();

                        if(this.pieceColor != pieceColor)
                        {
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
     * Creates a new bishop piece after it has made a move.
     *
     * @param move The move made by the bishop.
     * @return A new bishop piece with updated position.
     */
    @Override
    public Piece movePiece(final Move move)
    {
        return new Bishop(move.getDestinationCoord(), move.getMovedPiece().getPieceColor());
    }

    /**
     * Checks if the current coordinate is in the first column of the board and if the offset
     * corresponds to valid bishop moves in the first column.
     *
     * @param currentCoord The current coordinate of the bishop.
     * @param offset       The offset representing the move direction.
     * @return True if the bishop is in the first column and the offset corresponds to valid moves, otherwise false.
     */
    private static boolean isFirstCol(final int currentCoord, final int offset)
    {
        return BoardData.FIRST_COL[currentCoord] && ((offset == -9) || (offset == 7));
    }

    /**
     * Checks if the current coordinate is in the eighth column of the board and if the offset
     * corresponds to valid bishop moves in the eighth column.
     *
     * @param currentCoord The current coordinate of the bishop.
     * @param offset       The offset representing the move direction.
     * @return True if the bishop is in the eighth column and the offset corresponds to valid moves, otherwise false.
     */
    private static boolean isEighthCol(final int currentCoord, final int offset)
    {
        return BoardData.EIGHTH_COL[currentCoord] && ((offset == 9) || (offset == -7));
    }

    /**
     * Returns the string representation of the bishop piece.
     *
     * @return The string representation of the bishop piece.
     */
    @Override
    public String toString()
    {
        return PieceType.BISHOP.toString();
    }

}
