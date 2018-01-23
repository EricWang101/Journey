import java.awt.Color;

/**
 * The interface of a vehicle object that will be placed in an Environment
 */
public interface Vehicle {
	
	/** Updates the behavior of the vehicle after one unit of time*/
	public void tick(Environment environment) ; 
	
	/**Returns the state of the vehicle*/
	public VehicleState getState();
	
	/**Sets the state of the vehicle*/
	public void setState(VehicleState state); 
	
	/**Returns the current lane of the vehicle*/
	public int getLane();
	
	/** Sets the lane of the vehicle*/
	public void setLane(int lane);
	
	/** Returns the position of the vehicle*/
	public double getPosition();
	
	/** Returns a boolean value representing whether or not the vehicle's state is CRASHED */
	public boolean isCrashed();
	
	/** Returns the color of the vehicle*/
	public Color getColor();
	
	/** Returns the height of the vehicle*/
	public double getHeight();
	
	/** Returns a boolean value representing whether or not the car has passed another vehicle*/
	public boolean isPassing();
	
	/** Return's the top speed of a vehicle */
	public double getTopSpeed();
	
	/** Return's the current speed of the vehicle */
	public double getSpeed();
	
	/** Sets whether or not a vehicle has passed another vehicle */
	public void setPassing(boolean isPassing);
	
	/** Returns the vehicle that was passed by the object */
	public Vehicle getPassedVehicle(); 
	
	/** Sets the vehicle that was passed by the object */
	public void setPassedVehicle(Vehicle vehicle);
		
		
}
