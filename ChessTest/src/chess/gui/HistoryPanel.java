package chess.gui;

import chess.logic.board.Board;
import chess.logic.board.Move;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel to display the move history.
 */
public class HistoryPanel extends JPanel {

    private final DataModel model;
    private final JScrollPane scrollPane;
    private static final Dimension HISTORY_PANEL_DIMENSION = new Dimension(100, 400);

    /**
     * Constructs a HistoryPanel.
     */
    HistoryPanel() {
        this.setLayout(new BorderLayout());
        this.model = new DataModel();
        final  JTable table = new JTable(model);
        table.setRowHeight(15);
        this.scrollPane = new JScrollPane(table);
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.setPreferredSize(HISTORY_PANEL_DIMENSION);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    /**
     * Updates the move history panel.
     *
     * @param board   The current board.
     * @param movelog The move log.
     */
    void redo(final Board board, final Table.Movelog movelog) {
        int currentRow = 0;
        this.model.clear();
        for (final Move move : movelog.getMoves()) {
            final String moveText = move.toString();
            if (move.getMovedPiece().getPieceColor().isWhite()) {
                this.model.setValueAt(moveText, currentRow, 0);
            }
            else if (move.getMovedPiece().getPieceColor().isBlack()) {
                this.model.setValueAt(moveText, currentRow, 1);
                currentRow++;
            }
        }
        if (movelog.getMoves().size() > 0) {
            final Move lastMove = movelog.getMoves().get(movelog.size() - 1);
            final String moveText = lastMove.toString();

            if (lastMove.getMovedPiece().getPieceColor().isWhite()) {
                this.model.setValueAt(moveText + calculateCheckStHash(board), currentRow, 0);
            }
            else if (lastMove.getMovedPiece().getPieceColor().isBlack()) {
                this.model.setValueAt(moveText + calculateCheckStHash(board), currentRow -1, 1);
            }
        }
        final JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    /**
     * Calculates the check status hash for the given board.
     *
     * @param board The current board.
     * @return The check status hash.
     */
    private String calculateCheckStHash(final Board board) {
        if (board.getCurrentPlayer().isMated()) {
            return "#";
        }
        else if (board.getCurrentPlayer().isInCheck()) {
            return "+";
        }
        return "";
    }

    /**
     * Custom data model for the move history table.
     */
    private static class DataModel extends DefaultTableModel {
        private final List<Row> values;
        private static final String[] NAMES = {"White", "Black"};

        DataModel() {
            this.values = new ArrayList<>();
        }

        /**
         * Clears the data model.
         */
        public void clear() {
            this.values.clear();
            setRowCount(0);
        }
        @Override
        public int getRowCount() {
            if (this.values == null) {
                return 0;
            }
            return this.values.size();
        }
        @Override
        public int getColumnCount() {
            return NAMES.length;
        }
        @Override
        public Object getValueAt(final int row, final int col) {
            final Row currentRow = this.values.get(row);
            if (col == 0) {
                return currentRow.getWhiteMove();
            }
            else if (col == 1) {
                return currentRow.getBlackMove();
            }
            return null;
        }
        @Override
        public void setValueAt(final Object aValue, final int row, final int col) {
            final Row currentRow;
            if (this.values.size() <= row) {
                currentRow = new Row();
                this.values.add(currentRow);
            }
            else {
                currentRow = this.values.get(row);
            }

            if (col == 0) {
                currentRow.setWhiteMove((String)aValue);
                fireTableRowsInserted(row, row);
            }
            else if (col == 1) {
                currentRow.setBlackMove((String)aValue);
                fireTableCellUpdated(row, col);
            }
        }
        @Override
        public Class<?> getColumnClass(final int col) {
            return Move.class;
        }
        @Override
        public String getColumnName(final int col) {
            return NAMES[col];
        }
    }

    /**
     * Represents a row in the move history table.
     */
    private static class Row {

        private String whiteMove;
        private String blackMove;

        /**
         * Constructs a Row object.
         */
        Row() {

        }

        /**
         * Gets the move made by the white player.
         *
         * @return The white player's move.
         */
        public String getWhiteMove() {
            return this.whiteMove;
        }

        /**
         * Gets the move made by the black player.
         *
         * @return The black player's move.
         */
        public String getBlackMove() {
            return this.blackMove;
        }

        /**
         * Sets the move made by the white player.
         *
         * @param move The white player's move.
         */
        public void setWhiteMove(final String move) {
            this.whiteMove = move;
        }

        /**
         * Sets the move made by the black player.
         *
         * @param move The black player's move.
         */
        public void setBlackMove(final String move) {
            this.blackMove = move;
        }
    }
}
