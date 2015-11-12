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
import ca.ubc.ece.cpen221.mp4.vehicles.Bike;
import ca.ubc.ece.cpen221.mp4.vehicles.Truck;
import ca.ubc.ece.cpen221.mp4.vehicles.Vehicles;

public class BikeAI extends AbstractVehicleAI {

    public BikeAI() {

    }

    @Override
    public Command getNextAction(ArenaWorld world, Vehicles vehicle) {
        Bike bike = (Bike) vehicle;

        HashSet<Direction> validDirections = getValidDirections((World) world, bike);

        randomMovement((World) world, bike, validDirections);

        Location newLocation = runover((World) world, bike);
        if (newLocation == null) {
            return new WaitCommand();
        } else {
            return new MoveCommand(bike, newLocation);
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
    private HashSet<Direction> getValidDirections(World world, Bike bike) {
        int x = bike.getLocation().getX();
        int y = bike.getLocation().getY();

        HashSet<Direction> validDirections = new HashSet<>();
        validDirections.add(Direction.EAST);
        validDirections.add(Direction.WEST);
        validDirections.add(Direction.NORTH);
        validDirections.add(Direction.SOUTH);

        if (x > world.getWidth() - bike.getViewRange()) {
            validDirections.remove(Direction.EAST);
        } else if (x < bike.getViewRange()) {
            validDirections.remove(Direction.WEST);
        }

        if (y > world.getHeight() - bike.getViewRange()) {
            validDirections.remove(Direction.SOUTH);
        } else if (y < bike.getViewRange()) {
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
    private void randomMovement(World world, Bike bike, HashSet<Direction> validDirections) {
        if (validDirections.contains(bike.getCurrentDirection())) {
            int randomNumber = ((int) (Math.random() * 100)) % 2;

            if (randomNumber == 1) {
                bike.accelerate();
            } else {
                bike.decelerate();
                
                if (bike.getCurrentSpeed() == bike.getMinSpeed()) {
                    bike.changeDirection(validDirections);
                }
            }

        } else {
            if (bike.getCurrentSpeed() > bike.getMinSpeed()) {
                bike.decelerate();
            } else if (bike.getCurrentSpeed() == bike.getMinSpeed()) {
                bike.changeDirection(validDirections);
            }
        }
    }

    /**
     * kills all the animals in the way of the vehicle if it is stronger or will
     * destroy the vehicle if it is hit by a stronger animal
     * 
     * @param world the world where the vehicle is located
     * @param truck the vehicle 
     * @return null if the vehicle is dead or the location of the vehicle if the
     * move is successful.
     */
    private Location runover(World world, Bike bike) {
        Location passedLocation = bike.getLocation();
        Location startingLocation = passedLocation;

        Set<Item> closeItems = world.searchSurroundings(passedLocation, bike.getCurrentSpeed());

        for (int numberOfMoves = 0; numberOfMoves < bike.getCurrentSpeed() && !bike.isDead(); numberOfMoves++) {

            Location newlocation = new Location(passedLocation, bike.getCurrentDirection());

            if (Util.isValidLocation(world, newlocation)) {
                passedLocation = newlocation;

                for (Item item : closeItems) {
                    if (item.getLocation().equals(passedLocation)) {

                        if (item.getStrength() > bike.getStrength()) {
                            bike.loseEnergy(0);
                        } else {
                            item.loseEnergy(Integer.MAX_VALUE);
                        }
                    }
                }
            } else {
                bike.loseEnergy(0);
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
