package agh.ics.oop.mapObjects;

import agh.ics.oop.map.Vector2d;

public class Grass extends MapObject{
    private final int energyProfit;

    public Grass(Vector2d position, int energyProfit) {
        super(position);
        this.energyProfit = energyProfit;
    }

    @Override
    public boolean isTrackedByGenotype() {
        return false;
    }

    @Override
    public boolean hasEnergy() {
        return false;
    }

    @Override
    public int getEnergy() {
        return 0;
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergyProfit() {
        return energyProfit;
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public String getImageURL() {
        return "src/main/resources/grass.png";
    }
}
