package JUnit;

import de.hsa.games.fatsquirrel.core.Board;
import de.hsa.games.fatsquirrel.core.BoardFactory;
import de.hsa.games.fatsquirrel.core.Entity;
import de.hsa.games.fatsquirrel.core.FlattenedBoard;

public class DummyBoard extends Board {
    /**
     * creates a Board with the Factory
     *
     * @param boardFactory
     */
    public DummyBoard(BoardFactory boardFactory) {
        super(boardFactory);
    }

    @Override
    public void add(Entity e) {

    }

    @Override
    public void remove(Entity e) {
    }

    @Override
    public void relocate(Entity oldE, FlattenedBoard flattenedBoard) {

    }

    @Override
    public void update() {

    }
}
