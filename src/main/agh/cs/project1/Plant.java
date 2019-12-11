package agh.cs.project1;

/**
 * Plant that can grow on evolution map
 * It's size changes every turn until it reaches
 * max size
 */
public class Plant implements IMapElement {
    private final Vector2d position;
    private final static Integer maxSize = Config.PLANT_MAX_SIZE;
    private Integer size = Config.PLANT_DEFAULT_SIZE;


    public Plant(Vector2d position){
        this.position = position;
    }

    /**
     * makes plant grow given size
     * but not exceeds max size
     *
     * @param growSize describes how much plant grows every day
     */
    public void grow(Integer growSize){
        this.size+=growSize;
        if(this.size>Plant.maxSize)this.size=maxSize;
    }

    /**
     *
     * @return position of the plant on the map
     */
    public Vector2d getPosition(){
        return this.position;
    }

    /**
     *
     * @return size of the plant
     */
    Integer getSize(){
        return this.size;
    }

    /**
     *
     * @return string representing plant - *
     */
    public String toString() {
        return "*";
    }
}
