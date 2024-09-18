package chess.logic.board;

import chess.logic.pieces.Piece;

import java.util.Map;
import java.util.HashMap;

/**
 * Square represents a single square on a chess board.
 * Each square has a coordinate and can be either occupied by a piece or empty.
 */
public abstract class Square {
    /**
     * The coordinate of the square (squares are numbered from 0 to 63 instead of having 2 coordinates).
     */

    final int squareCoord; // the coordinate of the square (squares are  numbered from 0 t0 63 instead of having 2 coordinates

    /**
     * Constructor for the Square class.
     *
     * @param squareCoord The coordinate of the square.
     */
    private Square(int squareCoord) //super constructor for the abstract class
    {
        this.squareCoord = squareCoord;
    }

    /**
     * Checks if the square is occupied by a piece.
     *
     * @return True if the square is occupied, false otherwise.
     */
    public abstract boolean isOccupied(); //abstract method isOccupied , self-explanatory

    /**
     * Returns the piece on the square, or null if there is none.
     *
     * @return The piece on the square, or null if empty.
     */
    public abstract Piece getPiece();  // returns the piece on the square, null if there is none

    private static final Map<Integer, EmptySquare> ALL_EMPTY_SQUARES = createAllEmptySquares(); //basis for our board

    /**
     * Creates and initializes all empty squares on the chess board.
     * Each square is mapped to its corresponding coordinate.
     *
     * @return A map containing all empty squares with their coordinates.
     */
    private static Map<Integer, EmptySquare> createAllEmptySquares()  //maps the coordinates to the squares
    {
        Map<Integer, EmptySquare> emptySquareMap = new HashMap<>();
        for(int coord = 0; coord < 64; ++coord)
        {
            emptySquareMap.put((Integer)coord, new EmptySquare(coord));

        }
        return emptySquareMap;
    }

    /**
     * Creates a new square with the specified coordinate and piece.
     *
     * @param squareCoord The coordinate of the square.
     * @param piece       The piece to be placed on the square, or null if the square is empty.
     * @return A Square object representing the specified square.
     */
    public static Square createSquare(final int squareCoord, final Piece piece)
    {
        return piece != null ? new FullSquare(squareCoord, piece) : ALL_EMPTY_SQUARES.get((Object)squareCoord);
    }

    /**
     * Retrieves the coordinate of the square.
     *
     * @return The coordinate of the square.
     */
    public int getCoord()
    {
        return this.squareCoord;
    }

    /**
     * Represents an empty square on the chess board.
     */
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

    /**
     * Represents a square occupied by a piece on the chess board.
     */
    public static final class FullSquare extends Square  //second inheritor of the superclass
    {
        private final Piece pieceOnSquare;  //extra parameter as the square is not empty

        private FullSquare(final int fullSquareCoord, Piece piece)  // constructor of the class, with extra argument
        {
            super(fullSquareCoord);
            this.pieceOnSquare = piece;
        }

        @Override
        public boolean isOccupied()  //overriding the method to always return true
        {
            return true;
        }

        @Override
        public Piece getPiece()  //overriding the method to always return the piece

        {
            return this.pieceOnSquare;
        }

        @Override
        public String toString()
        {
            return getPiece().getPieceColor().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
        }
    }
}

