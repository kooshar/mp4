package ca.ubc.ece.cpen221.mp4.bombs;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.Item;


/**
 * 
 * @author curtishuebner
 * The bomb class encompasses all bombs
 */
public abstract class Bomb implements Actor, Item {
    
    private final int BOMB_CALORIES = 0;
    private final int BOMB_COOLDOWN_TIME = 1;
    protected boolean hasDetonated;
    
    //Bombs are not allowed to move
    private final Location location;
    
    public Bomb(Location location){
        this.location = location;
    }
     
    @Override
    public int getPlantCalories() {
        return BOMB_CALORIES;
    }

    @Override
    public int getMeatCalories() {
        return BOMB_CALORIES;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }
    
    @Override
    public abstract ImageIcon getImage();

    @Override
    public abstract String getName();
    
    @Override
    public abstract int getStrength();

    @Override
    public abstract void loseEnergy(int energy);

    @Override
    public abstract boolean isDead();

    @Override
    public int getCoolDownPeriod() {
        return BOMB_COOLDOWN_TIME;
    }

    @Override
    public abstract Command getNextAction(World world);

}
