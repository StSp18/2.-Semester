package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XY;

import java.util.List;

public interface BoardFactory {
    List<Entity> factoryBoard();

    XY getSize();

    int getAmountOfAutomatedMasterSquirrel();

    int getAmountOfHandOperatedMasterSquirrel();

    long getSteps();
}
