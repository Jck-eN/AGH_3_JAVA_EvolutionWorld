package agh.cs.project1;

import java.util.*;

public class EvolutionMap extends AbstractWorldMap {

    private Map<Vector2d, Plant> plants = new LinkedHashMap<>();

    private final Rectangle area = new Rectangle(new Vector2d(0,0), new Vector2d(Config.EVOLUTION_MAP_WIDTH-1, Config.EVOLUTION_MAP_HEIGHT-1));
    private final Rectangle jungle = new Rectangle(new Vector2d((Config.EVOLUTION_MAP_WIDTH-Config.JUNGLE_WIDTH)/2,(Config.EVOLUTION_MAP_HEIGHT-Config.JUNGLE_HEIGHT)/2),
            new Vector2d((Config.EVOLUTION_MAP_WIDTH-Config.JUNGLE_WIDTH)/2+Config.JUNGLE_WIDTH, (Config.EVOLUTION_MAP_HEIGHT-Config.JUNGLE_HEIGHT)/2+Config.JUNGLE_HEIGHT));

    private Vector2d getRandomPosition(Rectangle area){
        Random r = new Random();
        int x = r.nextInt((int) area.getTopRightCorner().x-area.getBottomLeftCorner().x+1);
        int y = r.nextInt((int) area.getTopRightCorner().y-area.getBottomLeftCorner().y+1);
        return new Vector2d(area.getBottomLeftCorner().x+x, area.getBottomLeftCorner().y+y);
    }

    private void addPlant(Rectangle areaToPlant){
        Vector2d position;
        int counter=0;
        do {
            if(counter > 2*areaToPlant.area()) return;
            position = getRandomPosition(areaToPlant);
            counter++;
        }
        while (this.isOccupied(position));
        Plant g = new Plant(position);
        this.plants.put(g.getPosition(), g);
    }

    private void addPlant(Rectangle areaToPlant, Rectangle areaForbidden){
        Vector2d position;
        int counter=0;
        Random r = new Random();
        do {
            if(counter > 2*areaToPlant.area()) return;
            position = getRandomPosition(areaToPlant);
            counter++;
        }
        while (this.isOccupied(position) || areaForbidden.hasPositionInside(position));
        Plant g = new Plant(position);
        this.plants.put(g.getPosition(), g);
    }

    public Plant plantAt(Vector2d position){
                if(this.plants.get(position) == null){
                    return null;
                }
                else return this.plants.get(position);
    }

    public Object objectAt(Vector2d position){
        Object object = super.objectAt(position);
        if(object!=null) return object;

        return this.plantAt(position);
    }

    public void run(){
        for(Plant plant : plants.values()){
            plant.grow(1);
        }
        ArrayList<Animal> tmpAnimalList = new ArrayList<>();
        for(Animal a : this.animals.values()){
                tmpAnimalList.add(a);
        }
        for(Animal a : tmpAnimalList){
            if(a.getEnergy() < 0 ){
                a.die();
                break;
            }
            a.rotate();
            Vector2d probablyNewPosition = a.getPosition().add(a.getDirection().toUnitVector());
            Vector2d newPosition = this.convertToPositionInMap(probablyNewPosition);
            Object elementOnNewPosition = this.objectAt(newPosition);
            if (elementOnNewPosition instanceof Plant) {
                this.eatPlant(a, (Plant) elementOnNewPosition);
                a.move(newPosition);
            } else if (elementOnNewPosition instanceof Animal) {
                a.createNewAnimal((Animal) elementOnNewPosition);
            } else {
                a.move(newPosition);
            }

        }


        this.addPlant(this.jungle);             //Inside the jungle
        this.addPlant(this.area, this.jungle);  //Outside the jungle
    }

    private void eatPlant(Animal a, Plant g){
        a.addEnergy(g.getSize());
        this.plants.remove(g.getPosition());
    };

    public boolean canMoveTo(Vector2d position){
        return true; //Currently animals can always move to position
    }


    public Vector2d getTopRightCorner(){
        return this.area.getTopRightCorner();
    }
    public Vector2d getBottomLeftCorner(){
        return this.area.getBottomLeftCorner();
    }

    public boolean remove(Animal a){
        if(animals.get(a.getPosition()) != null){
            animals.remove(a.getPosition());
            return true;
        }
        else return false;
    }

    private Vector2d convertToPositionInMap(Vector2d old){
        if(this.area.hasPositionInside(old)) return old;
        else{
            Integer new_x = Math.floorMod(old.x,this.getTopRightCorner().x-this.getBottomLeftCorner().x+1);
            Integer new_y = Math.floorMod(old.y,this.getTopRightCorner().y-this.getBottomLeftCorner().y+1);
            return new Vector2d(new_x, new_y);
        }
    }
}
