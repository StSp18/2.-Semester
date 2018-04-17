package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.ConsoleUI;
import de.hsa.games.fatsquirrel.UI;

public class GameImpl extends Game {
	BoardView view;
	UI ui;
	public GameImpl(State s) {
		super(s);
		view = s.flattenedBoard();
		ui = new ConsoleUI();
	}

	public void render() {
		ui.render(view);
	}

	public void processInput() {
		// TODO Auto-generated method stub

	}

}
