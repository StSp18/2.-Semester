package JUnit;

import de.hsa.games.fatsquirrel.botapi.SpawnException;
import de.hsa.games.fatsquirrel.core.*;
import de.hsa.games.fatsquirrel.util.XY;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Integrity_Test {
    DummyBoardFactory dummyBoardFactory = new DummyBoardFactory(false);
    // Test Entities
    private final HandOperatedMasterSquirrel masterSquirrel = dummyBoardFactory.masterSquirrel;
    private final MiniSquirrel miniSquirrel = dummyBoardFactory.miniSquirrel;
    private final BadBeast badBeast = dummyBoardFactory.badBeast;
    private final GoodBeast goodBeast = dummyBoardFactory.goodBeast;
    private final BadPlant badPlant = dummyBoardFactory.badPlant;
    private final GoodPlant goodPlant = dummyBoardFactory.goodPlant;
    private final Board board = new Board(dummyBoardFactory);
    private FlattenedBoard flattenedBoard = board.createFlattenedBoard();

    public Integrity_Test() throws SpawnException {
    }

    @Test
    public void testUpdate() {
        board.update();
        assertEquals("MasterSquirrel should have stayed", new XY(1, 1), masterSquirrel.xy);
        assertEquals("BadBeast should have moved towards MasterSquirrel", new XY(3, 3), badBeast.xy);
        assertEquals("GoodBeast should have moved away from MasterSquirrel", new XY(1, 4), goodBeast.xy);
    }
}
