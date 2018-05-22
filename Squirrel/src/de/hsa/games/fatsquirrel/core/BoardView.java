package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.util.XY;

public interface BoardView {
	public EntityType getEntityType(int x, int y);
	public XY getSize();
	
}
