package agh.cs.project1;

public class Vector2d {
    public final int x, y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }
    public String toString() {
        return String.format("(%s,%s)", this.x, this.y);
    }



    public boolean precedes(Vector2d other){
        return (other.x >= this.x && other.y >= this.y);
    }

    public boolean follows(Vector2d other){
        return (other.x <= this.x && other.y <= this.y);
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);

    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return (this.x == that.x && this.y == that.y);
    }
    public Vector2d opposite(){
        return new Vector2d(this.x * (-1), this.y * (-1));
    }

    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }
}
