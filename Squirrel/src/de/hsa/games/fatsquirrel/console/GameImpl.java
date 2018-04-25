package de.hsa.games.fatsquirrel.console;

import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.Game;
import de.hsa.games.fatsquirrel.core.HandOperatedMasterSquirrel;
import de.hsa.games.fatsquirrel.core.State;

public class GameImpl extends Game {
	private HandOperatedMasterSquirrel player;
	private ConsoleUI ui;
	private State s;
	
	public GameImpl(State s, Board b) {
		super(s);
		player = b.getPlayer();
		this.s = s;
		ui = new ConsoleUI();
	}
	
	
	protected void processInput() {
		player.setMoveDirection(ui.getCommand().getMD());
	}

	protected void render() {
		ui.render(s.flattenedBoard());
	}
}
