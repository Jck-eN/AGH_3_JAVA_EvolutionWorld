package agh.cs.project1;

import java.util.*;

    /**
     * Rectangular map with no borders with jungle in the middle
     * https://github.com/apohllo/obiektowe-lab/lab8
     *
     * @author Jacek N.
     */
public class EvolutionMap implements IWorldMap, IPositionChangeObserver {

    private final Map<Vector2d, Plant> plants = new LinkedHashMap<>();

    private final Rectangle area = new Rectangle(new Vector2d(0,0), new Vector2d(Config.EVOLUTION_MAP_WIDTH-1, Config.EVOLUTION_MAP_HEIGHT-1));
    private final Rectangle jungle = new Rectangle(new Vector2d((Config.EVOLUTION_MAP_WIDTH-Config.JUNGLE_WIDTH)/2,(Config.EVOLUTION_MAP_HEIGHT-Config.JUNGLE_HEIGHT)/2),
            new Vector2d((Config.EVOLUTION_MAP_WIDTH-Config.JUNGLE_WIDTH)/2+Config.JUNGLE_WIDTH, (Config.EVOLUTION_MAP_HEIGHT-Config.JUNGLE_HEIGHT)/2+Config.JUNGLE_HEIGHT));
    private Integer day=0;


    private final ArrayList[][] animalList = new ArrayList[this.getTopRightCorner().x-this.getBottomLeftCorner().x+1][];

    /**
     *  Creates empty list of animals for each map square
     */
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

     /**
     * Returns a list of Vector2d with free positions in area, that contains forbidden area inside
     *
     * @param area Rectangle in which we're looking for free positions
     * @param forbidden Rectangle in which we're not looking for free positions
     * @return  list of Vector2d positions containing no object on the map
     */
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

    /**
     *
     * @return random position containing no object on the map
     */

    Vector2d getRandomFreePosition(){
        return this.getRandomFreePositionWithForbiddenArea(this.area, null);
    }

    /**
     *
     * @param area Rectangle in which we're looking for free position
     * @param forbidden Rectangle in which we're not looking for free position
     * @return random free position on the map in area
     */
    private Vector2d getRandomFreePositionWithForbiddenArea(Rectangle area, Rectangle forbidden){
        ArrayList<Vector2d> freePos= this.freePositionsWithForbiddenArea(area, forbidden);
        if(freePos.size() == 0) return null;
        Random r = new Random();
        int idx = r.nextInt(freePos.size());
         return freePos.get(idx);
    }

    /**
     *
     * @param areaToPlant
     * Rectangle in which we want to add the plant
     */
    private void addPlant(Rectangle areaToPlant){
        this.addPlant(areaToPlant, null);
    }

    /**
     *
     * @param areaToPlant
     *              Rectangle in which we want to add the plant
     * @param areaForbidden
     *              Rectangle in area in which we don't want to add a plant
     */
    private void addPlant(Rectangle areaToPlant, Rectangle areaForbidden){
        Vector2d position = this.getRandomFreePositionWithForbiddenArea(areaToPlant, areaForbidden);
        if(position!=null){
            Plant g = new Plant(position);
            this.plants.put(position, g);
        }
    }

    /**
     * Adds animal to map and binding observer to that animal
     *
     * @param animal
     *          animal to add
     */
    public void place(Animal animal){
        if(animal == null) throw new IllegalArgumentException("Brak zwierzęcia do dodania");
        this.animalsAt(animal.getPosition()).add(animal);
        animal.addObserver(this);
    }

    /**
     *
     * @param position
     *              position to check
     * @return plant on the position, null - if there are no plants
     */
    private Plant plantAt(Vector2d position){
                if(this.plants.get(position) == null){
                    return null;
                }
                else return this.plants.get(position);
    }

    /**
     *
     * @param position
     *            Position to check.
     * @return  true if position is occupied
     */
    public boolean isOccupied(Vector2d position){
        return objectAt(position) != null;
    }

    /**
     * Checks if position is occupied
     *
     * @param position
     *            The position of the object.
     * @return one object on position on the map (first animal, or if not exists - plant)
     *          null if there are no objects
     */
    public Object objectAt(Vector2d position){
        Animal a = this.getFirstAnimalAt(position);
        if(a!= null) return a;
        else {
            return this.plantAt(position);
        }
    }
    /**
     *  Allows to get the strongest animal on position
     *
     * @param position position which we check animals on
     * @return animal with highest energy on position,
     *          null, if there are no animals
     */
    public Animal getFirstAnimalAt(Vector2d position){
        ArrayList<Animal> animals = this.animalsAt(position);
                             // Returns an animal with the highest energy
            if(animals.size()<1) return null;
            Animal tmp = animals.get(0);
            for(Animal animal : animals){
                if(animal.getEnergy() > tmp.getEnergy()){
                    tmp = animal;
                }
            }
            return tmp;
        }

    /**
     *
     * @param position position which we check animals on
     * @return animal with second highest energy on position
     */
    public Animal getSecondAnimalAt(Vector2d position){
        ArrayList<Animal> animals = this.animalsAt(position);
        if(animals.size()<2)return null;
        Animal withHighestEnergy = (Animal)this.objectAt(position);
        Animal withSecondEnergy = animals.get(0);
        for(Animal animal : animals){
            if(animal.getEnergy() <= withSecondEnergy.getEnergy()){
                withSecondEnergy = animal;   // returns lowestEnergyAnimal
            }
        }
        for(Animal animal : animals){
            if(animal.getEnergy() >= withSecondEnergy.getEnergy() && !animal.equals(withHighestEnergy)){
                withSecondEnergy = animal;   // returns secondHighest
            }
        }
        return withSecondEnergy;
    }

    /**
     *
     * @param position
     * @return
     */
    private ArrayList<Animal> getAllAnimalsWithHighestEnergy(Vector2d position){
        ArrayList<Animal> animals = this.animalsAt(position);
        if(animals.size()<1) return null;
        int maxEnergy = -1;
        for(Animal animal : animals){
            if(animal.getEnergy() > maxEnergy){
                maxEnergy = animal.getEnergy();
            }
        }
        ArrayList<Animal> withHighestEnergy = new ArrayList<>();
        for(Animal animal : animals){
            if(animal.getEnergy().equals(maxEnergy)){
                withHighestEnergy.add(animal);
            }
        }
        return withHighestEnergy;
    }

    /**
     * Gives list of animals on position
     *
     * @param position
     *              position to check
     * @return ArrayList of animals on position
     */
    private ArrayList animalsAt(Vector2d position){
        return this.animalList[position.x][position.y];
    }

    /**
     *
     * @return list of all animals on map
     */
    private ArrayList<Animal> allAnimals(){
        ArrayList<Animal> allAnimals = new ArrayList<>();
        for(int i =0; i<this.getTopRightCorner().x-this.getBottomLeftCorner().x+1; i++) {
            for(int j =0; j<this.getTopRightCorner().y-this.getBottomLeftCorner().y+1; j++) {
                allAnimals.addAll(this.animalList[i][j]);
            }
        }
        return allAnimals;
    }

    /**
     * Run one day of simulation:
     * plants grow,
     * kill animals with energy below minimal energy to move,
     * turn animals,
     * move animals,
     * eat plants,
     * seed plants
     */
    public void run(){
        for(Plant plant : plants.values()){     // Grow plants
            plant.grow(1);
        }
        ArrayList<Animal> allAnimalList = this.allAnimals();


        for(Animal a : allAnimalList) {         //Kill animals with energy below zero
            if(a.getEnergy() < Config.ENERGY_LOST_PER_MOVE ){
                a.die();
            }
        }

        allAnimalList = this.allAnimals();

        for(Animal a : allAnimalList) {         // Turn animals
            a.rotate();
        }

        for(Animal a : allAnimalList){          //Move animals

            Vector2d probablyNewPosition = a.getPosition().add(a.getDirection().toUnitVector());
            Vector2d newPosition = this.convertToPositionInMap(probablyNewPosition);
            a.move(newPosition);

        }
        for(int x=0; x<Config.EVOLUTION_MAP_WIDTH;x++){                 //Eating plants
            for(int y = 0; y< Config.EVOLUTION_MAP_HEIGHT; y++){
                Plant plant_tmp = plantAt(new Vector2d(x, y));
                ArrayList<Animal> animal_list = getAllAnimalsWithHighestEnergy(new Vector2d(x, y));
                if(plant_tmp!=null && animal_list != null){
                    if(animal_list.size()==1) {
                        this.eatPlant(animal_list, plant_tmp);
                    }
                }
            }
        }

        for(int x=0; x<Config.EVOLUTION_MAP_WIDTH;x++){                 //Creating new animals
            for(int y = 0; y< Config.EVOLUTION_MAP_HEIGHT; y++){
                if(animalsAt(new Vector2d(x, y)).size()>=2){
                    this.getFirstAnimalAt(new Vector2d(x, y)).createNewAnimal(this.getSecondAnimalAt(new Vector2d(x, y)));
                }
            }
        }

        this.day+=1;


        this.addPlant(this.jungle);             //Inside the jungle
        this.addPlant(this.area, this.jungle);  //Outside the jungle
    }

        /**
         * Animals eat plant
         *
         * @param animals all animals eating plant
         * @param plant plant to eat
         */
    private void eatPlant(ArrayList<Animal> animals, Plant plant){
        for(Animal animal : animals){
            animal.addEnergy(plant.getSize()/animals.size());
        }
        this.plants.remove(plant.getPosition());
        }


        /**
         *
         * @param position
         *            The position checked for the movement possibility.
         * @return  Always true - Currently animals can always move to position
         */
    public boolean canMoveTo(Vector2d position){
        return true;
    }

    /**
     *
     * @return top right corner of the map
     */
    Vector2d getTopRightCorner(){
        return this.area.getTopRightCorner();
    }

    /**
     *
     * @return bottom left corner of the map
     */
    Vector2d getBottomLeftCorner(){
        return this.area.getBottomLeftCorner();
    }

    /**
     *
     * @param animalToRemove animal that must be removed from the map
     * @return true if deleting animal has finished with success
     */
    public boolean remove(Animal animalToRemove){
        ArrayList<Animal> animals = this.animalsAt(animalToRemove.getPosition());
        if(animals.size()==0) return false;
        animals.remove(animalToRemove);
        return true;
    }

    /**
     * Converts position to proper position in map
     *
     * @param old position we want to convert
     * @return proper position on the map
     */
    private Vector2d convertToPositionInMap(Vector2d old){
        if(this.area.hasPositionInside(old)) return old;
        else{
            Integer new_x = Math.floorMod(old.x,this.getTopRightCorner().x-this.getBottomLeftCorner().x+1);
            Integer new_y = Math.floorMod(old.y,this.getTopRightCorner().y-this.getBottomLeftCorner().y+1);
            return new Vector2d(new_x, new_y);
        }
    }

    /**
     * Moves animal to new position and change
     * list containing that animal
     *
     * @param animal animal to move
     * @param oldPosition old position of animal
     * @param newPosition new position of animal
     */
    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition){
        this.animalsAt(oldPosition).remove(animal);
        this.animalsAt(newPosition).add(animal);
    }

    /**
     *
     * @param original base position
     * @return random free position around base position
     */
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

    /**
     *
     * @return jungle inside map
     */
    Rectangle getJungle(){
        return this.jungle;
    }

    /**
     *
     * @return current day of simulation
     */
    Integer getDay() {
        return day;
    }
}
