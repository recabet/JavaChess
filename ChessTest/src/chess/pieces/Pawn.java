package chess.pieces;

import chess.Color;
import chess.board.Board;
import chess.board.BoardData;
import chess.board.Move;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece
{
    private final static int[] PRESET_OFFSET={7,8,9,16};
    public Pawn(final int pieceCoord,final  Color pieceColor)
    {
        super(PieceType.PAWN,pieceCoord, pieceColor);
    }

    @Override
    public List<Move> getLegalMoves(Board board)
    {
        final List<Move> legalMoves=new ArrayList<>();
        for(final int currentOffset:PRESET_OFFSET)
        {
            int possibleDestinationCoord = this.pieceCoord + (this.pieceColor.getDirection()*currentOffset);
            if(!BoardData.isValidSquareCoord(possibleDestinationCoord))
            {
                continue;
            }
            if(currentOffset==8 && !board.getSquare(possibleDestinationCoord).isOccupied())
            {
                legalMoves.add(new Move.MajorPieceRegularMove(board,this,possibleDestinationCoord));
            }
            else if(currentOffset==16 && this.isFirstMove() && ((BoardData.SECOND_ROW[this.pieceCoord] && this.pieceColor==Color.BLACK) || (BoardData.SEVENTH_ROW[this.pieceCoord] && this.pieceColor==Color.WHITE)))
            {
                final int behindPossibleDestinationCoord=this.pieceCoord+(this.pieceColor.getDirection()*8);
                if(!board.getSquare(possibleDestinationCoord).isOccupied() && !board.getSquare(behindPossibleDestinationCoord).isOccupied())
                {
                    legalMoves.add(new Move.MajorPieceRegularMove(board,this,possibleDestinationCoord));
                }

            }
            else if(currentOffset==7 && !((BoardData.EIGHTH_COL[this.pieceCoord] && this.pieceColor==Color.WHITE)||(BoardData.FIRST_COL[this.pieceCoord] && this.pieceColor==Color.BLACK)) )
            {
                if(board.getSquare(possibleDestinationCoord).isOccupied())
                {
                    final Piece pieceAtDestination=board.getSquare(possibleDestinationCoord).getPiece();
                    if(this.pieceColor!=pieceAtDestination.pieceColor)
                    {
                        legalMoves.add(new Move.MajorPieceRegularMove(board,this,possibleDestinationCoord));
                    }
                }
            }
            else if(currentOffset==9 && !((BoardData.FIRST_COL[this.pieceCoord] && this.pieceColor==Color.WHITE)||(BoardData.EIGHTH_COL[this.pieceCoord] && this.pieceColor==Color.BLACK)) )
            {
                if(board.getSquare(possibleDestinationCoord).isOccupied())
                {
                    final Piece pieceAtDestination=board.getSquare(possibleDestinationCoord).getPiece();
                    if(this.pieceColor!=pieceAtDestination.pieceColor)
                    {
                        legalMoves.add(new Move.MajorPieceRegularMove(board,this,possibleDestinationCoord));
                    }
                }
            }

        }

        return legalMoves;
    }
    @Override
    public Piece movePiece(final Move move)
    {
        return new Pawn(move.getDestinationCoord(), move.getMovedPiece().getPieceColor());
    }
    @Override
    public String toString()
    {
        return PieceType.PAWN.toString();
    }
}
