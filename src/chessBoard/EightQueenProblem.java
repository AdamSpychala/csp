package chessBoard;

public class EightQueenProblem {

	// number of N-Queen Problem
	int n;
	ChessBoard chessBoard;
	// [i][j], i - column, j - row
	int[][] board;

	private static final int EMPTY = 0;
	private static final int QUEEN = 1;

	public EightQueenProblem(int n) {
		this.n = n;
		this.board = new int[n][n];
	}

	public EightQueenProblem(ChessBoard chessBoard, int n) {
		this.n = n;
		this.chessBoard = chessBoard;
		this.board = new int[n][n];
	}

	void solve(int queenPos) {
		for (int i = 0; i < board.length; i++) {

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
			System.out.println("Liczba sprawdzen: " + (2 * n - (i + j + 1)));
			for (int z = 2 * n - (i + j + 1); z > 0; z--) {
				if (board[n - z][(i + j) - (n -z)] == QUEEN)
					validPos = false;
				System.out.println("pozycja(" + (n - z) + "): [" + (n - z) + "][" + ((i + j) - (n -z))
						+ "]");
			}
		}
		else {
			System.out.println("Liczba sprawdzen: " + ((i + j + 1)));
			for (int z = (i + j ); z >= 0; z--) {
				if (board[z][(i + j) - (z)] == QUEEN)
					validPos = false;
				System.out.println("pozycja(" + (z) + "): [" + (z) + "][" + ((i + j) - ( z))
						+ "]");
			}
		}

		return validPos;
	}

	public static void main(String[] args) {
		EightQueenProblem problem = new EightQueenProblem(20);

		problem.checkPosition(10,10);
	}
}
