package ca.ubc.ece.cpen221.mp4.bombs;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.World;

public interface BombItem extends Actor {
    public void blowup(World world);
}
