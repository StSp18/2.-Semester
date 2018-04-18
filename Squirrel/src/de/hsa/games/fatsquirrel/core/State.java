package de.hsa.games.fatsquirrel.core;

public class State {
	private int highscore;
	private Board board;
	public State(Board board){
		highscore = 0;
		this.board= board;
	}
	
	public FlattenedBoard flattenedBoard() {
		return new FlattenedBoard(board);
	}
	
	public void setMoveDirection(XY moveDirection, int x, int y) {
		board.moveCharacter(moveDirection, x, y);
	}
	
	public void update() {
		board.update(flattenedBoard());
	}
	
	public int getUserControlled() {
		return board.getConsoleControlledEntitys();
	}
	
	public int getHighscore() {
		return highscore;
	}

}
