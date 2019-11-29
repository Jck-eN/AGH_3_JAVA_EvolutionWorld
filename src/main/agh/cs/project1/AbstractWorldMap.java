package agh.cs.project1;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected Map<Vector2d, Animal> animals = new LinkedHashMap<>();

    public boolean place(Animal animal){
        if(!this.canMoveTo(animal.getPosition())){
            throw new IllegalArgumentException("na polu: " + animal.getPosition().toString() + " nie można umieścić zwierzęcia");
        }
        animal.addObserver(this);
        this.animals.put(animal.getPosition(), animal);
        return true;
    }

    abstract public void run();

    public boolean isOccupied(Vector2d position){
        return objectAt(position) != null;
    }


    public Object objectAt(Vector2d position){
        return this.animals.get(position);
    }

    abstract public Vector2d getTopRightCorner();

    abstract public Vector2d getBottomLeftCorner();

    public String toString() {
        return new MapVisualizer(this).draw(this.getBottomLeftCorner(), this.getTopRightCorner());
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        Animal a = this.animals.get(oldPosition);
        this.animals.remove(oldPosition);
        this.animals.put(newPosition, a);
    }
}
