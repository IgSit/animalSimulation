package agh.ics.oop.mapObjects;

import agh.ics.oop.interfaces.IPositionChangeObserver;
import agh.ics.oop.map.Vector2d;
import agh.ics.oop.map.AbstractWorldMap;
import agh.ics.oop.map.MapDirection;

import java.util.ArrayList;
import java.util.Comparator;

public class Animal extends MapObject implements Comparator<Animal> {
    private MapDirection orientation;
    private int energy;
    private final Genes genes;
    private final ArrayList<Integer> dominantGenes;
    private boolean trackedByGenotype = false;
    private final AbstractWorldMap map;
    private int numberOfChildren = 0;
    private final int birthDay;

    public Animal(Vector2d position, int energy, AbstractWorldMap map) {  // constructor for non-born animal
        super(position);
        this.orientation = MapDirection.NORTH.makeRandomOrientation();
        this.energy = energy;
        genes = new Genes(32);
        dominantGenes = findDominantGenes();
        this.map = map;
        birthDay = map.getDay();
    }

    public Animal(Animal father, Animal mother) {
        super(father.position);
        orientation = MapDirection.NORTH.makeRandomOrientation();
        energy = (int) (0.25 * (father.energy + mother.energy) );
        genes = new Genes(father, mother);
        dominantGenes = findDominantGenes();
        map = father.map;
        birthDay = map.getDay();
    }

    public void setTrackedByGenotype(boolean trackedByGenotype) {
        this.trackedByGenotype = trackedByGenotype;
    }

    private ArrayList<Integer> findDominantGenes() {
        int[] countGeneRepetitions = new int[8];
        for (int i : genes.getGenes()) {
            countGeneRepetitions[i]++;
        }
        int maxRep = 0;
        for (int gene = 0; gene < 8; gene++) {
            maxRep = Math.max(maxRep, countGeneRepetitions[gene]);
        }
        ArrayList<Integer> dominant = new ArrayList<>();
        for (int gene = 0; gene < 8; gene++) {
            if (countGeneRepetitions[gene] == maxRep) dominant.add(gene);
        }
        return dominant;
    }

    public ArrayList<Integer> getDominantGenes() {
        return dominantGenes;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    public Genes getGenes() {
        return genes;
    }

    public int getNumberOfChildren() { return numberOfChildren; }

    public int calcLifeTime(int day) {
        return day - birthDay + 1;
    }

    public void changeEnergy(int value) {
        energy += value;
    }

    @Override
    public boolean hasEnergy() { return true; }

    @Override
    public boolean isTrackedByGenotype() {
        return trackedByGenotype;
    }

    @Override
    public String toString() {
        return switch (orientation) {
            case NORTH -> "N";
            case NORTHEAST -> "NE";
            case EAST -> "E";
            case SOUTHEAST -> "SE";
            case SOUTH -> "S";
            case SOUTHWEST -> "SW";
            case WEST -> "W";
            case NORTHWEST -> "NW";
        };
    }

    @Override
    public String getImageURL() {
        return switch (orientation) {
            case NORTH -> "src/main/resources/up.png";
            case NORTHWEST -> "src/main/resources/upleft.png";
            case WEST -> "src/main/resources/left.png";
            case SOUTHWEST -> "src/main/resources/downleft.png";
            case SOUTH -> "src/main/resources/down.png";
            case SOUTHEAST -> "src/main/resources/downright.png";
            case EAST -> "src/main/resources/right.png";
            case NORTHEAST -> "src/main/resources/upright.png";
        };
    }

    //communicate with the agh.ics.oop.map and move

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    protected void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }

    private Vector2d applyMapBorders(Vector2d position) {
        if (map.isBorderless()) {
            position = new Vector2d(position.x() % map.getWidth(), position.y() % map.getHeight());
        }
        return position;
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
            case FORWARD, BACKWARD -> {
                Vector2d unitVector = orientation.toUnitVector();
                if (direction == MoveDirection.BACKWARD)
                    unitVector = unitVector.opposite();
                Vector2d newPosition = position.add(unitVector);
                newPosition = applyMapBorders(newPosition);
                if (map.canMoveTo(newPosition)) {
                    positionChanged(position, newPosition);
                    position = newPosition;
                }
            }
        }
    }

    public void moveFreely() {
        int gene = genes.getRandomGene();
        if (gene == 0)
            move(MoveDirection.FORWARD);
        else if (gene == 4)
            move(MoveDirection.BACKWARD);
        else {
            for (int i = 0; i < gene; i++) {
                move(MoveDirection.RIGHT);
            }
        }
    }

    // copulate

    public Animal copulate(Animal mother) {
        Animal child = new Animal(this, mother);
        changeEnergy((int) (-0.25 * energy));
        mother.changeEnergy((int) (-0.25 * mother.energy));
        numberOfChildren++;
        mother.numberOfChildren++;
        return child;
    }

    @Override
    public int compare(Animal o1, Animal o2) {
        return o1.getEnergy() - o2.getEnergy();
    }
}
