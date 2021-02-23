package reversi;

public class board implements Cloneable {
	int WHITE = 2; //o
	int BLACK = 1; //x
	
	int score_white = 0;
	int score_black = 0;
	
	int boardSize;
	int[][] board;
	
	public board(int boardSize) {
		this.boardSize = boardSize;
		board = new int[boardSize][boardSize];

		//initialize the board
		board[boardSize/2 - 1][boardSize/2 - 1] = WHITE;
		board[boardSize/2 - 1][boardSize/2] = BLACK;
		board[boardSize/2][boardSize/2 - 1] = BLACK;
		board[boardSize/2][boardSize/2] = WHITE;
	}
	
	public board copy() {
		board r = new board(this.boardSize);
		r.score_black = this.score_black;
		r.score_white = this.score_white;
		
		int[][] matrix = new int[r.boardSize][r.boardSize];
		for (int i = 0; i < r.boardSize; i++) {
			for (int j=0; j < r.boardSize; j++) {
				matrix[i][j] = this.board[i][j];
			}
		}
		
		r.board = matrix;
		
		return r;
	}
	
	public boolean equalBoard(board b) {//check if 2 boards have equal piece positions
		for (int i = 0; i < boardSize; i++) 
			for (int j = 0; j < boardSize; j++) 
				if (b.board[i][j] != this.board[i][j]) return false;
			
		return true;
	}
	
	public void printBoard() {
		System.out.print("  ");
		for (char c = 'a'; c < 97+boardSize; ++c) {
			System.out.print(c + " ");
		} System.out.println();
		
		for (int i = 0; i < boardSize; i++) {
			System.out.print((i+1) + "|");
			for (int j = 0; j < boardSize; j++) {
				switch(board[i][j]) {
					case 0: System.out.print(" "); break;
					case 1: System.out.print("x"); break; // BLACK
					case 2: System.out.print("o"); break; // WHITE
				}
				System.out.print("|");
			};
			System.out.print(i+1);
			System.out.println();
		}	
		
		System.out.print("  ");
		for(char c = 'a'; c < 97+boardSize; ++c) {
			System.out.print(c + " ");
		} 
		System.out.println();
		updateScore();
		printScore();
	}
	
	public void updateScore() {
		score_white = 0; score_black = 0; //reset score to zero and count again
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (board[i][j] == 1) score_black++;
				else if (board[i][j] == 2) score_white++;
			}
		}
	}
	
	public void printScore() {
		System.out.println("Black: " + score_black + ", White: " + score_white);
	}
	
	

}
