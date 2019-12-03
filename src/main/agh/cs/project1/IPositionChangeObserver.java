package agh.cs.project1;

interface IPositionChangeObserver {
    void positionChanged(Animal a, Vector2d oldPosition, Vector2d newPosition);
}
