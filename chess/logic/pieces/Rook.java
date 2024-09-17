package chess.logic.pieces;

import chess.Color;
import chess.logic.board.Board;
import chess.logic.board.BoardData;
import chess.logic.board.Move;
import chess.logic.board.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rook chess piece.
 */
public class Rook extends Piece {
    private final static int[] PRESET_VECTOR_COORDS = {-8, -1, 1, 8};

    /**
     * Initializes a rook piece with the specified coordinate and color.
     *
     * @param pieceCoord The coordinate of the rook on the board.
     * @param pieceColor The color of the rook (BLACK or WHITE).
     */
    public Rook(final int pieceCoord, final Color pieceColor)
    {
        super(PieceType.ROOK, pieceCoord, pieceColor, true);
    }

    /**
     * Initializes a rook piece with the specified coordinate, color, and initial move status.
     *
     * @param pieceCoord  The coordinate of the rook on the board.
     * @param pieceColor  The color of the rook (BLACK or WHITE).
     * @param isFirstMove True if it's the rook's first move, false otherwise.
     */
    public Rook(final int pieceCoord, final Color pieceColor, final boolean isFirstMove)
    {
        super(PieceType.ROOK, pieceCoord, pieceColor, isFirstMove);
    }

    /**
     * Retrieves the legal moves available for the rook on the given board.
     *
     * @param board The board on which the rook is located.
     * @return A list of legal moves for the rook.
     */
    @Override
    public List<Move> getLegalMoves(final Board board)
    {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentOffset : PRESET_VECTOR_COORDS)
        {
            int possibleDestinationCoord = this.pieceCoord;
            while(BoardData.isValidSquareCoord(possibleDestinationCoord))
            {
                if(isFirstCol(possibleDestinationCoord, currentOffset) || isEighthCol(possibleDestinationCoord, currentOffset))
                {
                    break;
                }
                possibleDestinationCoord += currentOffset;
                if(BoardData.isValidSquareCoord(possibleDestinationCoord))
                {
                    final Square possibleDestinationSquare = board.getSquare(possibleDestinationCoord);
                    if(!possibleDestinationSquare.isOccupied())
                    {
                        legalMoves.add(new Move.MajorPieceRegularMove(board, this, possibleDestinationCoord));
                    } else
                    {
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
     * Checks if the current coordinate is in the first column of the board.
     *
     * @param currentCoord The current coordinate.
     * @param offset       The offset value.
     * @return True if the current coordinate is in the first column, false otherwise.
     */
    private static boolean isFirstCol(final int currentCoord, final int offset)
    {
        return BoardData.FIRST_COL[currentCoord] && (offset == -1);
    }

    /**
     * Checks if the current coordinate is in the eighth column of the board.
     *
     * @param currentCoord The current coordinate.
     * @param offset       The offset value.
     * @return True if the current coordinate is in the eighth column, false otherwise.
     */
    private static boolean isEighthCol(final int currentCoord, final int offset)
    {
        return BoardData.EIGHTH_COL[currentCoord] && (offset == 1);
    }

    /**
     * Moves the rook piece to the destination coordinate.
     *
     * @param move The move to be executed.
     * @return The rook piece after the move.
     */
    @Override
    public Piece movePiece(final Move move)
    {
        return new Rook(move.getDestinationCoord(), move.getMovedPiece().getPieceColor());
    }

    /**
     * Converts the rook piece to its string representation.
     *
     * @return The string representation of the rook piece.
     */
    @Override
    public String toString()
    {
        return PieceType.ROOK.toString();
    }
}
