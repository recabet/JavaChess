/**
 * The Table class represents the graphical user interface (GUI) for a chess game.
 * It includes the game frame, board panel, history panel, and taken pieces panel.
 */
package chess.gui;
import chess.logic.board.Board;
import chess.logic.board.BoardData;
import chess.logic.board.Move;
import chess.logic.board.Square;
import chess.logic.pieces.Piece;
import chess.logic.player.Changer;
import chess.logic.player.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javax.swing.SwingUtilities.*;
/**
 * Represents the graphical user interface (GUI) for a chess game.
 */
public class Table {
    private final JFrame gameFrame;// The main frame of the game
    private final HistoryPanel gameHistoryPanel;// Panel to display game history
    private final TakenPiecesPanel takenPiecesPanel;// Panel to display taken pieces
    private BoardPanel boardPanel;// Panel to display the chess board
    private final Movelog moveLog;// Records the moves made in the game
    private Board chessBoard;// Represents the current state of the chess board
    private static Dimension FRAME_DIMENSION=new Dimension(600,600);// Dimension of the game frame
    private static Dimension PANEL_BOARD_DIMENSION=new Dimension(400,350);// Dimension of the board panel
    private static Dimension PANEL_SQUARE_DIMENSION=new Dimension(10,10);// Dimension of each square on the board
    public final Color lightTileColor = Color.decode("#FFFACD");// Color of light tiles on the board
    public final Color darkTileColor = Color.decode("#0e8080");// Color of dark tiles on the board
    private String pieceIconPath="images/fancy/";// Path to the directory containing piece icons
    private Square sourceSquare;// The source square of a move
    private Square destinationSquare;// The destination square of a move
    private Piece humanMovedPiece;// The piece moved by the human player
    private BoardDirection boardDirection; // Direction of the board (normal or flipped)

    private boolean highlightLegals;// Indicates whether legal moves should be highlighted

    /**
     * Constructs a new Table object and initializes the game GUI.
     *
     * @throws IOException if an error occurs while loading resources
     */
    public Table() throws IOException {
        this.gameFrame=new JFrame("Java Chess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = constructJMenu();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(FRAME_DIMENSION);
        this.chessBoard=Board.initStdBoard();
        this.gameHistoryPanel = new HistoryPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.boardPanel=new BoardPanel();
        this.moveLog = new Movelog();
        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegals = false;
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.setVisible(true);
    }
    /**
     * Constructs the menu bar for the game.
     *
     * @return the constructed JMenuBar
     */
    private JMenuBar constructJMenu()
    {
        final JMenuBar menuBar=new JMenuBar();

        menuBar.add(addFileMenu());
        menuBar.add(initPreferencesMenu());
        return menuBar;
    }
    /**
     * Adds a "File" menu to the menu bar with associated menu items.
     *
     * @return the constructed JMenu
     */
    public static JMenu addFileMenu()
    {
        final JMenu fileMenu=new JMenu("File");

        final JMenuItem exit=new JMenuItem("Exit");
        fileMenu.add(exit);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        return fileMenu;

    }
    /**
     * Adds a "File" menu to the menu bar with associated menu items.
     *
     * @return the constructed JMenu
     */
    private JMenu initPreferencesMenu()
    {
        final JMenu preferencesMenu = new JMenu("Preferences");
        final JMenuItem boardFlipMenuItem = new JMenuItem("Flip the Board");
        // Menu item for flipping the board
        boardFlipMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardDirection = boardDirection.opposite();
                boardPanel.drawBoard(chessBoard);
            }
        });
        preferencesMenu.add(boardFlipMenuItem);

        preferencesMenu.addSeparator();
        // Checkbox item for toggling highlighting of legal moves
        final JCheckBoxMenuItem highlighterCheckbox = new JCheckBoxMenuItem("Highlight legal moves", false);
        highlighterCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                highlightLegals = highlighterCheckbox.isSelected();
            }
        });
        preferencesMenu.add(highlighterCheckbox);

        return preferencesMenu;
    }
    /**
     * Enum representing the direction of the chess board.
     */
    public enum BoardDirection
    {
        NORMAL {
            @Override
            List<SquarePanel> traverse(final List<SquarePanel> boardSquares) {
                return boardSquares;
            }
            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<SquarePanel> traverse(final List<SquarePanel> boardSquares) {
                return boardSquares.reversed();
            }
            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };
        abstract List<SquarePanel> traverse(final List<SquarePanel> boardSquares);
        abstract BoardDirection opposite();
    }
    /**
     * Represents the panel for displaying the chess board.
     */
    private class BoardPanel extends Panel
    {
        final List<SquarePanel> squarePanels;// List of square panels representing the board squares
        /**
         * Constructs a new BoardPanel.
         *
         * @throws IOException if an error occurs while loading resources
         */
        BoardPanel() throws IOException {
            super(new GridLayout(8,8));
            squarePanels=new ArrayList<>();
            for (int i=0; i<64; i++)
            {
                SquarePanel squarePanel=new SquarePanel(this, i);
                squarePanels.add(squarePanel);
                add(squarePanel);
                setPreferredSize(PANEL_BOARD_DIMENSION);
                validate();
            }
        }
        /**
         * Draws the chess board based on the provided board state.
         *
         * @param chessBoard the current state of the chess board
         */
        public void drawBoard(Board chessBoard) {
            removeAll();
            for(final SquarePanel squarePanel : boardDirection.traverse(squarePanels))
            {
                squarePanel.drawSquare(chessBoard);
                add(squarePanel);
            }
            validate();
            repaint();
        }
    }
    /**
     * Represents the move log of the game.
     */
    public static class Movelog {
        private final List<Move> moves;// List of moves made in the game

        Movelog() {
            this.moves = new ArrayList<>();
        }
        /**
         * Gets the list of moves in the move log.
         *
         * @return the list of moves
         */
        public List<Move> getMoves() {
            return this.moves;
        }
        /**
         * Adds a move to the move log.
         *
         * @param move the move to add
         */
        public void addMove(final Move move) {
            this.moves.add(move);
        }
        /**
         * Gets the number of moves in the move log.
         *
         * @return the number of moves
         */
        public int size() {
            return this.moves.size();
        }
        /**
         * Clears all moves from the move log.
         */
        public void clear() {
            this.moves.clear();
        }
        /**
         * Removes a move from the move log at the specified index.
         *
         * @param index the index of the move to remove
         * @return the removed move
         */
        public Move removeMove(int index) {
            return this.moves.remove(index);
        }
        /**
         * Removes a specific move from the move log.
         *
         * @param move the move to remove
         * @return true if the move was successfully removed, false otherwise
         */
        public boolean removeMove(final Move move) {
            return this.moves.remove(move);
        }
    }
    /**
     * Represents a square (a tile) on the chess board.
     */
    private class SquarePanel extends Panel
    {
        private final int SquareId;
        /**
         * Constructs a new SquarePanel.
         *
         * @param boardPanel the parent board panel
         * @param squareId   the ID of the square
         * @throws IOException if an error occurs while loading resources
         */
        SquarePanel(BoardPanel boardPanel, int squareId) throws IOException {
            super(new GridLayout());
            SquareId=squareId;
            setPreferredSize(PANEL_SQUARE_DIMENSION);
            assignSquarePiece(chessBoard);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Mouse event handlers
                    if(isRightMouseButton(e))
                    {
                        sourceSquare=null;
                        destinationSquare=null;
                        humanMovedPiece=null;
                    }
                    else if(isLeftMouseButton(e))
                    {
                        if(sourceSquare==null) {
                            sourceSquare = chessBoard.getSquare(squareId);
                            humanMovedPiece = sourceSquare.getPiece();
                            if (humanMovedPiece == null) {
                                sourceSquare = null;
                            }
                        }
                        else {
                            destinationSquare = chessBoard.getSquare(squareId);
                            final Move move = Move.MoveCreator.moveCreate(chessBoard, sourceSquare.getCoord(), destinationSquare.getCoord());
                            Player currentPlayer = chessBoard.getCurrentPlayer();
                            Changer changer1 = currentPlayer.makeMove(move);
                            final Changer changer = chessBoard.getCurrentPlayer().makeMove(move);
                            if (changer.getMoveSt().isDone()) {
                                chessBoard = changer.getChangedBoard();
                                moveLog.addMove(move);
                            }
                            sourceSquare = null;
                            destinationSquare = null;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                gameHistoryPanel.redo(chessBoard, moveLog);
                                takenPiecesPanel.redo(moveLog);
                                boardPanel.drawBoard(chessBoard);
                            }
                        });
                        }
                    }


                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            assignSquareColor();
            validate(); // Validates the layout of the panel
        }
        /**
         * Assigns the piece to the square based on the current board state.
         *
         * @param board the current state of the chess board
         */
        private void assignSquarePiece(final Board board) {
            this.removeAll();
            if(board.getSquare(this.SquareId).isOccupied()) {
                try{
                    final BufferedImage image = ImageIO.read(new File(pieceIconPath +
                            board.getSquare(this.SquareId).getPiece().getPieceColor().toString().substring(0, 1) +
                            board.getSquare(this.SquareId).getPiece().toString() +
                            ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch(final IOException e) {
                    e.printStackTrace();
                }
            }

        }
        /**
         * Highlights legal moves on the square.
         *
         * @param board the current state of the chess board
         */
        private void highlightLegalMoves(final Board board)
        {
            if (highlightLegals) {

                for (final Move move: LegalMovesOfPiece(board)) {
                    if (move.getDestinationCoord() == this.SquareId) {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("images/misc/green_dot.png")))));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        /**
         * Gets the legal moves of the piece on the square.
         *
         * @param board the current state of the chess board
         * @return a collection of legal moves
         */
        private Collection<Move> LegalMovesOfPiece(final Board board)
        {
            if (humanMovedPiece != null && humanMovedPiece.getPieceColor() == board.getCurrentPlayer().getColor()) {
                return humanMovedPiece.getLegalMoves(board);
            }
            return Collections.emptyList();
        }
        /**
         * Assigns the color to the square based on its position.
         */
        private void assignSquareColor() {
            if (BoardData.EIGHTH_RANK[this.SquareId] ||
                    BoardData.SIXTH_RANK[this.SquareId] ||
                    BoardData.FOURTH_RANK[this.SquareId] ||
                    BoardData.SECOND_RANK[this.SquareId] ){
                if (this.SquareId % 2 == 0) {
                    setBackground(lightTileColor);
                } else {
                    setBackground(darkTileColor);
                }
            } else if(BoardData.SEVENTH_RANK[this.SquareId] ||
                    BoardData.FIFTH_RANK[this.SquareId] ||
                    BoardData.THIRD_RANK[this.SquareId]  ||
                    BoardData.FIRST_RANK[this.SquareId]) {
                if (this.SquareId % 2 != 0) {
                    setBackground(lightTileColor);
                } else {
                    setBackground(darkTileColor);
                }
            }

        }
        /**
         * Draws the square on the chess board.
         *
         * @param chessBoard the current state of the chess board
         */
        public void drawSquare(Board chessBoard) {
            assignSquareColor();
            assignSquarePiece(chessBoard);
            highlightLegalMoves(chessBoard);
            validate();
            repaint();
        }
    }
}


