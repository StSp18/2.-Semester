package de.hsa.games.fatsquirrel;
import de.hsa.games.fatsquirrel.CommandPackage.Command;
import de.hsa.games.fatsquirrel.core.BoardView;

public interface UI {

    /**
     * @return next Command
     */
    Command getCommand();

    /**
     * draws the 2D view of the Board
     * @param view
     */
    void render(BoardView view);
}
