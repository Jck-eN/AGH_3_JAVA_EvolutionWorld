package agh.cs.project1;

public interface IPositionChangeObserver {
    public void positionChanged(Animal a, Vector2d oldPosition, Vector2d newPosition);
}
