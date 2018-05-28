package de.hsa.games.fatsquirrel.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SquirrelLogger {
    private Logger logger = Logger.getLogger("SquirrelLogger");
    public SquirrelLogger(Level level) {
        logger.setLevel(level);
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLevel(Level level) {
        logger.setLevel(level);
    }
}
