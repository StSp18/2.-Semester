package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XY;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class Board implements Board_Interface {
    private static Logger logger = Logger.getLogger("SquirrelLogger");
    private Entity[] board;
    private XY size;
    private long remainingSteps;
    private int player_count;

    public Board(BoardFactory bf) {
        player_count = bf.getAmountOfAutomatedMasterSquirrel() + bf.getAmountOfHandOperatedMasterSquirrel();
        board = bf.factoryBoard();
        size = bf.getSize();
    }

    public MasterSquirrel[] getPlayers() {
        MasterSquirrel[] players = new MasterSquirrel[player_count];
        for(int i = 0; i< player_count; i++) {
            players[i] = (MasterSquirrel) board[i];
        }
        return players;
    }

    public MasterSquirrel getPlayer() {
        return (MasterSquirrel) board[0];
    }

    public FlattenedBoard createFlattenedBoard() {
        Entity[][] flattenedBoard = new Entity[size.x][size.y];
        for(int i=0; i < board.length; i++) {
            flattenedBoard[board[i].xy.x][board[i].xy.y] = board[i];
        }
        return new FlattenedBoard(flattenedBoard, this);
    }

    public void add(Entity e) {
        Entity [] tBoard = new Entity[board.length+1];
        for(int i=0; i<board.length; i++) {
            tBoard[i]= board[i];
        }
        tBoard[board.length] = e;
        logger.finer(  this.getClass().getName() + ": Added: " + e.toString());
        board = tBoard;
    }


    public void remove(Entity e) {
        int k = 0;
        Entity [] tBoard = new Entity[board.length-1];
        for(int i=0; i<board.length; i++) {
            if (board[i].equals(e)) {
                logger.finer(  this.getClass().getName() + ": Removed: " + board[i].toString());
                k=1;
            } else {
                tBoard[i-k]=board[i];
            }
            if(k == 1) {
                board = tBoard;
            }
        }
    }

    public void relocate(Entity oldE, FlattenedBoard flattenedBoard) {
        int rndX;
        int rndY;
        do {
            rndX = ThreadLocalRandom.current().nextInt(1, getSize().x);
            rndY = ThreadLocalRandom.current().nextInt(1, getSize().y);
        } while (flattenedBoard.getEntityType(rndX, rndY) != EntityType.NONE);
        try {
            Entity newE = oldE.getClass().getConstructor(int.class, int.class).newInstance(rndX, rndY);
            for (int i = 0; i < board.length; i++) {
                if (board[i] == oldE) {
                    board[i] = newE;
                }
            }
            logger.finer(this.getClass().getName() + ": Relocated: " + oldE.toString() + ", to: " + newE.toString());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.severe(e.getMessage());
        }
    }

    public void update() {
        remainingSteps = board.length-1;
        for(int i=0; i<board.length ;i++, remainingSteps--) {
            if(board[i] instanceof Character) {
                ((Character)board[i]).nextStep(createFlattenedBoard());
            }
        }
    }

    public XY getSize() {
        return size;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < board.length; i++) {
            if (board[i] instanceof Wall) {
                s += "WALL";
            } else if (board[i] instanceof BadBeast) {
                s += "BAD_BEAST";
            } else if (board[i] instanceof BadPlant) {
                s += "BAD_PLANT";
            } else if (board[i] instanceof GoodBeast) {
                s += "GOOD_BEAST";
            } else if (board[i] instanceof GoodPlant) {
                s += "GOOD_PLANT";
            } else if (board[i] instanceof MasterSquirrel) {
                s += "MASTER_SQUIRREL";
            } else if (board[i] instanceof MiniSquirrel) {
                s += "MINI_SQUIRREL";
            }
            s += ", X: " + board[i].xy.x + ", Y: " + board[i].xy.y + '\n';
        }
        return s;
    }

    public long getRemainingSteps() {
        return remainingSteps;
    }
}
