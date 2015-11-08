package ca.ubc.ece.cpen221.mp4.ai;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.vehicles.Vehicles;

public interface VehicleAI {

    public Command getNextAction(ArenaWorld world, Vehicles vehicle);

}
