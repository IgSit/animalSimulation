package agh.ics.oop;

import java.util.ArrayList;

/**
 * The interface responsible for managing the moves of the animals.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo
 */
public interface IEngine {

    int getMoveDelay();

    ArrayList<IEngineMoveObserver> getObservers();

    void addObserver(IEngineMoveObserver observer);
}