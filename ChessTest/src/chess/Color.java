package chess;

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
        public Player selectPlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer)
        {
            return blackPlayer;
        }
    };

    public abstract int getDirection();
    public abstract boolean isWhite();

    public abstract boolean isBlack();

    public abstract Player selectPlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);

}
