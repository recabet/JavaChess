package chess.pieces;

import chess.Color;
import chess.board.Board;
import chess.board.Move;
import java.util.Collection;
import java.util.List;

public abstract class Piece
{
    final PieceType pieceType;
    final int pieceCoord;
    final Color pieceColor;
    final boolean isFirstMove=false;
    final int hashCode;
    Piece(final PieceType pieceType,final int pieceCoord,final Color pieceColor)
    {
        this.pieceType=pieceType;
        this.pieceColor=pieceColor;
        this.pieceCoord=pieceCoord;
        this.hashCode=calchashCode();
    }

    private int calchashCode()
    {
        int res=pieceType.hashCode();
        res=31*res+pieceColor.hashCode();
        res=31*res+pieceCoord;
        res=31*res+(isFirstMove?1:0);
        return res;
    }

    public Color getPieceColor()
    {
        return this.pieceColor;
    }
    public int getPieceCoord()
    {
        return pieceCoord;
    }
    public boolean isFirstMove()
    {
        return this.isFirstMove;
    }
    public PieceType getPieceType()
    {
        return this.pieceType;
    }
    public enum PieceType
    {
        KING("K")
                {
                    @Override
                    public boolean isKing()
                    {
                        return true;
                    }

                    @Override
                    public boolean isRook()
                    {
                        return false;
                    }
                },
        QUEEN("Q")
                {
                    @Override
                    public boolean isKing()
                    {
                        return false;
                    }

                    @Override
                    public boolean isRook()
                    {
                        return false;
                    }
                },
        ROOK("R")
                {
                    @Override
                    public boolean isKing()
                    {
                        return false;
                    }

                    @Override
                    public boolean isRook()
                    {
                        return true;
                    }
                },
        BISHOP("B")
                {
                    @Override
                    public boolean isKing()
                    {
                        return false;
                    }

                    @Override
                    public boolean isRook()
                    {
                        return false;
                    }
                },
        KNIGHT("N")
                {
                    @Override
                    public boolean isKing()
                    {
                        return false;
                    }

                    @Override
                    public boolean isRook()
                    {
                        return false;
                    }
                },
        PAWN("P")
                {
                    @Override
                    public boolean isKing()
                    {
                        return false;
                    }

                    @Override
                    public boolean isRook()
                    {
                        return false;
                    }
                };
        private final String name;
        PieceType(final String name)
        {
            this.name=name;
        };
        @Override
        public String toString()
        {
            return this.name;
        }
        public abstract boolean isKing();
        public abstract boolean isRook();

    }
    public abstract List<Move> getLegalMoves(final Board board);
    public abstract Piece movePiece(Move move);
    @Override
    public boolean equals(final Object object)
    {
        if(this==object)
        {
            return true;
        }
        if(!(object instanceof Piece))
        {
            return false;
        }
        final Piece other=(Piece) object;
        return pieceCoord ==other.getPieceCoord() && pieceType==other.getPieceType()&&pieceColor==other.getPieceColor()&&isFirstMove==other.isFirstMove();

    }
    public int gethashCode()
    {
        return this.hashCode;
    }
}
