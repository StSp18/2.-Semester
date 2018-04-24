package de.hsa.games.fatsquirrel.console;

import de.hsa.games.fatsquirrel.UI;
import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.Game;
import de.hsa.games.fatsquirrel.core.HandOperatedMasterSquirrel;
import de.hsa.games.fatsquirrel.core.State;

public class GameImpl extends Game {
	private HandOperatedMasterSquirrel player;
	private UI cUi;
	private Board b;
	
	public GameImpl(State s, Board b) {
		super(s);
		cUi = new ConsoleUI();
		player = b.getPlayer();
		this.b = b;
		
	}
	
	
	protected void processInput() {
		player.setMoveDirection(cUi.getCommand().getMD());
	}

	protected void render() {
		cUi.render(b.createflattenedBoard());
	}
}
