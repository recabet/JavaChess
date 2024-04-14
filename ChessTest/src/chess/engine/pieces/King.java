package chess.engine.pieces;

import chess.Color;
import chess.engine.board.Board;
import chess.engine.board.BoardData;
import chess.engine.board.Move;
import chess.engine.board.Square;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece
{
    private final static int[] PRESET_OFFSET={-9,-8,-7,-1,1,7,8,9};

    private final boolean isCastled;
    private final boolean shortCastleCapable;
    private final boolean longCastleCapable;

    public King(final int pieceCoord, final Color pieceColor, final boolean shortCastleCapable, final boolean longCastleCapable)
    {
        super(PieceType.KING,pieceCoord, pieceColor, true);
        this.isCastled = true;
        this.longCastleCapable = longCastleCapable;
        this.shortCastleCapable = shortCastleCapable;
    }
    public King(final int pieceCoord, final Color pieceColor, final boolean isFirstMove, final boolean isCastled, final boolean shortCastleCapable, final boolean longCastleCapable) {
            super(PieceType.KING,pieceCoord, pieceColor, isFirstMove);
            this.isCastled = isCastled;
            this.shortCastleCapable = shortCastleCapable;
            this.longCastleCapable = longCastleCapable;
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
                        legalMoves.add(new Move.MajorPieceAttackMove(board,this,pieceAtDestination,possibleDestinationCoord));
                    }
                }
            }

        }

        return legalMoves;
    }

    public boolean isCastled() {
        return this.isCastled;
    }

    public boolean isShortCastleCapable() {
        return this.shortCastleCapable;
    }

    public boolean isLongCastleCapable() {
        return this.longCastleCapable;
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
        return new King(move.getDestinationCoord(), move.getMovedPiece().getPieceColor(), false, move.isCastle(), false, false);
    }
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof King)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        final King king = (King) other;
        return isCastled == king.isCastled;
    }
    @Override
    public String toString()
    {
        return PieceType.KING.toString();
    }
}
