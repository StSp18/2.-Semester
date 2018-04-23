package de.hsa.games.fatsquirrel;

import de.hsa.games.fatsquirrel.console.GameImpl;
import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.State;

public class Launcher {

	public static void main(String[] args) {
		Board b = new Board();
		State s = new State(b);
		GameImpl g = new GameImpl(s);
		g.run();
	}

}
