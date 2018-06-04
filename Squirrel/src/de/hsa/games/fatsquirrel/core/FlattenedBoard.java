package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.botapi.SpawnException;
import de.hsa.games.fatsquirrel.util.XY;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class FlattenedBoard implements BoardView, EntityContext {
    private static Logger logger = Logger.getLogger("SquirrelLogger");
    private Board board;
    private Entity[][] flattenedBoard;

    public FlattenedBoard(Entity[][] fb, Board b) {
        this.flattenedBoard = fb;
        this.board = b;
    }

    public EntityType getEntityType(int x, int y) {
        return getEntityType(new XY(x, y));
    }

    public EntityType getEntityType(XY xy) {
        Entity e = flattenedBoard[xy.x][xy.y];
        if(e == null) {
            return EntityType.NONE;
        }
        return EntityType.values()[e.getId()];
    }

    @Override
    public void createMiniSquirrel(MasterSquirrel master,  XY direction, int energy) throws SpawnException {
        board.add(master.createMiniSquirrel(energy, direction));
    }

    @Override
    public void implodeMiniSquirrel(MiniSquirrel miniSquirrel, int radius) {
        int impactArea = (int) (radius * radius * 3.14);
        int accumulatedEnergy = 0;
        int xBorderMin = 0;
        int xBorderMax = getSize().x;
        int yBorderMin = 0;
        int yBorderMax = getSize().y;
        if(miniSquirrel.xy.x-radius > xBorderMin) {
            xBorderMin = miniSquirrel.xy.x-radius;
        }
        if(miniSquirrel.xy.x + radius < xBorderMax) {
            xBorderMax = miniSquirrel.xy.x + radius;
        }
        if(miniSquirrel.xy.y-radius > yBorderMin) {
            yBorderMin = miniSquirrel.xy.y-radius;
        }
        if(miniSquirrel.xy.y + radius < yBorderMax) {
            yBorderMax = miniSquirrel.xy.y + radius;
        }

        for(int i=xBorderMin; i<xBorderMax; i++) {
            for (int k=yBorderMin; k<yBorderMax;k++) {
                int distance = 0;
                if(miniSquirrel.xy.x-i<0) {
                    distance -= miniSquirrel.xy.x-i;
                } else {
                    distance += miniSquirrel.xy.x-i;
                }
                if(miniSquirrel.xy.y-k<0) {
                    distance -= miniSquirrel.xy.y-k;
                } else {
                    distance += miniSquirrel.xy.y-k;
                }
                int energyLoss = 200 * miniSquirrel.getEnergy()/impactArea * (1 - distance/radius);
                    Entity e = getEntity(flattenedBoard[i][k].xy);
                    switch (getEntityType(flattenedBoard[i][k].xy)) {
                        case BAD_PLANT:
                        case BAD_BEAST:
                        case GOOD_PLANT:
                        case GOOD_BEAST:
                            accumulatedEnergy += looseEnergy(e, energyLoss);
                            break;
                        case MASTER_SQUIRREL:
                            if(!((MasterSquirrel)e).myMiniSquirrel(miniSquirrel)) {
                                if(e.getEnergy() < energyLoss) {
                                    accumulatedEnergy += e.getEnergy();
                                } else {
                                    accumulatedEnergy += energyLoss;
                                }
                                e.updateEnergy(-energyLoss);
                            }
                            break;
                        case MINI_SQUIRREL:
                            if(!((MiniSquirrel)e).getMaster().myMiniSquirrel(miniSquirrel)) {
                                if (e.getEnergy() < energyLoss) {
                                    accumulatedEnergy += e.getEnergy();
                                    kill(e);
                                } else {
                                    accumulatedEnergy += energyLoss;
                                    e.updateEnergy(-energyLoss);
                                }
                            }
                            break;
                        case WALL:
                        case NONE:
                            break;
                    }

            }
        }
        miniSquirrel.getMaster().updateEnergy(accumulatedEnergy);
        kill(miniSquirrel);
        logger.finer("MiniSquirrel imploded and collected " + accumulatedEnergy + " for his Master");
    }

    @Override
    public long getRemainingSteps() {
        return board.getRemainingSteps();
    }

    @Override
    public boolean isMine(XY xy, Entity e) {
        if(e instanceof MasterSquirrel) {
            if(getEntityType(xy) == EntityType.MINI_SQUIRREL) {
                return ((MiniSquirrel) getEntity(xy)).getMaster() == e;
            }
        } else if (e instanceof MiniSquirrel) {
            return ((MiniSquirrel) e).getMaster() == getEntity(xy);
        }
        return false;
    }

    private int looseEnergy(Entity e, int energyLoss) {
        int accumulatedEnergy;
        int k = 1;
        if(e.getEnergy() < 0) {
            k = -1;
        }
        if(k*e.getEnergy() < energyLoss) {
            accumulatedEnergy = k*e.getEnergy();
            killAndReplace(e);
        } else {
            accumulatedEnergy = energyLoss;
            e.updateEnergy(k*-energyLoss);
        }
        return accumulatedEnergy;
    }

    public XY getSize() {
        return board.getSize();
    }

    public void tryMove(MasterSquirrel master, XY moveDirection) {
        XY newCoordinates = master.xy.plus(moveDirection);
        logger.finer("try move MasterSquirrel to: " + newCoordinates.toString());
        if (!newCoordinates.equals(master.xy)) {
            switch (getEntityType(newCoordinates)) {
            case NONE:
                break;
            case BAD_BEAST:
                if (((BadBeast) getEntity(newCoordinates)).bite(master)) {
                    killAndReplace(getEntity(newCoordinates));
                    logger.finest("Master walked into a BadBeast");
                } else {
                    return;
                }
                break;
            case BAD_PLANT:
            case GOOD_BEAST:
            case GOOD_PLANT:
                master.updateEnergy(getEntity(newCoordinates).getEnergy());
                killAndReplace(getEntity(newCoordinates));
                logger.finest("Master walked into a replaceable Entity");
                break;
            case MASTER_SQUIRREL:
                logger.finest("Master walked into another player");
                return;
            case MINI_SQUIRREL:
                if (master.myMiniSquirrel(getEntity(newCoordinates))) {
                    master.updateEnergy(getEntity(newCoordinates).getEnergy());
                    logger.finest("Master collected his MiniSquirrel");
                } else {
                    master.updateEnergy(150);
                    logger.finest("Master eliminated an enemy MiniSquirrel");
                }
                kill(getEntity(newCoordinates));
                break;
            case WALL:
                master.updateEnergy(getEntity(newCoordinates).getEnergy());
                master.wallBump();
                logger.finest("Master bumped into a Wall");
                return;
            default:
                break;
            }
            master.move(moveDirection);
            logger.finer("Moved Master");
        }
    }

    public void tryMove(MiniSquirrel miniSquirrel, XY moveDirection) {
        XY newCoordinates = miniSquirrel.xy.plus(moveDirection);
        logger.finer("Try move MiniSquirrel to:" + newCoordinates.toString());
        if (!newCoordinates.equals(miniSquirrel.xy)) {
            switch (getEntityType(newCoordinates)) {
            case NONE:
                break;
            case BAD_BEAST:
                if (((BadBeast) getEntity(newCoordinates)).bite(miniSquirrel)) {
                    killAndReplace(getEntity(newCoordinates));
                    logger.finest("Mini walked into a BadBeast");
                } else {
                    miniSquirrel.updateEnergy(-1);
                    if (miniSquirrel.getEnergy() <= 0) {
                        kill(miniSquirrel);
                        logger.finer("MiniSquirrel died through this move");
                    }
                    return;
                }
                break;
            case BAD_PLANT:
            case GOOD_BEAST:
            case GOOD_PLANT:
                miniSquirrel.updateEnergy(getEntity(newCoordinates).getEnergy());
                killAndReplace(getEntity(newCoordinates));
                logger.finest("Mini walked into a replaceable Entity");
                break;
            case MASTER_SQUIRREL:
                if (miniSquirrel.getMaster() == getEntity(newCoordinates)) {
                    getEntity(newCoordinates).updateEnergy(miniSquirrel.getEnergy());
                    logger.finest("Mini walked into his Master");
                } else {
                    logger.finest("Mini walked into an enemy player");
                }
                kill(miniSquirrel);
                return;
            case MINI_SQUIRREL:
                if (miniSquirrel.getMaster() != ((MiniSquirrel) getEntity(newCoordinates)).getMaster()) {
                    kill(miniSquirrel);
                    logger.finest("Mini walked into an enemy MiniSquirrel");
                }
                return;
            case WALL:
                miniSquirrel.updateEnergy(getEntity(newCoordinates).getEnergy());
                miniSquirrel.wallBump();
                logger.finest("Mini bumped into a Wall");
                return;
            default:
                break;

            }
            miniSquirrel.updateEnergy(-1);
            if (miniSquirrel.getEnergy() <= 0) {
                kill(miniSquirrel);
                logger.finer("MiniSquirrel died through this move");
            } else {
                miniSquirrel.move(moveDirection);
                logger.finer("Moved MiniSquirrel");
            }
        }
    }

    public void tryMove(GoodBeast goodBeast, XY moveDirection) {
        XY newCoordinates = goodBeast.xy.plus(moveDirection);
        logger.finer("Try move GoodBeast to:" + newCoordinates.toString());
        if (!newCoordinates.equals(goodBeast.xy)) {
            switch (getEntityType(newCoordinates)) {
            case NONE:
                goodBeast.move(moveDirection);
                logger.finest("GoodBeast moved");
                break;
            case MASTER_SQUIRREL:
            case MINI_SQUIRREL:
                getEntity(newCoordinates).updateEnergy(goodBeast.getEnergy());
                killAndReplace(goodBeast);
                logger.finest("GoodBeast died through this move");
                break;
            default:
                break;
            }
        }
    }

    public void tryMove(BadBeast badBeast, XY moveDirection) {
        XY newCoordinates = badBeast.xy.plus(moveDirection);
        logger.finer("Try move BadBeast to:" + newCoordinates.toString());
        if (!newCoordinates.equals(badBeast.xy)) {
            switch (getEntityType(newCoordinates)) {
            case NONE:
                badBeast.move(moveDirection);
                logger.finest("BadBeast moved");
                break;
            case MASTER_SQUIRREL:
            case MINI_SQUIRREL:
                if (badBeast.bite(getEntity(newCoordinates))) {
                    killAndReplace(badBeast);
                    logger.finest("BadBeast died through this move");
                }
                if (getEntity(newCoordinates).getEnergy()<=0 && getEntityType(newCoordinates) == EntityType.MINI_SQUIRREL) {
                    kill(getEntity(newCoordinates));
                    badBeast.move(moveDirection);
                    logger.finest("BadBeast killed MiniSquirrel");
                }
                break;
            default:
                break;
            }
        }
    }

    public Squirrel nearestPlayerEntity(XY pos) {
        boolean right = true;
        boolean left = true;
        boolean up = true;
        boolean down = true;
        for (int i = 1; i <= 6; i++) {
            if (pos.x + i > flattenedBoard[0].length - 1)
                right = false;
            if (pos.x - i < 0)
                left = false;
            if (pos.y + i > flattenedBoard[1].length - 1)
                down = false;
            if (pos.y - i < 0)
                up = false;
            for (int k = 0; k <= i; k++) {
                if (right && up && flattenedBoard[pos.x + k][pos.y - i] instanceof Squirrel) {
                    return (Squirrel) flattenedBoard[pos.x + k][pos.y - i];
                }
                if (right && up && flattenedBoard[pos.x + i][pos.y - k] instanceof Squirrel) {
                    return (Squirrel) flattenedBoard[pos.x + i][pos.y - k];
                }
                // obenlinks
                if (left && up && flattenedBoard[pos.x - i][pos.y - k] instanceof Squirrel) {
                    return (Squirrel) flattenedBoard[pos.x - i][pos.y - k];
                }
                if (left && up && flattenedBoard[pos.x - k][pos.y - i] instanceof Squirrel) {
                    return (Squirrel) flattenedBoard[pos.x - k][pos.y - i];
                }
                // untenrechts
                if (right && down && flattenedBoard[pos.x + k][pos.y + i] instanceof Squirrel) {
                    return (Squirrel) flattenedBoard[pos.x + k][pos.y + i];
                }
                if (right && down && flattenedBoard[pos.x + i][pos.y + k] instanceof Squirrel) {
                    return (Squirrel) flattenedBoard[pos.x + i][pos.y + k];
                }
                // untenlinks
                if (left && down && flattenedBoard[pos.x - i][pos.y + k] instanceof Squirrel) {
                    return (Squirrel) flattenedBoard[pos.x - i][pos.y + k];
                }
                if (left && down && flattenedBoard[pos.x - k][pos.y + i] instanceof Squirrel) {
                    return (Squirrel) flattenedBoard[pos.x - k][pos.y + i];
                }
            }
        }
        return null;
    }

    public void kill(Entity e) {
        board.remove(e);
    }

    public void killAndReplace(Entity e) {
        int rndX;
        int rndY;
        do {
            rndX = ThreadLocalRandom.current().nextInt(1, flattenedBoard[0].length);
            rndY = ThreadLocalRandom.current().nextInt(1, flattenedBoard[1].length);
        } while (getEntityType(rndX, rndY) != EntityType.NONE || (e.xy.x == rndX && e.xy.y == rndY));
        Class entity = e.getClass();
        try {
            board.relocate(e, (Entity) entity.getDeclaredConstructor(int.class, int.class).newInstance(rndX, rndY));
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e1) {
            logger.severe("Couldn't relocate Entity: " + e.toString() + "Exception: " + e1.getMessage());
        }
    }

    private Entity getEntity(XY coordinates) {
        return flattenedBoard[coordinates.x][coordinates.y];
    }

    public String toString() {
        String s = "";

        for (int k = 0; k < flattenedBoard[1].length; k++) {
            for (int i = 0; i < flattenedBoard[0].length; i++) {
                if (flattenedBoard[i][k] instanceof BadPlant) {
                    s += '-';
                } else if (flattenedBoard[i][k] instanceof GoodPlant) {
                    s += '+';
                } else if (flattenedBoard[i][k] instanceof BadBeast) {
                    s += 'B';
                } else if (flattenedBoard[i][k] instanceof GoodBeast) {
                    s += 'G';
                } else if (flattenedBoard[i][k] instanceof MasterSquirrel) {
                    s += 'O';
                } else if (flattenedBoard[i][k] instanceof MiniSquirrel) {
                    s += 'o';
                } else if (flattenedBoard[i][k] instanceof Wall) {
                    s += '|';
                } else {
                    s += ' ';
                }
            }
            s += '\n';
        }
        return s;
    }

}
