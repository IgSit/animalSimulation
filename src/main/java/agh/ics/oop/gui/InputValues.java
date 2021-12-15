package agh.ics.oop.gui;

public enum InputValues {
    WIDTH,
    HEIGHT,
    JUNGLE_RATIO,
    ANIMAL_ENERGY,
    PLANT_ENERGY,
    MOVE_ENERGY,
    ANIMAL_AMOUNT;

    public int toIndex() {
        return switch (this) {
            case WIDTH -> 0;
            case HEIGHT -> 1;
            case JUNGLE_RATIO -> 2;
            case ANIMAL_ENERGY -> 3;
            case PLANT_ENERGY -> 4;
            case MOVE_ENERGY -> 5;
            case ANIMAL_AMOUNT -> 6;
        };
    }
}
