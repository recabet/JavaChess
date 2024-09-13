package chess.logic.player;

import chess.Color;
import chess.logic.board.*;
import chess.logic.pieces.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class representing a player in the chess game.
 */
public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    /**
     * Constructor for Player class.
     *
     * @param board       The chess board.
     * @param playerMoves Collection of legal moves for the player.
     * @param enemyMoves  Collection of legal moves for the opponent.
     */
    Player(final Board board, final Collection<Move> playerMoves, final Collection<Move> enemyMoves)
    {
        this.board = board;
        this.playerKing = createKing();
        this.isInCheck = !Player.calcAttackOnSquare(this.playerKing.getPieceCoord(), enemyMoves).isEmpty();
        this.legalMoves = Collections.unmodifiableCollection(playerMoves);
    }

    /**
     * Calculates the attack moves on a specific square.
     *
     * @param pieceCoord The coordinate of the square.
     * @param moves      Collection of moves to check.
     * @return Collection of attack moves on the specified square.
     */
    public static Collection<Move> calcAttackOnSquare(int pieceCoord, Collection<Move> moves)
    {
        final List<Move> attackMoves = new ArrayList<>();
        for(final Move move : moves)
        {
            if(pieceCoord == move.getDestinationCoord())
            {
                attackMoves.add(move);
            }
        }
        return attackMoves;
    }

    /**
     * Creates the player's king piece.
     *
     * @return The king piece.
     */
    private King createKing()
    {
        for(final Piece piece : getActivePieces())
        {
            if(piece.getPieceType().isKing())
            {
                return (King) piece;
            }
        }
        throw new RuntimeException("HOW ARE YOU PLAYING WITHOUT A KING??");
    }

    /**
     * Checks if a move is legal for the player.
     *
     * @param move The move to check.
     * @return True if the move is legal, false otherwise.
     */
    public boolean isMoveLegal(final Move move)
    {
        return this.legalMoves.contains(move);
    }

    /**
     * Checks if the player is in check.
     *
     * @return True if the player is in check, false otherwise.
     */
    public boolean isInCheck()
    {
        return this.isInCheck;
    }

    /**
     * Checks if the player is mated (in checkmate).
     *
     * @return True if the player is mated, false otherwise.
     */
    public boolean isMated()
    {
        return this.isInCheck && hasEscape();
    }

    /**
     * Checks if the player has an escape move.
     *
     * @return True if the player has an escape move, false otherwise.
     */
    private boolean hasEscape()
    {
        for(final Move move : this.legalMoves)
        {
            final Changer changeMove = makeMove(move);
            if(changeMove.moveSt().isDone())
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Makes a move for the player and returns the result.
     *
     * @param move The move to make.
     * @return A Changer object containing the changed board and the move state.
     */
    public Changer makeMove(final Move move)
    {
        if(!isMoveLegal(move))
        {
            return new Changer(this.board, move, MoveSt.ILLEGAL_MOVE);
        }
        final Board changedBoard = move.make();
        final Collection<Move> attacksOnKing = Player.calcAttackOnSquare(changedBoard.getCurrentPlayer().getEnemy().getPlayerKing().getPieceCoord(), changedBoard.getCurrentPlayer().getLegalMoves());
        if(!attacksOnKing.isEmpty())
        {
            return new Changer(this.board, move, MoveSt.IN_CHECK);
        }
        return new Changer(changedBoard, move, MoveSt.DONE);
    }

    /**
     * Checks if the player is in stalemate.
     *
     * @return True if the player is in stalemate, false otherwise.
     */
    public boolean isStalemate()
    {
        return !this.isInCheck && hasEscape();
    }

    /**
     * Gets the player's king piece.
     *
     * @return The player's king piece.
     */
    public King getPlayerKing()
    {
        return this.playerKing;
    }

    /**
     * Checks if the player has castled.
     *
     * @return True if the player has castled, false otherwise.
     */
    public boolean isCastled()
    {
        return false;
    }

    /**
     * Gets the legal moves for the player.
     *
     * @return Collection of legal moves for the player.
     */
    public Collection<Move> getLegalMoves()
    {
        return this.legalMoves;
    }

    // why need this?
    public Changer MakeMove(final Move move)
    {
        return null;
    }

    /**
     * Abstract method to get the active pieces for the player.
     *
     * @return Collection of active pieces for the player.
     */
    public abstract Collection<Piece> getActivePieces();

    /**
     * Abstract method to get the color of the player.
     *
     * @return The color of the player.
     */
    public abstract Color getColor();

    /**
     * Abstract method to get the enemy player.
     *
     * @return The enemy player.
     */
    public abstract Player getEnemy();

    protected abstract Collection<Move> calcCastle(Collection<Move> playerLegalMoves, Collection<Move> enemyLegalMoves);
}
