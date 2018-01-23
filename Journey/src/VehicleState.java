/**
 * The different states allowed for a vehicle 
 */
public enum VehicleState {
	
	/**Vehicle is traveling at a constant rate*/
	CONSTANT,
	
	/**Vehicle is traveling at an increasing rate*/
	ACCELERATING, 
	
	/**Vehicle is traveling at a decreasing rate*/
	BRAKING, 
	
	/**Vehicle has collided with another vehicle, and has a speed of zero*/
	CRASHED;

}
