package de.hsa.games.fatsquirrel.core;

public class State {
    private int highscore;
    private Board board;
    public State(Board board) {
        highscore = 0;
        this.board= board;
    }

    /**
     * calls createFlattenedBoard() of Board
     *
     * @return
     */
    public FlattenedBoard flattenedBoard() {
        return board.createFlattenedBoard();
    }

    /**
     * calls update() of Board
     */
    public void update() {
        board.update();
    }

    public int getHighscore() {
        return highscore;
    }

}
