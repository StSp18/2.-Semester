package de.hsa.games.fatsquirrel.console;

public class NotEnoughEnergyException extends Exception {

	public NotEnoughEnergyException (String msg) {
		super(msg);
	}
}