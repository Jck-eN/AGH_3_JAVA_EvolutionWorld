package agh.cs.project1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RectangleTest {

    Rectangle a, b, c, d;
    @Before
    public void init(){
        a = new Rectangle(new Vector2d(0,0), new Vector2d(4,9));
        b = new Rectangle(new Vector2d(5,5), new Vector2d(5,9));
        c = new Rectangle(new Vector2d(0,0), new Vector2d(0,9));
        d = new Rectangle(new Vector2d(10,10), new Vector2d(10,10));
    }
    @Test
    public void getTopRightCorner() {
        Assert.assertEquals(a.getTopRightCorner(), new Vector2d(4,9));
        Assert.assertEquals(b.getTopRightCorner(), new Vector2d(5,9));
        Assert.assertEquals(c.getTopRightCorner(), new Vector2d(0,9));
        Assert.assertEquals(d.getTopRightCorner(), new Vector2d(10,10));
    }

    @Test
    public void getBottomLeftCorner() {
        Assert.assertEquals(a.getBottomLeftCorner(), new Vector2d(0,0));
        Assert.assertEquals(b.getBottomLeftCorner(), new Vector2d(5,5));
        Assert.assertEquals(c.getBottomLeftCorner(), new Vector2d(0,0));
        Assert.assertEquals(d.getBottomLeftCorner(), new Vector2d(10,10));
    }

    @Test
    public void hasPositionInside() {
        assertTrue(a.hasPositionInside(new Vector2d(2,3)));
        assertTrue(a.hasPositionInside(new Vector2d(0,9)));
        assertFalse(a.hasPositionInside(new Vector2d(10,4)));
        assertFalse(a.hasPositionInside(new Vector2d(-1,-1)));
    }

    @Test
    public void area() {
        Assert.assertEquals(a.area(), 50);
        Assert.assertEquals(b.area(), 5);
        Assert.assertEquals(c.area(), 10);
        Assert.assertEquals(d.area(), 1);
    }
}