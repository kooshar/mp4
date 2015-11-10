package ca.ubc.ece.cpen221.mp4.commands;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.bombs.*;
import ca.ubc.ece.cpen221.mp4.items.Item;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
/**
 * @author curtishuebner
 * A blow up command is a {@link Command} that simulates a BombItem
 * blowing up. This is usually followed by d
 *
 */
public class BlowUpCommand implements Command {
    
    //the bomb that will blowup
    Location location;
    int radius;
    int damage;
    /**
     * Instances a fresh blow up command that will call the
     * lose energy function on anything within the euclidean radius
     * and with an argument equal to the damage.
     * 
     * 
     * @param location is a valid-non null location
     * @param radius is a positive integer
     * @param damage is an integer, positive numbers mean that the surrounding
     * items take damage
     */
    public BlowUpCommand(Location location, int radius, int damage){
        this.location = location;
        this.radius = radius;
        this.damage = damage;
    }

    @Override
    public void execute(World world) throws InvalidCommandException {
        Iterable<Item> items = world.getItems();
        List<Item> targets = new LinkedList<Item>();
        
        //select all items in the given blast radius
        for (Item item : items) {
            if(Util.euclideanDistance(item.getLocation(), this.location) < radius)
                targets.add(item);
        }
        
        //Inflict damage on targets
        for (Item item : targets){
            item.loseEnergy(damage);
        }
        
    }

}
