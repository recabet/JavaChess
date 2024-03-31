package chess.pieces;

import chess.Color;
import chess.board.Board;
import chess.board.BoardData;
import chess.board.Move;
import chess.board.Square;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece
{
    private final static int[] PRESET_OFFSET = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final int pieceCoord, final Color pieceColor)
    {
        super(PieceType.KNIGHT,pieceCoord, pieceColor);
    }

    @Override
    public List<Move> getLegalMoves(final Board board)
    {
        int possibleDestinationCoord;
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentOffset : PRESET_OFFSET)
        {
            possibleDestinationCoord = this.pieceCoord + currentOffset;

            if (BoardData.isValidSquareCoord(possibleDestinationCoord))
            {
                if (isFirstCol(this.pieceCoord, currentOffset) || isSecondCol(this.pieceCoord, currentOffset) || isSeventhCol(this.pieceCoord, currentOffset) || isEighthCol(this.pieceCoord, currentOffset))
                {
                    continue;
                }
                final Square possibleDestinationSquare = board.getSquare(possibleDestinationCoord);
                if (!possibleDestinationSquare.isOccupied())
                {
                    legalMoves.add(new Move.MajorPieceRegularMove(board,this,possibleDestinationCoord));
                } else
                {
                    final Piece pieceAtDestination = possibleDestinationSquare.getPiece();
                    final Color pieceColor = pieceAtDestination.getPieceColor();
                    if (this.pieceColor != pieceColor)
                    {
                        legalMoves.add(new Move.AttackMove(board,this,pieceAtDestination,possibleDestinationCoord));
                    }
                }
            }

        }
        return legalMoves;
    }

    private static boolean isFirstCol(final int currentCoord, final int offset)
    {
        return BoardData.FIRST_COL[currentCoord] && ((offset == -17) || (offset == -10) || (offset == 6) || (offset == 15));
    }

    private static boolean isSecondCol(final int currentCoord, final int offset)
    {
        return BoardData.SECOND_COL[currentCoord] && ((offset == -10) || (offset == 6));
    }

    private static boolean isSeventhCol(final int currentCoord, final int offset)
    {
        return BoardData.SEVENTH_COL[currentCoord] && ((offset == -6) || (offset == 10));
    }

    private static boolean isEighthCol(final int currentCoord, final int offset)
    {
        return BoardData.EIGHTH_COL[currentCoord] && ((offset == -15) || (offset == -6) || (offset == 10) || (offset == 17));
    }
    @Override
    public Piece movePiece(final Move move)
    {
        return new Knight(move.getDestinationCoord(), move.getMovedPiece().getPieceColor());
    }
    @Override
    public String toString()
    {
        return PieceType.KNIGHT.toString();
    }
}
