package chess.engine.pieces;

import chess.Color;
import chess.engine.board.Board;
import chess.engine.board.BoardData;
import chess.engine.board.Move;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece
{
    private final static int[] PRESET_OFFSET={7,8,9,16};
    public Pawn(final int pieceCoord,final  Color pieceColor)
    {
        super(PieceType.PAWN,pieceCoord, pieceColor, true);
    }
    public Pawn(final int pieceCoord,final  Color pieceColor, final boolean isFirstMove) { super(PieceType.PAWN,pieceCoord, pieceColor, isFirstMove); }

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
                if(this.pieceColor.isPawnPromotionSquare(possibleDestinationCoord))
                {
                    legalMoves.add(new Move.PawnPromotion(new Move.MinorPieceRegularMove(board, this, possibleDestinationCoord), PieceData.INSTANCE.getMovedQueen(this.pieceColor, possibleDestinationCoord)));
                }
                else {
                    legalMoves.add(new Move.MinorPieceRegularMove(board,this,possibleDestinationCoord));
                }
            }
            else if(currentOffset==16 && this.isFirstMove() && ((BoardData.SEVENTH_RANK[this.pieceCoord] && this.pieceColor==Color.BLACK) || (BoardData.SECOND_RANK[this.pieceCoord] && this.pieceColor==Color.WHITE)))
            {
                final int behindPossibleDestinationCoord=this.pieceCoord+(this.pieceColor.getDirection()*8);
                if(!board.getSquare(possibleDestinationCoord).isOccupied() && !board.getSquare(behindPossibleDestinationCoord).isOccupied())
                {
                    legalMoves.add(new Move.PawnJump(board,this,possibleDestinationCoord));
                }

            }
            else if(currentOffset==7 && !((BoardData.EIGHTH_COL[this.pieceCoord] && this.pieceColor==Color.WHITE)||(BoardData.FIRST_COL[this.pieceCoord] && this.pieceColor==Color.BLACK)) )
            {
                if(board.getSquare(possibleDestinationCoord).isOccupied())
                {
                    final Piece pieceAtDestination=board.getSquare(possibleDestinationCoord).getPiece();
                    if(this.pieceColor!=pieceAtDestination.pieceColor)
                    {
                        if(this.pieceColor.isPawnPromotionSquare(possibleDestinationCoord))
                        {
                            legalMoves.add(new Move.PawnPromotion(new Move.MinorPieceRegularMove(board, this, possibleDestinationCoord), PieceData.INSTANCE.getMovedQueen(this.pieceColor, possibleDestinationCoord)));
                        }
                        else {
                            legalMoves.add(new Move.MinorPieceAttackMove(board,this,pieceAtDestination, possibleDestinationCoord));
                        }
                    }
//                    else if(board.getenPassantPawn()!=null)
//                    {
//                        if (board.getenPassantPawn().getPieceCoord()==(this.getPieceCoord()+(this.pieceColor.getDirection())))//if en passant piece is next to opposite color pawn
//                        {
//                            final Piece pieceOnCandidate=board.getenPassantPawn();
//                            if(this.pieceColor!=pieceOnCandidate.pieceColor)
//                            {
//                                legalMoves.add(new Move.EnPassent(board, this, pieceOnCandidate, possibleDestinationCoord));
//                            }
//                        }
//                    }
                }
            }
            else if(currentOffset==9 && !((BoardData.FIRST_COL[this.pieceCoord] && this.pieceColor==Color.WHITE)||(BoardData.EIGHTH_COL[this.pieceCoord] && this.pieceColor==Color.BLACK)) )
            {
                if(board.getSquare(possibleDestinationCoord).isOccupied())
                {
                    final Piece pieceAtDestination=board.getSquare(possibleDestinationCoord).getPiece();
                    if(this.pieceColor!=pieceAtDestination.pieceColor)
                    {
                        if(this.pieceColor.isPawnPromotionSquare(possibleDestinationCoord))
                        {
                            legalMoves.add(new Move.PawnPromotion(new Move.MinorPieceRegularMove(board, this, possibleDestinationCoord), PieceData.INSTANCE.getMovedQueen(this.pieceColor, possibleDestinationCoord)));
                        }
                        else {
                            legalMoves.add(new Move.MinorPieceAttackMove(board,this,pieceAtDestination, possibleDestinationCoord));
                        }
                    }
                }
//                else if(board.getenPassantPawn()!=null)
//                {
//                    if (board.getenPassantPawn().getPieceCoord()==(this.getPieceCoord()-(this.pieceColor.getDirection())))//if en passant piece is next to opposite color pawn
//                    {
//                        final Piece pieceOnCandidate=board.getenPassantPawn();
//                        if(this.pieceColor!=pieceOnCandidate.pieceColor)
//                        {
//                            legalMoves.add(new Move.EnPassent(board, this, pieceOnCandidate, possibleDestinationCoord));
//                        }
//                    }
//                }
            }

        }
        return legalMoves;
    }
    @Override
    public Piece movePiece(final Move move)
    {
        return PieceData.INSTANCE.getMovedPawn(move.getMovedPiece().getPieceColor(), move.getDestinationCoord());
        //return new Pawn(move.getDestinationCoord(), move.getMovedPiece().getPieceColor());
    }
    @Override
    public String toString() { return this.pieceType.toString(); }
//    public Piece getPromotionPiece()
//    {
//        return new Queen(this.pieceCoord,this.pieceColor, false);
//    }
}
