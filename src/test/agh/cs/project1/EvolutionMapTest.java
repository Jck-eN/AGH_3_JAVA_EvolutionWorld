package agh.cs.project1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class EvolutionMapTest {

    private final EvolutionMap map = new EvolutionMap();
    private Animal a1;
    private Animal a2;
    private Animal a3;
    private Animal a4;
    private Animal a5;
    private Animal a6;
    private Animal a7;
    private Animal a8;
    private Animal a9;

    @Before
    public void init(){
        a1 = new Animal(map, new Vector2d(10,10));
        a2 = new Animal(map, new Vector2d(11,11));
        a3 = new Animal(map, new Vector2d(10,11));
        a4 = new Animal(map, new Vector2d(11,10));
        a5 = new Animal(map, new Vector2d(50,20));
        a6 = new Animal(map, new Vector2d(9,10));
        a7 = new Animal(map, new Vector2d(9,9));
        a8 = new Animal(map, new Vector2d(10,9));
        a9 = new Animal(map, new Vector2d(11,9));
    }
    @Test
    public void objectAt() {
        Object o = map.objectAt(new Vector2d(10,10));
        Object o2 = map.objectAt(new Vector2d(11,11));
        assertEquals( o, (Object) a1);
        assertEquals( o2, (Object) a2);
        assertNull(map.objectAt(new Vector2d(2, 7)));
    }


    @Test
    public void getTopRightCorner() {
        Assert.assertEquals(map.getTopRightCorner(), new Vector2d(99, 29));
    }

    @Test
    public void getBottomLeftCorner() {
        Assert.assertEquals(map.getBottomLeftCorner(), new Vector2d(0, 0));
    }

    @Test
    public void removeAnimal() {
        assertTrue(map.remove(a1));
        assertTrue(map.remove(a2));
        assertTrue(map.remove(a3));
        assertFalse(map.remove(a3));
    }

    @Test
    public void findFreeSpaceNear() {
        Assert.assertEquals(map.findFreeSpaceNear(a1.getPosition()), new Vector2d(9,11));
    }
}
