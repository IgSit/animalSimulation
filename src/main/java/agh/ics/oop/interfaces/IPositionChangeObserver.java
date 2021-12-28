package agh.ics.oop.interfaces;

import agh.ics.oop.map.Vector2d;
import agh.ics.oop.mapObjects.Animal;

public interface IPositionChangeObserver {

    void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition);
}
