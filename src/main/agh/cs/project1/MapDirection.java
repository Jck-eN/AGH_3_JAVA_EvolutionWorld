package agh.cs.project1;

public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST ;


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

    public MapDirection next(){
        int idx = (this.ordinal() + 1) % MapDirection.values().length;
        return MapDirection.values()[idx];
    }

    public MapDirection previous(){
        int idx = (this.ordinal() - 1 + MapDirection.values().length) % MapDirection.values().length;
        return MapDirection.values()[idx];
    }

    public Vector2d toUnitVector(){
        Vector2d vector;
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
