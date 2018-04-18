package de.hsa.games.fatsquirrel.console;

import de.hsa.games.fatsquirrel.UI;
import de.hsa.games.fatsquirrel.core.BoardView;
import de.hsa.games.fatsquirrel.core.EntityType;
import de.hsa.games.fatsquirrel.core.Game;
import de.hsa.games.fatsquirrel.core.State;

public class GameImpl extends Game {
	private State s;
	private BoardView view;
	private UI ui;

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
		for(int i=0; i<view.getSize().getY();i++) {
			for(int k=0; k<view.getSize().getX();k++) {
				if(view.getEntityType(k, i) == EntityType.HandOperatedMasterSquirrel) {
					s.setMoveDirection(ui.getCommand().getMD().getMoveDirection(), k, i);
				}
				if(view.getEntityType(k, i) == EntityType.GoodPlant) {
					s.flattenedBoard().planNextMove(k, i);
				}
				if(view.getEntityType(k, i) == EntityType.BadPlant) {
					s.flattenedBoard().planNextMove(k, i);
				}
			}
		}
	}


	protected void update() {
		s.update();
	}

}
