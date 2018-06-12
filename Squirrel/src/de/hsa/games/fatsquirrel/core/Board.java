package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XY;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class Board implements Board_Interface {
    private static Logger logger = Logger.getLogger("SquirrelLogger");
    private List<Entity> board;
    private XY size;
    private long remainingSteps;
    private int player_count;

    public Board(BoardFactory bf) {
        player_count = bf.getAmountOfAutomatedMasterSquirrel() + bf.getAmountOfHandOperatedMasterSquirrel();
        board = bf.factoryBoard();
        size = bf.getSize();
        remainingSteps = bf.getSteps();
    }

    /**
     * for Multi-player mode
     *
     * @return all Players
     */
    public MasterSquirrel[] getPlayers() {
        MasterSquirrel[] players = new MasterSquirrel[player_count];
        for(int i = 0; i< player_count; i++) {
            players[i] = (MasterSquirrel) board.get(i);
        }
        return players;
    }

    /**
     * for Single-player mode
     *
     * @return the Player
     */
    public MasterSquirrel getPlayer( ) {
        return (MasterSquirrel) board.get(0);
    }

    /**
     * creates 2D View of the Board
     *
     * @return
     */
    public FlattenedBoard createFlattenedBoard() {
        Entity[][] flattenedBoard = new Entity[size.x][size.y];
        for (Entity e : board) {
            flattenedBoard[e.xy.x][e.xy.y] = e;
        }
        return new FlattenedBoard(flattenedBoard, this);
    }

    /**
     * adds given Entity to the Board
     *
     * @param e
     */
    public void add(Entity e) {
        board.add(e);
        logger.finer(  this.getClass().getName() + ": Added: " + e.toString());
    }

    /**
     * removes an Entity from the Board if it exists
     *
     * @param e
     */
    public void remove(Entity e) {
        board.remove(e);
        logger.finer(this.getClass().getName() + ": Removed: " + e.toString());
    }

    /**
     * replaces an entity and replaces it with a new one from the same type at a new random place
     *
     * @param oldE
     * @param flattenedBoard
     */
    public void relocate(Entity oldE, FlattenedBoard flattenedBoard) {
        int rndX;
        int rndY;
        do {
            rndX = ThreadLocalRandom.current().nextInt(1, getSize().x);
            rndY = ThreadLocalRandom.current().nextInt(1, getSize().y);
        } while (flattenedBoard.getEntityType(rndX, rndY) != EntityType.NONE);
        try {
            Entity newE = oldE.getClass().getConstructor(int.class, int.class).newInstance(rndX, rndY);
            board.set(board.indexOf(oldE), newE);
            logger.finer(this.getClass().getName() + ": Relocated: " + oldE.toString() + ", to: " + newE.toString());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.severe(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * calls nextStep of each Character on the Board
     */
    public void update() {
        remainingSteps = remainingSteps - 1;
        board.forEach(entity -> {
            if (entity instanceof Character) {
                ((Character) entity).nextStep(createFlattenedBoard());
            }
        });
    }

    /**
     * returns size
     *
     * @return
     */
    public XY getSize() {
        return size;
    }

    /**
     * Tells how many steps are left before an restart
     *
     * @return
     */
    public long getRemainingSteps() {
        return remainingSteps;
    }

    public String toString() {
        String s = "";
        for (Entity e : board) {
            String name = e.getClass().getName();
            s += name.substring(name.lastIndexOf('.') + 1);
            s += ", " + e.xy.toString() + '\n';
        }
        return s;
    }
}
