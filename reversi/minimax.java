package reversi;

public class minimax {
	//we know that in reversi, no next move of a game will ever return the same state
	//i.e. duplicate states do not exist in the state space, so we can use a tree set right?
	public static void generateTree(Node node, int player, int desiredDepth, int currentDepth) {
		//if desiredDepth is -1, we assume that we don't bound the depth of the tree.
		if (desiredDepth>=0)
			if (desiredDepth < currentDepth) return;
		
		for (int i = 0; i < node.getBoard().boardSize; i++) {
			for (int j = 0; j < node.getBoard().boardSize; j++) {
				//if [i][j] is empty and there is a legal move for our player there
				if (node.getBoard().board[i][j] == 0) {
					board tmpBoard = node.getBoard().copy();
					Node tmp = new Node(game.makeMove(tmpBoard, new int[] {i,j}, player));
					if (tmp!=null) {
						
						if (tmp.getBoard().equalBoard(node.getBoard())) continue;
						else node.addAsChild(tmp); //add the board as a child
						
						tmp.board.updateScore();
						
//						System.out.println("Printing board at depth: " + currentDepth); //tmp.getBoard().printBoard();
						generateTree(node.child, game.returnOpponent(player), desiredDepth, currentDepth+1);
					}
				}
			}
		}
		
	}
	
	
	
	public static void main(String[] args) {
		board b = new board(4);
		Node node = new Node(b);
		generateTree(node, 1, -1, 0);
		Node tmp = node;
		do {
			tmp.getBoard().printBoard();
			tmp = tmp.child;
		} while(tmp!=null) ;

		
	}
}
