package JUnit;

import de.hsa.games.fatsquirrel.botapi.SpawnException;
import de.hsa.games.fatsquirrel.core.*;
import de.hsa.games.fatsquirrel.util.XY;

import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Action;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

public class FlattenedBoard_Test {
    private final Mockery context = new JUnit4Mockery();
    private final HandOperatedMasterSquirrel masterSquirrel = new HandOperatedMasterSquirrel(1, 1);
    private final MiniSquirrel miniSquirrel = masterSquirrel.createMiniSquirrel(100, XY.DOWN);
    private final BadBeast badBeast = new BadBeast(4, 4);
    private final GoodBeast goodBeast = new GoodBeast(1, 4);
    private final BadPlant badPlant = new BadPlant(4, 1);
    private final GoodPlant goodPlant = new GoodPlant(3, 1);
    private final Board board = getMockedBoard();
    private FlattenedBoard flattenedBoard = board.createFlattenedBoard();
    public FlattenedBoard_Test() throws SpawnException {
    }

    @Test
    public void testImplodeMiniSquirrel() {

    }

    @Test
    public void testTryMoveMasterSquirrel() {
        board.add(miniSquirrel);
        flattenedBoard = board.createFlattenedBoard();
        int energy = masterSquirrel.getEnergy();
        moveMasterSquirrel(XY.DOWN);
        assertEquals("Should have eaten miniSquirrel", energy + 100, masterSquirrel.getEnergy());
        moveMasterSquirrel(XY.DOWN);
        energy = masterSquirrel.getEnergy();
        moveMasterSquirrel(XY.DOWN);
        assertEquals("Should have eaten GoodBeast", energy + 200, masterSquirrel.getEnergy());
        moveMasterSquirrel(XY.RIGHT_UP);
        moveMasterSquirrel(XY.RIGHT_UP);
        energy = masterSquirrel.getEnergy();
        moveMasterSquirrel(XY.RIGHT_UP);
        assertEquals("Should have eaten BadPlant", energy - 100, masterSquirrel.getEnergy());
        energy = masterSquirrel.getEnergy();
        moveMasterSquirrel(XY.LEFT);
        assertEquals("Should have eaten GoodPlant", energy + 100, masterSquirrel.getEnergy());
        moveMasterSquirrel(XY.RIGHT_DOWN);
        moveMasterSquirrel(XY.DOWN);
        masterSquirrel.updateEnergy(2000);
        for (int i = 0; i < 7; i++) {
            energy = masterSquirrel.getEnergy();
            flattenedBoard.tryMove(masterSquirrel, XY.DOWN);
            assertEquals("Should have been bitten by BadBeast, Steps " + (i + 1), energy - 150, masterSquirrel.getEnergy());
        }
    }

    private void moveMasterSquirrel(XY xy) {
        XY before = masterSquirrel.xy;
        flattenedBoard.tryMove(masterSquirrel, xy);
        assertEquals("MasterSquirrel should have moved there", before.plus(xy), masterSquirrel.xy);
    }

    @Test
    public void testTryMoveMiniSquirrel() {
        board.add(miniSquirrel);
        flattenedBoard = board.createFlattenedBoard();
        moveMiniSquirrel(XY.DOWN);
        int energy = miniSquirrel.getEnergy();
        moveMiniSquirrel(XY.DOWN);
        assertEquals("Should have eaten GoodBeast", energy + 199, miniSquirrel.getEnergy());
        moveMiniSquirrel(XY.RIGHT_UP);
        moveMiniSquirrel(XY.RIGHT_UP);
        energy = miniSquirrel.getEnergy();
        moveMiniSquirrel(XY.RIGHT_UP);
        assertEquals("Should have eaten BadPlant", energy - 101, miniSquirrel.getEnergy());
        energy = miniSquirrel.getEnergy();
        moveMiniSquirrel(XY.LEFT);
        assertEquals("Should have eaten GoodPlant", energy + 99, miniSquirrel.getEnergy());
        moveMiniSquirrel(XY.RIGHT_DOWN);
        moveMiniSquirrel(XY.DOWN);
        miniSquirrel.updateEnergy(2000);
        for (int i = 0; i < 7; i++) {
            energy = miniSquirrel.getEnergy();
            flattenedBoard.tryMove(miniSquirrel, XY.DOWN);
            assertEquals("Should have been bitten by BadBeast, Steps " + (i + 1), energy - 151, miniSquirrel.getEnergy());
        }
        moveMiniSquirrel(XY.LEFT_UP);
        moveMiniSquirrel(XY.LEFT_UP);
        int master_energy = masterSquirrel.getEnergy();
        flattenedBoard.tryMove(miniSquirrel, XY.LEFT_UP);
        assertEquals("Should have given energy to MasterSquirrel", master_energy + miniSquirrel.getEnergy(), masterSquirrel.getEnergy());
    }

    private void moveMiniSquirrel(XY xy) {
        XY before = miniSquirrel.xy;
        flattenedBoard.tryMove(miniSquirrel, xy);
        assertEquals("MiniSquirrel should have moved there", before.plus(xy), miniSquirrel.xy);
    }

    @Test
    public void testTryMoveBadBeast() {
        masterSquirrel.updateEnergy(2000);
        for (int i = 0; i < 2; i++) {
            XY before = badBeast.xy;
            flattenedBoard.tryMove(badBeast, XY.LEFT_UP);
            assertEquals("BadBeast should have moved there", before.plus(XY.LEFT_UP), badBeast.xy);
        }
        for (int i = 0; i < 7; i++) {
            int master_energy = masterSquirrel.getEnergy();
            int badBeast_energy = badBeast.getEnergy();
            flattenedBoard.tryMove(badBeast, XY.LEFT_UP);
            assertEquals("MasterSquirrel should loose energy, Step " + (i + 1), (master_energy - 150), masterSquirrel.getEnergy());
            assertEquals("BadBeast should loose energy", (badBeast_energy - 150), badBeast.getEnergy());
        }
        assertEquals("BadBeast should have no energy left", 0, badBeast.getEnergy());
    }

    @Test
    public void testTryMoveGoodBeast() {
        for (int i = 0; i < 2; i++) {
            XY before = goodBeast.xy;
            flattenedBoard.tryMove(goodBeast, XY.UP);
            assertEquals("GoodBeast should have moved there", before.plus(XY.UP), goodBeast.xy);
        }
        int master_energy = masterSquirrel.getEnergy();
        flattenedBoard.tryMove(goodBeast, XY.UP);
        assertEquals("MasterSquirrel should have gotten the Energy of GoodBeast", (master_energy + goodBeast.getEnergy()), masterSquirrel.getEnergy());

    }

    @Test
    public void testNearestPlayerEntity() {
        assertEquals("Has to find MasterSquirrel", board.getPlayer(), flattenedBoard.nearestPlayerEntity(new XY(2, 2)));
    }


    private Board getMockedBoard() {
        final BoardFactory boardFactory = context.mock(BoardFactory.class);
        context.checking(new Expectations() {{
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

    private Entity[] boardFactoryFake() {
        final Entity[] entities = new Entity[25];
        int id = 0;
        entities[id++] = masterSquirrel;
        entities[id++] = badBeast;
        entities[id++] = goodBeast;
        entities[id++] = badPlant;
        entities[id++] = goodPlant;
        for (int i = 0; i < 6; i++) {
            entities[id++] = new Wall(i, 0);
        }
        for (int i = 0; i < 6; i++) {
            entities[id++] = new Wall(i, 5);
        }
        for (int i = 1; i < 5; i++) {
            entities[id++] = new Wall(0, i);
        }
        for (int i = 1; i < 5; i++) {
            entities[id++] = new Wall(5, i);
        }
        return entities;
    }
}
