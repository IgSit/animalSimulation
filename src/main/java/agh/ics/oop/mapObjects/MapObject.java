package agh.ics.oop.mapObjects;

import agh.ics.oop.interfaces.IPositionChangeObserver;
import agh.ics.oop.map.Vector2d;

import java.util.HashSet;

public abstract class MapObject{
    protected Vector2d position;
    protected final HashSet<IPositionChangeObserver> observers;

    public MapObject(Vector2d position) {
        this.position = position;
        observers = new HashSet<>();
    }

    public abstract String getImageURL();

    public abstract boolean hasEnergy();

    public abstract int getEnergy();

    public abstract boolean isTrackedByGenotype();

    public Vector2d getPosition() {
        return position;
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }
}
