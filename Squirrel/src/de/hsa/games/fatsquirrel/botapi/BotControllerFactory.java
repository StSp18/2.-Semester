package de.hsa.games.fatsquirrel.botapi;

public interface BotControllerFactory {
    public BotController createMasterBotController();
    public BotController createMiniBotController();
}
