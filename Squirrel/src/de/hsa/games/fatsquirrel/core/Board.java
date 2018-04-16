package de.hsa.games.fatsquirrel.core;

public class Board{
	private Entity[][] board;
	
	public Board() {
		System.out.println("Constructor");
		BoardFactory bf = new BoardFactory();
		board = bf.factoryBoard();
	}
	
	
	public String toString() {
		String s ="";
		for(int i=0; i<board[0].length;i++) {
			for(int k=0; k<board[1].length;k++) {
				if(board[i][k]!=null) {
					if(board[i][k] instanceof Wall) {
						s += "|";
					} else if(board[i][k] instanceof BadBeast) {
						s += "M";
					} else if(board[i][k] instanceof BadPlant) {
						s += "-";
					} else if(board[i][k] instanceof GoodBeast) {
						s += "G";
					} else if(board[i][k] instanceof GoodPlant) {
						s += "+";
					} else if(board[i][k] instanceof MasterSquirrel) {
						s += "O";
					} else if(board[i][k] instanceof MiniSquirrel) {
						s += "o";
					}
				} else {
					s +=" ";
				}
			}
			s+="\n";
		}
		
		return s;
	}
}
