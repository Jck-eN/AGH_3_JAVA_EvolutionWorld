package agh.cs.project1;

/**
 * Describes observer that is notified about animal's position change
 */
interface IPositionChangeObserver {
    void positionChanged(Animal a, Vector2d oldPosition, Vector2d newPosition);
}
