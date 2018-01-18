import java.awt.Color;

public class Car implements Vehicle {
	
	private static double topSpeed = 15.0;
	private static Color color = Color.MAGENTA;
	//protected static int length = 40;
	
	private Environment environment; 
	private double position;
	private int lane;
	private double speed; 
	private double acceleration; 
	private double brakingPower; 
	private VehicleState state = VehicleState.CONSTANT;
	
	
	public Car(Environment env, double pos, int lane, double speed, double accel, double braking) {
		this.environment = env;
		this.position = pos;
		this.lane = lane;
		this.speed = speed;
		this.acceleration = accel;
		this.brakingPower = braking;
		

	}
	@Override
	public void tick() {
			
	}

	@Override
	public VehicleState getState() {
		return this.state;
	}

	@Override
	public void setState(VehicleState state) {
		this.state = state; 
	}

	@Override
	public int getLane() {
		return this.lane;
	}

	@Override
	public void setLane(int lane) {
		this.lane = lane;	
	}
	
	public double getPosition() {
		return this.position;
	}

	
	public boolean isCrashed() {
		return state == VehicleState.CRASHED;
	}

	@Override
	public Color getColor() {
		return color;
		
	}

}
