package agh.ics.oop;

import java.util.Comparator;

public class Animal extends MapObject implements Comparator<Animal> {
    private MapDirection orientation;
    private int energy;
    private final Genes genes;
    private final IWorldMap map;

    public Animal(Vector2d position, int energy, IWorldMap map) {  // constructor for non-born animal
        super(position);
        this.orientation = MapDirection.NORTH.makeRandomOrientation();
        this.energy = energy;
        genes = new Genes(32);
        this.map = map;
    }

    public Animal(Animal father, Animal mother) {
        super(father.position);
        orientation = MapDirection.NORTH.makeRandomOrientation();
        energy = (int) (0.25 * (father.energy + mother.energy) );
        genes = new Genes(father, mother);
        map = father.map;
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public Genes getGenes() {
        return genes;
    }

    public void changeEnergy(int value) {
        energy += value;
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
            case SOUTHEAST -> "src/main/resources/downrigth.png";
            case EAST -> "src/main/resources/right.png";
            case NORTHEAST -> "src/main/resources/upright.png";
        };
    }

    //communicate with the map and move

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

    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
            case FORWARD, BACKWARD -> {
                Vector2d unitVector = orientation.toUnitVector();
                if (direction == MoveDirection.BACKWARD)
                    unitVector = unitVector.opposite();
                Vector2d newPosition = position.add(unitVector);
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
        return child;
    }

    @Override
    public int compare(Animal o1, Animal o2) {
        return o1.getEnergy() - o2.getEnergy();
    }
}
