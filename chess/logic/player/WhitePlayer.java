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
 * Represents a white player in a chess game.
 */
public class WhitePlayer extends Player {

    /**
     * Constructs a WhitePlayer object.
     *
     * @param board                   The chess board.
     * @param legalWhiteStdLegalMoves Collection of legal moves for the white player.
     * @param legalBlackStdLegalMoves Collection of legal moves for the black player.
     */
    public WhitePlayer(Board board, Collection<Move> legalWhiteStdLegalMoves, Collection<Move> legalBlackStdLegalMoves)
    {
        super(board, legalWhiteStdLegalMoves, legalBlackStdLegalMoves);
    }

    /**
     * Gets the active pieces for the white player.
     *
     * @return Collection of active pieces for the white player.
     */
    @Override
    public Collection<Piece> getActivePieces()
    {
        return this.board.getWhitePieces();
    }

    /**
     * Gets the color of the player.
     *
     * @return The color of the player.
     */
    @Override
    public Color getColor()
    {
        return Color.WHITE;
    }

    /**
     * Gets the enemy player (black player).
     *
     * @return The enemy player (black player).
     */
    @Override
    public Player getEnemy()
    {
        return this.board.blackPlayer();
    }

    @Override
    protected Collection<Move> calcCastle(Collection<Move> playerLegalMoves, Collection<Move> enemyLegalMoves)
    {
        final List<Move> kingCastle = new ArrayList<>();
        if(this.playerKing.isFirstMove() && !this.isInCheck())
        {
            if(!this.board.getSquare(61).isOccupied() && !this.board.getSquare(62).isOccupied())
            {
                final Square rookSquare = this.board.getSquare(63);
                if(rookSquare.isOccupied() && rookSquare.getPiece().isFirstMove())
                {
                    if(Player.calcAttackOnSquare(61, enemyLegalMoves).isEmpty() && Player.calcAttackOnSquare(62, enemyLegalMoves).isEmpty() && rookSquare.getPiece().getPieceType().isRook())
                    {
                        kingCastle.add(null);
                    }

                }
            }
            if(!this.board.getSquare(57).isOccupied() && !this.board.getSquare(58).isOccupied() && !this.board.getSquare(59).isOccupied())
            {
                final Square rookSquare = this.board.getSquare(56);
                if(rookSquare.isOccupied() && rookSquare.getPiece().isFirstMove())
                {
                    kingCastle.add(null);
                }
            }
        }
        return kingCastle;
    }
}
