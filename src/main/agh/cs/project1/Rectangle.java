package agh.cs.project1;

/**
 *  Rectangle based on two vectors (bottom-left corner and top-right corner)
 */
class Rectangle {
    private final Vector2d topRightCorner;
    private final Vector2d bottomLeftCorner;

    Rectangle(Vector2d bottomLeft, Vector2d topRight){
        this.bottomLeftCorner = bottomLeft;
        this.topRightCorner = topRight;
    }

    /**
     *
     * @return top-right corner of rectangle
     */
    public Vector2d getTopRightCorner(){
        return this.topRightCorner;
    }

    /**
     *
     * @return bottom-left corner of rectangle
     */
    public Vector2d getBottomLeftCorner(){
        return this.bottomLeftCorner;
    }

    /**
     *
     * @param position position to check
     * @return true if given position is placed inside the rectangle
     * (or in the borders)
     */
    public boolean hasPositionInside(Vector2d position){
        return position.precedes(this.getTopRightCorner()) && position.follows(this.getBottomLeftCorner());
    }

    /**
     *
     * @return calculated area of the rectangle
     */
    public int area(){
        return (this.getTopRightCorner().x-this.getBottomLeftCorner().x+1)*
                (this.getTopRightCorner().y-this.getBottomLeftCorner().y+1);
    }

}
