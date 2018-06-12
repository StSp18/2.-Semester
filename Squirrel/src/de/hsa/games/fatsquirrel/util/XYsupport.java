package de.hsa.games.fatsquirrel.util;

import java.util.concurrent.ThreadLocalRandom;

public class XYsupport {
    private static XY[] directions = new XY[] {XY.DOWN, XY.UP, XY.LEFT, XY.RIGHT, XY.LEFT_DOWN, XY.LEFT_UP, XY.RIGHT_DOWN, XY.RIGHT_UP, XY.ZERO_ZERO};
    private static String[] names = new String[] {"DOWN", "UP", "LEFT", "RIGHT", "LEFT_DOWN", "LEFT_UP", "RIGHT_DOWN", "RIGHT_UP", "ZERO_ZERO"};

    /**
     * checks if a given xy is a moveDirection
     *
     * @param xy
     * @return
     */
    public static boolean isDirection(XY xy) {
        for(XY direction: directions) {
            if(xy.equals(direction)) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns the moveDirection that distance you from a given coordinate
     *
     * @param yours
     * @param others
     * @return
     */
    public static XY moveAway(XY yours, XY others) {
        return moveTowards(others, yours);
    }

    /**
     * returns the moveDirection that gets you closer to a given coordinate
     *
     * @param yours
     * @param others
     * @return
     */
    public static XY moveTowards(XY yours, XY others) {
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

    /**
     * returns the moveDirection corresponding to the given name
     *
     * @param s
     * @return
     */
    public static XY valueOf(String s) {
        for (int i = 0; i < names.length; i++) {
            if(s.equals(names[i])) {
                return directions[i];
            }
        }
        return XY.ZERO_ZERO;
    }

    /**
     * generates a random moveDirection
     *
     * @return
     */
    public static XY rndMoveDirection() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, 8);
        return directions[randomNum];
    }


}
