package reversi;

class Node{ //left child right sibling tree
	board board;
	Node child;
	Node next;

	//for min max
	//lose: utility = -1, draw: utility = 0, win: utility = score
	public int utility = 0; 
	
	//will be updated with updateHeuristic function in minimax.java
	//hscore stands for heuristic score
	public int hScore = 0; 
	
	public Node(board board) {
		this.board = board;
	}
	
	public board getBoard(){
		return this.board;
	}
	
	public void addAsChild(Node n) {//inserts a node as a child of another node
		n.next = this.child;
		this.child = n;
	}
	
	public void printNode() {
		board.printBoard();
	}

	public void updateUtility(int player) {
		if (board.score_black == board.score_white) {
			utility = 0;
			return;
		}
		
		if (player == board.BLACK){
			if (board.score_black > board.score_white)
				utility = board.score_black;
			else 
				utility = -1;
		}
		
		else if (player == board.WHITE){
			if (board.score_black < board.score_white)
				utility = board.score_white;
			else utility = -1;
		}
	}
}

public class BoardTree {
	Node root;
	
	public BoardTree(board b) {
		this.root = new Node(b);
	}
	
	

}
