package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.core.mastersquirrelbot.ControllerContextImpl;

public class AutomatedMasterSquirrel  extends MasterSquirrel{
	private MasterSquirrelBot bot;
	public AutomatedMasterSquirrel(int id, int x, int y, Board board) {
		super(id, x, y);
		ControllerContextImpl controller = new ControllerContextImpl(this, board);
		bot = new MasterSquirrelBot(controller);
	}


}
