package de.hsa.games.fatsquirrel.botimpls;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;

public class Group3WallEFactory implements BotControllerFactory {
    @Override
    public BotController createMasterBotController() {
        return new Group3WallE();
    }

    @Override
    public BotController createMiniBotController() {
        return new Group3WallE();
    }
}
