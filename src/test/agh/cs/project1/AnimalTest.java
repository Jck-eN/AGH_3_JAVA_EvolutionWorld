package agh.cs.project1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnimalTest {



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
    public void getPosition() {
        Assert.assertEquals(a1.getPosition(), new Vector2d(10, 10));
        Assert.assertEquals(a5.getPosition(), new Vector2d(50, 20));
        Assert.assertEquals(a9.getPosition(), new Vector2d(11, 9));
    }


    @Test
    public void move() {
        a1.move(new Vector2d(11,20));
        Assert.assertEquals(a1.getPosition(), new Vector2d(11,20));
        a2.move(new Vector2d(11,21));
        Assert.assertEquals(a2.getPosition(), new Vector2d(11,21));
    }

    @Test
    public void getEnergy() {
        Assert.assertEquals(a1.getEnergy(), Config.DEFAULT_ENERGY);
        Assert.assertNotEquals((int) a2.getEnergy(), -10);
        a1.lostEnergy(10);
        Assert.assertEquals((int) a1.getEnergy(), Config.DEFAULT_ENERGY - 10);
    }

    @Test
    public void addEnergy() {
        a1.addEnergy(10);
        a2.addEnergy(3);
        Assert.assertEquals((int) a1.getEnergy(), Config.DEFAULT_ENERGY + 10);
        Assert.assertEquals((int) a2.getEnergy(), Config.DEFAULT_ENERGY + 3);
        Assert.assertNotEquals((int) a3.getEnergy(), Config.DEFAULT_ENERGY - 1);

    }

    @Test
    public void lostEnergy() {
        a1.lostEnergy(10);
        Assert.assertEquals((int) a1.getEnergy(), Config.DEFAULT_ENERGY - 10);
        a1.lostEnergy(Config.DEFAULT_ENERGY);
        Assert.assertEquals((int) a1.getEnergy(), -10);
    }


    @Test
    public void createNewAnimal() {
        Animal a = a1.createNewAnimal(a2);
        Assert.assertEquals(a.getEnergy(), (Integer)(Config.DEFAULT_ENERGY/2));
        Assert.assertEquals(a1.getEnergy(), (Integer)(Config.DEFAULT_ENERGY*3/4));
        Assert.assertEquals(a2.getEnergy(), (Integer)(Config.DEFAULT_ENERGY*3/4));
        Assert.assertEquals(a.getPosition(), new Vector2d(9,11));
    }
}