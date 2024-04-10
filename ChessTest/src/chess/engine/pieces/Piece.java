package chess.engine.pieces;

import chess.Color;
import chess.engine.board.Board;
import chess.engine.board.Move;

import java.util.List;

public abstract class Piece
{
    final PieceType pieceType;
    final int pieceCoord;
    final Color pieceColor;
    final boolean isFirstMove;
    final int hashCode;
    Piece(final PieceType pieceType,final int pieceCoord,final Color pieceColor, final boolean isFirstMove)
    {
        this.pieceType=pieceType;
        this.pieceColor=pieceColor;
        this.pieceCoord=pieceCoord;
        this.isFirstMove = isFirstMove;
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
    public int getPieceValue() {
        return this.pieceType.getPieceValue();
    }

    public enum PieceType
    {
        KING(10000, "K")
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
        QUEEN(900, "Q")
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
        ROOK(500, "R")
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
        BISHOP(300, "B")
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
        KNIGHT(300,"N")
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
        PAWN(100, "P")
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
        private int pieceValue;

        PieceType(final int pieceValue, final String name)
        {
            this.name=name;
            this.pieceValue = pieceValue;
        };
        @Override
        public String toString()
        {
            return this.name;
        }
        public int getPieceValue() { return this.pieceValue; }
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
        return pieceCoord ==other.getPieceCoord() && pieceType==other.getPieceType()&& pieceColor==other.getPieceColor()&&isFirstMove==other.isFirstMove();

    }
    public int gethashCode()
    {
        return this.hashCode;
    }
}
