package de.hsa.games.fatsquirrel.util;

import java.util.Objects;

import static java.lang.Math.sqrt;

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

    public XY minus(XY xy) {
        return new XY(x-xy.x, y-xy.y);
    }

    public XY times(int factor) {
        return new XY(x*factor, y*factor);
    }

    public double length() {
        return sqrt(x*x + y*y);
    }

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

    public XY plus(XY xy) {
        return new XY(x+xy.x,y+xy.y);
    }


    public String toString() {
        return ("X: " + x + ", Y: " + y);
    }
}
