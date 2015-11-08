package ca.ubc.ece.cpen221.mp4.vehicles;


/**
 * 
 * @author curtishuebner
 * An interface for the vehicle AI
 */
public interface VehicleInterface {
    
    /**
     * 
     * @return The Maximum Speed of the vehicle
     */
    public int getMaxSpeed();
    
    /**
     * 
     * @return The maximum acceleration of the vehicle
     */
    public int getMaxAcceleration();
    
    /**
     * 
     * @return The maximum speed at which the vehicle can turn
     */
    public int getMaximumTuringSpeed();
}
