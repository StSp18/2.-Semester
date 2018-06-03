package de.hsa.games.fatsquirrel.botimpls;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.botimpls.Group3RndBotController;

public class Group3RndFactory implements BotControllerFactory {
    @Override
    public BotController createMasterBotController() {
        return new Group3RndBotController();
    }

    @Override
    public BotController createMiniBotController() {
        return new Group3RndBotController();
    }
}
