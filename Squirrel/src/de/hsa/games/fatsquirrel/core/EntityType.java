package de.hsa.games.fatsquirrel.core;

public enum EntityType {
	BadPlant('-'), GoodPlant('+'), BadBeast('B'), GoodBeast('G'), AutomatedMasterSquirrel('O'), HandOperatedMasterSquirrel('O'), MiniSquirrel('o'), Wall('|'), Air(' ');
	private final char wert;

	private EntityType(char wert) {
		this.wert = wert;
	}

	public char getChar() {
		return wert;
	}
}