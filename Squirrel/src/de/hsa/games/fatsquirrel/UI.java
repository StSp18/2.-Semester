package de.hsa.games.fatsquirrel;
import de.hsa.games.fatsquirrel.core.BoardView;
import de.hsa.games.fatsquirrel.core.MoveDirection;

public interface UI {
	MoveCommand getCommand();
	void render(BoardView view);
}
