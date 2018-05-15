package de.hsa.games.fatsquirrel.botapi;

public class RndFactory implements BotControllerFactory{
    @Override
    public BotController createMasterBotController() {
        return new RndBotController();
    }

    @Override
    public BotController createMiniBotController() {
        return new RndBotController();
    }
}
