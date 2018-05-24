package de.hsa.games.fatsquirrel.core;


import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.botapi.OutOfViewException;
import de.hsa.games.fatsquirrel.botapi.SpawnException;
import de.hsa.games.fatsquirrel.util.XY;
import de.hsa.games.fatsquirrel.util.XYsupport;

public class MiniSquirrelBot extends MiniSquirrel {

    public MiniSquirrelBot(MasterSquirrel master, int energy, int x, int y) {
        super(master, energy, x, y);
    }

    @Override
    public void nextStep(EntityContext context) {
        if (!Stunned()) {
            setMoveDirection(XYsupport.rndMoveDirection());
            context.tryMove(this, moveDirection);
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

        public void implode(int radius) throws Exception{
            radius = bot.getEnergy()/100;
            if(radius<2) {
                radius = 2;
            }
            if(radius>10) {
                radius = 10;
            }
            context.implodeMiniSquirrel(bot, radius);
        }

        @Override
        public XY getViewLowerLeft() {
            return bot.xy.plus(new XY(-10, 10));
        }

        @Override
        public XY getViewUpperRight() {
            return bot.xy.plus(new XY(10, -10));
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
                context.createMiniSquirrel(bot.getMaster(), direction, energy);
            } catch (SpawnException e) {

            }
        }

        @Override
        public int getEnergy() {
            return bot.getEnergy();
        }

        public XY directionOfMaster() {
            return XYsupport.moveTowards(bot.xy, bot.getMaster().xy);
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
        public XY locate() {
            return bot.xy;
        }
    }



}
