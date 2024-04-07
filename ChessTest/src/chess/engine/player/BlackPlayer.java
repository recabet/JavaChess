package chess.engine.player;

import chess.Color;
import chess.engine.board.Square;
import chess.engine.pieces.*;
import chess.engine.board.Board;
import chess.engine.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlackPlayer extends Player
{
    public BlackPlayer(Board board, Collection<Move> legalBlackStdLegalMoves, Collection<Move> legalWhiteStdLegalMoves)
    {
        super(board, legalBlackStdLegalMoves, legalWhiteStdLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces()
    {
        return this.board.getBlackPieces();
    }

    @Override
    public Color getColor()
    {
        return Color.BLACK;
    }

    @Override
    public Player getEnemy()
    {
        return this.board.whitePlayer();
    }

    @Override
    protected Collection<Move> calcCastle(final Collection<Move> playerLegalMoves, final Collection<Move> enemyLegalMoves)
    {
        final List<Move> shortCastleMoves = new ArrayList<>();
        if (this.playerKing.isFirstMove() && !this.isInCheck())
        {
            if (!(this.board.getSquare(6).isOccupied()) && this.board.getSquare(5).isOccupied())
            {
                final Square rookCoord = this.board.getSquare(7);
                if (rookCoord.isOccupied() && rookCoord.getPiece().isFirstMove() && (Player.calcAttackOnSquare(5, enemyLegalMoves).isEmpty()) && (Player.calcAttackOnSquare(6, enemyLegalMoves).isEmpty() && rookCoord.getPiece().getPieceType().isRook()))
                {
                    shortCastleMoves.add(new Move.ShortCastle(this.board,this.playerKing,6,(Rook)rookCoord.getPiece(),rookCoord.getCoord(),5));
                }
            }
            if (!(this.board.getSquare(1).isOccupied() && this.board.getSquare(3).isOccupied() && this.board.getSquare(2    ).isOccupied()))
            {
                final Square rookCoord = this.board.getSquare(0);
                if (rookCoord.isOccupied() && rookCoord.getPiece().isFirstMove())
                {
                    shortCastleMoves.add(new Move.LongCastle(this.board,this.playerKing,2,(Rook)rookCoord.getPiece(),rookCoord.getCoord(),3));
                }
            }
        }
        return shortCastleMoves;
    }
}

