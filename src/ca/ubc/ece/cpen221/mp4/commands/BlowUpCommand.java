package ca.ubc.ece.cpen221.mp4.commands;

import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.bombs.*;
import java.util.Set;

public class BlowUpCommand implements Command {
    
    //the bomb that will blowup
    BombItem bomb;
    
    public BlowUpCommand(BombItem bomb){
        this.bomb = bomb;
    }
    
    @Override
    public void execute(World world) throws InvalidCommandException {
        //blowup the bomb
        bomb.blowup(world);
    }

}
