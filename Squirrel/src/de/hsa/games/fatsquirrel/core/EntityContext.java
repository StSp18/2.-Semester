package de.hsa.games.fatsquirrel.core;

public interface EntityContext {
	public XY getSize();
	public void tryMove(MiniSquirrel miniSquirrel, XY moveDirection);
	public void tryMove(GoodBeast goodBeast, XY moveDirection);
	public void tryMove(BadBeast badBeast, XY moveDirection);
	public void tryMove(MasterSquirrel master, XY moveDirection);
	public Squirrel nearestPlayerEntity(XY pos);
	public void kill(Entity entity);
	public void killAndReplace(Entity entity);
	public EntityType getEntityType(XY xy);
}
