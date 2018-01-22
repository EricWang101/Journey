import java.awt.Color;

/**
 * A concrete class that implements the Vehicle interface; 
 * Stores data of a single car;
 * This object will be added to an environment;
 */
public class Car implements Vehicle {
	
	protected static final int carHeight = 35; // The height of the car
	private static final double topSpeed = 4.0; // The top speed of the car(Pixels per tick)
	private static final Color color = Color.MAGENTA; // The color of the car
	
	private Environment environment; // The environment the car object will be placed in
	private double position; // The position of the car on the road
	private int lane; // The lane the car is on 
	private double speed; // The current speed(Pixels per tick) 
	private double acceleration; // The acceleration of the car 
	private double brakingPower; // the braking power of the car 
	protected boolean passing = false; // Whether or not the car passed another vehicle
	private Vehicle passedVehicle; // The vehicle that was passed
	private VehicleState state = VehicleState.CONSTANT;
	
	/** The class constructor, which sets up the car properties */
	public Car(Environment env, double pos, int lane, double speed, double accel, double braking) {
		this.environment = env;
		this.position = pos;
		this.lane = lane;
		this.speed = speed;
		this.acceleration = accel;
		this.brakingPower = braking;	
	}
	
	/** Clones , and returns a new Car object */
	public Car clone() {
		return new Car(environment,position,lane,speed,acceleration,brakingPower);
	}
	
	/** Updates the behavior of  the car after one unit of time has passed */
	public void tick(Environment environment) {
		switch(this.state) {
			case CONSTANT: 
				break;
			case ACCELERATING: 
				if((speed<topSpeed) && (speed*acceleration<environment.getSpeedLimit())) { 
					if(speed < 1.0) this.speed = 1.0;
					if(speed*acceleration > environment.getSpeedLimit()) {
						this.speed = environment.getSpeedLimit();
					}else {
						speed = speed*acceleration;
					}
				}
				break;
			case BRAKING:
				if(speed > 0) {
					speed = speed*brakingPower;
				}
				break; 
			case CRASHED:
				speed = 0.0;
				return;	
		}
		position += speed; // Adds the speed of the car, which varys depending on the state to the car's position
	}

	/** Returns the state of the car */
	@Override
	public VehicleState getState() {
		return this.state;
	}
	
	@Override
	/** Sets the state of the car */
	public void setState(VehicleState state) {
		this.state = state; 
	}
	
	/**Returns the current lane of the car*/
	@Override
	public int getLane() {
		return this.lane;
	}
	
	/** Sets the lane of the car*/
	@Override
	public void setLane(int lane) {
		this.lane = lane;	
	}
	
	/** Returns the position of the car*/
	public double getPosition() {
		return this.position;
	}

	/** Returns a boolean value representing whether or not the car's state is CRASHED */
	public boolean isCrashed() {
		return state == VehicleState.CRASHED;
	}
	
	/** Returns the color of the car*/
	@Override
	public Color getColor() {
		return color;
	}

	/** Returns the height of the car*/
	@Override
	public double getHeight() {
		return carHeight;
	}
	
	/** Returns a boolean value representing whether or not the car has passed another car*/
	public boolean isPassing() {
		return this.passing;
	}

	/** Return's the top speed of a car */
	@Override
	public double getTopSpeed() {
		return this.topSpeed;
	}
	
	/** Return's the current speed of the car */
	@Override
	public double getSpeed() {
		return this.speed;
	}

	/** Sets whether or not a car has passed another vehicle */
	@Override
	public void setPassing(boolean isPassing) {
		this.passing = isPassing;
		
	}

	/** Returns the vehicle that was passed by this object */
	@Override
	public Vehicle getPassedVehicle() {
		return this.passedVehicle;
	}

	/** Sets the vehicle that was passed by this object */
	@Override
	public void setPassedVehicle(Vehicle vehicle) {
		this.passedVehicle = vehicle;
		
	}

}
