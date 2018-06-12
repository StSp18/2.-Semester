package de.hsa.games.fatsquirrel.util;

import java.util.Objects;

import static java.lang.Math.sqrt;

/**
 * holds an immutable 2D coordinates
 */
public final class XY {
    public final int x, y;

    public XY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static final XY ZERO_ZERO = new XY(0, 0);
    public static final XY RIGHT = new XY(1, 0);
    public static final XY LEFT = new XY(-1, 0);
    public static final XY UP = new XY(0, -1);
    public static final XY DOWN = new XY(0, 1);
    public static final XY RIGHT_UP = new XY(1, -1);
    public static final XY RIGHT_DOWN = new XY(1, 1);
    public static final XY LEFT_UP = new XY(-1, -1);
    public static final XY LEFT_DOWN = new XY(-1, 1);

    /**
     * adds two xy objects
     *
     * @param xy
     * @return
     */
    public XY plus(XY xy) {
        return new XY(x + xy.x, y + xy.y);
    }

    /**
     * subtracts two xy objects
     *
     * @param xy
     * @return
     */
    public XY minus(XY xy) {
        return new XY(x - xy.x, y - xy.y);
    }

    /**
     * multiplies a xy with an factor
     *
     * @param factor
     * @return
     */
    public XY times(int factor) {
        return new XY(x*factor, y*factor);
    }

    /**
     * calculates the length of the vector
     *
     * @return
     */
    public double length() {
        return sqrt(x * x + y * y);
    }

    /**
     * calculates the length of the vector between two xy objects
     *
     * @param xy
     * @return
     */
    public double distanceFrom(XY xy) {
        return xy.length()-length();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XY xy = (XY) o;
        return x == xy.x &&
                y == xy.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public String toString() {
        return ("X: " + x + ", Y: " + y);
    }
}
