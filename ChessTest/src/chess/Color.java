package chess;

import chess.engine.board.BoardData;
import chess.engine.player.BlackPlayer;
import chess.engine.player.Player;
import chess.engine.player.WhitePlayer;

public enum Color
{
    WHITE {
        @Override
        public int getDirection()
        {
            return -1;
        }

        @Override
        public boolean isWhite()
        {
            return true;
        }

        @Override
        public boolean isBlack()
        {
            return false;
        }

        @Override
        public int getOppositeDirection()
        { return 1;}

        @Override
        public boolean isPawnPromotionSquare(int squareId) {
            return BoardData.EIGHTH_RANK[squareId];
        }

        @Override
        public Player selectPlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer)
        {
            return whitePlayer;
        }
    }
        ,
    BLACK {
        @Override
        public int getDirection()
        {
            return 1;
        }

        @Override
        public boolean isWhite()
        {
            return false;
        }

        @Override
        public boolean isBlack()
        {
            return true;
        }
        @Override
        public int getOppositeDirection()
        { return -1;}

        @Override
        public boolean isPawnPromotionSquare(int squareId) {
            return BoardData.FIRST_RANK[squareId];
        }

        @Override
        public Player selectPlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer)
        {
            return blackPlayer;
        }
    };

    public abstract int getDirection();
    public abstract boolean isWhite();

    public abstract boolean isBlack();

    public abstract Player selectPlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
    public abstract int getOppositeDirection();
    public abstract boolean isPawnPromotionSquare(int squareId);

}
