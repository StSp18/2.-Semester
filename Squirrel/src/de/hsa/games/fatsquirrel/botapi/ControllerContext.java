package de.hsa.games.fatsquirrel.botapi;

import de.hsa.games.fatsquirrel.core.EntityType;
import de.hsa.games.fatsquirrel.util.XY;

public interface ControllerContext {
    XY getViewLowerLeft ();
    XY getViewUpperRight();
    EntityType getEntityAt(XY xy) throws OutOfViewException;
    void move(XY direction);
    void spawnMiniBot(XY direction, int energy) throws SpawnException;
    int getEnergy();
    XY locate();
    XY directionOfMaster();
    long getRemainingSteps();
    boolean isMine(XY xy) throws OutOfViewException;

    void implode(int impactRadius);
}
