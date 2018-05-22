package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.botapi.RndFactory;
import de.hsa.games.fatsquirrel.util.XY;

import java.util.logging.Logger;

public class Board{
	private static Logger logger = Logger.getLogger("SquirrelLogger");
	private Entity[] board;
	private XY size;
	private long remainingSteps;

	public Board(BoardFactory bf) {
		board = bf.factoryBoard();
		size = bf.getSize();
	}

	public MasterSquirrel botPlayer() {
		for(int i=0;i<board.length;i++) {
			if(board[i] instanceof MasterSquirrel) {
				board[i] = new MasterSquirrelBot(board[i].getX(), board[i].getY(), new RndFactory());
				return (MasterSquirrel) board[i];
			}
		}
		return null;
	}

	public MasterSquirrel getPlayer( ) {
		for(int i=0;i<board.length;i++) {
			if(board[i] instanceof MasterSquirrel) {
				return (MasterSquirrel) board[i];
			}
		}
		return null;
	}
	
	public FlattenedBoard createFlattenedBoard() {
		Entity[][] flattenedBoard = new Entity[size.getX()][size.getY()];
		for(int i=0; i < board.length; i++) {
			flattenedBoard[board[i].getX()][board[i].getY()] = board[i];
		}
		return new FlattenedBoard(flattenedBoard, this);
	}
	
	public void add(Entity e) {
		Entity [] tBoard = new Entity[board.length+1];
		for(int i=0; i<board.length; i++) {
			tBoard[i]= board[i];
		}
		tBoard[board.length] = e;
		logger.finer(  this.getClass().getName() + ": Added: " + e.toString());
		board = tBoard;
	}
		
	
	public void remove(int id) {
		int k = 0;
		Entity [] tBoard = new Entity[board.length-1];
		for(int i=0; i<board.length; i++) {
			if(board[i].getId() == id) {
                logger.finer(  this.getClass().getName() + ": Removed: " + board[i].toString());
				k=1;
			} else {
				tBoard[i-k]=board[i];
			}
			if(k == 1) {
				board = tBoard;
			}
		}
	}
	
	public void relocate(Entity oldE, Entity newE) {
		for(int i=0; i<board.length; i++) {
			if(board[i] == oldE) {
				board[i] = newE;
			}
		}
        logger.finer(  this.getClass().getName() + ": Relocated: " + oldE.toString() + ", to: " + newE.toString());
	}
	
	public void update() {
	    remainingSteps = board.length-1;
		for(int i=0; i<board.length ;i++, remainingSteps--) {
			if(board[i] instanceof Character) {
				((Character)board[i]).nextStep(createFlattenedBoard());
			}
		}
	}
	
	public XY getSize() {
		return size;
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < board.length; i++) {
			if (board[i] instanceof Wall) {
				s += "WALL";
			} else if (board[i] instanceof BadBeast) {
				s += "BAD_BEAST";
			} else if (board[i] instanceof BadPlant) {
				s += "BAD_PLANT";
			} else if (board[i] instanceof GoodBeast) {
				s += "GOOD_BEAST";
			} else if (board[i] instanceof GoodPlant) {
				s += "GOOD_PLANT";
			} else if (board[i] instanceof MasterSquirrel) {
				s += "MASTER_SQUIRREL";
			} else if (board[i] instanceof MiniSquirrel) {
				s += "MINI_SQUIRREL";
			}
			s += ", X: " + board[i].xy.getX() + ", Y: " + board[i].xy.getY() + '\n';
		}
		return s;
	}

    public long getRemainingSteps() {
	    return remainingSteps;
    }
}
