package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.console.GameImpl;

public class Launcher {

	public static void main(String[] args) {
		Board b = new Board();
		State s = new State(b);
		GameImpl g = new GameImpl(s);
		g.run();
	}

}
