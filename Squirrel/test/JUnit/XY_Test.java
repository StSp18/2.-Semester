package JUnit;

import de.hsa.games.fatsquirrel.util.XY;

import static org.junit.Assert.*;

import org.junit.Test;

public class XY_Test {
    private final XY XY1 = new XY(5, 8);
    private final XY XY2 = new XY(6, 1);
    private final XY XY3 = new XY(6, 8);
    private final XY XY4 = new XY(4, 3);

    @Test
    public void testPlus() {
        assertEquals("X: 5+6=11, Y: 8+1=9", new XY(11, 9), XY1.plus(XY2));
    }

    @Test
    public void testMinus() {
        assertEquals("X: 5-6=-1, Y: 8-1=7", new XY(-1, 7), XY1.minus(XY2));
    }

    @Test
    public void testTimes() {
        assertEquals("X: 5*3 = 15, Y: 8*3=24", new XY(15, 24), XY1.times(3));
    }

    @Test
    public void testDistanceFrom() {
        assertEquals("5.0-10.0 = -5.0", -5.0, XY3.distanceFrom(XY4), 0.0);
    }

    @Test
    public void testLength() {
        assertEquals("sqrt(6*6+8*8)=10.0", 10.0, XY3.length(), 0.0000001);
    }

    @Test
    public void testEquals() {
        assertTrue("XY elements with same Coordinates are equal", XY1.equals(new XY(5, 8)));
        assertFalse("XY elements with different Coordinates are not equal", XY1.equals(XY2));
        assertFalse("XY elements and other Objects are not equal", XY1.equals("false"));
    }

}
