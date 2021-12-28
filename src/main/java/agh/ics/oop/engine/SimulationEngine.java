package agh.ics.oop.engine;

import agh.ics.oop.gui.PartialApp;
import agh.ics.oop.interfaces.IEngine;
import agh.ics.oop.interfaces.IEngineMoveObserver;
import agh.ics.oop.map.RectangularMap;
import agh.ics.oop.map.Vector2d;
import agh.ics.oop.mapObjects.Animal;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.shuffle;

public class SimulationEngine implements IEngine, Runnable{
    private final RectangularMap map;
    private final ArrayList<IEngineMoveObserver> observers;
    private final int moveDelay = 300;
    private final PartialApp app;

    public SimulationEngine(RectangularMap map, int numberOfAnimals, PartialApp app) {
        this.map = map;
        observers = new ArrayList<>();
        generateAnimals(numberOfAnimals, map.getStartEnergy());
        this.app = app;
    }

    public RectangularMap getMap() {
        return map;
    }

    private void generateAnimals(int numberOfAnimals, int startEnergy) {
        List<Vector2d> positions = new ArrayList<>();
        for (int i = map.getLowerLeft().x(); i <= map.getUpperRight().x(); i++) {
            for (int j = map.getLowerLeft().y(); j <= map.getUpperRight().y(); j++) {
                positions.add(new Vector2d(i, j));
            }
        }
        shuffle(positions);
        positions = positions.subList(0, numberOfAnimals);
        for (Vector2d position : positions) {
            Animal animal = new Animal(position, startEnergy, map);
            map.place(animal);
        }
    }
    @Override
    public int getMoveDelay() {
        return moveDelay;
    }

    @Override
    public void addObserver(IEngineMoveObserver observer) {
        observers.add(observer);
    }

    @Override
    public ArrayList<IEngineMoveObserver> getObservers() {
        return observers;
    }

    @Override
    public void run() {
        while (map.getNumberOfAnimals() > 0){
            map.removingDeadAnimalsPhase();
            map.movingAllAnimalsPhase(this);
            map.eatingGrassesPhase();
            map.copulatingPhase();
            map.grassGrowthPhase();
            app.newDay(map.getDay());
        }
    }
}
