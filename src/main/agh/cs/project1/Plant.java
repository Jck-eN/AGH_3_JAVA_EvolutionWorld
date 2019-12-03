package agh.cs.project1;

public class Plant implements IMapElement {
    private final Vector2d position;
    private final static Integer maxSize = Config.PLANT_MAX_SIZE;

    private Integer size = Config.PLANT_DEFAULT_SIZE;


    public Plant(Vector2d position){
        this.position = position;
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
