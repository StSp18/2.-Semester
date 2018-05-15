package de.hsa.games.fatsquirrel.botapi;

import de.hsa.games.fatsquirrel.core.MoveDirection;

public class RndBotController implements BotController {
    @Override
    public void nextStep(ControllerContext view) {
        view.move(MoveDirection.rndMoveDirection().getXY());
    }
}
