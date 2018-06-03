package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XY;

public interface BoardFactory {
    Entity[] factoryBoard();

    XY getSize();

    int getAmountOfAutomatedMasterSquirrel();

    int getAmountOfHandOperatedMasterSquirrel();

}
