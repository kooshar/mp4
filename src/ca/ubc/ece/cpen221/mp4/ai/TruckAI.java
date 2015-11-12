package ca.ubc.ece.cpen221.mp4.ai;

import java.util.HashSet;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.vehicles.Truck;
import ca.ubc.ece.cpen221.mp4.vehicles.Vehicles;

public class TruckAI extends AbstractVehicleAI {

    public TruckAI() {

    }

    @Override
    public Command getNextAction(ArenaWorld world, Vehicles vehicle) {
        Truck truck = (Truck) vehicle;

        HashSet<Direction> validDirections = getValidDirections((World) world, truck);

        randomMovement((World) world, truck, validDirections);

        Location newLocation = runover((World) world, truck);
        if (newLocation == null) {
            return new WaitCommand();
        } else {
            return new MoveCommand(truck, newLocation);
        }
    }
    
    /**
     * Return the HashSet of valid direction for the vehicle to choose from(directions 
     * that are not that close to the walls and allow for a safe turn around before a 
     * hit)
     * 
     * @param world the world where the vehicle is located
     * @param bagger288 the vehicle 
     * @return the HashSeet of valid Directions
     */
    private HashSet<Direction> getValidDirections(World world, Truck truck) {
        int x = truck.getLocation().getX();
        int y = truck.getLocation().getY();

        HashSet<Direction> validDirections = new HashSet<>();
        validDirections.add(Direction.EAST);
        validDirections.add(Direction.WEST);
        validDirections.add(Direction.NORTH);
        validDirections.add(Direction.SOUTH);

        if (x > world.getWidth() - truck.getViewRange()) {
            validDirections.remove(Direction.EAST);
        } else if (x < truck.getViewRange()) {
            validDirections.remove(Direction.WEST);
        }

        if (y >= world.getHeight() - truck.getViewRange()) {
            validDirections.remove(Direction.SOUTH);
        } else if (y <= truck.getViewRange()) {
            validDirections.remove(Direction.NORTH);
        }

        return validDirections;

    }

    /**
     * decides a movement for the vehicle. the movement can be either acceleration
     * deceleration or change in the direction of speed depending on the speed an where
     * the object is located in the world
     * 
     * @param world the world where the vehicle is located
     * @param bagger288 the vehicle to choose a movement for
     * @param validDirections directions that are not close to the wall
     */
    private void randomMovement(World world, Truck truck, HashSet<Direction> validDirections) {
        if (validDirections.contains(truck.getCurrentDirection())) {
            int randomNumber = ((int) (Math.random() * 100)) % 2;

            if (randomNumber == 1) {
                truck.accelerate();
            } else {
                truck.decelerate();
                
                if (truck.getCurrentSpeed() == truck.getMinSpeed()) {
                    truck.changeDirection(validDirections);
                }
            }

        } else {
            if (truck.getCurrentSpeed() > truck.getMinSpeed()) {
                truck.decelerate();
            } else if (truck.getCurrentSpeed() == truck.getMinSpeed()) {
                truck.changeDirection(validDirections);
            }
        }
    }

    /**
     * kills all the animals in the way of the vehicle if it is stronger or will
     * destroy the vehicle if it is hit by a stronger animal
     * 
     * @param world
     *            the world where the vehicle is located
     * @param truck
     *            the vehicle
     * @return null if the vehicle is dead or the location of the vehicle if the
     *         move is successful.
     */
    private Location runover(World world, Truck truck) {
        Location passedLocation = truck.getLocation();
        Location startingLocation = passedLocation;

        Set<Item> closeItems = world.searchSurroundings(passedLocation, truck.getCurrentSpeed());

        for (int numberOfMoves = 0; numberOfMoves < truck.getCurrentSpeed() && !truck.isDead(); numberOfMoves++) {

            Location newlocation = new Location(passedLocation, truck.getCurrentDirection());

            if (Util.isValidLocation(world, newlocation)) {
                passedLocation = newlocation;

                for (Item item : closeItems) {
                    if (item.getLocation().equals(passedLocation)) {

                        if (item.getStrength() > truck.getStrength()) {
                            truck.loseEnergy(0);
                        } else {
                            item.loseEnergy(Integer.MAX_VALUE);
                        }
                    }
                }
            } else {
                truck.loseEnergy(0);
                break;
            }

        }
        if (passedLocation.equals(startingLocation)) {
            return null;
        } else {
            return passedLocation;
        }
    }
}
