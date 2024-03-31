package chess.pieces;

import chess.Color;
import chess.board.Board;
import chess.board.BoardData;
import chess.board.Move;
import chess.board.Square;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece
{
    private final static int[] PRESET_OFFSET={-9,-8,-7,-1,1,7,8,9};
    public King(final int pieceCoord, final Color pieceColor)
    {
        super(PieceType.KING,pieceCoord, pieceColor);
    }

    @Override
    public List<Move> getLegalMoves(Board board)
    {
        final List<Move> legalMoves=new ArrayList<>();
        int possibleDestinationCoord;
        for(final int currentOffset: PRESET_OFFSET)
        {
            possibleDestinationCoord=this.pieceCoord+currentOffset;
            if( isFirstCol(this.pieceCoord,currentOffset)||isEighthCol(this.pieceCoord,currentOffset))
            {
                continue;
            }
            if(BoardData.isValidSquareCoord(possibleDestinationCoord))
            {
                final Square possibleDestinationSquare=board.getSquare(possibleDestinationCoord);
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
        return BoardData.FIRST_COL[currentCoord] && ((offset == -9) || (offset == -1) || (offset == 7));
    }

    private static boolean isEighthCol(final int currentCoord, final int offset)
    {
        return BoardData.EIGHTH_COL[currentCoord] && ((offset == 1) || (offset == -7) || (offset == 9));
    }
    @Override
    public Piece movePiece(final Move move)
    {
        return new King(move.getDestinationCoord(), move.getMovedPiece().getPieceColor());
    }
    @Override
    public String toString()
    {
        return PieceType.KING.toString();
    }
}
