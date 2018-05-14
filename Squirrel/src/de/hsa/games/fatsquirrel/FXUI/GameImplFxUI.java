package de.hsa.games.fatsquirrel.FXUI;

import de.hsa.games.fatsquirrel.core.*;

public class GameImplFxUI extends Game {
    HandOperatedMasterSquirrel player;

    public GameImplFxUI(State s, Board b, FxUI fxUI) {
        super(s, b);
        player = b.getPlayer();
        ui = fxUI;
    }

    @Override
    public void run() {
        render();
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
