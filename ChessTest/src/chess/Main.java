package chess;

import chess.engine.board.Board;
import chess.gui.Table;

import javax.swing.*;

public class Main
{
    public static void main(String[]args)
    {
        Board board=Board.initStdBoard();
        System.out.println(board);
        Table table=new Table();

    }

}
