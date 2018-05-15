package de.hsa.games.fatsquirrel.core;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.console.NotEnoughEnergyException;

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
            return bot.getXY().add(new XY(-1, 1));
        }

        @Override
        public XY getViewUpperRight() {
            return bot.getXY().add(new XY(1, -1));
        }

        @Override
        public EntityType getEntityAt(XY xy) {
            if (xy.getX() <= getViewUpperRight().getX() && xy.getY() >= getViewUpperRight().getY() && xy.getX() >= getViewLowerLeft().getX() &&
                    xy.getY() <= getViewLowerLeft().getY()) {
                return context.getEntityType(xy);
            } else {
                return EntityType.Air;
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
                if (context.getEntityType(bot.getXY().add(direction)) != EntityType.Air) {
                    throw new AlreadyOccupiedException("This Coordinate is already occupied");
                }
                context.createMiniSquirrel(bot, direction, energy);
            } catch (NotEnoughEnergyException e) {

            } catch (AlreadyOccupiedException e) {

            }
        }

        @Override
        public int getEnergy() {
            return bot.getEnergy();
        }
    }

}
