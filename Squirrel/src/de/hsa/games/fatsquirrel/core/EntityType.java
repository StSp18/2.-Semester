package de.hsa.games.fatsquirrel.core;

public enum EntityType {
	BAD_PLANT('-'), GOOD_PLANT('+'), BAD_BEAST('B'), GOOD_BEAST('G'), MASTER_SQUIRREL('O'), MINI_SQUIRREL('o'), WALL('|'), NONE(' ');
	private final char value;

	EntityType(char value) {
		this.value = value;
	}

	public char getChar() {
		return value;
	}
}