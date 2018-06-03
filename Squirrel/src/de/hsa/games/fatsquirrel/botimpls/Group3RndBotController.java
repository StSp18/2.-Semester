package de.hsa.games.fatsquirrel.botimpls;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.util.XYsupport;

public class Group3RndBotController implements BotController {
    @Override
    public void nextStep(ControllerContext view) {
        view.getEnergy();
        view.move(XYsupport.rndMoveDirection());
    }
}
