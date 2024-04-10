package chess.gui;

import chess.engine.board.Board;
import chess.engine.board.Move;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryPanel extends JPanel {

    private final DataModel model;
    private final JScrollPane scrollPane;
    private static final Dimension HISTORY_PANEL_DIMENSION = new Dimension(100, 400);

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

    private String calculateCheckStHash(final Board board) {
        if (board.getCurrentPlayer().isMated()) {
            return "#";
        }
        else if (board.getCurrentPlayer().isInCheck()) {
            return "+";
        }
        return "";
    }

    private static class DataModel extends DefaultTableModel {
        private final List<Row> values;
        private static final String[] NAMES = {"White", "Black"};

        DataModel() {
            this.values = new ArrayList<>();
        }

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

    private static class Row {

        private String whiteMove;
        private String blackMove;

        Row() {

        }

        public String getWhiteMove() {
            return this.whiteMove;
        }
        public String getBlackMove() {
            return this.blackMove;
        }
        public void setWhiteMove(final String move) {
            this.whiteMove = move;
        }
        public void setBlackMove(final String move) {
            this.blackMove = move;
        }
    }
}
