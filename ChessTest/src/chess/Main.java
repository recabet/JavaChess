
package chess;

import chess.engine.board.Board;
import chess.gui.Table;

import javax.swing.*;
import java.io.IOException;

public class Main
{
    public static void main(String[]args) throws IOException {
        Board board=Board.initStdBoard();
        System.out.println(board);   // debugging purposes
        Table table=new Table();
    }
}
