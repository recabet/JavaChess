package chess.engine.player;
import chess.Color;
import chess.engine.board.Square;
import chess.engine.pieces.*;
import chess.engine.board.Board;
import chess.engine.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WhitePlayer extends Player
{
    public WhitePlayer(Board board, Collection<Move> legalWhiteStdLegalMoves, Collection<Move> legalBlackStdLegalMoves)
    {
        super(board,legalWhiteStdLegalMoves,legalBlackStdLegalMoves);

    }

    @Override
    public Collection<Piece> getActivePieces()
    {
        return this.board.getWhitePieces();
    }

    @Override
    public Color getColor()
    {
        return Color.WHITE;
    }

    @Override
    public Player getEnemy()
    {
        return this.board.blackPlayer();
    }

    @Override
    protected Collection<Move> calcCastle(final Collection<Move> playerLegalMoves,final  Collection<Move> enemyLegalMoves)
    {
        final List<Move> shortCastleMoves =new ArrayList<>();
        if(this.playerKing.isFirstMove()&&!this.isInCheck())
        {
            if(!(this.board.getSquare(61).isOccupied()) && this.board.getSquare(62).isOccupied())
            {
                final Square rookCoord=this.board.getSquare(63);
                if(rookCoord.isOccupied()&&rookCoord.getPiece().isFirstMove() &&(Player.calcAttackOnSquare(61,enemyLegalMoves).isEmpty()) &&(Player.calcAttackOnSquare(62,enemyLegalMoves).isEmpty() && rookCoord.getPiece().getPieceType().isRook()))
                {
                    shortCastleMoves.add(new Move.ShortCastle(this.board,this.playerKing,62,(Rook) rookCoord.getPiece(),rookCoord.getCoord(),61));
                }

            }
            if(!(this.board.getSquare(59).isOccupied() && this.board.getSquare(58).isOccupied() &&this.board.getSquare(57).isOccupied()))
            {
                final Square rookCoord=this.board.getSquare(56);
                if(rookCoord.isOccupied()&&rookCoord.getPiece().isFirstMove())
                {
                    shortCastleMoves.add(new Move.LongCastle(this.board,this.playerKing,58,(Rook)rookCoord.getPiece(),rookCoord.getCoord(),59));
                }
            }
        }
        return shortCastleMoves;
    }
}
