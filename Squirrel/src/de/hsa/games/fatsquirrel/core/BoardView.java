package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XY;

public interface BoardView {
    /**
     * @param x Coordinate
     * @param y Coordinate
     * @return Type of Entity at that position
     */
    EntityType getEntityType(int x, int y);

    /**
     * @return size of Board
     */
    XY getSize();

}
