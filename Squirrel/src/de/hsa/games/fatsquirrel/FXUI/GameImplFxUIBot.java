package de.hsa.games.fatsquirrel.FXUI;

import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.Game;
import de.hsa.games.fatsquirrel.core.State;

public class GameImplFxUIBot extends Game {

    public GameImplFxUIBot(State s, Board b, FxUI fxUI) {
        super(s, b);
        player = b.botPlayer();
        ui = fxUI;
    }

    @Override
    public void run() {
        render();
        ((FxUI) ui).message("Energy: " + String.valueOf(player.getEnergy()));
        update();
    }

    @Override
    public void processInput() {

    }
}
