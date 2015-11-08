package ca.ubc.ece.cpen221.mp4.ai;

import java.util.Iterator;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.vehicles.Vehicles;

public class AbstractVehicleAI implements VehicleAI {
    
    public boolean isLocationEmpty(ArenaWorld world, ArenaAnimal animal, Location location) { // returns
        // true
        // if
        // location
        // is
        // empty
        if (!Util.isValidLocation(world, location)) {
            return false;
        }
        Set<Item> possibleMoves = world.searchSurroundings(animal);
        Iterator<Item> it = possibleMoves.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.getLocation().equals(location)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Command getNextAction(ArenaWorld world, Vehicles vehicle) {
        return new WaitCommand();
    }

}
