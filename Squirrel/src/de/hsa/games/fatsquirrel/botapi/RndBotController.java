package de.hsa.games.fatsquirrel.botapi;

import de.hsa.games.fatsquirrel.util.XYsupport;

public class RndBotController implements BotController {
    @Override
    public void nextStep(ControllerContext view) {
        view.getEnergy();
        view.move(XYsupport.rndMoveDirection());
    }
}
