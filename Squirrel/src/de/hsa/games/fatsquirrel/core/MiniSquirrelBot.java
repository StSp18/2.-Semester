package de.hsa.games.fatsquirrel.core;


import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.console.NotEnoughEnergyException;

public class MiniSquirrelBot extends MiniSquirrel {

    public MiniSquirrelBot(MasterSquirrel master, int energy, int x, int y) {
        super(master, energy, x, y);
    }

    @Override
    public void nextStep(EntityContext context) {
        if (!Stunned()) {
            setMoveDirection(MoveDirection.rndMoveDirection());
            context.tryMove(this, getMoveDirection().getXY());
        }
        if(getEnergy() < 0) {
            updateEnergy(-getEnergy());
        }
    }

    private class ControllerContextImpl implements ControllerContext {
        private MiniSquirrel bot;
        private EntityContext context;

        public ControllerContextImpl(MiniSquirrel bot, EntityContext context) {
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
                context.createMiniSquirrel(bot.getMaster(), direction, energy);
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
