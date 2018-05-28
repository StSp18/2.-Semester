package de.hsa.games.fatsquirrel.core;

public class State {
    // TODO highscore
    private int highscore;
    private Board board;
    public State(Board board) {
        highscore = 0;
        this.board= board;
    }

    public FlattenedBoard flattenedBoard() {
        return board.createFlattenedBoard();
    }

    public void update() {
        board.update();
    }

    public int getHighscore() {
        return highscore;
    }

}
