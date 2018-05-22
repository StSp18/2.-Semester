package de.hsa.games.fatsquirrel.FXUI;

import de.hsa.games.fatsquirrel.core.*;

public class GameImplFxUI extends Game {

    public GameImplFxUI(State s, Board b, FxUI fxUI) {
        super(s, b);
        player = b.getPlayer();
        ui = fxUI;
    }

    @Override
    public void run() {
        render();
        ((FxUI) ui).message("Energy: " + String.valueOf(player.getEnergy()));
        processInput();
        update();
    }

    @Override
    public void processInput() {
        player.setMoveDirection(MoveDirection.valueOf(ui.getCommand().getCommandTypeInfo().getName().toLowerCase()));
    }

    public long getFps() {
        return fps;
    }
}