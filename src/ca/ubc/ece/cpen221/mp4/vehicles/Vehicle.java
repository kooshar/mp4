package ca.ubc.ece.cpen221.mp4.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.Item;

public abstract class Vehicle implements Actor, Item, VehicleInterface {
    private static final int VEHICLE_CALORIES = 0;
    private int remainingEnergy;
    
    @Override
    public abstract int getMaxSpeed();
    
    @Override
    public abstract int getMaxAcceleration();
    
    @Override
    public abstract int getMaximumTuringSpeed();
    
    @Override
    public int getPlantCalories() {
        return VEHICLE_CALORIES;
    }

    @Override
    public int getMeatCalories() {
        return VEHICLE_CALORIES;
    }

    @Override
    public abstract ImageIcon getImage();

    @Override
    public abstract String getName();

    @Override
    public abstract Location getLocation();

    @Override
    public int getStrength() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void loseEnergy(int energy) {
        remainingEnergy -= energy;
    }

    @Override
    public abstract boolean isDead();

    @Override
    public abstract int getCoolDownPeriod();

    @Override
    public Command getNextAction(World world) {
        // TODO Auto-generated method stub
        return null;
    }

}
