package agh.cs.project1;

public class Vector2d {

    public final int x, y;

    /**
     * Creates vector2d based on given coordinates
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return string based on vector's coordinates. Eg. (2,4)
     */
    public String toString() {
        return String.format("(%s,%s)", this.x, this.y);
    }


    /**
     *
     * @param other vector to compare
     * @return true if both coordinates of first vector vector are bigger or equal to other
     */
    public boolean precedes(Vector2d other){
        return (other.x >= this.x && other.y >= this.y);
    }

    /**
     *
     * @param other vector to compare
     * @return true if both coordinates of first vector vector are smaller or equal to other
     *
     */
    public boolean follows(Vector2d other){
        return (other.x <= this.x && other.y <= this.y);
    }

    /**
     *
     * @param other vector to compare
     * @return upper right of rectangle created from two vectors
     *
     */
    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }
    /**
     *
     * @param other vector to compare
     * @return bottom left of rectangle created from two vectors
     *
     */
    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    /**
     *
     * @param other vector to add
     * @return sum of two vectors
     */
    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    /**
     *
     * @param other vector to subtract
     * @return difference of two vectors
     */
    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);

    }

    /**
     *
     * @param other vector to compare
     * @return true if both vectors have the same coordinates
     */
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return (this.x == that.x && this.y == that.y);
    }

    /**
     *
     * @return opposite vector
     */
    public Vector2d opposite(){
        return new Vector2d(this.x * (-1), this.y * (-1));
    }

    /**
     * Calculates hashcode based on vector coordinates
     *
     * @return calculated hashcode for vector
     */
    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }
}
