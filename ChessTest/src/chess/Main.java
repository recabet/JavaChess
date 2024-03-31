package chess;

import chess.board.Board;

public class Main
{
    public static void main(String[]args)
    {
        Board board=Board.initStdBoard();
        System.out.println(board);
    }
}
