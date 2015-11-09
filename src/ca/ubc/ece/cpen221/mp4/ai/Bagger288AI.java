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
import ca.ubc.ece.cpen221.mp4.vehicles.Bagger288;
import ca.ubc.ece.cpen221.mp4.vehicles.Truck;
import ca.ubc.ece.cpen221.mp4.vehicles.Vehicles;

public class Bagger288AI extends AbstractVehicleAI {

    public Bagger288AI() {

    }

    @Override
    public Command getNextAction(ArenaWorld world, Vehicles vehicle) {
        Bagger288 bagger288 = (Bagger288) vehicle;

        HashSet<Direction> validDirections = getValidDirections((World) world, bagger288);

        randomMovement((World) world, bagger288, validDirections);

        Location newLocation = runover((World) world, bagger288);
        if (newLocation == null) {
            return new WaitCommand();
        } else {
            return new MoveCommand(bagger288, newLocation);
        }
    }

    private HashSet<Direction> getValidDirections(World world, Bagger288 bagger288) {
        int bagger288x = bagger288.getLocation().getX();
        int bagger288y = bagger288.getLocation().getY();

        HashSet<Direction> validDirections = new HashSet<>();
        validDirections.add(Direction.EAST);
        validDirections.add(Direction.WEST);
        validDirections.add(Direction.NORTH);
        validDirections.add(Direction.SOUTH);

        if (bagger288x > world.getWidth() - bagger288.getViewRange()) {
            validDirections.remove(Direction.EAST);
        } else if (bagger288x < bagger288.getViewRange()) {
            validDirections.remove(Direction.WEST);
        }

        if (bagger288y > world.getHeight() - bagger288.getViewRange()) {
            validDirections.remove(Direction.SOUTH);
        } else if (bagger288y < bagger288.getViewRange()) {
            validDirections.remove(Direction.NORTH);
        }

        return validDirections;

    }

    private void randomMovement(World world, Bagger288 bagger288, HashSet<Direction> validDirections) {
        if (validDirections.contains(bagger288.getCurrentDirection())) {
            int randomNumber = ((int) (Math.random() * 100)) % 2;

            if (randomNumber == 1) {
                bagger288.accelerate();
            } else {
                if (bagger288.getCurrentSpeed() > bagger288.getMinSpeed()) {
                    bagger288.decelerate();
                } else if (bagger288.getCurrentSpeed() == bagger288.getMinSpeed()) {
                    bagger288.changeDirection(validDirections);
                }
            }

        } else {
            if (bagger288.getCurrentSpeed() > bagger288.getMinSpeed()) {
                bagger288.decelerate();
            } else if (bagger288.getCurrentSpeed() == bagger288.getMinSpeed()) {
                bagger288.changeDirection(validDirections);
            }
        }
    }

    /**
     * kills all the animals in the way of the vehicle if it is stronger or will
     * destroy the vehicle if it is hit
     * 
     * @param world
     * @return
     * @return
     */
    private Location runover(World world, Bagger288 bagger288) {
        Location passedLocation = bagger288.getLocation();
        Location startingLocation = passedLocation;

        Set<Item> closeItems = world.searchSurroundings(passedLocation, bagger288.getCurrentSpeed());

        for (int numberOfMoves = 0; numberOfMoves < bagger288.getCurrentSpeed() && !bagger288.isDead(); numberOfMoves++) {

            Location newlocation = new Location(passedLocation, bagger288.getCurrentDirection());

            if (Util.isValidLocation(world, newlocation)) {
                passedLocation = newlocation;

                for (Item item : closeItems) {
                    if (item.getLocation().equals(passedLocation)) {

                        if (item.getStrength() > bagger288.getStrength()) {
                            bagger288.loseEnergy(0);
                        } else {
                            item.loseEnergy(Integer.MAX_VALUE);
                        }
                    }
                }
            } else {
                bagger288.loseEnergy(0);
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
