package agh.ics.oop;

public class OptionsParser {

    public MoveDirection[] parse(String[] movements) {
        int n = movements.length;

        MoveDirection[] result = new MoveDirection[n];
        for (int k = 0; k < n; k++){
            result[k] = switch (movements[k]) {
                case "f", "forward" -> MoveDirection.FORWARD;
                case "b", "backward" -> MoveDirection.BACKWARD;
                case "r", "right" -> MoveDirection.RIGHT;
                case "l", "left" -> MoveDirection.LEFT;
                default -> throw new IllegalArgumentException(movements[k] + " is not a legal movement");
            }
            ;
        }
        return result;
    }

}

