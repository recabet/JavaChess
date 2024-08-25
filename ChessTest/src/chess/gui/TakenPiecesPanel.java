package chess.gui;

import chess.logic.board.Move;
import chess.logic.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Panel to display the taken pieces.
 */
public class TakenPiecesPanel extends JPanel {

    private final JPanel northPanel;
    private final JPanel southPanel;

    private static final Color PANEL_COLOR = Color.decode("0xFDF5E6");
    private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(40, 80);
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

    /**
     * Constructs a TakenPiecesPanel.
     */
    public TakenPiecesPanel()
    {
        super(new BorderLayout());
        this.setBackground(PANEL_COLOR);
        this.setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8, 2));
        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        add(this.northPanel, BorderLayout.NORTH);
        add(this.southPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_DIMENSION);
    }

    /**
     * Updates the TakenPiecesPanel with the pieces that have been taken during the game.
     *
     * @param movelog The MoveLog containing all the moves made in the game.
     */
    public void redo(final Table.Movelog movelog)
    {
        this.southPanel.removeAll();
        this.northPanel.removeAll();

        // Lists to store taken pieces
        final List<Piece> wTakenPieces = new ArrayList<>();
        final List<Piece> bTakenPieces = new ArrayList<>();

        // Iterate through the moves in the MoveLog
        for(final Move move : movelog.getMoves())
        {
            // Check if the move is an attack
            if(move.isAttack())
            {
                final Piece takenPiece = move.getAttackedPiece();
                // Add the taken piece to the appropriate list based on its color
                if(takenPiece.getPieceColor().isWhite())
                {
                    wTakenPieces.add(takenPiece);
                } else
                {
                    bTakenPieces.add(takenPiece);
                }
            }
        }

        // Sort the taken pieces based on their values
        wTakenPieces.sort(Comparator.comparingInt(Piece::getPieceValue));
        bTakenPieces.sort(Comparator.comparingInt(Piece::getPieceValue));

        // Add images of the taken white pieces to the south panel
        for(final Piece takenPiece : wTakenPieces)
        {
            try
            {
                final BufferedImage image = ImageIO.read(new File("images/simple/" + takenPiece.getPieceColor().toString().charAt(0) + takenPiece.toString() + ".gif"));
                final ImageIcon ic = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(ic.getIconWidth() - 15, ic.getIconWidth() - 15, Image.SCALE_SMOOTH)));
                this.southPanel.add(imageLabel);
            } catch(IOException e)
            {
                throw new RuntimeException(e);
            }
        }

        // Add images of the taken black pieces to the south panel
        for(final Piece takenPiece : bTakenPieces)
        {
            try
            {
                final BufferedImage image = ImageIO.read(new File("images/simple/" + takenPiece.getPieceColor().toString().charAt(0) + takenPiece.toString() + ".gif"));
                final ImageIcon ic = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(ic.getIconWidth() - 15, ic.getIconWidth() - 15, Image.SCALE_SMOOTH)));
                this.southPanel.add(imageLabel);
            } catch(IOException e)
            {
                throw new RuntimeException(e);
            }
        }

        validate();
    }

}
