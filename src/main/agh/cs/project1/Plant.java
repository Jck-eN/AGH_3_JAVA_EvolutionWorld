package agh.cs.project1;

public class Plant implements IMapElement {
    private Vector2d position;
    private final static Integer maxSize = 10;

    private Integer size = 5;


    public Plant(Vector2d position){
        this.position = position;
    }
    public Plant(Vector2d position, Integer size){
        this(position);
        this.size = size;
    }

    public void grow(Integer growSize){
        this.size+=growSize;
        if(this.size>Plant.maxSize)this.size=maxSize;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public Integer getSize(){
        return this.size;
    }

    public String toString() {
        return "*";
    }
}
