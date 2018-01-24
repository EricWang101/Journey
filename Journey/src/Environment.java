import java.util.ArrayList;

/**
 * The interface of an environment object that will be shown by a display object
 */
public interface Environment {
	
	/** Sets the display */
	public void setDisplay(Display display);
	
	/** Draws the vehicles in the environment by calling the appropriete draw function in the Display class */
	public void draw();
	
	/** Adds a vehicle */
	public void addVehicle(Vehicle vehicle);
	
	/** Returns the vehicles in the environment */
	public ArrayList<Vehicle> getAllVehicles();
	
	/** Returns the numberOfLanes in the environment */
	public int getLanes();
	
	/** Sets the numberOfLanes of the environment */
	public void setLanes(int numberOfLanes);
	
	/** Returns the speed limit of the environment */
	public double getSpeedLimit();
	
	/** Sets the speed limit of the environment */
	public void setSpeedLimit(double speedLimit);
	
	/** Returns the closest in-front vehicle in the same lane */
	public Vehicle nextVehicle(Vehicle behind);
	
	/** Updates the state of the environment, and specifically the behavior of the vehicles after one unit of time*/
	public void tick();
	
	/** Clears all the vehicles in the environment*/
	public void clear();
	
	/** Resets the timer */
	public void resetTimer();
	
	/** Stops the timer */
	public void stopTimer();
	
	/** Starts the timer */
	public void startTimer();
	
	/** Returns a boolean value whether or not the timer is running */ 
	public boolean isRunning();
		
}
