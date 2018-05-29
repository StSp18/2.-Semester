package de.hsa.games.fatsquirrel.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SquirrelLogger {
    private Logger logger = Logger.getLogger("SquirrelLogger");
    public SquirrelLogger(Level level) {
        logger.setLevel(level);
        FileHandler fh;
        try {
            fh = new FileHandler("D:\\LogFiles\\MyLogFile.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.setUseParentHandlers(false);
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLevel(Level level) {
        logger.setLevel(level);
    }
}
