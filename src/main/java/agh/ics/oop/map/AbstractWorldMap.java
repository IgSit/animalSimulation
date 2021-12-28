package agh.ics.oop.map;


import agh.ics.oop.mapObjects.Animal;
import agh.ics.oop.interfaces.IPositionChangeObserver;
import agh.ics.oop.interfaces.IWorldMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

abstract public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected final LinkedHashMap<Vector2d, ArrayList<Animal>> animals;
    protected final HashSet<Animal> allAnimalSet;
    protected double averageLifeTime = 0;
    protected int numberOfDeadAnimals = 0;
    protected int day = 0;

    public AbstractWorldMap() {
        animals = new LinkedHashMap<>();
        allAnimalSet = new HashSet<>();
    }

    public abstract Vector2d getLowerLeft();

    public abstract Vector2d getUpperRight();

    public abstract boolean isBorderless();

    public abstract int getWidth();

    public abstract int getHeight();

    @Override
    public abstract void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition);

    @Override
    public boolean place(Animal newAnimal) {  // only for non-born animals
        Vector2d position = newAnimal.getPosition();
        if (canPlace(position)) {
            ArrayList<Animal> animalList = new ArrayList<>();
            animalList.add(newAnimal);
            animals.put(position, animalList);
            allAnimalSet.add(newAnimal);
            newAnimal.addObserver(this);
            return true;
        }
        throw new IllegalArgumentException("Position " + position + " is already occupied");
    }

    public boolean canPlace(Vector2d position) {
        ArrayList<Animal> animalList = animals.get(position);
        return animalList == null || animalList.size() == 0;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    // stats methods

    public int getNumberOfAnimals() {
        return allAnimalSet.size();
    }

    public double getAverageLifeTime() {
        return averageLifeTime;
    }

    public double getAverageAnimalEnergy() {
        double sum = 0;
        for (Animal animal : allAnimalSet) {
            sum += animal.getEnergy();
        }
        return sum / allAnimalSet.size();
    }

    public double getAverageAmountOfChildren() {
        double sum = 0;
        for (Animal animal : allAnimalSet) {
            sum += animal.getNumberOfChildren();
        }
        return sum / allAnimalSet.size();
    }

    public int getDay() {
        return day;
    }
}
