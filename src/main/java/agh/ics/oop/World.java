package agh.ics.oop;

public class World {
    public static void main(String[] args){
        try {
            RectangularMap map = new RectangularMap(10, 10, 0.5, 25,
                    15, 2);
            IEngine engine = new SimulationEngine(map, 8);
            engine.run();
            System.out.println(map);
        }
        catch(IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            System.exit(1);
        }
    }
}
