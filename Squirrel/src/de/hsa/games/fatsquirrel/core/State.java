package de.hsa.games.fatsquirrel.core;

public class State {
	private int highscore;
	private Board board;
	private FlattenedBoard flattenedBoard;
	public State(Board board){
		highscore = 0;
		this.board= board;
		flattenedBoard = new FlattenedBoard(this.board);
	}
	
	public FlattenedBoard flattenedBoard() {
		return flattenedBoard;
	}
	
	public void setMoveDirection(MoveDirection moveDirection, int x, int y) {
		board.setMoveDirection(moveDirection, x, y);
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
