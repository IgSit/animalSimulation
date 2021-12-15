package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void ToString() {
        Vector2d example = new Vector2d(1, 2);
        String result = example.toString();
        assertEquals("(1, 2)", result);
    }

    @Test
    void precedes() {
        Vector2d v1 = new Vector2d(1, 1);
        Vector2d v2 = new Vector2d(2, 2);
        Vector2d v3 = new Vector2d(2, 3);

        boolean res1 = v1.precedes(v2);
        assertTrue(res1);

        boolean res2 = v2.precedes(v3);
        assertTrue(res2);

        boolean res3 = v3.precedes(v3);
        assertTrue(res3);
    }

    @Test
    void follows() {
        Vector2d v1 = new Vector2d(1, 1);
        Vector2d v2 = new Vector2d(2, 2);
        Vector2d v3 = new Vector2d(2, 3);

        boolean res1 = v3.follows(v2);
        assertTrue(res1);

        boolean res2 = v2.follows(v1);
        assertTrue(res2);

        boolean res3 = v1.follows(v1);
        assertTrue(res3);
    }

    @Test
    void upperRight() {
        Vector2d v1 = new Vector2d(2, 1);
        Vector2d v2 = new Vector2d(1, 2);

        Vector2d result = v1.upperRight(v2);
        Vector2d expected = new Vector2d(2, 2);
        assertEquals(expected, result);

    }

    @Test
    void lowerLeft() {
        Vector2d v1 = new Vector2d(2, 1);
        Vector2d v2 = new Vector2d(1, 2);

        Vector2d result = v1.lowerLeft(v2);
        Vector2d expected = new Vector2d(1, 1);
        assertEquals(expected, result);
    }

    @Test
    void add() {
        Vector2d v1 = new Vector2d(2, 1);
        Vector2d v2 = new Vector2d(3, 2);

        Vector2d result = v1.add(v2);
        Vector2d expected = new Vector2d(5, 3);
        assertEquals(expected, result);
    }

    @Test
    void subtract() {
        Vector2d v1 = new Vector2d(2, 1);
        Vector2d v2 = new Vector2d(3, 1);

        Vector2d result = v1.subtract(v2);
        Vector2d expected = new Vector2d(-1, 0);
        assertEquals(expected, result);
    }

    @Test
    void testEquals() {
        Vector2d v1 = new Vector2d(2, 1);
        Vector2d v2 = new Vector2d(2, 1);
        Vector2d v3 = new Vector2d(3, 6);
        Object o1 = new Object();

        boolean res1 = v1.equals(v2);
        assertTrue(res1);

        boolean res2 = v1.equals(v3);
        assertFalse(res2);

        boolean res3 = v2.equals(o1);
        assertFalse(res3);

    }

    @Test
    void opposite() {
        Vector2d v1 = new Vector2d(2, -1);
        Vector2d result = v1.opposite();
        Vector2d expected = new Vector2d(-2, 1);
        assertEquals(expected, result);
    }
}