package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XY;

public interface Board_Interface {
    void add(Entity e);

    void remove(Entity e);

    void relocate(Entity oldE, FlattenedBoard flattenedBoard);

    XY getSize();

    long getRemainingSteps();

    MasterSquirrel[] getPlayers();

    MasterSquirrel getPlayer();
}
