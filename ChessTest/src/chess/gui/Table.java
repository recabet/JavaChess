package chess.gui;
import chess.engine.board.BoardData;
import chess.engine.board.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.cert.CertPathValidatorException;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private final JFrame gameFrame;
    private BoardPanel boardPanel;
    private static Dimension FRAME_DIMENSION=new Dimension(600,600);
    private static Dimension PANEL_BOARD_DIMENSION=new Dimension(400,350);
    private static Dimension PANEL_SQUARE_DIMENSION=new Dimension(10,10);
    public final Color lightTileColor = Color.decode("#FFFACD");
    public final Color darkTileColor = Color.decode("#593E1A");

    public Table()
    {
        this.gameFrame=new JFrame("Java Chess");
        this.gameFrame.setSize(FRAME_DIMENSION);

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
        return fileMenu;

    }
    //Defining two classes for board and tile
    private class BoardPanel extends Panel
    {
        final List<SquarePanel> squarePanels;
        BoardPanel()
        {
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

    }
    private class SquarePanel extends Panel
    {
        private final int SquareId;

        SquarePanel(BoardPanel boardPanel, int squareId)
        {
            super(new GridLayout());
            SquareId=squareId;
            setPreferredSize(PANEL_SQUARE_DIMENSION);
            assignSquareColor();
            validate();//why we use it?
        }


        private void assignSquareColor() {
            if (BoardData.FIRST_ROW[this.SquareId] ||
                    BoardData.THIRD_ROW[this.SquareId] ||
                    BoardData.FIFTH_ROW[this.SquareId] ||
                    BoardData.SEVENTH_ROW[this.SquareId] ){
                if (this.SquareId % 2 == 0) {
                    setBackground(lightTileColor);
                } else {
                    setBackground(darkTileColor);
                }
            } else if(BoardData.SECOND_ROW[this.SquareId] ||
                    BoardData.FOURTH_ROW[this.SquareId] ||
                    BoardData.SIXTH_ROW[this.SquareId]  ||
                    BoardData.EIGHTH_ROW[this.SquareId]) {
                if (this.SquareId % 2 != 0) {
                    setBackground(lightTileColor);
                } else {
                    setBackground(darkTileColor);
                }
            }
        }
    }
}


