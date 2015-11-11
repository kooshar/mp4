package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.ai.CarnivoreAI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public class Tiger extends AbstractArenaAnimal {
    
    private static final int INITIAL_ENERGY = 1000;
    private static final int MAX_ENERGY = 1120;
    private static final int STRENGTH = 400;
    private static final int VIEW_RANGE = 10;
    private static final int MIN_BREEDING_ENERGY = 400;
    private static final int COOLDOWN = 2;
    private static final ImageIcon TigerImage = Util.loadImage("tiger.gif");
    
    public Tiger(AI ai, Location location) {
        super.setCOOLDOWN(COOLDOWN);
        super.setINITIAL_ENERGY(INITIAL_ENERGY);
        super.setEnergy(INITIAL_ENERGY);
        super.setMAX_ENERGY(MAX_ENERGY);
        super.setMIN_BREEDING_ENERGY(MIN_BREEDING_ENERGY);
        super.setSTRENGTH(STRENGTH);
        super.setVIEW_RANGE(VIEW_RANGE);
        super.setAI(ai);
        super.setLocation(location);
    }

    @Override
    public LivingItem breed() {
        Tiger child = new Tiger(new CarnivoreAI(this.getEnergy()/2), this.getLocation());
        child.setEnergy(this.getEnergy()/2);
        this.setEnergy(this.getEnergy()/2);
        return child;
    }

    @Override
    public String getName() {
        return "Tiger";
    }
    
    
    @Override
    public ImageIcon getImage(){
        return TigerImage;
        
    }

}
