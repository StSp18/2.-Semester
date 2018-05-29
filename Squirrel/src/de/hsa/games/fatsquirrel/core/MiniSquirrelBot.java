package de.hsa.games.fatsquirrel.core;


import de.hsa.games.fatsquirrel.botapi.*;
import de.hsa.games.fatsquirrel.util.XY;
import de.hsa.games.fatsquirrel.util.XYsupport;

public class MiniSquirrelBot extends MiniSquirrel {
    public static int eyesight = 10;
    private BotController controller;

    public MiniSquirrelBot(MasterSquirrel master, int energy, int x, int y, BotControllerFactory factory) {
        super(master, energy, x, y);
        controller = factory.createMasterBotController();
    }

    @Override
    public void nextStep(EntityContext context) {
        if (!Stunned()) {
            controller.nextStep(new ControllerContextProxy(new MiniSquirrelBot.ControllerContextImpl(this, context)).Proxy());
            context.tryMove(this, moveDirection);
        }
    }

    private class ControllerContextImpl implements ControllerContext {

        private MiniSquirrel bot;
        private EntityContext context;

        public ControllerContextImpl(MiniSquirrel bot, EntityContext context) {
            this.bot = bot;
            this.context = context;
        }

        public void implode(int radius) {
            if (radius < 2 || radius > 10) {
                throw new IllegalArgumentException();
            }
            context.implodeMiniSquirrel(bot, radius);
        }

        @Override
        public XY getViewLowerLeft() {
            return cut(bot.xy.plus(new XY(-eyesight, eyesight)));
        }

        @Override
        public XY getViewUpperRight() {
            return cut(bot.xy.plus(new XY(eyesight, -eyesight)));
        }

        @Override
        public EntityType getEntityAt(XY xy) throws OutOfViewException {
            if (inBounds(xy)) {
                return context.getEntityType(xy);
            } else {
                throw new OutOfViewException();
            }
        }

        @Override
        public void move(XY direction) {
            if (isDirection(direction)) {
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
            return moveTowards(bot.xy, bot.getMaster().xy);
        }

        @Override
        public long getRemainingSteps() {
            return context.getRemainingSteps();
        }

        @Override
        public boolean isMine(XY xy) throws OutOfViewException {
            if (inBounds(xy)) {
                return context.isMine(xy, bot);
            } else {
                throw new OutOfViewException();
            }
        }

        @Override
        public XY locate() {
            return bot.xy;
        }

        private boolean isDirection(XY xy) {
            XY[] directions = new XY[]{XY.DOWN, XY.UP, XY.LEFT, XY.RIGHT, XY.LEFT_DOWN, XY.LEFT_UP, XY.RIGHT_DOWN, XY.RIGHT_UP, XY.ZERO_ZERO};
            for (XY direction : directions) {
                if (xy.equals(direction)) {
                    return true;
                }
            }
            return false;
        }

        private XY cut(XY xy) {
            XY cut;
            if (xy.x > context.getSize().x) {
                cut = new XY(context.getSize().x, 0);
            } else {
                cut = new XY(xy.x, 0);
            }
            if (xy.y > context.getSize().y) {
                cut = new XY(cut.x, context.getSize().y);
            } else {
                cut = new XY(cut.x, xy.y);
            }
            return cut;
        }

        private boolean inBounds(XY xy) {
            return xy.x <= getViewUpperRight().x && xy.y >= getViewUpperRight().y && xy.x >= getViewLowerLeft().x && xy.y <= getViewLowerLeft().y;
        }

        private XY moveTowards(XY yours, XY others) {
            int x = 0;
            int y = 0;
            if (others.x < yours.x) {
                x = -1;
            }
            if (others.x > yours.x) {
                x = 1;
            }
            if (others.y < yours.y) {
                y = -1;
            }
            if (others.y > yours.y) {
                y = 1;
            }
            return new XY(x, y);

        }
    }



}
