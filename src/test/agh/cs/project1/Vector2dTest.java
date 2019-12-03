package agh.cs.project1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Vector2dTest {
    private Vector2d v1;
    private Vector2d v2;
    private Vector2d v3;
    private Vector2d v4;

    @Before
    public void createVector(){
        v1 = new Vector2d(3, 4);
        v2 = new Vector2d(0, -1);
        v3 = new Vector2d(0, -1);
        v4 = new Vector2d(6, 2);
    }

    @Test
    public void equalsTest(){
        MapDirection dir = MapDirection.EAST;
        assertEquals(v2, v3);
        assertNotEquals(v1, v2);
        assertNotEquals(v1, dir);
    }

    @Test
    public void toStringTest(){
        assertEquals("(3,4)", v1.toString() );
        assertEquals("(0,-1)", v2.toString() );
    }

    @Test
    public void precedesTest(){
        assertTrue(v2.precedes(v1));
        assertTrue(v2.precedes(v3));
        assertFalse(v1.precedes(v3));
        assertFalse(v1.precedes(v4));
    }
    @Test
    public void followsTest(){
        assertTrue(v1.follows(v2));
        assertTrue(v3.follows(v2));
        assertFalse(v3.follows(v1));
        assertFalse(v4.follows(v1));
    }

    @Test
    public void upperRightTest(){
        assertEquals(v1.upperRight(v2), new Vector2d(3,4));
        assertEquals(v2.upperRight(v3), new Vector2d(0,-1));
        assertEquals(v4.upperRight(v1), new Vector2d(6,4));
    }
    @Test
    public void lowerLeftTest(){
        assertEquals(v1.lowerLeft(v2), new Vector2d(0,-1));
        assertEquals(v2.lowerLeft(v3), new Vector2d(0,-1));
        assertEquals(v4.lowerLeft(v1), new Vector2d(3,2));
    }
    @Test
    public void addTest(){
        assertEquals(v1.add(v2), new Vector2d(3,3));
        assertEquals(v2.add(v3), new Vector2d(0,-2));
        assertEquals(v4.add(v1), new Vector2d(9,6));
    }
    @Test
    public void subtractTest(){
        assertEquals(v1.subtract(v2), new Vector2d(3,5));
        assertEquals(v2.subtract(v3), new Vector2d(0,0));
        assertEquals(v4.subtract(v1), new Vector2d(3,-2));
    }
    @Test
    public void oppositeTest(){
        assertEquals(v1.opposite(), new Vector2d(-3,-4));
        assertEquals(v2.opposite(), new Vector2d(0,1));
        assertEquals(v4.opposite(), new Vector2d(-6,-2));
    }
}