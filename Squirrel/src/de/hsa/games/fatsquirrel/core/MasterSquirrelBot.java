package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.botapi.*;
import de.hsa.games.fatsquirrel.util.XY;
import de.hsa.games.fatsquirrel.util.XYsupport;

public class MasterSquirrelBot extends MasterSquirrel{
    private BotController controller;

    protected MasterSquirrelBot(int x, int y, BotControllerFactory factory) {
        super(x, y);
        controller = factory.createMasterBotController();
    }

    @Override
    public void nextStep(EntityContext context) {
        if (!Stunned()) {
            controller.nextStep(new ControllerContextImpl(this, context));
            context.tryMove(this, moveDirection);
        }
    }

    private class ControllerContextImpl implements ControllerContext {

        private MasterSquirrel bot;
        private EntityContext context;

        public ControllerContextImpl(MasterSquirrel bot, EntityContext context) {
            this.bot = bot;
            this.context = context;
        }

        @Override
        public XY getViewLowerLeft() {
            return bot.xy.plus(new XY(-15, 15));
        }

        @Override
        public XY getViewUpperRight() {
            return bot.xy.plus(new XY(15, -15));
        }

        @Override
        public EntityType getEntityAt(XY xy) throws OutOfViewException {
            if (xy.x <= getViewUpperRight().x && xy.y >= getViewUpperRight().y && xy.x >= getViewLowerLeft().x &&
                    xy.y <= getViewLowerLeft().y) {
                return context.getEntityType(xy);
            } else {
                throw new OutOfViewException();
            }
        }

        @Override
        public void move(XY direction) {
            if (XYsupport.isDirection(direction)) {
                bot.setMoveDirection(direction);
            } else {
                throw new NoTeleportException("You are no bunny so walk");
            }
        }

        @Override
        public void spawnMiniBot(XY direction, int energy) {
            try {
                if (context.getEntityType(bot.xy.plus(direction)) != EntityType.NONE) {
                    throw new SpawnException("This Coordinate is already occupied");
                }
                context.createMiniSquirrel(bot, direction, energy);
            } catch (SpawnException e) {

            }
        }

        @Override
        public int getEnergy() {
            return bot.getEnergy();
        }

        @Override
        public XY locate() {
            return bot.xy;
        }

        @Override
        public XY directionOfMaster() {
            return null;
        }

        @Override
        public long getRemainingSteps() {
            return context.getRemainingSteps();
        }

        @Override
        public boolean isMine(XY xy) throws OutOfViewException {
            if (xy.x <= getViewUpperRight().x && xy.y >= getViewUpperRight().y && xy.x >= getViewLowerLeft().x &&
                    xy.y <= getViewLowerLeft().y) {
                return context.isMine(xy, bot);
            } else {
                throw new OutOfViewException();
            }
        }

        @Override
        public void implode(int impactRadius) throws Exception{
            throw new Exception();
        }
    }

}
