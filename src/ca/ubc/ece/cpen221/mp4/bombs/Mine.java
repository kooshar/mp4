package ca.ubc.ece.cpen221.mp4.bombs;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BlowUpCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;

/**
 * The mine is an item that issues a blow up command
 * when an item with more strength than it does moves near it. 
 * @author curtishuebner
 */
public class Mine extends Bomb {
    
    public Mine(Location location) {
        super(location);
    }

    private final ImageIcon MINE_IMAGE = Util.loadImage("Mine.gif");
    private static final int MINE_DAMAGE = 100;
    private static final int MINE_BLAST_RADIUS = 4;
    private static final int MINE_STRENGTH = 10;
    private static final int  MINE_TRIGGER_DISTANCE = 2;
    private boolean shouldBlowUp = false;
    
    @Override
    public ImageIcon getImage() {
        return MINE_IMAGE;
    }

    @Override
    public String getName() {
        return "Mine";
    }

    @Override
    public int getStrength() {
        return MINE_STRENGTH;
    }

    @Override
    //mines don't lose energy
    public void loseEnergy(int energy) {
        if (energy > MINE_STRENGTH)
            shouldBlowUp = true;
    }

    @Override
    public boolean isDead() {
        return super.hasDetonated;
    }

    @Override
    public Command getNextAction(World world) {
        Iterable<Item> items = world.getItems();
        for (Item item : items){
            if (item.getStrength() > MINE_STRENGTH &&
                this.getLocation().getDistance(item.getLocation()) < MINE_TRIGGER_DISTANCE){
                shouldBlowUp = true;
            }
        }
        if (shouldBlowUp){
            super.hasDetonated = true;
            return new BlowUpCommand(this.getLocation(),MINE_BLAST_RADIUS,MINE_STRENGTH);
        }
        
        return new WaitCommand();
    }

}
