package de.hsa.games.fatsquirrel.FXUI;

import de.hsa.games.fatsquirrel.core.*;
import de.hsa.games.fatsquirrel.util.XYsupport;

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
        if (ui.getCommand().getCommandTypeInfo().getName().toUpperCase().equals("EXIT"))
            System.exit(0);
        player.setMoveDirection(XYsupport.valueOf(ui.getCommand().getCommandTypeInfo().getName()));
    }

    public long getFps() {
        return fps;
    }
}
