package chess.engine.pieces;

import chess.Color;
import chess.engine.board.Board;
import chess.engine.board.BoardData;
import chess.engine.board.Move;
import chess.engine.board.Square;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece
{
    private final static int[] PRESET_VECTOR_COORDS={-9,-7,7,9};
    public Bishop(final int pieceCoord,final  Color pieceColor) { super(PieceType.BISHOP,pieceCoord, pieceColor, true); }
    public Bishop(final int pieceCoord,final  Color pieceColor, final boolean isFirstMove) { super(PieceType.BISHOP,pieceCoord, pieceColor, isFirstMove); }

    @Override
    public List<Move> getLegalMoves(final Board board)
    {
        final List<Move> legalMoves=new ArrayList<>();

        for(final int currentOffset:PRESET_VECTOR_COORDS)
        {
            int possibleDestinationCoord=this.pieceCoord;
            while(BoardData.isValidSquareCoord(possibleDestinationCoord))
            {
                if(isFirstCol(possibleDestinationCoord,currentOffset)||isEighthCol(possibleDestinationCoord,currentOffset))
                {
                    break;
                }
                possibleDestinationCoord+=currentOffset;
                if (BoardData.isValidSquareCoord(possibleDestinationCoord))
                {
                    final Square possibleDestinationSquare = board.getSquare(possibleDestinationCoord);
                    if (!possibleDestinationSquare.isOccupied())
                    {
                        legalMoves.add(new Move.MajorPieceRegularMove(board,this,possibleDestinationCoord));
                    }
                    else
                    {
                        final Piece pieceAtDestination = possibleDestinationSquare.getPiece();
                        final Color pieceColor = pieceAtDestination.getPieceColor();
                        if (this.pieceColor != pieceColor)
                        {
                            legalMoves.add(new Move.AttackMove(board,this,pieceAtDestination,possibleDestinationCoord));
                        }
                        break;
                    }
                }
            }
        }

        return legalMoves;
    }

    @Override
    public Piece movePiece(final Move move)
    {
        return new Bishop(move.getDestinationCoord(), move.getMovedPiece().getPieceColor());
    }

    private static boolean isFirstCol(final int currentCoord,final int offset)
    {
        return BoardData.FIRST_COL[currentCoord] && ((offset==-9)||(offset==7));
    }
    private static boolean isEighthCol(final int currentCoord,final int offset)
    {
        return BoardData.EIGHTH_COL[currentCoord] && ((offset==9)||(offset==-7));
    }
    @Override
    public String toString()
    {
        return PieceType.BISHOP.toString();
    }
}
