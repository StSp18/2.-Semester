package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XY;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class Board{
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

    public MasterSquirrel[] getPlayers() {
        MasterSquirrel[] players = new MasterSquirrel[player_count];
        for(int i = 0; i< player_count; i++) {
            players[i] = (MasterSquirrel) board.get(i);
        }
        return players;
    }

    public MasterSquirrel getPlayer( ) {
        return (MasterSquirrel) board.get(0);
    }

    public FlattenedBoard createFlattenedBoard() {
        Entity[][] flattenedBoard = new Entity[size.x][size.y];
        for (Entity e : board) {
            flattenedBoard[e.xy.x][e.xy.y] = e;
        }
        return new FlattenedBoard(flattenedBoard, this);
    }

    public void add(Entity e) {
        board.add(e);
        logger.finer(  this.getClass().getName() + ": Added: " + e.toString());
    }


    public void remove(Entity e) {
        board.remove(e);
        logger.finer(this.getClass().getName() + ": Removed: " + e.toString());
    }

    public void relocate(Entity oldE, Entity newE) {
        board.set(board.indexOf(oldE), newE);
        logger.finer(  this.getClass().getName() + ": Relocated: " + oldE.toString() + ", to: " + newE.toString());
    }

    public void update() {
        remainingSteps = remainingSteps - 1;
        board.forEach(entity -> {
            if (entity instanceof Character) {
                ((Character) entity).nextStep(createFlattenedBoard());
            }
        });
    }

    public XY getSize() {
        return size;
    }

    public long getRemainingSteps() {
        return remainingSteps;
    }
    public String toString() {
        String s = "";
        for (Entity e : board) {
            if (e instanceof Wall) {
                s += "WALL";
            } else if (e instanceof BadBeast) {
                s += "BAD_BEAST";
            } else if (e instanceof BadPlant) {
                s += "BAD_PLANT";
            } else if (e instanceof GoodBeast) {
                s += "GOOD_BEAST";
            } else if (e instanceof GoodPlant) {
                s += "GOOD_PLANT";
            } else if (e instanceof MasterSquirrel) {
                s += "MASTER_SQUIRREL";
            } else if (e instanceof MiniSquirrel) {
                s += "MINI_SQUIRREL";
            }
            s += ", " + e.xy.toString() + '\n';
        }
        return s;
    }
}
