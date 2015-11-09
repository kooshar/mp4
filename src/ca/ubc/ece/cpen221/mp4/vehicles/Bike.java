package ca.ubc.ece.cpen221.mp4.vehicles;

import java.util.HashSet;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.ai.VehicleAI;
import ca.ubc.ece.cpen221.mp4.commands.Command;

public class Bike implements Vehicles {

    private int MAX_ENERGY=100;
    private int INITIAL_SPEED=1;
    private int MAX_SPEED=3;
    private int MIN_SPEED=3;
    private int ACCELERATION=3;
    private int DECELERATION=3;
    private int STRENGTH=200;
    private int VIEW_RANGE=5;
    private int COOLDOWN=3;
    private ImageIcon IMAGE=Util.loadImage("motorcycles.gif");

    private VehicleAI ai;

    private Location currentlocation;
    private int energy = MAX_ENERGY;
    private int currentSpeed = INITIAL_SPEED;
    private Direction currentDirection = Util.getRandomDirection();
    

    /**
     * Create a new {@link Bike} with an {@link AI} at
     * <code>initialLocation</code>. The <code> initialLocation </code> must be
     * valid and empty
     *
     * @param bikeAI
     *            the AI designed for Bikes
     * @param initialLocation
     *            the location where this Fox will be created
     */
    public Bike(VehicleAI bikeAI, Location initialLocation) {
        this.ai = bikeAI;
        this.currentlocation = initialLocation;

        this.energy = MAX_ENERGY;
    }
    
    @Override
    public void moveTo(Location targetLocation) {
        currentlocation = targetLocation;
    }
    
    
    @Override
    public int getMovingRange() {
        return currentSpeed;
    }

    @Override
    public ImageIcon getImage() {
        return IMAGE;
    }

    @Override
    public Location getLocation() {
        return currentlocation;
    }

    @Override
    public int getStrength() {
        return STRENGTH;
    }

    @Override
    public void loseEnergy(int energy) {
        this.energy -= MAX_ENERGY;

    }

    @Override
    public boolean isDead() {
        return energy <= 0;
    }

    @Override
    public int getPlantCalories() {
        // A Vehicle is not a plant
        return 0;
    }

    @Override
    public int getMeatCalories() {
        // A Vehicle is not an animal
        return 0;
    }

    @Override
    public int getMaxSpeed() {
        return MAX_SPEED;
    }

    @Override
    public int getCurrentSpeed() {
        return currentSpeed;
    }

    @Override
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    @Override
    public int getAcceleration() {
        return ACCELERATION;
    }

    @Override
    public int getDeceleration() {
        return DECELERATION;
    }

    @Override
    public void accelerate() {
        currentSpeed = Math.min(currentSpeed + ACCELERATION, MAX_SPEED);
    }

    @Override
    public void decelerate() {
        currentSpeed = Math.max(currentSpeed - DECELERATION, MIN_SPEED);
    }

    @Override
    public int getViewRange() {
        return VIEW_RANGE;
    }

    @Override
    public int getCoolDownPeriod() {
        return COOLDOWN;
    }

    public void changeDirection(HashSet<Direction> validDirections) {
        Direction previuosDirection = this.currentDirection;

        while (currentDirection == previuosDirection && !validDirections.contains(currentDirection)) {
            currentDirection = Util.getRandomDirection();
        }
    }

    @Override
    public Command getNextAction(World world) {
        Command nextAction = ai.getNextAction(world, this);
        return nextAction;
    }

    @Override
    public String getName() {
        return "Bike";
    }

    @Override
    public int getMinSpeed() {
        return MIN_SPEED;
    }

}
