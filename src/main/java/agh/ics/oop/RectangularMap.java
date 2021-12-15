package agh.ics.oop;

import java.util.*;
import static java.util.Collections.shuffle;

public class RectangularMap extends AbstractWorldMap{
    // basic info constants
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final int width;
    private final int height;
    private final Vector2d jungleLowerLeft;
    private final Vector2d jungleUpperRight;
    private final int startEnergy;
    private final int plantEnergy;
    private final int dayEnergyCost;
    private final boolean borderless;
    // map objects
    private final LinkedHashMap<Vector2d, Grass> grasses;
    private final HashSet<Grass> allGrassSet;
    private final MapVisualiser mapVisualiser = new MapVisualiser(this);
    private final AnimalComparator animalComparator = new AnimalComparator();

    public RectangularMap(int width, int height, double ratio, int startEnergy,
                          int plantEnergy, int dayEnergyCost, boolean borderless) {
        super();
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width - 1, height - 1);
        this.width = width;
        this.height = height;
        jungleLowerLeft = calcJungleLowerLeft(width, height, ratio);
        jungleUpperRight = calcJungleUpperRight(width, height, ratio);
        this.startEnergy = startEnergy;
        this.plantEnergy = plantEnergy;
        this.dayEnergyCost = dayEnergyCost;
        this.borderless = borderless;
        grasses = new LinkedHashMap<>();
        allGrassSet = new HashSet<>();
        generateGrass();
    }

    public int getStartEnergy() {
        return startEnergy;
    }

    @Override
    public Vector2d getLowerLeft() {
        return lowerLeft;
    }

    @Override
    public Vector2d getUpperRight() {
        return upperRight;
    }

    @Override
    public boolean isBorderless() {
        return borderless;
    }

    @Override
    public int getWidth() {
        return  width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    // I do not mess with perfect centering of jungle, who cares

    private Vector2d calcJungleLowerLeft(int width, int height, double ratio) {
        int jungleWidth =(int) (width * ratio);
        int jungleHeight = (int) (height * ratio);
        int x = (width / 2) - (jungleWidth / 2);
        int y = (height / 2) - (jungleHeight / 2);
        return new Vector2d(x, y);
    }
    private Vector2d calcJungleUpperRight(int width, int height, double ratio) {
        int jungleWidth =(int) (width * ratio);
        int jungleHeight = (int) (height * ratio);
        int x = (width / 2) + (jungleWidth / 2) - 1;
        int y = (height / 2) + (jungleHeight / 2) - 1;
        return new Vector2d(x, y);
    }

    public boolean inJungle(Vector2d position) {
        return jungleLowerLeft.precedes(position) && jungleUpperRight.follows(position);
    }

    // integration with interfaces

    @Override
    public String toString() {
        return mapVisualiser.draw(lowerLeft, upperRight);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (lowerLeft.precedes(position) && upperRight.follows(position));
    }

    @Override
    public MapObject objectAt(Vector2d position) {
        ArrayList<Animal> animalList = animals.get(position);
        if (animalList != null && animalList.size() > 0)
            return animalList.get(0);
        if (grasses.get(position) != null)
            return grasses.get(position);
        return null;
    }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        animals.get(oldPosition).remove(animal);
        animals.computeIfAbsent(newPosition, k -> new ArrayList<>());
        animals.get(newPosition).add(animal);
        animals.get(newPosition).sort(animalComparator);
    }

    // feeding animals and removing grass

    public ArrayList<Animal> getStrongestAnimals(Vector2d position) {
        ArrayList<Animal> result = new ArrayList<>();
        ArrayList<Animal> animalList = animals.get(position);
        if (animalList == null || animalList.size() == 0)
            return result;
        result.add(animalList.get(0));
        for (int i = 1; i < animalList.size(); i++) {
            if (animalList.get(i).getEnergy() == animalList.get(0).getEnergy())
                result.add(animalList.get(i));
            else
                break;
        }
        return result;
    }

    public boolean isConsumable(Grass grass) {  // check whether there are animal(s) on that position
        return animals.get(grass.getPosition()) != null && animals.get(grass.getPosition()).size() != 0;
    }

    public void removeGrass(Grass grass) {  // in order to work grass has to be consumable
        Vector2d position = grass.getPosition();
        ArrayList<Animal> animalList = getStrongestAnimals(position);
        int profitPerAnimal = grass.getEnergyProfit() / animalList.size();
        for (Animal animal: animalList) {
            animal.changeEnergy(profitPerAnimal);
        }
        grasses.remove(position);
        allGrassSet.remove(grass);
    }

    // grass generation

    private void generateGrass() {  // generate grass at the beginning of map
        List<Grass> jungleGrass = new ArrayList<>();
        List<Grass> desertGrass = new ArrayList<>();
        for (int i = 0; i <= upperRight.x(); i++) {
            for (int j = 0; j <= upperRight.y(); j++) {
                Vector2d position = new Vector2d(i, j);
                Grass grass = new Grass(position, plantEnergy);
                if (inJungle(position))
                    jungleGrass.add(grass);
                else
                    desertGrass.add(grass);
            }
        }
        int numberOfGrass = calcArea(jungleLowerLeft, jungleUpperRight) / 3;

        // evenly distributing grasses between jungle and desert (by half) will result in higher density of grass in jg

        shuffle(jungleGrass);
        shuffle(desertGrass);
        jungleGrass = jungleGrass.subList(0, numberOfGrass / 2);
        desertGrass = desertGrass.subList(0, numberOfGrass / 2);

        for (Grass grass : jungleGrass) {
            grasses.put(grass.getPosition(), grass);
            allGrassSet.add(grass);
        }
        for (Grass grass : desertGrass) {
            grasses.put(grass.getPosition(), grass);
            allGrassSet.add(grass);
        }
    }

    public void addRandomGrass (Vector2d lowerLeft, Vector2d upperRight) {
        int area = calcArea(lowerLeft, upperRight);
        int width = upperRight.x() - lowerLeft.x() + 1;
        int height = upperRight.y() - lowerLeft.y() + 1;
        int numberOfAttempts = 0;
        while (numberOfAttempts < area) {
            int x = (int) (Math.random() * width + lowerLeft.x());
            int y = (int) (Math.random() * height + lowerLeft.y());
            Vector2d position = new Vector2d(x, y);
            if (!isOccupied(position)) {
                Grass grass = new Grass(position, plantEnergy);
                grasses.put(position, grass);
                allGrassSet.add(grass);
                break;
            }
            numberOfAttempts++;
        }
    }

    private int calcArea(Vector2d lowerLeft, Vector2d upperRight) {
        return (upperRight.x() - lowerLeft.x() + 1) * (upperRight.y() - lowerLeft.y() + 1);
    }

    // copulating

    public boolean isReproductive(Animal animal) {  // whether the animal can copulate
        return animal.getEnergy() >= startEnergy / 2;
    }

    public boolean areAnimalsReproductive(ArrayList<Animal> animalList) {
        if (animalList == null || animalList.size() < 2)
            return false;
        return isReproductive(animalList.get(0)) && isReproductive(animalList.get(1));
    }

    public void CreateAndPlaceChild(Vector2d position) {  // has to pass areAnimalsReproductive
        Animal father = animals.get(position).get(0);
        Animal mother = animals.get(position).get(1);
        Animal child = father.copulate(mother);
        child.addObserver(this);
        animals.get(position).add(child);
        allAnimalSet.add(child);
        animals.get(position).sort(animalComparator);
    }

    // methods for carrying the epoque (methods for all present animals/grasses)
    // not sure whether these methods should be here or in SimulationEngine, decided to be here

    public void removingDeadAnimalsPhase() {
        day++;
        // apply dayEnergyCost
        ArrayList<Animal> toRemove = new ArrayList<>();
        for (Animal animal : allAnimalSet) {
            animal.changeEnergy(-dayEnergyCost);
            if (animal.getEnergy() <= 0) {
                toRemove.add(animal);
            }
        }
        for (Animal animal : toRemove) {
            animals.get(animal.getPosition()).remove(animal);
            allAnimalSet.remove(animal);
            animal.removeObserver(this);
            numberOfDeadAnimals++;
            averageLifeTime += (animal.calcLifeTime(getDay()) - averageLifeTime) / numberOfDeadAnimals;
            // found that equation on Internet, hope it works
        }
    }

    public void movingAllAnimalsPhase(IEngine engine) {
        for (Animal animal : allAnimalSet) {
            try {
                Thread.sleep(engine.getMoveDelay());
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            animal.moveFreely();
            for (IEngineMoveObserver observer : engine.getObservers()) {
                observer.mapChanged();
            }
        }
    }

    public void eatingGrassesPhase() {
        ArrayList<Grass> toRemove = new ArrayList<>();
        for (Grass grass : allGrassSet) {
            if (isConsumable(grass))
                toRemove.add(grass);
        }
        for (Grass grass : toRemove) {
            removeGrass(grass);
        }
    }

    public void copulatingPhase() {
        ArrayList<Vector2d> toAdd = new ArrayList<>();
        Set<Vector2d> positions = animals.keySet();
        for (Vector2d position : positions) {
            if (areAnimalsReproductive(animals.get(position)))
                toAdd.add(position);
        }
        for (Vector2d position : toAdd) {
            CreateAndPlaceChild(position);
        }
    }

    public void grassGrowthPhase() {
        addRandomGrass(jungleLowerLeft, jungleUpperRight);
        addRandomGrass(lowerLeft, upperRight);
    }

    // stats methods
    public int getNumberOfGrass() {
        return allGrassSet.size();
    }

    public ArrayList<ArrayList<Integer>> findDominantGenotypes() {
        HashMap<ArrayList<Integer>, Integer> map = new HashMap<>();
        for (Animal animal : allAnimalSet) {
            ArrayList<Integer> animalGenes = animal.getDominantGenes();
            if (map.containsKey(animalGenes)) {
                Integer newValue = map.get(animalGenes) + 1;
                map.put(animalGenes, newValue);
            }
            else map.put(animalGenes, 1);
        }
        Set<ArrayList<Integer>> set = map.keySet();
        int dominantRepNumber = 0;
        for (ArrayList<Integer> genotype : set) {
            dominantRepNumber = Math.max(dominantRepNumber, map.get(genotype));
        }
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (ArrayList<Integer> genotype : set) {
            if (map.get(genotype) == dominantRepNumber) result.add(genotype);
        }
        return result;
    }

    public void trackDominantGeneAnimals() {
        ArrayList<ArrayList<Integer>> dominantGenotypes = findDominantGenotypes();
        for (Animal animal : allAnimalSet) {
            if (dominantGenotypes.contains(animal.getDominantGenes())) animal.setTrackedByGenotype(true);
        }
    }

    public void untrackDominantGeneAnimals() {
        for (Animal animal : allAnimalSet) {
            animal.setTrackedByGenotype(false);
        }
    }
}
