package chess.engine.board;

import chess.engine.pieces.*;
import chess.engine.pieces.Piece;

public abstract class Move
{
    final Board board;
    final Piece movedPiece;
    final int destinationCoord;
    final boolean isFirstMove;
    public static final Move  INVALID_MOVE=new InvalidMove();

    private Move(final Board board, final Piece movedPiece, final int destinationCoord)
    {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoord = destinationCoord;
        this.isFirstMove = movedPiece.isFirstMove();
    }

    private Move(final Board board, final int destinationCoord) {
        this.board = board;
        this.destinationCoord = destinationCoord;
        this.movedPiece = null;
        this.isFirstMove = false;
    }

    public boolean isAttack()
    {
        return false;
    }
    public Piece getAttackedPiece()
    {
        return null;
    }
    public boolean isCastle()
    {
        return false;
    }
    public int getCurrentCoord() { return this.getMovePiece().getPieceCoord(); }
    public Piece getMovePiece() { return this.movedPiece; }
    public Piece getColor() { return null; }

    public int hashCode()
    {
        int res=1;
        res=31*res+this.destinationCoord;
        res=31*res+this.movedPiece.gethashCode();
        res=31*res+this.movedPiece.getPieceCoord();
        return res;
    }
    @Override
    public boolean equals(final Object object)
    {
        if (this==object)
        {
            return true;
        }
        if(!(object instanceof Move ))
        {
            return false;
        }
        final Move otherMove=(Move) object;
        return   this.getCurrentCoord()==otherMove.getCurrentCoord() && this.getDestinationCoord()==otherMove.getDestinationCoord()
                 && this.getMovedPiece().equals(otherMove.getMovedPiece());
    }


    public Board make()
    {
        final Board.Builder builder = new Board.Builder();
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces())
        {
            if (!this.movedPiece.equals(piece))
            {
                builder.setPiece(piece);
            }
        }
        for (final Piece piece : this.board.getCurrentPlayer().getEnemy().getActivePieces())
        {
            builder.setPiece(piece);
        }
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.getCurrentPlayer().getEnemy().getColor());
        return builder.build();
    }

//    public int getCurrentCoord()
//    {
//        return this.movedPiece.getPieceCoord();
//    }

    public static final class MajorPieceRegularMove extends Move
    {
        public MajorPieceRegularMove(Board board, Piece movedPiece, int destinationCoord)
        {
            super(board, movedPiece, destinationCoord);
        }
        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MajorPieceRegularMove && super.equals(other);
        }
        @Override
        public String toString() {
            return movedPiece.getPieceType().toString() + BoardData.getPositionAtCoord(this.destinationCoord);
        }

    }

    public static class MinorPieceRegularMove extends Move
    {
        public MinorPieceRegularMove(Board board, Piece movedPiece, int destinationCoord)
        {
            super(board, movedPiece, destinationCoord);
        }
    }

    public static class AttackMove extends Move
    {
        final private Piece attackedPiece;

        public AttackMove(final Board board, final Piece movedPiece, final Piece attackedPiece, final int destinationCoord)
        {
            super(board, movedPiece, destinationCoord);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board make()
        {
            return null;
        }
        @Override
        public  boolean isAttack()
        {
            return true;
        }
        @Override
        public  Piece getAttackedPiece()
        {
            return this.attackedPiece;
        }
        @Override
        public int hashCode()
        {
            return this.attackedPiece.hashCode()+super.hashCode();
        }
        @Override
        public boolean equals(Object object)
        {
            if(this==object)
            {
                return true;
            }
            if(!(object instanceof AttackMove))
            {
                return false;
            }
            final AttackMove otherAttack=(AttackMove) object;
            return super.equals(otherAttack) &&getAttackedPiece().equals(otherAttack.getAttackedPiece());
        }

    }

    public static class MinorPieceAttackMove extends AttackMove
    {
        public MinorPieceAttackMove(Board board, Piece movedPiece, Piece attackedPiece, int destinationCoord)
        {
            super(board, movedPiece, attackedPiece, destinationCoord);
        }
    }

    public static final class PawnJump extends Move
    {
        public PawnJump(Board board, Piece movedPiece, int destinationCoord)
        {
            super(board, movedPiece, destinationCoord);
        }
        @Override
        public Board make()
        {
            final Board.Builder builder=new Board.Builder();
            for(final Piece piece:this.board.getCurrentPlayer().getActivePieces())
            {
                if(!this.movedPiece.equals(piece))
                {
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece :this.board.getCurrentPlayer().getEnemy().getActivePieces())
            {
                builder.setPiece(piece);
            }
            final Pawn movedPawn=(Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassent(movedPawn);
            builder.setMoveMaker(this.board.getCurrentPlayer().getEnemy().getColor());
            return builder.build();
        }
        @Override
        public String toString() {
            return BoardData.getPositionAtCoord(this.destinationCoord);
        }
    }
    public static class EnPassent extends MinorPieceAttackMove
    {

        public EnPassent(Board board, Piece movedPiece, Piece attackedPiece, int destinationCoord)
        {
            super(board, movedPiece, attackedPiece, destinationCoord);
        }
    }

    public Piece getMovedPiece()
    {
        return movedPiece;
    }
    public static final class InvalidMove extends Move
    {
        private InvalidMove()
        {
            super(null,-1);
        }
        @Override
        public Board make()
        {
            throw new RuntimeException("Can you even play?");
        }

    }

    public int getDestinationCoord()
    {
        return this.destinationCoord;
    }
    public static class MoveCreator
    {
        public static Move moveCreate(final Board board,final int currentCoord,final int destinationCoord)
        {
            for(final Move move : board.getAllLegalMoves())
            {
                if(move.getCurrentCoord()==currentCoord && move.getDestinationCoord()==destinationCoord)
                {
                    return move;
                }
            }
            return INVALID_MOVE;
            //throw new RuntimeException("not legal");
        }
    }
    static abstract class Castle extends Move
    {
        protected final Rook castlingRook;
        protected final int castlingRookInitCoord;
        protected final int getCastlingRookDestCoord;
        private Castle(Board board, Piece movedPiece, int destinationCoord,final Rook castlingRook,final int castlingRookInitCoord,final int castlingRookDestCoord)
        {
            super(board, movedPiece, destinationCoord);
            this.castlingRook=castlingRook;
            this.castlingRookInitCoord=castlingRookInitCoord;
            this.getCastlingRookDestCoord=castlingRookDestCoord;
        }
        public Rook getCastlingRook()
        {
            return this.castlingRook;
        }
        @Override
        public boolean isCastle()
        {
            return true;
        }
        @Override
        public Board make()
        {
            final Board.Builder builder =new Board.Builder();
            for(final Piece piece:this.board.getCurrentPlayer().getActivePieces())
            {
                if(!(this.movedPiece.equals(piece) && this.castlingRook.equals(piece) ))
                {
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece :this.board.getCurrentPlayer().getEnemy().getActivePieces())
            {
                builder.setPiece(piece);
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setPiece(new Rook(this.getCastlingRookDestCoord,this.castlingRook.getPieceColor()));
            builder.setMoveMaker(this.board.getCurrentPlayer().getEnemy().getColor());
            return builder.build();
        }
    }
    public static final class ShortCastle extends Castle
    {
        public ShortCastle(Board board, Piece movedPiece, int destinationCoord, final Rook castlingRook, final int castlingRookInitCoord, final int castlingRookDestCoord)
        {
            super(board, movedPiece, destinationCoord,castlingRook,castlingRookInitCoord,castlingRookDestCoord);
        }
        @Override
        public String toString()
        {
            return "0-0";
        }
    }
    public static final class LongCastle extends Castle
    {

        public LongCastle(Board board, Piece movedPiece, int destinationCoord, final Rook castlingRook, final int castlingRookInitCoord, final int castlingRookDestCoord)
        {
            super(board, movedPiece, destinationCoord,castlingRook,castlingRookInitCoord,castlingRookDestCoord);
        }
        @Override
        public String toString()
        {
            return "0-0-0";
        }
    }

}
