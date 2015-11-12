/**
 * 
 */
/**
 * @author koosha
 *
 */
package ca.ubc.ece.cpen221.mp4.vehicles;

import java.util.HashSet;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

public interface Vehicles extends MoveableItem, Actor {

    /**
     * Returns the maximum speed that this vehicle can move with. For example, a
     * {@link Vehicles} with Maximum Speed 3 can move with speeds 0,1,2,3.
     *
     * @return the maximum moving speed
     */
    int getMaxSpeed();
    
    /**
     * Returns the minimum speed that this vehicle can turn with. 
     *
     * @return the minimum turning speed
     */
    int getMinSpeed();
    
    /**
     * Returns the strength of  this vehicle 
     *
     * @return the strength of the vehicle
     */
    int getStrength();

    /**
     * Returns the current speed that this vehicle can move with. For example, a
     * {@link Vehicles} with current Speed 1 can move one box in each step.
     *
     * @return the current moving speed
     */
    int getCurrentSpeed();

    /**
     * Returns the current direction that this vehicle can move with.
     *
     * @return the current moving direction
     */
    Direction getCurrentDirection();

    /**
     * Returns the acceleration rate of this vehicle.
     *
     * @return the current moving direction
     */
    int getAcceleration();

    /**
     * Returns the deceleration rate of this vehicle.
     *
     * @return the current moving direction
     */
    int getDeceleration();

    /**
     * Adds one Acceleration to the current speed that the vehicle is moving
     * with if the vehicle is not going with the max energy.
     *
     * @modifies the current moving speed
     */
    void accelerate();

    /**
     * subtracts one Deceleration from the current speed that the vehicle is
     * moving with if the vehicle is going with a speed more than 1
     *
     * @modifies the current moving speed
     */
    void decelerate();

    /**
     * Returns the range of the animal's vision. The range is measured in
     * Manhattan Distance, for example, if an animal has view range of 2, then
     * it can see all valid locations in the rectangle
     * {(x-2,y-2),(x+2,y-2),(x-2,y+2),(x+2,y+2)}, where (x,y) are the
     * coordinates of its current location.
     *
     * @return the view range of this animal
     */
    int getViewRange();
    
    /**
     * Returns a random direction from the validDirections. validDirections
     * should not be empty
     * 
     * @param validDirections the valid directions to choose from
     */
    public void changeDirection(HashSet<Direction> validDirections);
}