package agh.ics.oop;

public class Grass extends MapObject{
    private final int energyProfit;

    public Grass(Vector2d position,int energyProfit) {
        super(position);
        this.energyProfit = energyProfit;
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