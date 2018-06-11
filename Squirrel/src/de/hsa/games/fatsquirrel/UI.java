package de.hsa.games.fatsquirrel;
import de.hsa.games.fatsquirrel.CommandPackage.Command;
import de.hsa.games.fatsquirrel.core.BoardView;

public interface UI {

    /**
     * @return current Commad
     */
    Command getCommand();

    /**
     * @param view
     */
    void render(BoardView view);
}
