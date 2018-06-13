package JUnit;

import de.hsa.games.fatsquirrel.botapi.SpawnException;
import de.hsa.games.fatsquirrel.core.*;
import de.hsa.games.fatsquirrel.util.XY;

import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FlattenedBoard_Test {
    DummyBoardFactory dummyBoardFactory = new DummyBoardFactory(false);
    // Test Entities
    private final HandOperatedMasterSquirrel masterSquirrel = dummyBoardFactory.masterSquirrel;
    private final MiniSquirrel miniSquirrel = dummyBoardFactory.miniSquirrel;
    private final BadBeast badBeast = dummyBoardFactory.badBeast;
    private final GoodBeast goodBeast = dummyBoardFactory.goodBeast;
    private final BadPlant badPlant = dummyBoardFactory.badPlant;
    private final GoodPlant goodPlant = dummyBoardFactory.goodPlant;
    private final DummyBoard board = new DummyBoard(new DummyBoardFactory(false));
    private FlattenedBoard flattenedBoard;

    public FlattenedBoard_Test() throws SpawnException {
    }

    @Test
    public void testImplodeMiniSquirrel() {

    }

    @Test
    public void testTryMoveMasterSquirrel() {
        flattenedBoard = new FlattenedBoard(constructFlatBoardSquirrel(), board);
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
        flattenedBoard = new FlattenedBoard(constructFlatBoardSquirrel(), board);
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
        flattenedBoard = new FlattenedBoard(constructFlatBoardBeast(), board);
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
        flattenedBoard = new FlattenedBoard(constructFlatBoardBeast(), board);
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
        flattenedBoard = new FlattenedBoard(constructFlatBoardBeast(), board);
        assertEquals("Has to find MasterSquirrel", masterSquirrel, flattenedBoard.nearestPlayerEntity(new XY(2, 2)));
    }

    private Entity[][] constructFlatBoardSquirrel() {
        Entity[][] flat = constructFlatBoardBeast();
        flat[miniSquirrel.xy.x][miniSquirrel.xy.y] = miniSquirrel;
        return flat;
    }

    private Entity[][] constructFlatBoardBeast() {
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
        Entity[][] flat = new Entity[6][6];
        for (Entity e : entities) {
            flat[e.xy.x][e.xy.y] = e;
        }
        return flat;
    }
}
