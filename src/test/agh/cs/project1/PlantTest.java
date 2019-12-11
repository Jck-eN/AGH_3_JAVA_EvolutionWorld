package agh.cs.project1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlantTest {

    private Plant p1;
    private Plant p2;
    private Plant p3;

    @Before
    public void init(){
        p1=new Plant(new Vector2d(1,3));
        p2=new Plant(new Vector2d(1,7));
        p3=new Plant(new Vector2d(2,2));
    }

    @Test
    public void grow() {
        p1.grow(0);
        p2.grow(2);
        p3.grow(Config.PLANT_MAX_SIZE);
        Assert.assertEquals((int) p2.getSize(), Config.PLANT_DEFAULT_SIZE + 2);
        Assert.assertEquals(p3.getSize(), Config.PLANT_MAX_SIZE);
        Assert.assertEquals(p1.getSize(), Config.PLANT_DEFAULT_SIZE);
    }

    @Test
    public void getPosition() {
        Assert.assertEquals(p1.getPosition(), new Vector2d(1,3));
        Assert.assertEquals(p2.getPosition(), new Vector2d(1,7));
        Assert.assertEquals(p3.getPosition(), new Vector2d(2,2));
    }

    @Test
    public void getSize() {
        Assert.assertEquals(p1.getSize(), Config.PLANT_DEFAULT_SIZE);
        Assert.assertNotEquals((int) p1.getSize(), -10);
        Assert.assertNotEquals(0, (int) p2.getSize());
    }

    @Test
    public void testToString() {
        Assert.assertEquals(p1.toString(), "*");
    }
}
