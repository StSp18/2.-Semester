package JUnit;

import de.hsa.games.fatsquirrel.core.*;
import de.hsa.games.fatsquirrel.util.XY;

import de.hsa.games.fatsquirrel.util.XYsupport;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import static org.junit.Assert.*;

public class Character_nextStep_Test {
    private final Mockery context = new JUnit4Mockery();
    private final EntityContext entityContext = context.mock(EntityContext.class);
    private final MasterSquirrel masterSquirrel = new HandOperatedMasterSquirrel(0, 6);
    final Squirrel squirrel = new HandOperatedMasterSquirrel(1, 2);

    @Test
    public void testBadBeastEncountersSquirrel() {
        final BadBeast badBeast = new BadBeast(1, 4);
        final XY moveDirection = XYsupport.moveTowards(badBeast.xy, squirrel.xy);

        context.checking(new Expectations() {{
            oneOf(entityContext).nearestPlayerEntity(badBeast.xy);
            will(returnValue(squirrel));
            oneOf(entityContext).tryMove(badBeast, moveDirection);
        }});

        badBeast.nextStep(entityContext);

        assertTrue("badBeast won't move this turn --> it is aSleep: ", badBeast.aSleep());

    }

    @Test
    public void testBeastASleep() {
        final GoodBeast goodBeast = new GoodBeast(4, 8);
        for (int i = 0; i < 18; i++) {
            if (i % 4 == 0) {
                assertFalse("Beast moves --> aSleep() == false", goodBeast.aSleep());
            } else {
                assertTrue("Beast sleeps --> aSleep() == true", goodBeast.aSleep());
            }
        }

    }

    @Test
    public void testSquirrelStunned() {
        masterSquirrel.wallBump();
        for (int i = 0; i < 6; i++) {
            if (i < 3) {
                assertTrue("Squirrel is stunned: ", masterSquirrel.Stunned());
            } else {
                assertFalse("Squirrel is not stunned: ", masterSquirrel.Stunned());
            }
        }
    }


    @Test
    public void testBadBeastMovesSomewhere() {
        final BadBeast badBeast = new BadBeast(1, 4);

        context.checking(new Expectations() {{
            oneOf(entityContext).nearestPlayerEntity(badBeast.xy);
            will(returnValue(null));
            oneOf(entityContext).tryMove(badBeast, badBeast.getMoveDirection());
        }});

        badBeast.nextStep(entityContext);
    }

    @Test
    public void testGoodBeastEncountersSquirrel() {
        final GoodBeast goodBeast = new GoodBeast(1, 4);
        final XY moveDirection = XYsupport.moveTowards(goodBeast.xy, squirrel.xy);

        context.checking(new Expectations() {{
            oneOf(entityContext).nearestPlayerEntity(goodBeast.xy);
            will(returnValue(squirrel));
            oneOf(entityContext).tryMove(goodBeast, moveDirection);
        }});

        goodBeast.nextStep(entityContext);
    }

    @Test
    public void testGoodBeastMovesSomewhere() {
        final GoodBeast goodBeast = new GoodBeast(1, 4);

        context.checking(new Expectations() {{
            oneOf(entityContext).nearestPlayerEntity(goodBeast.xy);
            will(returnValue(null));
            oneOf(entityContext).tryMove(goodBeast, goodBeast.getMoveDirection());
        }});

        goodBeast.nextStep(entityContext);
    }

    @Test
    public void testMasterSquirrel() {
        masterSquirrel.setMoveDirection(XY.DOWN);

        context.checking(new Expectations() {{
            oneOf(entityContext).tryMove(masterSquirrel, XY.DOWN);
        }});
    }

}
