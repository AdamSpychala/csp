package chessBoard;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class EightQueenProblem {

	// number of N-Queen Problem
	int n;
	ChessBoard chessBoard;
	// [i][j], i - column, j - row
	int[][] board;
	int solutions = 0;

	int itTemp = 0;

	private static final int EMPTY = 0;
	private static final int QUEEN = 1;
	private static final int CROSS = 2;

	public EightQueenProblem(int n) {
		this.n = n;
		this.board = new int[n][n];

		chessBoard = new ChessBoard(n);
		runBoard();
	}

	public EightQueenProblem(ChessBoard chessBoard, int n) {
		this.n = n;
		this.chessBoard = chessBoard;
		this.board = new int[n][n];
	}

	void runBoard() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				JFrame f = new JFrame("ChessChamp");
				f.add(chessBoard.getGui());
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

	void solveBT() {
		solveBT(0);
		clearBoard(0);
	}

	void solveBT(int queenNr) {
		boolean foundSolution = false;
		for (int i = 0; i < n && !foundSolution; i++) {
			chessBoard.setRed(queenNr, i);
			 try {
			 Thread.sleep(200); // 1000 milliseconds is one second.
			
			 } catch (InterruptedException ex) {
			 Thread.currentThread().interrupt();
			 }

			chessBoard.setQueen(queenNr, i);

			clearBoard(queenNr);
			System.out.println("[" + queenNr + "][" + i + "]");
			if (checkPosition(queenNr, i)) {
				board[queenNr][i] = QUEEN;
				chessBoard.setQueen(queenNr, i);
				// foundSolution = true;
				if (queenNr == (n - 1)) {
					++solutions;
				} else
					solveBT(queenNr + 1);
				// } else {
			}
			chessBoard.unsetRed(queenNr, i);
		}
	}

	void solveFC() {
		solveFC(0);
		clearBoard(0);
	}

	void solveFC(int queenNr) {
		boolean foundSolution = false;
		for (int i = 0; i < n && !foundSolution; i++) {
			chessBoard.setRed(queenNr, i);
			try {
				Thread.sleep(200); // 1000 milliseconds is one second.

			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			System.out.println("[" + queenNr + "][" + i + "]");
			if (board[queenNr][i] < CROSS && checkPosition(queenNr, i)) {
				setQueen(queenNr, i);
				disableQueenNBH(queenNr, i);
				if (queenNr == (n - 1))
					++solutions;
				else
					solveFC(queenNr + 1);
			}
			clearBoardFC(queenNr, i);
			chessBoard.unsetRed(queenNr, i);
		}
	}

	void clearBoard(int from) {
		for (int i = from; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = EMPTY;
				chessBoard.removeField(i, j);
				board[i][j] = EMPTY;
			}
		}
	}

	void clearBoardFC(int from, int row) {
		if (board[from][row] == QUEEN)
			setCross(from, row, from);
		for (int i = from; i < board.length; i++) {
			int j = (i == from) ? row : 0;
			while (j + 1 < board.length) {
				if (board[i][j] >= (CROSS + from)) {
					chessBoard.removeField(i, j);
					board[i][j] = EMPTY;
				}
				j++;
			}
		}
	}

	void setQueen(int i, int j) {
		chessBoard.setQueen(i, j);
		board[i][j] = QUEEN;
	}

	void setCross(int i, int j, int weight) {
		try {
			Thread.sleep(200); // 1000 milliseconds is one second.

		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		board[i][j] = CROSS + weight;
		chessBoard.setCross(i, j);
	}

	void disableQueenNBH(int i, int j) {
		for (int z = i + 1; z < n; z++) {
			if (board[z][j] < CROSS)
				setCross(z, j, i);
		}
		for (int z = 0; z < n; z++) {
			if (board[i][z] != QUEEN && board[i][z] < CROSS)
				setCross(i, z, i);
		}

		// System.out.println("Liczba sprawdzen: " + (2 * n - (i + j + 1)));
		for (int z = i + 1; z < n && (j + z - i) < n; z++) {
			if (board[z][j + z - i] < CROSS)
				setCross(z, j + z - i, i);
		}

		for (int z = i + 1; z < n && (j - z + i) > 0; z++) {
			if (board[z][j - z + i] < CROSS)
				setCross(z, j - z + i, i);
		}
	}

	boolean checkPosition(int i, int j) {
		boolean validPos = true;
		for (int z = 0; z < n; z++) {
			if (board[z][j] == QUEEN)
				validPos = false;
		}
		for (int z = 0; z < n; z++) {
			if (board[i][z] == QUEEN)
				validPos = false;
		}

		if (i + j >= n) {
			// System.out.println("Liczba sprawdzen: " + (2 * n - (i + j + 1)));
			for (int z = 2 * n - (i + j + 1); z > 0; z--) {
				if (board[n - z][(i + j) - (n - z)] == QUEEN)
					validPos = false;
				// System.out.println("pozycja(" + (n - z) + "): [" + (n - z)
				// + "][" + ((i + j) - (n - z)) + "]");
			}
		} else {
			// System.out.println("Liczba sprawdzen: " + ((i + j + 1)));
			for (int z = (i + j); z >= 0; z--) {
				if (board[z][(i + j) - (z)] == QUEEN)
					validPos = false;
				// System.out.println("pozycja(" + (z) + "): [" + (z) + "]["
				// + ((i + j) - (z)) + "]");
			}
		}

		if (i >= j) {
			// System.out.println("Liczba sprawdzen: " + ((n - i) + j));
			for (int z = ((n - i) + j); z > 0; z--) {
				// System.out.println("pozycja(" + (z) + "): [" + (n-z) + "]["
				// + ((n-z) - (i - j)) + "]");
				if (board[n - z][n - z - (i - j)] == QUEEN)
					validPos = false;
			}
		} else {
			// System.out.println("Liczba sprawdzen: " + ((n - j) + i));
			for (int z = ((n - j) + i); z > 0; z--) {
				// System.out.println("pozycja(" + (z) + "): [" + (z-1) + "]["
				// + ((z-1) - (i-j)) + "]");
				if (board[z - 1][(z - 1) - (i - j)] == QUEEN)
					validPos = false;
			}
		}
		return validPos;
	}

	public static void main(String[] args) {
		EightQueenProblem problem = new EightQueenProblem(10);

		problem.solveFC();
		System.out.println("Solutions: " + problem.solutions);
		// problem.checkPosition(3, 5);
	}
}
