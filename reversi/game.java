package reversi;

import java.util.Scanner;

public class game {
	/*
	 * we need to return a NEW board for a legal move, and return NULL if NO legal
	 * move. we can have a boolean that checks for legal moves - if boolean is TRUE,
	 * we return board, else return NULL.
	 */
	public static int returnOpponent(int player) {
		return (player == 1) ? 2 : 1;
	}

	public static board checkRight(board board, int[] coordinates, int player) {
		// return NULL if no legal move found, otherwise change the board to our new
		// board and return that
		int row = coordinates[0];
		int col = coordinates[1];

		if (col >= board.boardSize)
			return null; // if column is too far beyond and we havent found an answer
		else if (board.board[row][col] == 0)
			return null; // if we encounter an empty slot
		else if (board.board[row][col] == player)
			return board;
		else if (board.board[row][col] == returnOpponent(player)) {
//			System.out.println("Changed " + row + "," + col + " to " + player+ " with checkRight");
			board.board[row][col] = player;
		}

		return checkRight(board, new int[] { row, col + 1 }, player);
	}

	public static board checkLeft(board board, int[] coordinates, int player) {
		int row = coordinates[0];
		int col = coordinates[1];

		if (col < 0)
			return null;
		else if (board.board[row][col] == 0)
			return null;
		else if (board.board[row][col] == player)
			return board;
		else if (board.board[row][col] == returnOpponent(player)) {
//			System.out.println("Changed " + row + "," + col + " to " + player+ " with checkLeft");
			board.board[row][col] = player;
		}

		return checkLeft(board, new int[] { row, col - 1 }, player);
	}

	public static board checkUp(board board, int[] coordinates, int player) {
		int row = coordinates[0];
		int col = coordinates[1];

		if (row < 0)
			return null;
		else if (board.board[row][col] == 0)
			return null;
		else if (board.board[row][col] == player)
			return board;
		else if (board.board[row][col] == returnOpponent(player)) {
//			System.out.println("Changed " + row + "," + col + " to " + player+ " with checkUp");
			board.board[row][col] = player;
		}

		return checkUp(board, new int[] { row - 1, col }, player);
	}

	public static board checkUpRight(board board, int[] coordinates, int player) {
		int row = coordinates[0];
		int col = coordinates[1];

		if (row < 0 || col >= board.boardSize)
			return null;
		else if (board.board[row][col] == 0)
			return null;
		else if (board.board[row][col] == player)
			return board;
		else if (board.board[row][col] == returnOpponent(player)) {
//			System.out.println("Changed " + row + "," + col + " to " + player+ " with checkUpRight");
			board.board[row][col] = player;
		}

		return checkUpRight(board, new int[] { row - 1, col + 1 }, player);
	}

	public static board checkDownLeft(board board, int[] coordinates, int player) {
		int row = coordinates[0];
		int col = coordinates[1];

		if (row >= board.boardSize || col < 0)
			return null;
		else if (board.board[row][col] == 0)
			return null;
		else if (board.board[row][col] == player)
			return board;
		else if (board.board[row][col] == returnOpponent(player)) {
//			System.out.println("Changed " + row + "," + col + " to " + player+ " with checkDownLeft");
			board.board[row][col] = player;
		}

		return checkDownLeft(board, new int[] { row + 1, col - 1 }, player);
	}

	public static board checkUpLeft(board board, int[] coordinates, int player) {
		int row = coordinates[0];
		int col = coordinates[1];

		if (row < 0 || col < 0)
			return null;
		else if (board.board[row][col] == 0)
			return null;
		else if (board.board[row][col] == player)
			return board;
		else if (board.board[row][col] == returnOpponent(player)) {
//			System.out.println("Changed " + row + "," + col + " to " + player+ " with checkUpLeft");
			board.board[row][col] = player;
		}

		return checkUpLeft(board, new int[] { row - 1, col - 1 }, player);
	}

	public static board checkDownRight(board board, int[] coordinates, int player) {
		int row = coordinates[0];
		int col = coordinates[1];

		if (row >= board.boardSize || col >= board.boardSize)
			return null;
		else if (board.board[row][col] == 0)
			return null;
		else if (board.board[row][col] == player)
			return board;
		else if (board.board[row][col] == returnOpponent(player)) {
//			System.out.println("Changed " + row + "," + col + " to " + player+ " with checkDownRight");
			board.board[row][col] = player;
		}

		return checkDownRight(board, new int[] { row + 1, col + 1 }, player);
	}

	public static board checkDown(board board, int[] coordinates, int player) {
		int row = coordinates[0];
		int col = coordinates[1];

		if (row >= board.boardSize)
			return null;
		else if (board.board[row][col] == 0)
			return null; // if we encounter an empty slot
		else if (board.board[row][col] == player)
			return board;
		else if (board.board[row][col] == returnOpponent(player)) {
//			System.out.println("Changed " + row + "," + col + " to " + player+ " with checkDown");
			board.board[row][col] = player;
		}

		return checkDown(board, new int[] { row + 1, col }, player);
	}

	public static board makeMove(board board, int[] coordinates, int player) {
		board auxBoard = board;
		boolean hasLegalMove = false; // this way we can keep track of whether a board has been altered

		// row and col are coordinates of the piece you are checking
		int row = coordinates[0];
		int col = coordinates[1];

		// if the given coordinate is NOT empty, its an illegal move so we return a null
		// board
		if (auxBoard.board[row][col] != 0)
			return null;
		else
			auxBoard.board[row][col] = player;

		// check right - remember that the check right method ALTERS the actual board in
		// case of a legal move,
		// so to avoid setting auxBoard to null, we can use a temporary one to do it for
		// us.
		board tmp1 = board.copy();
		if (board.board[row][col] == player && col != board.boardSize - 1) {
			if (board.board[row][col + 1] == returnOpponent(player))
				if (checkRight(tmp1, new int[] { row, col + 1 }, player) != null) {
					hasLegalMove = true;
//					System.out.println("has legal right move");
					checkRight(auxBoard, new int[] { row, col + 1 }, player);
				}
		}

		tmp1 = board.copy();
		if (board.board[row][col] == player && col != 0) {
			if (board.board[row][col - 1] == returnOpponent(player))
				if (checkLeft(tmp1, new int[] { row, col - 1 }, player) != null) {
					hasLegalMove = true;
//					System.out.println("Has legal left move");
					checkLeft(auxBoard, new int[] { row, col - 1 }, player);
				}
		}

		tmp1 = board.copy();
		if (board.board[row][col] == player && row != board.boardSize - 1) {
			if (board.board[row + 1][col] == returnOpponent(player))
				if (checkDown(tmp1, new int[] { row + 1, col }, player) != null) {
					hasLegalMove = true;
//					System.out.println("Has Legal down Move");
					checkDown(auxBoard, new int[] { row + 1, col }, player);
				}
		}

		tmp1 = board.copy();
		if (board.board[row][col] == player && row != 0) {
			if (board.board[row - 1][col] == returnOpponent(player))
				if (checkUp(tmp1, new int[] { row - 1, col }, player) != null) {
					hasLegalMove = true;
//					System.out.println("Has legal UP move");
					checkUp(auxBoard, new int[] { row - 1, col }, player);
				}
		}

		tmp1 = board.copy();
		if (board.board[row][col] == player && row != 0 && col != board.boardSize - 1) {
			if (board.board[row - 1][col + 1] == returnOpponent(player)) {
				if (checkUpRight(tmp1, new int[] { row - 1, col + 1 }, player) != null) {
					hasLegalMove = true;
//					System.out.println("Has Legal Move UPRIGHt");
					checkUpRight(auxBoard, new int[] { row - 1, col + 1 }, player);
				}
			}
		}

		tmp1 = board.copy();
		if (board.board[row][col] == player && row != 0 && col != 0) {
			if (board.board[row - 1][col - 1] == returnOpponent(player)) {
				if (checkUpLeft(tmp1, new int[] { row - 1, col - 1 }, player) != null) {
					;
					hasLegalMove = true;
//					System.out.println("Has Legal Move UpLeft");
					checkUpLeft(auxBoard, new int[] { row - 1, col - 1 }, player);
				}
			}
		}

		tmp1 = board.copy();
		if (board.board[row][col] == player && row != board.boardSize - 1 && col != 0) {
			if (board.board[row + 1][col - 1] == returnOpponent(player)) {
				if (checkDownLeft(tmp1, new int[] { row + 1, col - 1 }, player) != null) {
					hasLegalMove = true;
//					System.out.println("Has Legal Move DownLeft");
					checkDownLeft(auxBoard, new int[] { row + 1, col - 1 }, player);
				}
			}
		}

		tmp1 = board.copy();
		if (board.board[row][col] == player && row != board.boardSize - 1 && col != board.boardSize - 1) {
			if (board.board[row + 1][col + 1] == returnOpponent(player)) {
				if (checkDownRight(tmp1, new int[] { row + 1, col + 1 }, player) != null) {
					hasLegalMove = true;
//					System.out.println("Has Legal Move DownRight");
					checkDownRight(auxBoard, new int[] { row + 1, col + 1 }, player);
				}
			}
		}

		if (hasLegalMove == false)
			auxBoard.board[row][col] = 0;

		auxBoard.updateScore();
		return auxBoard;
	}

	public static boolean canBlackMove(board board) {
		for (int i = 0; i < board.boardSize; i++)
			for (int j = 0; j < board.boardSize; j++)
				if (board.board[i][j] == 0) {
					board tmp = makeMove(board.copy(), new int[] { i, j }, board.BLACK);
					if (tmp != null && !tmp.equalBoard(board)) // if a move exists for black
						return true;
				}

		return false;
	}

	public static boolean canWhiteMove(board board) {
		for (int i = 0; i < board.boardSize; i++)
			for (int j = 0; j < board.boardSize; j++)
				if (board.board[i][j] == 0) {
					board tmp = makeMove(board.copy(), new int[] { i, j }, board.WHITE);
					if (tmp != null && !tmp.equalBoard(board)) // if a move exists for white
						return true;
				}

		return false;
	}

	public static boolean isGameOver(board board) {
		if (!canWhiteMove(board) && !canBlackMove(board))
			return true;

		return false;
	}

	public static int[] inputStringToArray(String input) {// convert input string to matrix coordinates
		int[] coordinate = new int[2];
		char[] aux = input.toCharArray();
		// invalid input
		if(aux.length!=2) {
			coordinate[0] = -1;
			coordinate[1] = -1;
		}
		// aux[0] will carry the column, aux[1] will carry the row, we must reverse it
		// for the coordinate
		else {
		coordinate[0] = (int) aux[1] - 49;
		coordinate[1] = (int) aux[0] - 97;}
//		System.out.println("Input Matrix Coordinates: " + coordinate[0] + "," + coordinate[1]);
		return coordinate;
	}

	public static void main(String[] args) {
		int boardSize = 4;
		board board = new board(boardSize);
		board.printBoard();

		Scanner s = new Scanner(System.in);
		int player = board.BLACK;
		String input = "";
		while (!isGameOver(board)) {
			if (player == board.BLACK) {
				if (!canBlackMove(board)) {
					System.out.println("Black cannot move");
					player = returnOpponent(player);
					continue;
				} else
					System.out.println("Black (x) plays.");
			} else if (player == board.WHITE) {
				if (!canWhiteMove(board)) {
					System.out.println("White cannot move");
					player = returnOpponent(player);
					continue;
				} else
					System.out.println("White (o) plays");
			}

			System.out.println("Enter Your Move: ");
			input = s.next();
			
			int[] coordinate = inputStringToArray(input);
			
			while (coordinate[0] < 0 || coordinate[1] < 0 || coordinate[0] > boardSize || coordinate[1] > boardSize) {
				System.out.println("That is not a valid input. Enter new move: ");
				input = s.next();
				coordinate = inputStringToArray(input);
			}
			board tmp = makeMove(board.copy(), coordinate, player);
			while (tmp == null || tmp.equalBoard(board)) {
				System.out.println("That is not a valid move. Enter new move: ");
				input = s.next();
				tmp = makeMove(board.copy(), inputStringToArray(input), player);
			}

			makeMove(board, inputStringToArray(input), player).printBoard();
			player = returnOpponent(player);
		}

		System.out.println("Game Over");
		if (board.score_white == board.score_black)
			System.out.println("Draw");
		else if (board.score_white > board.score_black)
			System.out.println("White Wins");
		else
			System.out.println("Black Wins");
	}

}
