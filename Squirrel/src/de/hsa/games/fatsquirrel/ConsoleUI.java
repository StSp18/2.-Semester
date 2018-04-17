package de.hsa.games.fatsquirrel;

import de.hsa.games.fatsquirrel.core.BoardView;

public class ConsoleUI implements UI{
	public void render(BoardView view) {
		String s = "";
		for (int i = 0; i < view.getSize().getX()+2; i++) {
			for (int k = 0; k < view.getSize().getY()+2; k++) {
				s += view.getEntityType(i, k).getChar();
			}
			s += '\n';
		}
		System.out.println(s);
		
	}

	public MoveCommand getCommand() {
		return new MoveCommand();
	}
}
