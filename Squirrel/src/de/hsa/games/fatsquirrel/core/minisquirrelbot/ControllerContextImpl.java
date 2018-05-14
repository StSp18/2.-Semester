package de.hsa.games.fatsquirrel.core.minisquirrelbot;

import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.console.NotEnoughEnergyException;
import de.hsa.games.fatsquirrel.core.*;

public class ControllerContextImpl implements ControllerContext {
    private MiniSquirrel bot;
    private Board board;

    public ControllerContextImpl(MiniSquirrel bot, Board board) {
        this.bot = bot;
        this.board = board;
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
            return board.createflattenedBoard().getEntityType(xy.getX(), xy.getX());
        } else {
            return EntityType.Air;
        }
    }

    @Override
    public void move(XY direction) {
        if (direction.getX() <= 1 && direction.getX() >= -1 && direction.getY() <= 1 && direction.getY() >= -1) {
            bot.setMoveDirection(MoveDirection.searchMoveDirection(direction));
        } else {
            bot.setMoveDirection(MoveDirection.stay);
        }
    }

    @Override
    public void spawnMiniBot(XY direction, int energy) {
        try {
            if (board.createflattenedBoard().getEntityType(bot.getXY().add(direction)) != EntityType.Air) {
                throw new AlreadyOccupiedException("This Coordinate is already occupied");
            }
            board.add(bot.createMiniSquirrel(board.getIdCount(), energy, direction));
        } catch (NotEnoughEnergyException e) {

        } catch (AlreadyOccupiedException e) {

        }
    }

    @Override
    public int getEnergy() {
        return bot.getEnergy();
    }
}
