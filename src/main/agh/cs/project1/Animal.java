package agh.cs.project1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Animal class describes animal moving on the map
 *
 * @author Jacek N.
 */
public class Animal implements IMapElement {
    private final IWorldMap map;
    private MapDirection direction;
    private Vector2d position;
    private final List<IPositionChangeObserver> observers = new ArrayList<>();
    private Genotype genotype;
    private Integer energy = Config.DEFAULT_ENERGY;

    /**
     * Creates new animal and places it on the map,
     * generates random direction and random genotype.
     *
     * @param map Map we want to add animal on.
     * @param initialPosition First position of an animal.
     */
    public Animal(IWorldMap map, Vector2d initialPosition){
        this.direction = this.generateRandomDirection();
        this.map = map;
        this.position = initialPosition;
        this.map.place(this);
        this.genotype = new Genotype();
    }

    /**
     * @param energy Initial energy of an animal.
     */
    private Animal(IWorldMap map, Vector2d initialPosition, int energy){
        this(map, initialPosition);
        this.energy = energy;
    }

    /**
     * @param genotype initial genotype of animal
     */
    private Animal(IWorldMap map, Vector2d initialPosition, Integer energy, Genotype genotype){
        this(map, initialPosition, energy);
        this.genotype = genotype;
    }

    /**
     *
     * @return direction of animal
     */
    public MapDirection getDirection() {
        return direction;
    }

    /**
     *
     * @return position of animal
     */
    public Vector2d getPosition() {
        return position;
    }

    /**
     *
     * @return string based on direction of animal
     */
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

    /**
     *
     * @return long string based on position and direction of animal
     */
    public String toStringLong() {
        return String.format("pozycja: %s | kierunek: %s", position.toString(), direction.toString());
    }


    /**
     * Moves animal - make animal lost energy, convert position to proper on the map
     * and init observers notification
     *
     * @param newPosition new position we move the animal
     */
    public void move(Vector2d newPosition){
        this.lostEnergy(Config.ENERGY_LOST_PER_MOVE);
        Vector2d tmpOldPosition = new Vector2d(this.position.x, this.position.y);
        this.position = newPosition;
        positionChanged(tmpOldPosition, newPosition);
    }


    /**
     * Add observer to the animal, that is notified about position changes
     *
     * @param observer observer to add
     */
    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    /**
     * Removes observer of animal
     *
     * @param observer observer we want to remove
     */
    public void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }

    /**
     * notifies all observers about position change
     *
     * @param oldPosition old position of an animal
     * @param newPosition new position of an animal
     */
    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver o : observers){
           o.positionChanged(this, oldPosition, newPosition);
        }
    }

    /**
     *
     * @return energy of an animal as int
     */
    public Integer getEnergy(){
        return this.energy;
    }
    /**
     * Increases energy of an animal
     *
     * @param energyToAdd number of energy to add to an animal
     */
    public void addEnergy(int energyToAdd){
        this.energy += energyToAdd;
    }

    /**
     * Decreases energy of an animal
     *
     * @param energyToRemove number of energy to remove from an animal
     */
    void lostEnergy(int energyToRemove){
        this.energy -= energyToRemove;
    }

    /**
     * Kills animal - run animal deletion procedure on map
     */
    public void die(){
        this.map.remove(this);
    }

    /**
     *  Generates random direction animal can mave on the map.
     *
     * @return random direction animal can mave on the map
     */
    private MapDirection generateRandomDirection(){
        Random r = new Random();
        Integer dir_no = r.nextInt(MapDirection.values().length);
        return MapDirection.values()[dir_no];
    }

    /**
     * Rotates the animal
     */
    public void rotate(){
        Integer curr_dir = this.getDirection().ordinal();
        Integer rotation = this.genotype.getRandomGene();
        this.direction = MapDirection.values()[(curr_dir+rotation)%MapDirection.values().length];
    }

    /**
     * Creates new animal-child
     *
     * @param other other animal-parent
     * @return new animal deriving genotype and energy from its parents
     */
    public Animal createNewAnimal(Animal other){
        Vector2d freePosition = this.map.findFreeSpaceNear(this.getPosition());
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
}
