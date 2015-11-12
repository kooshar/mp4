package ca.ubc.ece.cpen221.mp4.ai;

import java.util.ArrayList;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

/**
 * Your Sheep AI.
 */
public class SheepAI extends AbstractAI {

    private final int BREEDING_CONS = 5;

    public SheepAI() {
    }

    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        ArrayList<Direction> safeDirections = getSafeDirections(world, animal);
        ArrayList<Direction> emptyDirections = getEmptyDirections(world, animal);
        ArrayList<Direction> emptySafeDirections = getEmptySafeDirections(safeDirections, emptyDirections);
        Direction foodDirection = getFoodDirection(world, animal, emptyDirections);


        if (safeDirections.size() == 4) {
            if (getFood(world, animal) != null) {
                return new EatCommand(animal, getFood(world, animal));

            } else if (animal.getEnergy() > BREEDING_CONS * animal.getMinimumBreedingEnergy()
                    && getBreedingLocation(world, animal) != null) {
                return new BreedCommand(animal, getBreedingLocation(world, animal));

            } else if (foodDirection != null) {
                Location newLocation = new Location(animal.getLocation(), foodDirection);
                return new MoveCommand(animal, newLocation);

            } else if(emptyDirections.size()!=0){
                Direction randomDirection = emptyDirections.get((int) (Math.random() * emptyDirections.size()));
                Location newLocation = new Location(animal.getLocation(), randomDirection);
                return new MoveCommand(animal, newLocation);
            }else{
                return new WaitCommand();

            }

        } else {
            if (getFood(world, animal) != null) {
                return new EatCommand(animal, getFood(world, animal));

            } else if (emptySafeDirections.size() != 0) {
                Direction randomDirection = emptySafeDirections.get((int) (Math.random() * emptySafeDirections.size()));
                Location newLocation = new Location(animal.getLocation(), randomDirection);
                return new MoveCommand(animal, newLocation);

            } else {
                return new WaitCommand();
            }
        }

    }

    private ArrayList<Direction> getSafeDirections(ArenaWorld world, ArenaAnimal animal) {
        ArrayList<Direction> safeDirections = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            if (Util.isValidLocation(world, new Location(animal.getLocation(), direction))
                    && Util.isLocationEmpty((World) world, new Location(animal.getLocation(), direction))) {

                safeDirections.add(direction);
            }
        }

        Set<Item> souroundingItems = world.searchSurroundings(animal);
        for (Item items : souroundingItems) {
            if (items.getName().equals("fox")) {
                if (items.getLocation().getY() > animal.getLocation().getY()) {
                    safeDirections.remove(Direction.SOUTH);
                } else {
                    safeDirections.remove(Direction.NORTH);
                }

                if (items.getLocation().getX() > animal.getLocation().getX()) {
                    safeDirections.remove(Direction.EAST);
                } else {
                    safeDirections.remove(Direction.WEST);
                }
            }
        }

        return safeDirections;
    }

    private ArrayList<Direction> getEmptyDirections(ArenaWorld world, ArenaAnimal animal) {
        ArrayList<Direction> emptyDirections = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            if (Util.isValidLocation(world, new Location(animal.getLocation(), direction))
                    && Util.isLocationEmpty((World) world, new Location(animal.getLocation(), direction))) {

                emptyDirections.add(direction);
            }
        }

        return emptyDirections;
    }

    private Location getBreedingLocation(ArenaWorld world, ArenaAnimal animal) {
        for (Direction direction : Direction.values()) {
            Location newLocation = new Location(animal.getLocation(), direction);

            if (Util.isValidLocation(world, newLocation) && Util.isLocationEmpty((World) world, newLocation)) {
                return newLocation;
            }
        }
        return null;
    }

    private Item getFood(ArenaWorld world, ArenaAnimal animal) {
        Set<Item> itemsAround = world.searchSurroundings(animal);

        for (Item item : itemsAround) {
            if (item.getName().equals("grass") && item.getLocation().getDistance(animal.getLocation()) == 1) {
                return item;
            }
        }

        return null;
    }

    private ArrayList<Direction> getEmptySafeDirections(ArrayList<Direction> safeDirections,
            ArrayList<Direction> emptyDirections) {
        ArrayList<Direction> emptySafeDirections = new ArrayList<>();

        for (Direction direction : safeDirections) {
            if (emptyDirections.contains(direction)) {
                emptySafeDirections.add(direction);
            }
        }
        return emptySafeDirections;
    }

    private Direction getFoodDirection(ArenaWorld world, ArenaAnimal animal, ArrayList<Direction> emptyDirections) {
        Set<Item> itemsAround = world.searchSurroundings(animal);

        for (Item item : itemsAround) {
            if (item.getName().equals("grass")) {
                int itemX = item.getLocation().getX();
                int itemY = item.getLocation().getY();

                if (itemX > animal.getLocation().getX() && emptyDirections.contains(Direction.EAST)) {
                    return Direction.EAST;
                } else if (itemX < animal.getLocation().getX() && emptyDirections.contains(Direction.WEST)) {
                    return Direction.WEST;
                }

                if (itemY > animal.getLocation().getY() && emptyDirections.contains(Direction.SOUTH)) {
                    return Direction.SOUTH;
                } else if (itemY < animal.getLocation().getY() && emptyDirections.contains(Direction.NORTH)) {
                    return Direction.NORTH;
                }
            }
        }
        return null;
    }
}
