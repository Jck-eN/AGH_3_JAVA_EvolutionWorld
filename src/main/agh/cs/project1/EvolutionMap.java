package agh.cs.project1;

import java.util.*;

public class EvolutionMap implements IWorldMap, IPositionChangeObserver {

    public final Map<Vector2d, Plant> plants = new LinkedHashMap<>();

    private final Rectangle area = new Rectangle(new Vector2d(0,0), new Vector2d(Config.EVOLUTION_MAP_WIDTH-1, Config.EVOLUTION_MAP_HEIGHT-1));
    private final Rectangle jungle = new Rectangle(new Vector2d((Config.EVOLUTION_MAP_WIDTH-Config.JUNGLE_WIDTH)/2,(Config.EVOLUTION_MAP_HEIGHT-Config.JUNGLE_HEIGHT)/2),
            new Vector2d((Config.EVOLUTION_MAP_WIDTH-Config.JUNGLE_WIDTH)/2+Config.JUNGLE_WIDTH, (Config.EVOLUTION_MAP_HEIGHT-Config.JUNGLE_HEIGHT)/2+Config.JUNGLE_HEIGHT));


    private final ArrayList[][] animalList = new ArrayList[this.getTopRightCorner().x-this.getBottomLeftCorner().x+1][];

    EvolutionMap(){
        for(int i =0; i<this.getTopRightCorner().x-this.getBottomLeftCorner().x+1; i++) {
            this.animalList[i] = new ArrayList[this.getTopRightCorner().y - this.getBottomLeftCorner().y + 1];
        }
        for(int i =0; i<this.getTopRightCorner().x-this.getBottomLeftCorner().x+1; i++) {
            for(int j =0; j<this.getTopRightCorner().y-this.getBottomLeftCorner().y+1; j++) {
                animalList[i][j] = new ArrayList<Animal>();
            }
        }
    }

    private Vector2d getRandomPosition(Rectangle area){
        Random r = new Random();
        int x = r.nextInt(area.getTopRightCorner().x -area.getBottomLeftCorner().x+1);
        int y = r.nextInt(area.getTopRightCorner().y -area.getBottomLeftCorner().y+1);
        return new Vector2d(area.getBottomLeftCorner().x+x, area.getBottomLeftCorner().y+y);
    }

    public ArrayList<Vector2d> freePositions(Rectangle area){
        return freePositionsWithForbiddenArea(area, null);
    }

    public ArrayList<Vector2d> freePositions(){
        return  freePositionsWithForbiddenArea(this.area, null);
    }

    private ArrayList<Vector2d> freePositionsWithForbiddenArea(Rectangle area, Rectangle forbidden){
        ArrayList<Vector2d> freePos= new ArrayList<>();
        for(int i=area.getBottomLeftCorner().x; i<= area.getTopRightCorner().x; i++){
            for(int j=area.getBottomLeftCorner().y; j<= area.getTopRightCorner().y; j++){
                if(!this.isOccupied(new Vector2d(i, j)) ){
                    if(forbidden == null) freePos.add(new Vector2d(i, j));
                    else if (!forbidden.hasPositionInside(new Vector2d(i, j))){
                        freePos.add(new Vector2d(i, j));
                    }
                }
            }
        }
        return freePos;
    }

    public Vector2d getRandomFreePosition(Rectangle area){
        return this.getRandomFreePositionWithForbiddenArea(area, null);
    }
    public Vector2d getRandomFreePosition(){
        return this.getRandomFreePositionWithForbiddenArea(this.area, null);
    }

    private Vector2d getRandomFreePositionWithForbiddenArea(Rectangle area, Rectangle forbidden){
        ArrayList<Vector2d> freePos= this.freePositionsWithForbiddenArea(area, forbidden);
        if(freePos.size() == 0) return null;
        Random r = new Random();
        int idx = r.nextInt(freePos.size());
         return  freePos.get(idx);
    }

    private void addPlant(Rectangle areaToPlant){
        this.addPlant(areaToPlant, null);
    }

    private void addPlant(Rectangle areaToPlant, Rectangle areaForbidden){
        Vector2d position = this.getRandomFreePositionWithForbiddenArea(areaToPlant, areaForbidden);
        Plant g = new Plant(position);
        this.plants.put(position, g);
    }

    public void place(Animal animal){
        if(animal == null) throw new IllegalArgumentException("Brak zwierzęcia do dodania");
        this.animalsAt(animal.getPosition()).add(animal);
        animal.addObserver(this);
    }


    private Plant plantAt(Vector2d position){
                if(this.plants.get(position) == null){
                    return null;
                }
                else return this.plants.get(position);
    }

    public boolean isOccupied(Vector2d position){
        return objectAt(position) != null;
    }

    public Object objectAt(Vector2d position){
        ArrayList<Animal> a = this.animalsAt(position);
        if(a.size()==0) {
            return this.plantAt(position);
        }
        else{                               // Returns an animal with the highest energy
            Animal tmp = a.get(0);
            for(Animal animal : a){
                if(animal.getEnergy() > tmp.getEnergy()){
                    tmp = animal;
                }
            }
            return tmp;
        }
    }

    private ArrayList animalsAt(Vector2d position){
        return this.animalList[position.x][position.y];
    }

    public ArrayList<Animal> allAnimals(){
        ArrayList<Animal> allanimals = new ArrayList<>();
        for(int i =0; i<this.getTopRightCorner().x-this.getBottomLeftCorner().x+1; i++) {
            for(int j =0; j<this.getTopRightCorner().y-this.getBottomLeftCorner().y+1; j++) {
                allanimals.addAll(this.animalList[i][j]);
            }
        }
        return allanimals;
    }

    public void run(){
        for(Plant plant : plants.values()){
            plant.grow(1);
        }
        ArrayList<Animal> tmpAnimalList = this.allAnimals();


        for(Animal a : tmpAnimalList) {
            if(a.getEnergy() < 0 ){
                a.die();
            }
        }

        tmpAnimalList = this.allAnimals();

        for(Animal a : tmpAnimalList) {
            a.rotate();
        }

        for(Animal a : tmpAnimalList){

            Vector2d probablyNewPosition = a.getPosition().add(a.getDirection().toUnitVector());
            Vector2d newPosition = this.convertToPositionInMap(probablyNewPosition);
            Object elementOnNewPosition = this.objectAt(newPosition);
            a.move(newPosition);
            if (elementOnNewPosition instanceof Plant) {
                this.eatPlant(a, (Plant) elementOnNewPosition);
            }
            else if (elementOnNewPosition instanceof Animal) {
                a.createNewAnimal((Animal) elementOnNewPosition);
            }

        }


        this.addPlant(this.jungle);             //Inside the jungle
        this.addPlant(this.area, this.jungle);  //Outside the jungle
    }

    private void eatPlant(Animal a, Plant g){
        a.addEnergy(g.getSize());
        this.plants.remove(g.getPosition());
    }

    public boolean canMoveTo(Vector2d position){
        return true;                            //Currently animals can always move to position
    }


    Vector2d getTopRightCorner(){
        return this.area.getTopRightCorner();
    }
    Vector2d getBottomLeftCorner(){
        return this.area.getBottomLeftCorner();
    }

    public boolean remove(Animal a){
        ArrayList<Animal> animals = this.animalsAt(a.getPosition());
        if(animals.size()==0) return false;
        animals.remove(a);
        return true;
    }

    private Vector2d convertToPositionInMap(Vector2d old){
        if(this.area.hasPositionInside(old)) return old;
        else{
            Integer new_x = Math.floorMod(old.x,this.getTopRightCorner().x-this.getBottomLeftCorner().x+1);
            Integer new_y = Math.floorMod(old.y,this.getTopRightCorner().y-this.getBottomLeftCorner().y+1);
            return new Vector2d(new_x, new_y);
        }
    }

    public void positionChanged(Animal a, Vector2d oldPosition, Vector2d newPosition){
        this.animalsAt(oldPosition).remove(a);
        this.animalsAt(newPosition).add(a);
    }

    public String toString() {
        return new MapVisualizer(this).draw(this.getBottomLeftCorner(), this.getTopRightCorner());
    }


    public Vector2d findFreeSpaceNear(Vector2d original) {
        for(int x = -1; x < 2; x++){
            for(int y = -1; y < 2; y++){
                if(x == 0 && y == 0){
                    continue;
                }
                Vector2d newPos = original.add(new Vector2d(x, y));
                newPos = this.convertToPositionInMap(newPos);
                if(this.objectAt(newPos)== null){
                    return newPos;
                }
            }
        }
        return null;
    }

    public Vector2d getJungleBottomLeft(){
        return this.jungle.getBottomLeftCorner();
    }

    public Vector2d getJungleTopRight(){
        return this.jungle.getTopRightCorner();
    }


}
