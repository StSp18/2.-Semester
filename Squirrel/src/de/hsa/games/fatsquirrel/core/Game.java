package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.UI;

public abstract class Game {
    protected State state;
    protected UI ui;
    protected Board board;
    protected long fps = 100;
    protected MasterSquirrel player;

    public Game(State state, Board board) {
        this.state = state;
        this.board = board;
    }

    public abstract void run();

    protected void render() {
        ui.render(state.flattenedBoard());
    }

    public abstract void processInput();

    protected void update() {
        state.update();
    }

    public long getFps(){
        return fps;
    }
}
