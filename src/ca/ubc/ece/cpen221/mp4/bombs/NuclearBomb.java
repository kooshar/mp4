package ca.ubc.ece.cpen221.mp4.bombs;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BlowUpCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;

public class NuclearBomb extends Bomb {
    
    //TODO: change Image
    private final ImageIcon NUCLEAR_BOMB_IMAGE = Util.loadImage("unknown.gif");
    private static final int NUCLEAR_BOMB_DAMAGE = Integer.MAX_VALUE;
    private static final int NUCLEAR_BOMB_BLAST_RADIUS = Integer.MAX_VALUE;
    private int remainingFuse;
    private boolean hasDetonated;
    
    public NuclearBomb(Location location,int fuseDuration) {
        super(location);
        hasDetonated = false;
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
        return hasDetonated;
    }

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
