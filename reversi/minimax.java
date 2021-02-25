package reversi;

public class minimax {
	static int exploredTerminals = 0;
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
	
	//maxmin without any heuristic or pruning
	public static Node maxOfTree(Node root, int player) {
		if (root.child == null) {//if it is a leaf node
			exploredTerminals++;
			root.updateUtility(player); 
			return root;
		} 
		
		//iterate through children and find the maximum of those
		Node tmp = root.child; //iterator
		int tmputility = minOfTree(tmp, player).utility;
		
		while(tmp!=null) {//find the max of the kids, update utility
			if (tmp.next!=null) {
				if (minOfTree(tmp.next, player).utility > tmputility) tmputility = tmp.next.utility;
			}
			tmp = tmp.next;
		}
		
		root.utility = tmputility; //tmputility is the max utility
		return root;
	}

	public static Node minOfTree(Node root, int player) {
		if (root.child == null) {//if it is a leaf node
			exploredTerminals++;
			root.updateUtility(player); 
			return root;
		}
		
		//iterate through children and find the maximum of those
		Node tmp = root.child; //iterator
		int tmputility = maxOfTree(tmp, player).utility;
		
		while(tmp!=null) {//find the max of the kids, update utility
			if (tmp.next!=null) {
				if (maxOfTree(tmp.next, player).utility < tmputility) tmputility = tmp.next.utility;
			}
			tmp = tmp.next;
		}
		root.utility = tmputility; //tmputility is the min of tree
		return root;
	}
	
	public static board minMax(board b, int player) {
		exploredTerminals = 0;
		Node node = new Node(b);
		//generates a tree, then minmaxes it
		generateTree(node, player, -1, 0);
		maxOfTree(node, player);
		
		Node tmp = node.child; //we can use tmp to iterate through the children
		int tmputility = tmp.utility; 
		
		Node placeholder = tmp; //this is the return Node
		
		while(tmp!=null) {
			if (tmp.next!=null) {
				if(tmputility < tmp.next.utility) {
					placeholder = tmp.next;
					tmputility = tmp.utility;
				}
			}
			tmp = tmp.next;
		}
		placeholder.printNode();
		System.out.println("Num of Terminals reached: " + exploredTerminals);
		return placeholder.getBoard(); //this placeholder will return the "best move"
	}
	
	//heuristic and pruning
	public static void updateHeuristic(Node n, int player) {
		//for now it returns a random number, but later we can change it
//		n.hScore = (int)Math.round(Math.random()*100);
		n.getBoard().updateScore(); n.updateUtility(player);
		
		
		int count = 0;
		int playerScore = n.board.returnPlayerScore(player);
		int opponentScore = n.board.returnOpponentScore(player);
		
		count = playerScore - opponentScore;
		int tmp = n.board.board[0][0] + n.board.board[0][n.board.boardSize-1] 
				+ n.board.board[n.board.boardSize-1][0] 
				+ n.board.board[n.board.boardSize-1][n.board.boardSize-1]; 
		count = count+tmp; //reward corner pieces
		count += n.utility;
		
		n.hScore = count;
	}
	
	public static Node pruneMax(Node root, int player, int alpha, int beta) {
		if (root.child == null) {//if it is a leaf node
			exploredTerminals++;
			updateHeuristic(root, player);
			return root;
		} 
		
		int v = -10000; //effectively -infinity i guess
		
		//iterate through children and find the maximum of those
		Node tmp = root.child; //iterator
		int vprime = pruneMin(tmp, player, alpha, beta).hScore;
		
		while(tmp!=null) {//find the max of the kids, update utility
			if (tmp.next!=null) {
				vprime = pruneMin(tmp.next, player, alpha, beta).hScore;
				if (vprime>v) v = vprime;
				if (vprime>=beta) {
					root.hScore = v;
					return root;
				}
				if (vprime>alpha) alpha = vprime;
			}
			tmp = tmp.next;
		}
		
		root.hScore = v; //tmputility is the max utility
		return root;
	}
	
	public static Node pruneMin(Node root, int player, int alpha, int beta) {
		if (root.child == null) {//if it is a leaf node
			exploredTerminals++;
			updateHeuristic(root, player);
			return root;
		} 
		
		int v = 10000; //effectively +infinity i guess
		
		//iterate through children and find the maximum of those
		Node tmp = root.child; //iterator
		int vprime = pruneMax(tmp, player, alpha, beta).hScore;
		
		while(tmp!=null) {//find the max of the kids, update utility
			if (tmp.next!=null) {
				vprime = pruneMax(tmp.next, player, alpha, beta).hScore;
				if (vprime<v) v = vprime;
				if (vprime<=alpha) {
					root.hScore = v;
					return root;
				}
				if (vprime < beta) beta = vprime;
			}
			tmp = tmp.next;
		}
		
		root.hScore = v;
		return root;
	}
	
	private static int emptySpace(board b) {
		int count = 0;
		for (int i = 0; i < b.boardSize; i++) {
			for (int j = 0; j < b.boardSize; j++) {
				if (b.board[i][j] == 0) count++;
			}
		}
		return count;
	}
	
	private static int returnAppropriateDepth(board b) {
		//with every new move, the size of the search tree decreases and we can search deeper
		//minimax benefits by searching deeper bc it means access to more information
		int emptySpaces = emptySpace(b);
		if (emptySpaces < 10) return 6;
		else return 5;
	}
	
	public static board pruneMinMax(board b, int player) {
		exploredTerminals = 0;
		
		int depth = returnAppropriateDepth(b);
		
		Node node = new Node(b);
		//generates a tree, then minmaxes it
		generateTree(node, player, depth, 0); //change the depth as we go? 
		pruneMax(node, player, 0, 0);
		
		Node tmp = node.child; //we can use tmp to iterate through the children
		int tmphScore = tmp.hScore; 
		
		Node placeholder = tmp; //this is the return Node
		
		while(tmp!=null) {
			if (tmp.next!=null) {
				if(tmphScore < tmp.next.hScore) {
					placeholder = tmp.next;
					tmphScore = tmp.hScore;
				}
			}
			tmp = tmp.next;
		}
		placeholder.printNode();
		System.out.println("Num of Terminals reached: " + exploredTerminals + ", Search Depth: " + depth);
		return placeholder.getBoard(); //this placeholder will return the "best move"
	}
	
}
