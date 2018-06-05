package JUnit;

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
    private final Mockery context = new JUnit4Mockery();
    private final HandOperatedMasterSquirrel masterSquirrel = new HandOperatedMasterSquirrel(1, 1);
    private final BadBeast badBeast = new BadBeast(4, 4);
    private final GoodBeast goodBeast = new GoodBeast(1, 4);
    private final BadPlant badPlant = new BadPlant(4, 1);
    private final GoodPlant goodPlant = new GoodPlant(3, 1);
    private final Board board = getMockedBoard();
    private FlattenedBoard flattenedBoard = board.createFlattenedBoard();

    @Test
    public void testUpdate() {
        board.update();
        assertEquals("MasterSquirrel should have stayed", new XY(1, 1), masterSquirrel.xy);
        assertEquals("BadBeast should have moved towards MasterSquirrel", new XY(3, 3), badBeast.xy);
        assertEquals("GoodBeast should have moved away from MasterSquirrel", new XY(1, 4), goodBeast.xy);
    }


    private Board getMockedBoard() {
        final BoardFactory boardFactory = context.mock(BoardFactory.class);
        context.checking(new Expectations() {{
            oneOf(boardFactory).getSteps();
            will(returnValue((long) 100));
            oneOf(boardFactory).getAmountOfHandOperatedMasterSquirrel();
            will(returnValue(1));
            oneOf(boardFactory).getAmountOfAutomatedMasterSquirrel();
            will(returnValue(0));
            oneOf(boardFactory).factoryBoard();
            will(returnValue(boardFactoryFake()));
            oneOf(boardFactory).getSize();
            will(returnValue(new XY(6, 6)));
        }});
        return new Board(boardFactory);
    }

    private List<Entity> boardFactoryFake() {
        final List<Entity> entities = new ArrayList<>();
        int id = 0;
        entities.add(masterSquirrel);
        entities.add(badBeast);
        entities.add(goodBeast);
        entities.add(badPlant);
        entities.add(goodPlant);
        for (int i = 0; i < 6; i++) {
            entities.add(new Wall(i, 0));
        }
        for (int i = 0; i < 6; i++) {
            entities.add(new Wall(i, 5));
        }
        for (int i = 1; i < 5; i++) {
            entities.add(new Wall(0, i));
        }
        for (int i = 1; i < 5; i++) {
            entities.add(new Wall(5, i));
        }
        return entities;
    }
}
