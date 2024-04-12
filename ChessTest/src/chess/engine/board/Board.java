package chess.engine.board;

import chess.Color;
import chess.engine.pieces.*;
import chess.engine.player.BlackPlayer;
import chess.engine.player.WhitePlayer;
import chess.engine.player.*;

import java.util.*;
public class Board
{
    private final List<Square> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;
    private final Pawn enPassantPawn;

    private Board(Builder builder)
    {
        this.gameBoard = initGameBoard(builder);
        this.whitePieces = trackActivePieces(this.gameBoard, Color.WHITE);
        this.blackPieces = trackActivePieces(this.gameBoard, Color.BLACK);
        final Collection<Move> legalWhiteStdLegalMoves = getLegalMoves(this.whitePieces);
        final Collection<Move> legalBlackStdLegalMoves = getLegalMoves(this.blackPieces);
        this.whitePlayer=new WhitePlayer(this,legalWhiteStdLegalMoves,legalBlackStdLegalMoves);
        this.blackPlayer=new BlackPlayer(this,legalBlackStdLegalMoves,legalWhiteStdLegalMoves);
        this.currentPlayer=builder.MoveMaker.selectPlayer(this.whitePlayer,this.blackPlayer);
        this.enPassantPawn= builder.enPassentPawn;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        for (int numSquares = 0; numSquares < 64; ++numSquares)
        {
            final String squaretxt =this.gameBoard.get(numSquares).toString();
            builder.append(String.format("%3s", squaretxt));
            if ((numSquares + 1) % 8 == 0)
            {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private Collection<Move> getLegalMoves(Collection<Piece> colorPieces)
    {
        final List<Move> legalMoves = new ArrayList<>();
        for (final Piece piece : colorPieces)
        {
            legalMoves.addAll(piece.getLegalMoves(this));
        }
        return legalMoves;
    }
    public Player getCurrentPlayer()
    {
        return this.currentPlayer;
    }

    static private Collection<Piece> trackActivePieces(final List<Square> gameBoard, final Color color)
    {
        final List<Piece> activePieces = new ArrayList<>();
        for (final Square square : gameBoard)
        {
            if (square.isOccupied())
            {
                final Piece piece = square.getPiece();
                if (piece.getPieceColor() == color)
                {
                    activePieces.add(piece);
                }

            }
        }
        return activePieces;
    }

    public Square getSquare(final int squareCoord)
    {
        return gameBoard.get(squareCoord);
    }

    //HMM INTERESTING!!
    private static List<Square> initGameBoard(final Builder builder)
    {
        final List<Square> squares = new ArrayList<>();
        for (int numSquares = 0; numSquares < 64; ++numSquares)
        {
            squares.add(Square.createSquare(numSquares, builder.initialBoard.get(numSquares)));

        }
        return squares;
    }
    public Collection<Piece> getBlackPieces()
    {
        return this.blackPieces;
    }
    public Collection<Piece> getWhitePieces()
    {
        return this.whitePieces;
    }
    public Player whitePlayer()
    {
        return this.whitePlayer;
    }
    public Player blackPlayer()
    {
        return this.blackPlayer;
    }

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
        builder.setPiece(new King(60, Color.WHITE));
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
        builder.setPiece(new King(4, Color.BLACK));
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

    public Iterable<Move> getAllLegalMoves()
    {
        List<Move> allLegalMoves = new ArrayList<>();
        allLegalMoves.addAll(this.whitePlayer.getLegalMoves());
        allLegalMoves.addAll(this.blackPlayer.getLegalMoves());
        return allLegalMoves;
    }

    public Pawn getenPassantPawn() {
        return this.enPassantPawn;
    }

    public static class Builder
    {
        Map<Integer, Piece> initialBoard;
        Color MoveMaker;
        Pawn enPassentPawn;

        public Builder()
        {
            this.initialBoard=new HashMap<>();

        }

        public Builder setPiece(final Piece piece)
        {
            this.initialBoard.put(piece.getPieceCoord(), piece);
            return this;
        }

        public Builder setMoveMaker(final Color moveMaker)
        {
            this.MoveMaker = moveMaker;
            return this;
        }

        public Board build()
        {
            return new Board(this);
        }

        public void setEnPassent(Pawn movedPawn)
        {
            this.enPassentPawn=movedPawn;
        }
    }
}
