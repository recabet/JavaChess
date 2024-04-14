package chess.logic.pieces;

import chess.Color;
import chess.logic.board.Board;
import chess.logic.board.BoardData;
import chess.logic.board.Move;
import chess.logic.board.Square;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents a King piece in the chess game.
 */
public class King extends Piece
{
    /** Array containing the preset offset values for the King's movements. */
    private final static int[] PRESET_OFFSET = {-9, -8, -7, -1, 1, 7, 8, 9};

    /** Indicates whether the King has been castled. */
    private final boolean isCastled;

    /** Indicates whether the King is capable of performing a short castle. */
    private final boolean shortCastleCapable;

    /** Indicates whether the King is capable of performing a long castle. */
    private final boolean longCastleCapable;

    /**
     * Constructs a King object with the specified parameters.
     *
     * @param pieceCoord          The coordinate of the King on the chessboard.
     * @param pieceColor          The color of the King.
     * @param shortCastleCapable  Whether the King is capable of performing a short castle.
     * @param longCastleCapable   Whether the King is capable of performing a long castle.
     */
    public King(final int pieceCoord, final Color pieceColor, final boolean shortCastleCapable, final boolean longCastleCapable) {
        super(PieceType.KING, pieceCoord, pieceColor, true);
        this.isCastled = true;
        this.longCastleCapable = longCastleCapable;
        this.shortCastleCapable = shortCastleCapable;
    }

    /**
     * Constructs a King object with the specified parameters.
     *
     * @param pieceCoord          The coordinate of the King on the chessboard.
     * @param pieceColor          The color of the King.
     * @param isFirstMove         Whether it's the King's first move.
     * @param isCastled           Whether the King has been castled.
     * @param shortCastleCapable  Whether the King is capable of performing a short castle.
     * @param longCastleCapable   Whether the King is capable of performing a long castle.
     */
    public King(final int pieceCoord, final Color pieceColor, final boolean isFirstMove, final boolean isCastled,
                final boolean shortCastleCapable, final boolean longCastleCapable) {
        super(PieceType.KING, pieceCoord, pieceColor, isFirstMove);
        this.isCastled = isCastled;
        this.shortCastleCapable = shortCastleCapable;
        this.longCastleCapable = longCastleCapable;
    }
    /**
     * Determines all the legal moves that the King can make on the chessboard.
     *
     * @param board The current state of the chessboard.
     * @return A list of legal moves that the King can make.
     */
    @Override
    public List<Move> getLegalMoves(Board board) {
        // Initialize an empty list to store legal moves
        final List<Move> legalMoves = new ArrayList<>();

        // Iterate through each preset offset value representing possible King movements
        for (final int currentOffset : PRESET_OFFSET) {
            // Calculate the possible destination coordinate
            int possibleDestinationCoord = this.pieceCoord + currentOffset;

            // Check if the possible destination coordinate is in the first or eighth column,
            // and continue to the next offset if it is
            if (isFirstCol(this.pieceCoord, currentOffset) || isEighthCol(this.pieceCoord, currentOffset)) {
                continue;
            }

            // Check if the possible destination coordinate is valid
            if (BoardData.isValidSquareCoord(possibleDestinationCoord)) {
                // Retrieve the square at the possible destination coordinate from the chessboard
                final Square possibleDestinationSquare = board.getSquare(possibleDestinationCoord);

                // If the square is not occupied, add a regular move to the legal moves list
                if (!possibleDestinationSquare.isOccupied()) {
                    legalMoves.add(new Move.MajorPieceRegularMove(board, this, possibleDestinationCoord));
                } else {
                    // If the square is occupied by an opponent's piece, add an attack move to the legal moves list
                    final Piece pieceAtDestination = possibleDestinationSquare.getPiece();
                    final Color pieceColor = pieceAtDestination.getPieceColor();
                    if (this.pieceColor != pieceColor) {
                        legalMoves.add(new Move.MajorPieceAttackMove(board, this, pieceAtDestination, possibleDestinationCoord));
                    }
                }
            }
        }

        // Return the list of legal moves
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
