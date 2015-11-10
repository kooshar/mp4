package ca.ubc.ece.cpen221.mp4.bombs;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BlowUpCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;

/**
 * The fire bomb deals a bit of damage to everything near it for an extended duration of time.
 * @author curtishuebner
 *
 */
public class FireBomb extends Bomb {
    
    private static final ImageIcon FIRE_BOMB_IMAGE = Util.loadImage("Mine.gif");
    private static final ImageIcon FIRE_IMAGE = Util.loadImage("Fire.gif");
    private static final int FIRE_BOMB_DAMAGE = 1;
    private static final int FIRE_BOMB_BLAST_RADIUS = 2;
    private static final int FIRE_BOMB_STRENGTH = 20;
    private static final int FIRE_BOMB_DURATION = 50;
    
    private boolean shouldBlowUp = false;
    private int burntime = 0;

    public FireBomb(Location location) {
        super(location);
    }

    @Override
    public ImageIcon getImage() {
        if (burntime > 0){
            return FIRE_IMAGE;
        } else{
            return FIRE_BOMB_IMAGE;
        }
    }

    @Override
    public String getName() {
        return "Fire Bomb";
    }

    @Override
    public int getStrength() {
        return FIRE_BOMB_STRENGTH;
    }

    @Override
    public void loseEnergy(int energy) {
        if (energy > FIRE_BOMB_STRENGTH){
            System.out.println("bla");
            shouldBlowUp = true;
        }
            
            

    }

    @Override
    public boolean isDead() {
        return super.hasDetonated;
    }

    @Override
    /*
     * Burning is simulated by issuing several blow up commands in series with small parameters
     * The duration is kept track of by incrementing the burntime variable
     * (non-Javadoc)
     * @see ca.ubc.ece.cpen221.mp4.bombs.Bomb#getNextAction(ca.ubc.ece.cpen221.mp4.World)
     */
    public Command getNextAction(World world) {
        if (burntime == FIRE_BOMB_DURATION)
            super.hasDetonated = true;
            
        if (shouldBlowUp == true && super.hasDetonated == false){
            this.burntime++;
            return new BlowUpCommand(this.getLocation(),FIRE_BOMB_BLAST_RADIUS,FIRE_BOMB_DAMAGE);
        }
                
        return new WaitCommand();
    }

}
