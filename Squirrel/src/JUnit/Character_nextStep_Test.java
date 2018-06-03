package JUnit;

import de.hsa.games.fatsquirrel.core.*;
import de.hsa.games.fatsquirrel.util.XY;

import de.hsa.games.fatsquirrel.util.XYsupport;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

public class Character_nextStep_Test {
    private final GoodBeast goodBeast = new GoodBeast(2, 8);
    private final MasterSquirrel masterSquirrel = new HandOperatedMasterSquirrel(0, 6);
    private final MiniSquirrel miniSquirrel = new MiniSquirrel(masterSquirrel, 100, 4, 1);

    @Test
    public void testBadBeastEncountersSquirrel() {
        Mockery context = new JUnit4Mockery();
        final BadBeast badBeast = new BadBeast(1, 4);
        final EntityContext entityContext = context.mock(EntityContext.class);
        final Squirrel squirrel = new HandOperatedMasterSquirrel(1, 2);
        final XY moveDirection = XYsupport.moveTowards(badBeast.xy, squirrel.xy);
        context.checking(new Expectations() {{
            oneOf(entityContext).nearestPlayerEntity(badBeast.xy);
            will(returnValue(squirrel));
            oneOf(entityContext).tryMove(badBeast, moveDirection);
        }});

        badBeast.nextStep(entityContext);
    }

    @Test
    public void testBadBeastMovesSomewhere() {
        Mockery context = new JUnit4Mockery();
        final BadBeast badBeast = new BadBeast(1, 4);
        final EntityContext entityContext = context.mock(EntityContext.class);
        final Squirrel squirrel = new HandOperatedMasterSquirrel(1, 2);

        context.checking(new Expectations() {{
            oneOf(entityContext).nearestPlayerEntity(badBeast.xy);
            will(returnValue(null));
            oneOf(entityContext).tryMove(badBeast, badBeast.getMoveDirection());
        }});

        badBeast.nextStep(entityContext);
    }
}
