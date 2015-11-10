package ca.ubc.ece.cpen221.mp4.bombs;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BlowUpCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;

public class Mine extends Bomb {
    
    public Mine(Location location) {
        super(location);
    }

    //TODO: change Image
    private final ImageIcon MINE_IMAGE = Util.loadImage("bear.gif");
    private static final int MINE_DAMAGE = 100;
    private static final int MINE_BLAST_RADIUS = 4;
    private static final int MINE_STRENGTH = 10;
    private static final int  MINE_TRIGGER_DISTANCE = 2;
    
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
    }

    @Override
    public boolean isDead() {
        return super.hasDetonated;
    }

    @Override
    public Command getNextAction(World world) {
        Iterable<Item> items = world.getItems();
        boolean shouldBlowUp = false;
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
