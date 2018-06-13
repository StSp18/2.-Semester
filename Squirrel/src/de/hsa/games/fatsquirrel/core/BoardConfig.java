package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XY;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class BoardConfig {
    private static Logger logger = Logger.getLogger("SquirrelLogger");
    private int amountOfWall = 10;
    private int amountOfGoodPlant = 6;
    private int amountOfBadPlant = 4;
    private int amountOfGoodBeast = 2;
    private int amountOfBadBeast = 2;
    private int amountOfHandOperatedMasterSquirrel;
    private int amountOfAutomatedMasterSquirrel;
    private int sizeX = 100;
    private int sizeY = 100;
    private XY size;
    private int wallCount;
    private int amountOfEntity;
    private List<String> botNames = new ArrayList<>();
    private long steps = 100;
    private Path highscoresPath = Paths.get("Squirrel/resources/highscoresPath.json");

    BoardConfig(boolean bots) {
        botNames.add("Group3RndFactory");
        botNames.add("Group3WallEFactory");
        Properties properties = new Properties();
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("BoardConfig.properties");

        if (inputStream != null) {
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                logger.severe("Couldn't load BoardConfig: " + e.getMessage());
                System.exit(-1);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.severe(e.getMessage());
                }
            }
            if (properties.containsKey("Wall"))
                amountOfWall = Integer.parseInt(properties.getProperty("Wall"));
            if (properties.containsKey("GoodPlant"))
                amountOfGoodPlant = Integer.parseInt(properties.getProperty("GoodPlant"));
            if (properties.containsKey("BadPlant"))
                amountOfBadPlant = Integer.parseInt(properties.getProperty("BadPlant"));
            if (properties.containsKey("GoodBeast"))
                amountOfGoodBeast = Integer.parseInt(properties.getProperty("GoodBeast"));
            if (properties.containsKey("BadBeast"))
                amountOfBadBeast = Integer.parseInt(properties.getProperty("BadBeast"));
            if (properties.containsKey("SizeX"))
                sizeX = Integer.parseInt(properties.getProperty("SizeX"));
            if (properties.containsKey("SizeY"))
                sizeY = Integer.parseInt(properties.getProperty("SizeY"));
            if (properties.containsKey("Steps"))
                steps = Integer.parseInt(properties.getProperty("Steps"));
            if(properties.containsKey("highscorePath"))
                highscoresPath = Paths.get(properties.getProperty("highscorePath"));
            if (properties.containsKey("BotNames")) {
                String names = properties.getProperty("BotNames").trim();
                botNames.clear();
                while (names.contains(" ")) {
                    botNames.add(names.substring(0, names.indexOf(' ')));
                    names = names.substring(names.indexOf(' ') + 1);
                }
                botNames.add(names);
            }
        }
        if (bots) {
            amountOfHandOperatedMasterSquirrel = 0;
            amountOfAutomatedMasterSquirrel = botNames.size();
        } else {
            //steps = 100000000;
            amountOfHandOperatedMasterSquirrel = 1;
            amountOfAutomatedMasterSquirrel = 0;
        }
        size = new XY(sizeX, sizeY);
        wallCount = size.x * 2 + size.y * 2 - 4;
        amountOfEntity = amountOfHandOperatedMasterSquirrel + amountOfAutomatedMasterSquirrel + amountOfBadBeast + amountOfGoodBeast + amountOfBadPlant + amountOfWall + amountOfGoodPlant;
    }

    public int getAmountOfWall() {
        return amountOfWall;
    }

    public int getAmountOfGoodPlant() {
        return amountOfGoodPlant;
    }

    public int getAmountOfBadPlant() {
        return amountOfBadPlant;
    }

    public int getAmountOfGoodBeast() {
        return amountOfGoodBeast;
    }

    public int getAmountOfBadBeast() {
        return amountOfBadBeast;
    }

    public int getAmountOfHandOperatedMasterSquirrel() {
        return amountOfHandOperatedMasterSquirrel;
    }

    public int getAmountOfAutomatedMasterSquirrel() {
        return amountOfAutomatedMasterSquirrel;
    }

    public XY getSize() {
        return size;
    }
    public int getAmountOfEntity() {
        return amountOfEntity;
    }
    public int getWallCount() {
        return wallCount;
    }

    public String[] getBotNames() {
        String[] names = new String[botNames.size()];
        for (int i = 0; i < names.length; i++) {
            names[i] = botNames.get(i);
        }
        return names;
    }

    public long getSteps() {
        return steps;
    }

    public Path getHighscoresPath() {
        return highscoresPath;
    }
}
