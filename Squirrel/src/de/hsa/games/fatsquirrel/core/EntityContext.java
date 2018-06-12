package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.botapi.SpawnException;
import de.hsa.games.fatsquirrel.util.XY;

public interface EntityContext {
    /**
     * size of the Board
     *
     * @return
     */
    XY getSize();

    /**
     * changes the state of the miniSquirrel depending on what it encounters
     *
     * @param miniSquirrel
     * @param moveDirection
     */
    void tryMove(MiniSquirrel miniSquirrel, XY moveDirection);

    /**
     * changes the state of the goodBeast depending on what it encounters
     *
     * @param goodBeast
     * @param moveDirection
     */
    void tryMove(GoodBeast goodBeast, XY moveDirection);

    /**
     * changes the state of the badBeast depending on what it encounters
     *
     * @param badBeast
     * @param moveDirection
     */
    void tryMove(BadBeast badBeast, XY moveDirection);

    /**
     * changes the state of the masterSquirrel depending on what it encounters
     *
     * @param master
     * @param moveDirection
     */
    void tryMove(MasterSquirrel master, XY moveDirection);

    /**
     * returns the nearest Squirrel if it exits otherwise null
     *
     * @param pos
     * @return
     */
    Squirrel nearestPlayerEntity(XY pos);

    /**
     * removes a entity from the game
     *
     * @param entity
     */
    void kill(Entity entity);

    /**
     * replaces a entity with a new one from the same type
     *
     * @param entity
     */
    void killAndReplace(Entity entity);

    /**
     * returns the EntityType at the coordinate xy
     *
     * @param xy
     * @return
     */
    EntityType getEntityType(XY xy);

    /**
     * forces a master to create a miniSquirrel
     *
     * @param master
     * @param direction
     * @param energy
     * @throws SpawnException occurs if the masterSquirrel has to little energy or the given energy value is negative
     */
    void createMiniSquirrel(MasterSquirrel master, XY direction, int energy) throws SpawnException;

    /**
     * implodes a miniSquirrel and calculates the damage done
     *
     * @param miniSquirrel
     * @param radius
     */
    void implodeMiniSquirrel(MiniSquirrel miniSquirrel, int radius);

    /**
     * shows remaining steps before the Board gets reset
     *
     * @return
     */
    long getRemainingSteps();

    /**
     * returns true if the entity is a masterSquirrel and one of his miniSquirrels is at the given coordinate
     * @param xy
     * @param e
     * @return
     */
    boolean isMine(XY xy, Entity e);
}
