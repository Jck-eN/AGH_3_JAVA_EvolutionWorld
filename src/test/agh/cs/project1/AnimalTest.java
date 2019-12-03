package agh.cs.project1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnimalTest {



    EvolutionMap map = new EvolutionMap();
    Animal a1,a2,a3,a4,a5,a6, a7, a8, a9;

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
        Assert.assertTrue(a1.getEnergy().equals(Config.DEFAULT_ENERGY));
        Assert.assertFalse(a2.getEnergy().equals(-10));
        a1.lostEnergy(10);
        Assert.assertTrue(a1.getEnergy().equals(Config.DEFAULT_ENERGY-10));
    }

    @Test
    public void addEnergy() {
        a1.addEnergy(10);
        a2.addEnergy(3);
        Assert.assertTrue(a1.getEnergy().equals(Config.DEFAULT_ENERGY+10));
        Assert.assertTrue(a2.getEnergy().equals(Config.DEFAULT_ENERGY+3));
        Assert.assertFalse(a3.getEnergy().equals(Config.DEFAULT_ENERGY-1));

    }

    @Test
    public void lostEnergy() {
        a1.lostEnergy(10);
        Assert.assertTrue(a1.getEnergy().equals(Config.DEFAULT_ENERGY-10));
        a1.lostEnergy(Config.DEFAULT_ENERGY);
        Assert.assertTrue(a1.getEnergy().equals(-10));
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