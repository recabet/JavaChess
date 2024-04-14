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

}
