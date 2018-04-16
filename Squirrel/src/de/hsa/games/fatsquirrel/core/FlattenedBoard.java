package de.hsa.games.fatsquirrel.core;

public class FlattenedBoard implements BoardView ,EntityContext{
		private Board b;
	public FlattenedBoard(Board b) {
		this.b = b;
	}
	
	public Entity[][] flatten() {
		return b.flatten();
	}

	public EntityType getEntityType(int x, int y) {
		
	}
	public XY getSize() {
		return b.getSize();
	}
	
	public String toString() {
		return;
	}
}
