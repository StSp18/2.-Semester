package de.hsa.games.fatsquirrel.FXUI;

import de.hsa.games.fatsquirrel.core.*;
import de.hsa.games.fatsquirrel.util.XYsupport;

public class GameImplFxUI extends Game {

    public GameImplFxUI(State state, Board board, FxUI fxUI) {
        super(state, board, fxUI);
    }

    @Override
    public void run() {
        render();
        ((FxUI) ui).message("Energy: " + playersEnergy());
        processInput();
        update();
    }

    @Override
    public void processInput() {
        if (ui.getCommand().getCommandTypeInfo().getName().toUpperCase().equals("EXIT"))
            System.exit(0);
        player[0].setMoveDirection(XYsupport.valueOf(ui.getCommand().getCommandTypeInfo().getName()));
    }

    public long getFps() {
        return fps;
    }
}
