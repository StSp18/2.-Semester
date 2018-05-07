package de.hsa.games.fatsquirrel.core;

public class Board{
	private Entity[] board;
	private XY size;
	private int idcount;
	
	public Board(BoardFactory bf) {
		board = bf.factoryBoard();
		size = bf.getSize();
		idcount = board.length;
	}

	public HandOperatedMasterSquirrel getPlayer( ) {
		for(int i=0;i<board.length;i++) {
			if(board[i] instanceof HandOperatedMasterSquirrel) {
				return (HandOperatedMasterSquirrel) board[i];
			}
		}
		return null;
	}
	
	public FlattenedBoard createflattenedBoard() {
		Entity[][] flattenedBoard = new Entity[size.getX()+2][size.getY()+2];
		for(int i=0; i < board.length; i++) {
			flattenedBoard[board[i].getX()][board[i].getY()] = board[i];
		}
		return new FlattenedBoard(flattenedBoard, this);
	}
	
	public Entity[][] flatten() {
		Entity[][] flattenedBoard = new Entity[size.getX()+2][size.getY()+2];
		for(int i=0; i < board.length; i++) {
			flattenedBoard[board[i].getX()][board[i].getY()] = board[i];
		}
		return flattenedBoard;
	}
	
	public void add(Entity e) {
		Entity [] tboard = new Entity[board.length+1]; 
		for(int i=0; i<board.length; i++) {
			tboard[i]= board[i];
		}
		tboard[board.length] = e;
		board = tboard;
		idcount++;
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
			idcount--;
		}
	}
	
	public void relocate(Entity oldE, Entity newE) {
		for(int i=0; i<board.length; i++) {
			if(board[i] == oldE) {
				board[i] = newE;
			}
		}
		
	}
	
	public void update() {
		for(int i=0; i<board.length ;i++) {
			if(board[i] instanceof Character) {
				((Character)board[i]).nextStep(createflattenedBoard());
			}
		}
	}
	
	public XY getSize() {
		return size;
	}

	public int getIdcount() {
		return idcount;
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
