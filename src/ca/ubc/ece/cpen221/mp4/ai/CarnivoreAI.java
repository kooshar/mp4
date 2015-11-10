package ca.ubc.ece.cpen221.mp4.ai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

public class CarnivoreAI extends ArenaAnimalAI {
    
    private static final int ENERGYTRESHOLD = 0;
    private static final int BREEDTHRESHOLD = 10;
    private static final double HISTERISIS = 0.1;
    
    private Direction previousDirection = Direction.NORTH;
    
    public CarnivoreAI(int energy) {
        super(energy);
    }
    
    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        
        //check to see if the animal can breed
        if(animal.getMaxEnergy() - animal.getEnergy() < BREEDTHRESHOLD &&
                animal.getEnergy() > animal.getMinimumBreedingEnergy() ||
           animal.getEnergy() == animal.getMaxEnergy()){
            Location newLocation = getEmptyAdjacentLocation(world,animal);
            if (newLocation != null){
                return new BreedCommand(animal,newLocation);
            }
        }
        
        //filter edible things
        Set<Item> items = world.searchSurroundings(animal);
        Set<Item> edibleThings = new HashSet<Item>();
        for (Item item: items){
            if(item.getMeatCalories() > ENERGYTRESHOLD){
                edibleThings.add(item);
            }
        }
        
        //findNearestPrey
        Item nearestPrey = null;
        for (Item item : edibleThings){
            
            if (nearestPrey == null &&
               animal.getLocation().getDistance(item.getLocation()) > 0 &&
               animal.getStrength() > item.getStrength()){
                nearestPrey = item;
            }
            if (nearestPrey != null){
                int dnearest = animal.getLocation().getDistance(nearestPrey.getLocation());
                int dcandidate = animal.getLocation().getDistance(item.getLocation());
                if (dnearest > dcandidate && 
                   animal.getLocation().getDistance(item.getLocation()) > 0 &&
                   animal.getStrength() > item.getStrength()){
                    nearestPrey = item;
                }
            }
        }
        
        AbstractAI ai = new AbstractAI();
        
        //if prey is in sight
        if (nearestPrey != null){
            Direction direction = Util.getDirectionTowards(animal.getLocation(), nearestPrey.getLocation());
            int distance = animal.getLocation().getDistance(nearestPrey.getLocation());
            //if prey is adjacent
            if (distance == 1 && nearestPrey.getStrength() < animal.getStrength()){
                return new EatCommand(animal,nearestPrey);
            }
            
            List<Direction> directions = new ArrayList<Direction>();
            if (nearestPrey.getStrength() > animal.getStrength()){
                directions.add(direction);
            }
            
            //add other directions to check

            
            for(Direction d : directions){
                previousDirection = d;
                Location newLocation = new Location(animal.getLocation(),d);
                if (Util.isValidLocation(world, newLocation) && ai.isLocationEmpty(world, animal, newLocation)){
                    return new MoveCommand(animal,newLocation);
                }
            }
        }
        
        //move in same direction as before with given probability
        if (Math.random() > HISTERISIS){
            Location newLocation = new Location(animal.getLocation(),previousDirection);
            if (Util.isValidLocation(world, newLocation) && ai.isLocationEmpty(world, animal, newLocation)){
                return new MoveCommand(animal,newLocation);
            }
        }
        
        //move randomly
        for (int i =  0; i < 10; i++){
            Direction direction = Util.getRandomDirection();
            Location newLocation = new Location(animal.getLocation(),direction);
            if (Util.isValidLocation(world, newLocation) && ai.isLocationEmpty(world, animal, newLocation)){
                previousDirection = direction;
                return new MoveCommand(animal,newLocation);
            }
        }
            
        return new WaitCommand();
    }
    /**
     * returns the difference between a and b.
     * @param a
     * @param b
     * @return
     */
    private static Location getDeltas(Location a, Location b){
        return new Location(a.getX()-b.getX(), a.getY()-b.getY());
        
    }
    private static Location getEmptyAdjacentLocation(ArenaWorld world, ArenaAnimal animal) {
        int posX = animal.getLocation().getX();
        int posY = animal.getLocation().getY();
        
        AbstractAI ai = new AbstractAI();
        
        List<Location> locations = new ArrayList<Location>();
        locations.add(new Location(posX+1,posY));
        locations.add(new Location(posX-1,posY));
        locations.add(new Location(posX,posY+1));
        locations.add(new Location(posX,posY-1));
        for (Location testLocation : locations)
            if (ai.isLocationEmpty(world, animal, testLocation)){
                return testLocation;
            }
        
        return null;
    }

}
