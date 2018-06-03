package de.hsa.games.fatsquirrel.botimpls;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.botapi.OutOfViewException;
import de.hsa.games.fatsquirrel.core.EntityType;
import de.hsa.games.fatsquirrel.util.XY;

public class Group3WallE implements BotController {
    private static XY[] directions = new XY[]{XY.UP, XY.RIGHT, XY.DOWN, XY.LEFT};
    int moveDirection = 0;

    @Override
    public void nextStep(ControllerContext view) {
        try {
            if (view.getEntityAt(view.locate().plus(directions[moveDirection])) == EntityType.WALL) {
                if (moveDirection < 3) {
                    moveDirection++;
                } else {
                    moveDirection = 0;
                }
            }
            view.move(directions[moveDirection]);
        } catch (OutOfViewException e) {
            e.printStackTrace();
        }
    }
}
