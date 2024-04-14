package chess.engine.player;

import chess.Color;
import chess.engine.pieces.*;
import chess.engine.board.Board;
import chess.engine.board.Move;

import java.util.Collection;

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

}

