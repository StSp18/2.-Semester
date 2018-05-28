package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.botapi.SpawnException;
import de.hsa.games.fatsquirrel.util.XY;

public interface EntityContext {
    XY getSize();
    void tryMove(MiniSquirrel miniSquirrel, XY moveDirection);
    void tryMove(GoodBeast goodBeast, XY moveDirection);
    void tryMove(BadBeast badBeast, XY moveDirection);
    void tryMove(MasterSquirrel master, XY moveDirection);
    Squirrel nearestPlayerEntity(XY pos);
    void kill(Entity entity);
    void killAndReplace(Entity entity);
    EntityType getEntityType(XY xy);
    void createMiniSquirrel(MasterSquirrel master, XY direction, int energy) throws SpawnException;
    void implodeMiniSquirrel(MiniSquirrel miniSquirrel, int radius);
    long getRemainingSteps();
    boolean isMine(XY xy, Entity e);
}
