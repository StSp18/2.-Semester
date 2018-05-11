package FXUI;

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
        update();
    }

    @Override
    public void processInput() {
        move();
    }

    public long getFps() {
        return fps;
    }

    public void move() {
        player.setMoveDirection(MoveDirection.valueOf(ui.getCommand().getCommandTypeInfo().getName()));
    }
}
