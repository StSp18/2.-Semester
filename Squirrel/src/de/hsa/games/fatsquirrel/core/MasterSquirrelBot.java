package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.botapi.*;
import de.hsa.games.fatsquirrel.util.XY;

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
            context.tryMove(this, getMoveDirection().getXY());
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
            return bot.getXY().add(new XY(-15, 15));
        }

        @Override
        public XY getViewUpperRight() {
            return bot.getXY().add(new XY(15, -15));
        }

        @Override
        public EntityType getEntityAt(XY xy) throws OutOfViewException {
            if (xy.getX() <= getViewUpperRight().getX() && xy.getY() >= getViewUpperRight().getY() && xy.getX() >= getViewLowerLeft().getX() &&
                    xy.getY() <= getViewLowerLeft().getY()) {
                return context.getEntityType(xy);
            } else {
                throw new OutOfViewException();
            }
        }

        @Override
        public void move(XY direction) {
            if (direction.getX() <= 1 && direction.getX() >= -1 && direction.getY() <= 1 && direction.getY() >= -1) {
                bot.setMoveDirection(MoveDirection.searchMoveDirection(direction));
            } else {
                throw new NoTeleportException("You are no bunny so walk");
            }
        }

        @Override
        public void spawnMiniBot(XY direction, int energy) {
            try {
                if (context.getEntityType(bot.getXY().add(direction)) != EntityType.NONE) {
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
            return bot.getXY();
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
            if (xy.getX() <= getViewUpperRight().getX() && xy.getY() >= getViewUpperRight().getY() && xy.getX() >= getViewLowerLeft().getX() &&
                    xy.getY() <= getViewLowerLeft().getY()) {
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
