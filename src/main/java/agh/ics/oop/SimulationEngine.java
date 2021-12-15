package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.shuffle;

public class SimulationEngine implements IEngine{
    private final RectangularMap map;

    public SimulationEngine(RectangularMap map, int numberOfAnimals) {
        this.map = map;
        generateAnimals(numberOfAnimals, map.getStartEnergy());
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

    public void run() {
        while (map.getNumberOfAnimals() > 0){
            System.out.println(map);
            map.removingDeadAnimalsPhase();
            map.movingAllAnimalsPhase();
            map.eatingGrassesPhase();
            map.copulatingPhase();
            map.grassGrowthPhase();
        }
    }
}
