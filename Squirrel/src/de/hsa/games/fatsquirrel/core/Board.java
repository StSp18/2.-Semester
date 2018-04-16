package de.hsa.games.fatsquirrel.core;

public class Board implements BoardView {
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
