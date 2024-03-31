package chess.board;

import chess.pieces.Piece;
import java.util.Map;
import java.util.HashMap;
public abstract class Square
{
    final int squareCoord; // the coordinate of the square (squares are  numbered from 0 t0 63 instead of having 2 coordinates

    private Square(int squareCoord) //super constructor for the abstract class
    {
        this.squareCoord=squareCoord;
    }
    public abstract boolean isOccupied(); //abstract method isOccupied , self-explanatory
    public abstract Piece getPiece();  // returns the piece on the square, null if there is none
    private static  final Map<Integer,EmptySquare> ALL_EMPTY_SQUARES=createAllEmptySquares(); //basis for our board

    private static Map<Integer,EmptySquare> createAllEmptySquares()  //maps the coordinates to the squares
    {
        Map<Integer,EmptySquare> emptySquareMap=new HashMap<>();
        for(int coord=0;coord<64;++coord)
        {
            emptySquareMap.put(coord,new EmptySquare(coord));

        }
        return emptySquareMap;
    }

    public static Square createSquare(final int squareCoord,final Piece piece)
    {
        return piece!=null? new FullSquare(squareCoord,piece):ALL_EMPTY_SQUARES.get(squareCoord);
    }

    public int getCoord()
    {
        return this.squareCoord;
    }

    public static final class EmptySquare extends Square  //first inheritor of the superclass
    {

        private EmptySquare(final int emptySquareCoord)
        {
            super(emptySquareCoord);
        }

        @Override
        public boolean isOccupied()  //overriding the method to always return false
        {
            return false;
        }
        @Override
        public Piece getPiece()  //overriding the method to always return null
        {
            return null;
        }
        @Override
        public String toString()
        {
            return "-";
        }
    }
    public static final class FullSquare extends Square  //second inheritor of the superclass
    {
        private final Piece pieceOnSquare;  //extra parameter as the square is not empty
        private FullSquare(final int fullSquareCoord,Piece piece)  // constructor of the class, with extra argument
        {
            super(fullSquareCoord);
            this.pieceOnSquare=piece;
        }
        @Override
        public boolean isOccupied()  //overriding the method to always return true
        {
            return true;
        }
        @Override
        public Piece getPiece()  //overriding the method to always return the piece

        {
            return this.pieceOnSquare ;
        }
        @Override
        public String toString()
        {
            return getPiece().getPieceColor().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
        }
    }
}

