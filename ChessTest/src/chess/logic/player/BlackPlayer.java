package chess.logic.player;

import chess.Color;
import chess.logic.board.Square;
import chess.logic.pieces.*;
import chess.logic.board.Board;
import chess.logic.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a player with black pieces in a chess game.
 */
public class BlackPlayer extends Player {
    /**
     * Initializes a black player with the given board and legal moves.
     *
     * @param board                   The board associated with the player.
     * @param legalBlackStdLegalMoves Legal moves available for black pieces.
     * @param legalWhiteStdLegalMoves Legal moves available for white pieces.
     */
    public BlackPlayer(Board board, Collection<Move> legalBlackStdLegalMoves, Collection<Move> legalWhiteStdLegalMoves)
    {
        super(board, legalBlackStdLegalMoves, legalWhiteStdLegalMoves);
    }

    /**
     * Retrieves the active black pieces controlled by this player.
     *
     * @return Collection of active black pieces.
     */
    @Override
    public Collection<Piece> getActivePieces()
    {
        return this.board.getBlackPieces();
    }

    /**
     * Gets the color associated with this player.
     *
     * @return The color of the player (BLACK).
     */
    @Override
    public Color getColor()
    {
        return Color.BLACK;
    }

    /**
     * Retrieves the opponent player.
     *
     * @return The player controlling the white pieces.
     */
    @Override
    public Player getEnemy()
    {
        return this.board.whitePlayer();
    }

    @Override
    protected Collection<Move> calcCastle(Collection<Move> playerLegalMoves, Collection<Move> enemyLegalMoves)
    {
        final List<Move> kingCastle = new ArrayList<>();
        if(this.playerKing.isFirstMove() && !this.isInCheck())
        {
            if(!this.board.getSquare(5).isOccupied() && !this.board.getSquare(6).isOccupied())
            {
                final Square rookSquare = this.board.getSquare(7);
                if(rookSquare.isOccupied() && rookSquare.getPiece().isFirstMove())
                {
                    if(Player.calcAttackOnSquare(5, enemyLegalMoves).isEmpty() && Player.calcAttackOnSquare(6, enemyLegalMoves).isEmpty() && rookSquare.getPiece().getPieceType().isRook())
                    {
                        kingCastle.add(null);
                    }

                }
            }
            if(!this.board.getSquare(1).isOccupied() && !this.board.getSquare(2).isOccupied() && !this.board.getSquare(3).isOccupied())
            {
                final Square rookSquare = this.board.getSquare(0);
                if(rookSquare.isOccupied() && rookSquare.getPiece().isFirstMove())
                {
                    kingCastle.add(null);
                }
            }
        }
        return kingCastle;
    }
}
