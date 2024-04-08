package chess.engine.player;

import chess.Color;
import chess.engine.board.*;
import chess.engine.pieces.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player
{
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> enemyMoves)
    {
        this.board = board;
        this.playerKing = createKing();
        this.legalMoves=new ArrayList<>(legalMoves);
        this.legalMoves.addAll(calcCastle(legalMoves,enemyMoves));
        this.isInCheck = !Player.calcAttackOnSquare(this.playerKing.getPieceCoord(), enemyMoves).isEmpty();
    }

    public static Collection<Move> calcAttackOnSquare(int pieceCoord, Collection<Move> moves)
    {
        final List<Move> attackMoves = new ArrayList<>();
        for (final Move move : moves)
        {
            if (pieceCoord == move.getDestinationCoord())
            {
                attackMoves.add(move);
            }
        }
        return attackMoves;
    }

    private King createKing()
    {
        for (final Piece piece : getActivePieces())
        {
            if (piece.getPieceType().isKing())
            {
                return (King) piece;
            }
        }
        throw new RuntimeException("HOW ARE YOU PLAYING WITHOUT A KING??");
    }

    public boolean isMoveLegal(final Move move)
    {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck()
    {
        return this.isInCheck;
    }

    public boolean isMated()
    {
        return this.isInCheck && !hasEscape();
    }

    private boolean hasEscape()
    {
        for (final Move move : this.legalMoves)
        {
            final Changer changeMove = makeMove(move);
            if (changeMove.getMoveSt().isDone())
            {
                return true;
            }
        }
        return false;
    }
//changed to public
    public Changer makeMove(final Move move)
    {
        if (!isMoveLegal(move))
        {
            return new Changer(this.board, move, MoveSt.ILLEGAL_MOVE);
        }
        final Board changedBoard = move.make();
        final Collection<Move> attacksOnKing = Player.calcAttackOnSquare(changedBoard.getCurrentPlayer().getEnemy().getPlayerKing().getPieceCoord(), changedBoard.getCurrentPlayer().getLegalMoves());
        if(!attacksOnKing.isEmpty())
        {
            return new Changer(this.board,move,MoveSt.IN_CHECK);
        }
        return new Changer(changedBoard,move,MoveSt.DONE);
    }

    public boolean isStalemate()
    {
        return !this.isInCheck && !hasEscape();
    }

    public King getPlayerKing()
    {
        return this.playerKing;
    }

    public boolean isCastled()
    {
        return false;
    }

    public Collection<Move> getLegalMoves()
    {
    return this.legalMoves;
    }

    public Changer MakeMove(final Move move)
    {
        return null;
    }//why need this?

    public abstract Collection<Piece> getActivePieces();

    public abstract Color getColor();

    public abstract Player getEnemy();
    protected abstract Collection<Move> calcCastle(Collection<Move> playerLegalMoves,Collection<Move> enemyLegalMoves);


}
