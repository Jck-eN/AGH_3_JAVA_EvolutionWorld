package agh.cs.project1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal implements IMapElement {
    public static Integer animalCount = 0;
    private IWorldMap map;
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2,2);
    private List<IPositionChangeObserver> observers = new ArrayList<>();
    private Genotype genotype;
    private Integer energy = Config.DEFAULT_ENERGY;
    private Integer id;


    public Animal(IWorldMap map, Vector2d initialPosition){
        this.id = Animal.animalCount++;
        this.direction = this.generateRandomDirection();
        this.map = map;
        this.position = initialPosition;
        this.map.place(this);
        this.genotype = new Genotype();
    }

    public Animal(IWorldMap map, Vector2d initialPosition, int energy){
        this(map, initialPosition);
        this.energy = energy;
    }


    public Animal(IWorldMap map, Vector2d initialPosition, Integer energy, Genotype genotype){
        this(map, initialPosition, energy);
        this.genotype = genotype;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public Vector2d getPosition() {
        return position;
    }

    public String toString() {
        switch (this.direction){
            case NORTH: return "N";
            case EAST: return "E";
            case SOUTH: return "S";
            case WEST: return "W";
            case NORTH_EAST: return "Q";
            case NORTH_WEST: return "R";
            case SOUTH_EAST: return "T";
            case SOUTH_WEST: return "F";
            default: throw new IllegalArgumentException("Niepoprawny kierunek zwierzęcia");
        }
    }
    public String toStringLong() {
        return String.format("pozycja: %s | kierunek: %s", position.toString(), direction.toString());
    }



    public void move(Vector2d newPosition){
        this.lostEnergy(Config.ENERGY_LOST_PER_MOVE);
        Vector2d tmpOldPosition = new Vector2d(this.position.x, this.position.y);
        this.position = newPosition;
        positionChanged(tmpOldPosition, newPosition);
    }



    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver o : observers){
           o.positionChanged(oldPosition, newPosition);
        }
    }
    public Integer getEnergy(){
        return this.energy;
    }

    public void addEnergy(int energyToAdd){
        this.energy += energyToAdd;
    }

    public void lostEnergy(int energyToRemove){
        this.energy -= energyToRemove;
    }

    public void die(){
        this.map.remove(this);
    }

    private MapDirection generateRandomDirection(){
        Random r = new Random();
        Integer dir_no = r.nextInt((int) MapDirection.values().length);
        return MapDirection.values()[dir_no];
    }

    public void rotate(){
        Integer curr_dir = this.getDirection().ordinal();
        Integer rotation = this.genotype.getRandomGene();
        this.direction = MapDirection.values()[(curr_dir+rotation)%MapDirection.values().length];
    }

    public Animal createNewAnimal(Animal other){
        Vector2d freePosition = this.findFreeSpot();
        if(this.getEnergy() < Config.REQUIRED_ENERGY || other.getEnergy() < Config.REQUIRED_ENERGY || freePosition == null){
            return null;
        }
        Integer newEnergy = this.getEnergy()/4;
        this.lostEnergy(newEnergy);
        Integer otherEnergy = other.getEnergy()/4;
        newEnergy += otherEnergy;
        other.lostEnergy(otherEnergy);

        return new Animal(this.map,
                freePosition,
                newEnergy,
                this.genotype.merge(other.genotype));

    }

    private Vector2d findFreeSpot() {
        Vector2d origin = this.getPosition();
        for(int x = -1; x < 2; x++){
            for(int y = -1; y < 2; y++){
                if(x == 0 && y == 0){
                    continue;
                }
                Vector2d newPos = new Vector2d(x, y).add(origin);
                if(this.map.objectAt(newPos)== null){
                    return newPos;
                }
            }
        }
        return null;
    }
}
