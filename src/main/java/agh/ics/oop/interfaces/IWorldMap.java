package agh.ics.oop.interfaces;

import agh.ics.oop.map.Vector2d;
import agh.ics.oop.mapObjects.Animal;
import agh.ics.oop.mapObjects.MapObject;

/**
 * The interface responsible for interacting with the agh.ics.oop.map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo
 */
public interface IWorldMap {
    /**
     * Indicate if any object can move to the given position.
     *
     * @param position The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    boolean canMoveTo(Vector2d position);

    /**
     * Place a animal on the agh.ics.oop.map.
     *
     * @param animal The animal to place on the agh.ics.oop.map.
     * @return True if the animal was placed. The animal cannot be placed if the agh.ics.oop.map is already occupied.
     */
    boolean place(Animal animal);

    /**
     * Return true if given position on the agh.ics.oop.map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an object at a given position.
     *
     * @param position The position of the object.
     * @return Object or null if the position is not occupied.
     */
    MapObject objectAt(Vector2d position);
}