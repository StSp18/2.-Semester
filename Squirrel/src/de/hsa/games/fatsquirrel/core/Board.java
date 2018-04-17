package de.hsa.games.fatsquirrel.core;

public class Board{
	private Entity[] board;
	private BoardFactory bf = new BoardFactory();

	public Board() {
		board = bf.factoryBoard();
	}

	public Entity[][] flatten() {
		Entity[][] flattnedBoard = new Entity[bf.getSize().getX()+2][bf.getSize().getY()+2];
		for(int i=0; i < board.length; i++) {
			flattnedBoard[board[i].getX()][board[i].getY()] = board[i];
		}
		return flattnedBoard;
	}

	public void add(Entity e) {
		Entity [] tboard = new Entity[board.length]; 
		for(int i=0; i<board.length; i++) {
			tboard[i]= board[i];
		}
		tboard[board.length] = e;
		board = tboard;
	}
	
	public void remove(Entity e) {
		int k = 0;
		Entity [] tboard = new Entity[board.length]; 
		for(int i=0; i<board.length; i++) {
			if(board[i] == e) {
				k=1;
			} else {
				tboard[i-k]=board[i];
			}
			board = tboard;
		}
	}
	
	public void relocate(Entity oldE, Entity newE) {
		for(int i=0; i<board.length; i++) {
			if(board[i] == oldE) {
				board[i] = newE;
			}
		}
		
	}
	
	public XY getSize() {
		return bf.getSize();
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < board.length; i++) {
			if (board[i] instanceof Wall) {
				s += "Wall";
			} else if (board[i] instanceof BadBeast) {
				s += "BadBeast";
			} else if (board[i] instanceof BadPlant) {
				s += "BadPlant";
			} else if (board[i] instanceof GoodBeast) {
				s += "GoodBeast";
			} else if (board[i] instanceof GoodPlant) {
				s += "GoodPlant";
			} else if (board[i] instanceof MasterSquirrel) {
				s += "MasterSquirrel";
			} else if (board[i] instanceof MiniSquirrel) {
				s += "MiniSquirrel";
			}
			s += ", X: " + board[i].xy.getX() + ", Y: " + board[i].xy.getY() + '\n';
		}
		return s;
	}
}
