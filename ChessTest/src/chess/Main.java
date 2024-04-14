package chess;

import chess.logic.board.Board;
import chess.gui.Table;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Initialize a standard chess board
        Board board = Board.initStdBoard();

        // Print the board (for debugging purposes)
        System.out.println(board);

        // Create a new Table instance (the GUI)
        Table table = new Table();
    }
}
