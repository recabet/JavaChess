package chess.logic.board;

import chess.Color;
import chess.logic.pieces.*;
import chess.logic.player.BlackPlayer;
import chess.logic.player.WhitePlayer;
import chess.logic.player.*;

import java.util.*;

/**
 * Represents a Board in a chess game.
 * This class manages the game board, including pieces, player turns, and legal moves.
 */
public class Board {
    private final List<Square> gameBoard; // The game board represented as a list of squares
    private final Collection<Piece> whitePieces; // Collection of white pieces on the board
    private final Collection<Piece> blackPieces; // Collection of black pieces on the board
    private final WhitePlayer whitePlayer; // The white player
    private final BlackPlayer blackPlayer; // The black player
    private final Player currentPlayer; // The current player
    private final Move transitionMove; // The transition move

    private Board(Builder builder)
    {
        this.gameBoard = initGameBoard(builder);
        this.whitePieces = trackActivePieces(this.gameBoard, Color.WHITE);
        this.blackPieces = trackActivePieces(this.gameBoard, Color.BLACK);
        final Collection<Move> legalWhiteStdLegalMoves = getLegalMoves(this.whitePieces);
        final Collection<Move> legalBlackStdLegalMoves = getLegalMoves(this.blackPieces);
        this.whitePlayer = new WhitePlayer(this, legalWhiteStdLegalMoves, legalBlackStdLegalMoves);
        this.blackPlayer = new BlackPlayer(this, legalBlackStdLegalMoves, legalWhiteStdLegalMoves);
        this.currentPlayer = builder.MoveMaker.selectPlayer(this.whitePlayer, this.blackPlayer);
        this.transitionMove = builder.transitionMove != null ? builder.transitionMove : Move.INVALID_MOVE;
    }

    /**
     * Returns a string representation of the chess board. Each square on the board is represented by its
     * respective piece or an empty square. The representation is formatted to display the board in a
     * human-readable format with 8 rows and 8 columns.
     *
     * @return a string representation of the chess board
     */
    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        for(int numSquares = 0; numSquares < 64; ++numSquares)
        {
            final String squaretxt = this.gameBoard.get(numSquares).toString();
            builder.append(String.format("%3s", squaretxt));
            if((numSquares + 1) % 8 == 0)
            {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    /**
     * Retrieves all legal moves available for the pieces of the specified color on the board.
     * This method iterates through all pieces of the specified color and retrieves their legal moves
     * using the Piece's getLegalMoves() method, then adds them to a list of legal moves.
     *
     * @param colorPieces the collection of pieces of the specified color
     * @return a collection of all legal moves available for the pieces of the specified color
     */
    private Collection<Move> getLegalMoves(Collection<Piece> colorPieces)
    {
        final List<Move> legalMoves = new ArrayList<>();
        for(final Piece piece : colorPieces)
        {
            legalMoves.addAll(piece.getLegalMoves(this));
        }
        return legalMoves;
    }

    /**
     * Retrieves the current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer()
    {
        return this.currentPlayer;
    }

    /**
     * Tracks and retrieves all active pieces of the specified color on the chess board.
     * This method iterates through all squares on the board and checks if each square is occupied.
     * If a square is occupied, it retrieves the piece on that square and checks if the piece's color
     * matches the specified color. If so, the piece is considered active and added to the list of active pieces.
     *
     * @param gameBoard the list of squares representing the chess board
     * @param color     the color of the pieces to track (WHITE or BLACK)
     * @return a collection containing all active pieces of the specified color on the board
     */
    static private Collection<Piece> trackActivePieces(final List<Square> gameBoard, final Color color)
    {
        final List<Piece> activePieces = new ArrayList<>();
        for(final Square square : gameBoard)
        {
            if(square.isOccupied())
            {
                final Piece piece = square.getPiece();
                if(piece.getPieceColor() == color)
                {
                    activePieces.add(piece);
                }

            }
        }
        return activePieces;
    }

    /**
     * Retrieves a square on the board based on its coordinate.
     *
     * @param squareCoord the coordinate of the square
     * @return the square at the specified coordinate
     */
    public Square getSquare(final int squareCoord)
    {
        return gameBoard.get(squareCoord);
    }

    /**
     * Initializes the game board based on the initial configuration provided by the builder.
     * This method creates a list of squares representing the chess board. It iterates through each square
     * index (from 0 to 63) and creates a new square using the Square.createSquare() method, passing the square index
     * and the corresponding piece from the initial board configuration provided by the builder.
     *
     * @param builder the builder containing the initial board configuration
     * @return a list of squares representing the initialized game board
     */
    private static List<Square> initGameBoard(final Builder builder)
    {
        final List<Square> squares = new ArrayList<>();
        for(int numSquares = 0; numSquares < 64; ++numSquares)
        {
            squares.add(Square.createSquare(numSquares, builder.initialBoard.get(numSquares)));

        }
        return squares;
    }

    /**
     * Retrieves the collection of black pieces on the board.
     *
     * @return the collection of black pieces
     */
    public Collection<Piece> getBlackPieces()
    {
        return this.blackPieces;
    }

    /**
     * Retrieves the collection of white pieces on the board.
     *
     * @return the collection of white pieces
     */
    public Collection<Piece> getWhitePieces()
    {
        return this.whitePieces;
    }

    /**
     * Retrieves all pieces on the board.
     *
     * @return a collection containing all pieces on the board
     */
    public Collection<Piece> getAllPieces()
    {
        Collection<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(this.whitePieces);
        allPieces.addAll(this.blackPieces);
        return allPieces;
    }

    /**
     * Retrieves the white player.
     *
     * @return the white player
     */
    public Player whitePlayer()
    {
        return this.whitePlayer;
    }

    /**
     * Retrieves the black player.
     *
     * @return the black player
     */
    public Player blackPlayer()
    {
        return this.blackPlayer;
    }

    /**
     * Initializes a standard chess board with the default starting position of pieces for both players.
     * This method constructs a new builder, adds pieces for both white and black players to the initial board configuration,
     * and sets the move maker to white. It then builds a new board using the builder and returns it.
     *
     * @return a new Board object representing the standard chess board with the default starting position of pieces
     */

    public static Board initStdBoard()
    {
        final Builder builder = new Builder();
        //WHITE pieces
        builder.setPiece(new Rook(63, Color.WHITE));
        builder.setPiece(new Rook(56, Color.WHITE));
        builder.setPiece(new Knight(57, Color.WHITE));
        builder.setPiece(new Knight(62, Color.WHITE));
        builder.setPiece(new Bishop(58, Color.WHITE));
        builder.setPiece(new Bishop(61, Color.WHITE));
        builder.setPiece(new Queen(59, Color.WHITE));
        builder.setPiece(new King(60, Color.WHITE, true, true));
        builder.setPiece(new Pawn(48, Color.WHITE));
        builder.setPiece(new Pawn(49, Color.WHITE));
        builder.setPiece(new Pawn(50, Color.WHITE));
        builder.setPiece(new Pawn(51, Color.WHITE));
        builder.setPiece(new Pawn(52, Color.WHITE));
        builder.setPiece(new Pawn(53, Color.WHITE));
        builder.setPiece(new Pawn(54, Color.WHITE));
        builder.setPiece(new Pawn(55, Color.WHITE));
        //Black pieces
        builder.setPiece(new Rook(0, Color.BLACK));
        builder.setPiece(new Rook(7, Color.BLACK));
        builder.setPiece(new Knight(1, Color.BLACK));
        builder.setPiece(new Knight(6, Color.BLACK));
        builder.setPiece(new Bishop(2, Color.BLACK));
        builder.setPiece(new Bishop(5, Color.BLACK));
        builder.setPiece(new Queen(3, Color.BLACK));
        builder.setPiece(new King(4, Color.BLACK, true, true));
        builder.setPiece(new Pawn(8, Color.BLACK));
        builder.setPiece(new Pawn(9, Color.BLACK));
        builder.setPiece(new Pawn(10, Color.BLACK));
        builder.setPiece(new Pawn(11, Color.BLACK));
        builder.setPiece(new Pawn(12, Color.BLACK));
        builder.setPiece(new Pawn(13, Color.BLACK));
        builder.setPiece(new Pawn(14, Color.BLACK));
        builder.setPiece(new Pawn(15, Color.BLACK));

        builder.setMoveMaker(Color.WHITE);
        return builder.build();
    }

    /**
     * Retrieves all legal moves available on the current board for both white and black players.
     * This method retrieves the legal moves for the white player and the black player, combines them into a single list,
     * and returns an iterable collection of all legal moves.
     *
     * @return an iterable collection of all legal moves available on the current board
     */
    public Iterable<Move> getAllLegalMoves()
    {
        List<Move> allLegalMoves = new ArrayList<>();
        allLegalMoves.addAll(this.whitePlayer.getLegalMoves());
        allLegalMoves.addAll(this.blackPlayer.getLegalMoves());
        return allLegalMoves;
    }

    /**
     * Represents a builder for creating a Board object with a custom configuration of pieces.
     * This class allows setting pieces, move maker, and transition move before building the board.
     */
    public static class Builder {
        Map<Integer, Piece> initialBoard;
        Color MoveMaker;
        Move transitionMove;

        /**
         * Constructs a new Builder object with an empty initial board configuration.
         */
        public Builder()
        {
            this.initialBoard = new HashMap<>();
        }

        /**
         * Adds a piece to the initial board configuration.
         *
         * @param piece the piece to add to the initial board configuration
         * @return the current Builder object for method chaining
         */
        public Builder setPiece(final Piece piece)
        {
            this.initialBoard.put(piece.getPieceCoord(), piece);
            return this;
        }

        /**
         * Sets the color of the player to make the next move.
         *
         * @param moveMaker the color of the player to make the next move (WHITE or BLACK)
         * @return the current Builder object for method chaining
         */
        public Builder setMoveMaker(final Color moveMaker)
        {
            this.MoveMaker = moveMaker;
            return this;
        }

        /**
         * Builds a new Board object based on the current builder configuration.
         *
         * @return a new Board object with the specified initial board configuration and move maker
         */
        public Board build()
        {
            return new Board(this);
        }

        /**
         * Sets the transition move for the board.
         *
         * @param transitionMove the transition move to set
         * @return the current Builder object for method chaining
         */
        public Builder setTransitionMove(final Move transitionMove)
        {
            this.transitionMove = transitionMove;
            return this;
        }


    }
}
