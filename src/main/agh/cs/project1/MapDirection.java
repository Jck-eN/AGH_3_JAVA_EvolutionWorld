package agh.cs.project1;

/**
 * Directions that animals can move
 *
 * @author Jacek N.
 */
public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST ;

    /**
     *
     * @return name of direction in polish language
     */
    public String toString() {
        switch(this){
            case EAST: return "Wschód";
            case SOUTH_EAST: return "Południowy wschód";
            case SOUTH: return "Południe";
            case SOUTH_WEST: return "Południowy zachód";
            case WEST: return "Zachód";
            case NORTH_WEST: return "Północny zachód";
            case NORTH: return "Północ";
            case NORTH_EAST: return "Północny wschód";
            default: return "";
        }
    }

    /**
     *
     * @return unit vector based on direction
     * of the length 1 (or sqrt(2) diagonally)
     */
    public Vector2d toUnitVector(){
        switch(this){
            case EAST:
                return new Vector2d(1,0);
            case SOUTH_EAST:
                return new Vector2d(1,-1);
            case NORTH_EAST:
                return new Vector2d(1,1);
            case WEST:
                return new Vector2d(-1, 0);
            case SOUTH_WEST:
                return new Vector2d(-1,-1);
            case NORTH_WEST:
                return new Vector2d(-1,1);
            case NORTH:
                return new Vector2d(0,1);
            case SOUTH:
                return new Vector2d(0,-1);
            default:
                throw new IllegalArgumentException("Nieprawidłowy kierunek!");
        }

    }

}
