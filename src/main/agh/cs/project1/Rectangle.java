package agh.cs.project1;

public class Rectangle {
    private final Vector2d topRightCorner;
    private final Vector2d bottomLeftCorner;

    Rectangle(Vector2d bottomLeft, Vector2d topRight){
        this.bottomLeftCorner = bottomLeft;
        this.topRightCorner = topRight;
    }

    public Vector2d getTopRightCorner(){
        return this.topRightCorner;
    }
    public Vector2d getBottomLeftCorner(){
        return this.bottomLeftCorner;
    }

    public boolean hasPositionInside(Vector2d position){
        return position.precedes(this.getTopRightCorner()) && position.follows(this.getBottomLeftCorner());
    }
    public int area(){
        return (this.getTopRightCorner().x-this.getBottomLeftCorner().x+1)*
                (this.getTopRightCorner().y-this.getBottomLeftCorner().y+1);
    }

}
