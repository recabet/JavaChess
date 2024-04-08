package chess.gui;
import chess.engine.board.Board;
import chess.engine.board.BoardData;
import chess.engine.board.Move;
import chess.engine.board.Square;
import chess.engine.pieces.Piece;
import chess.engine.player.Changer;
import chess.engine.player.Player;

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
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {
    private final JFrame gameFrame;
    private BoardPanel boardPanel;
    private Board chessBoard;
    private static Dimension FRAME_DIMENSION=new Dimension(600,600);
    private static Dimension PANEL_BOARD_DIMENSION=new Dimension(400,350);
    private static Dimension PANEL_SQUARE_DIMENSION=new Dimension(10,10);
    public final Color lightTileColor = Color.decode("#FFFACD");
    public final Color darkTileColor = Color.decode("#593E1A");
    private String pieceIconPath="images/";
    private Square sourceSquare;
    private Square destinationSquare;
    private Piece humanMovedPiece;


    public Table() throws IOException {
        this.gameFrame=new JFrame("Java Chess");
        this.gameFrame.setSize(FRAME_DIMENSION);
        this.chessBoard=Board.initStdBoard();
        this.gameFrame.setJMenuBar(constructJMenu());
        this.boardPanel=new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);

    }
    // Creates JMenuBar and adds JMenu using addFileMenu method
    public static JMenuBar constructJMenu()
    {
        final JMenuBar menuBar=new JMenuBar();

        menuBar.add(addFileMenu());
        return menuBar;

    }
    //Creates Menu named "File" and Menu item.When clicking item, it prints something.
    public static JMenu addFileMenu()
    {
        final JMenu fileMenu=new JMenu("File");

        final JMenuItem openPNG =new JMenuItem("Load PNG file");
        openPNG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("open up pgn file!");
            }
        });
        fileMenu.add(openPNG);
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
    //Defining two classes for board and tile
    private class BoardPanel extends Panel
    {
        final List<SquarePanel> squarePanels;
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

        public void drawBoard(Board chessBoard) {
            removeAll();
            for(SquarePanel squarePanel:squarePanels)
            {
                squarePanel.drawSquare(chessBoard);
                add(squarePanel);
            }
            validate();
            repaint();
        }
    }
    private class SquarePanel extends Panel
    {
        private final int SquareId;

        SquarePanel(BoardPanel boardPanel, int squareId) throws IOException {
            super(new GridLayout());
            SquareId=squareId;
            setPreferredSize(PANEL_SQUARE_DIMENSION);
            assignSquarePiece(chessBoard);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
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
                            System.out.println(destinationSquare.getCoord());
                            final Move move = Move.MoveCreator.moveCreate(chessBoard, sourceSquare.getCoord(), destinationSquare.getCoord());
                            Player currentPlayer = chessBoard.getCurrentPlayer();
                            System.out.println(currentPlayer);
                            Changer changer1 = currentPlayer.makeMove(move);
                            System.out.println(changer1);
                            final Changer changer = chessBoard.getCurrentPlayer().makeMove(move);
                            if (changer.getMoveSt().isDone()) {
                                chessBoard = changer.getChangedBoard();
                            }
                            sourceSquare = null;
                            destinationSquare = null;
                            humanMovedPiece = null;
                        }
                        //Why we need this?
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {

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
            validate();//why we use it?
        }
        private void assignSquarePiece(final Board board) {
            this.removeAll();
            if(board.getSquare(this.SquareId).isOccupied()) {
                try{
                    final BufferedImage image = ImageIO.read(new File(pieceIconPath +
                            board.getSquare(this.SquareId).getPiece().getPieceColor().toString().charAt(0) +
                            board.getSquare(this.SquareId).getPiece().toString() +
                            ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch(final IOException e) {
                    e.printStackTrace();
                }
            }

        }

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

        public void drawSquare(Board chessBoard) {
            assignSquareColor();
            assignSquarePiece(chessBoard);
            validate();
            repaint();

        }
    }
}


