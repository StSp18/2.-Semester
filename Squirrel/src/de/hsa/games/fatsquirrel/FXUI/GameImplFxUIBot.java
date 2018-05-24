package de.hsa.games.fatsquirrel.FXUI;

import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.Game;
import de.hsa.games.fatsquirrel.core.MasterSquirrel;
import de.hsa.games.fatsquirrel.core.State;

public class GameImplFxUIBot extends Game {
    private MasterSquirrel[] players;

    public GameImplFxUIBot(State s, Board b, FxUI fxUI) {
        super(s, b);
        players = b.getPlayers();
        ui = fxUI;
    }

    @Override
    public void run() {
        render();
        ((FxUI) ui).message("Energy: " + String.valueOf(playersEnergy()));
        update();
    }

    private String playersEnergy() {
        String s = "";
        for(int i=0; i<players.length; i++) {
            s += "Player " + (i+1) + ": " + players[i].getEnergy() + ", ";
        }
        s = s.substring(0, s.length()-2);
        return s;
    }

    @Override
    public void processInput() {

    }
}
