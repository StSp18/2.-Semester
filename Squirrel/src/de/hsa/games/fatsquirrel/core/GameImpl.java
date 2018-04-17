package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.ConsoleUI;
import de.hsa.games.fatsquirrel.UI;

public class GameImpl extends Game {
	State s;
	BoardView view;
	UI ui;

	public GameImpl(State s) {
		super(s);
		this.s = s;
	}

	protected void render() {
		view = s.flattenedBoard();
		ui = new ConsoleUI();
		ui.render(view);
	}

	protected void processInput() {
		HandOperatedMasterSquirrel[] sA = s.flattenedBoard().process();
		for (int i = 0; i < sA.length; i++) {
			s.flattenedBoard().tryMove(sA[i], ui.getCommand().getMD().getMoveDirection());
		}
	}

	protected void update() {
		s.update();
	}

}
