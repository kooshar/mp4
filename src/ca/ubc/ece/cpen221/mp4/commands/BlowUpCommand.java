package ca.ubc.ece.cpen221.mp4.commands;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.bombs.*;
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
    
    public BlowUpCommand(Location location, int radius, int damage){
        this.location = location;
        this.radius = radius;
        this.damage = damage;
    }


    @Override
    public void execute(World world) throws InvalidCommandException {
        //TODO blowup the bomb
    }

}
