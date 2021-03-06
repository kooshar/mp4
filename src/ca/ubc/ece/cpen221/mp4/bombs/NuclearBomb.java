package ca.ubc.ece.cpen221.mp4.bombs;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BlowUpCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;


/**
 * 
 * @author curtishuebner
 * The Nuclear Bomb is intended to make all items on the board lose all of there energy.
 */
public class NuclearBomb extends Bomb {
    
    private final ImageIcon NUCLEAR_BOMB_IMAGE = Util.loadImage("Nuke.gif");
    private static final int NUCLEAR_BOMB_DAMAGE = Integer.MAX_VALUE;
    private static final int NUCLEAR_BOMB_BLAST_RADIUS = Integer.MAX_VALUE;
    private int remainingFuse;
    
    public NuclearBomb(Location location,int fuseDuration) {
        super(location);
        super.hasDetonated = false;
        remainingFuse = fuseDuration;
    }

    @Override
    public ImageIcon getImage() {
        return NUCLEAR_BOMB_IMAGE;
    }

    @Override
    public String getName() {
        return "Nuclear Bomb";
    }

    @Override
    public int getStrength() {
        return Integer.MAX_VALUE;
    }

    @Override
    //Don't lose energy
    public void loseEnergy(int energy) {}

    @Override
    public boolean isDead() {
        return super.hasDetonated;
    }
    
    /**
     * Issues a blow up command that destroys everything in the world. 
     */
    @Override
    public Command getNextAction(World world) {
        this.remainingFuse--;
        if (this.remainingFuse == 0){
            hasDetonated = true;
            return new BlowUpCommand(this.getLocation(), NUCLEAR_BOMB_BLAST_RADIUS, NUCLEAR_BOMB_DAMAGE);
        }
        else{
            return new WaitCommand();
        }
    }

}
