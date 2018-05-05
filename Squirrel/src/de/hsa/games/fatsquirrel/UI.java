package de.hsa.games.fatsquirrel;
import CommandPackage.Command;
import de.hsa.games.fatsquirrel.console.MoveCommand;
import de.hsa.games.fatsquirrel.core.BoardView;

public interface UI {
	Command getCommand();
	void render(BoardView view);
}
