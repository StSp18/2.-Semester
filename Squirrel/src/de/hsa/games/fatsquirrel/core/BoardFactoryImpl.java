package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.util.XY;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class BoardFactoryImpl extends BoardConfig implements BoardFactory {
    private static Logger logger = Logger.getLogger("SquirrelLogger");

    public BoardFactoryImpl(boolean bots) {
        super(bots);
    }

    public List<Entity> factoryBoard() {
        int id = 0;
        XY[] rndCor = rndCor();
        List<Entity> board = new ArrayList<>();

        // Fill in of Entities
        for (int i = 0; i < getAmountOfHandOperatedMasterSquirrel(); i++, id++) {
            board.add(new HandOperatedMasterSquirrel(rndCor[id].x, rndCor[id].y));
        }
        for (int i = 0; i < getAmountOfAutomatedMasterSquirrel(); i++, id++) {
            board.add(new MasterSquirrelBot(rndCor[id].x, rndCor[id].y, getNextFactory(i)));
        }
        for (int i = 0; i < getAmountOfWall(); i++, id++) {
            board.add(new Wall(rndCor[id].x, rndCor[id].y));
        }
        for (int i = 0; i < getAmountOfGoodPlant(); i++, id++) {
            board.add(new GoodPlant(rndCor[id].x, rndCor[id].y));
        }
        for (int i = 0; i < getAmountOfBadPlant(); i++, id++) {
            board.add(new BadPlant(rndCor[id].x, rndCor[id].y));
        }
        for (int i = 0; i < getAmountOfGoodBeast(); i++, id++) {
            board.add(new GoodBeast(rndCor[id].x, rndCor[id].y));
        }
        for (int i = 0; i < getAmountOfBadBeast(); i++, id++) {
            board.add(new BadBeast(rndCor[id].x, rndCor[id].y));
        }
        // Fill in of Outside Walls
        for (int i = 0; i < getSize().x; i++, id++) {
            board.add(new Wall(i, 0));
        }
        for (int i = 0; i < getSize().x; i++, id++) {
            board.add(new Wall(i, getSize().y - 1));
        }
        for (int i = 1; i < getSize().y - 1; i++, id++) {
            board.add(new Wall(0, i));
        }
        for (int i = 1; i < getSize().y - 1; i++, id++) {
            board.add(new Wall(getSize().x - 1, i));
        }
        return board;
    }

    private BotControllerFactory getNextFactory(int i) {
        try {
            Class factory = Class.forName("de.hsa.games.fatsquirrel.botimpls." + getBotNames()[i]);
            return (BotControllerFactory) factory.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            logger.severe("Couldn't load BotFactory" + e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    private XY[] rndCor() {
        XY[] ranCor = new XY[getAmountOfEntity()];
        int count = 0;
        int rndX;
        int rndY;
        boolean check;

        while (ranCor[getAmountOfEntity() - 1] == null) {
            check = true;
            rndX = ThreadLocalRandom.current().nextInt(1, getSize().x - 1);
            rndY = ThreadLocalRandom.current().nextInt(1, getSize().y - 1);
            for (int i = 0; i < count; i++) {
                if (ranCor[i].x == rndX && ranCor[i].y == rndY) {
                    check = false;
                    break;
                }
            }
            if (check) {
                ranCor[count] = new XY(rndX, rndY);
                count++;
            }
        }

        return ranCor;

    }
}
