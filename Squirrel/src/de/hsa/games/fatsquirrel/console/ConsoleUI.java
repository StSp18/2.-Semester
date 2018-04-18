package de.hsa.games.fatsquirrel.console;

import de.hsa.games.fatsquirrel.UI;
import de.hsa.games.fatsquirrel.core.BoardView;

public class ConsoleUI implements UI{
	public void render(BoardView view) {
		String s = "";
		for (int i = 0; i < view.getSize().getY(); i++) {
			for (int k = 0; k < view.getSize().getX(); k++) {
				s += view.getEntityType(k, i).getChar();
			}
			s += '\n';
		}
		System.out.println(s);
		
	}

	public MoveCommand getCommand() {
		return new MoveCommand();
	}
}
