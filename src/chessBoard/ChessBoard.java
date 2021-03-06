package chessBoard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ChessBoard {

	private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	private JButton[][] chessBoardSquares;
	private JPanel chessBoard;
	private final JLabel message = new JLabel("Chess Champ is ready to play!");
	private int size;
	private ArrayList<JButton> buttonList = new ArrayList<>();

	ChessBoard(int size) {
		this.size = size;
		chessBoardSquares = new JButton[size][size];
		initializeGui();
	}

	public final void initializeGui() {
		// set up the main GUI
		gui.setBorder(new EmptyBorder(5, 5, 5, 5));
		JToolBar tools = new JToolBar();
		tools.setFloatable(false);
		gui.add(tools, BorderLayout.PAGE_START);
		tools.add(new JButton("New")); // TODO - add functionality!
		tools.add(new JButton("Save")); // TODO - add functionality!
		tools.add(new JButton("Restore")); // TODO - add functionality!
		tools.addSeparator();
		tools.add(new JButton("Resign")); // TODO - add functionality!
		tools.addSeparator();
		tools.add(message);

		gui.add(new JLabel("?"), BorderLayout.LINE_START);

		chessBoard = new JPanel(new GridLayout(size, size));
		chessBoard.setBorder(new LineBorder(Color.BLACK));
		gui.add(chessBoard);

		// create the chess board squares
		Insets buttonMargin = new Insets(0, 0, 0, 0);
		for (int ii = 0; ii < chessBoardSquares.length; ii++) {
			for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
				JButton b = new JButton();
				b.setMargin(buttonMargin);
				// our chess pieces are 64x64 px in size, so we'll
				// 'fill this in' using a transparent icon..
				ImageIcon icon = new ImageIcon(new BufferedImage(64, 64,
						BufferedImage.TYPE_INT_ARGB));
				b.setIcon(icon);
				b.setBorderPainted(false);
				b.setFocusable(false);
				b.setEnabled(false);
				if ((jj % 2 == 1 && ii % 2 == 1)
						|| (jj % 2 == 0 && ii % 2 == 0)) {
					b.setBackground(Color.YELLOW);
				} else {
					b.setBackground(Color.BLACK);
				}
				chessBoardSquares[jj][ii] = b;
			}
		}

		// fill the black non-pawn piece row
		for (int ii = 0; ii < size; ii++) {
			for (int jj = 0; jj < size; jj++) {
				switch (jj) {
				default:
					chessBoard.add(chessBoardSquares[jj][ii]);
				}
			}
		}
	}

	JButton getButton(int i, int j) {
		return chessBoardSquares[i][j];
	}

	public void cac() {
		setQueen(0, 0);
	}

	public void setQueen(int i, int j) {
		ImageIcon img = new ImageIcon(
				"D:\\programowanie\\workspace\\csp\\src\\queen.png");
		getButton(i, j).setIcon(img);
	}

	public void setCross(int i, int j) {
		ImageIcon img = new ImageIcon(
				"D:\\programowanie\\workspace\\csp\\src\\cross.png");
		getButton(i, j).setIcon(img);
	}

	public void setRed(int i, int j) {
		getButton(i, j).setBackground(Color.RED);
	}

	public void unsetRed(int i, int j) {
		if ((j % 2 == 1 && i % 2 == 1)
						|| (j % 2 == 0 && i % 2 == 0)) 
			getButton(i,j).setBackground(Color.YELLOW);
		else
			getButton(i,j).setBackground(Color.BLACK);
	}

	void removeField(int i, int j) {
		ImageIcon img = new ImageIcon(new BufferedImage(64, 64,
				BufferedImage.TYPE_INT_ARGB));
		getButton(i, j).setIcon(img);
	}

	public final JComponent getChessBoard() {
		return chessBoard;
	}

	public final JComponent getGui() {
		return gui;
	}

	public void runChessBoard() {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				ChessBoard cb = new ChessBoard(size);

				JFrame f = new JFrame("ChessChamp");
				f.add(cb.getGui());
				// f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				// f.setLocationByPlatform(true);

				// ensures the frame is the minimum size it needs to be
				// in order display the components within it
				f.pack();
				// ensures the minimum size is enforced.
				// f.setMinimumSize(f.getSize());
				f.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(r);
	}
}