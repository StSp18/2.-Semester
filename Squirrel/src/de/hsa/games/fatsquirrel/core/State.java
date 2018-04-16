package de.hsa.games.fatsquirrel.core;

public class State {
	private int highscore;
	private Board board;
	public State(Board board){
		highscore = 0;
		this.board= board;
		
		
	}
	
	public int getHighscore() {
		return highscore;
	}

}
