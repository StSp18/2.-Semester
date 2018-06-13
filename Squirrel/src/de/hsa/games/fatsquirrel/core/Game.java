package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.UI;

public abstract class Game {
    protected final State state;
    protected final UI ui;
    protected final Board board;
    protected final long fps = 100;
    protected final MasterSquirrel[] player;

    public Game(State state, Board board, UI ui) {
        this.state = state;
        this.board = board;
        this.ui = ui;
        player = board.getPlayers();
    }

    public abstract void run();

    public abstract void processInput();

    protected void render() {
        ui.render(state.flattenedBoard());
    }

    protected void update() {
        state.update();
    }

    protected String playersEnergy() {
        String s = "";
        for(int i=0; i<player.length; i++) {
            s += "Player " + (i+1) + ": " + player[i].getEnergy() + ", ";
        }
        s = s.substring(0, s.length()-2);
        return s;
    }

    public long getFps(){
        return fps;
    }
}
