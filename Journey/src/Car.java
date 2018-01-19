import java.awt.Color;

public class Car implements Vehicle {
	
	private static double topSpeed = 1.0;
	private static Color color = Color.MAGENTA;
	protected static final int carHeight = 35;
	
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
	
	public Car clone() {
		return new Car(environment,position,lane,speed,acceleration,brakingPower);
	}
	

	public void tick(Environment environment) {
		switch(state) {
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
				if(speed > 0) speed = speed*brakingPower;
			case CRASHED:
				speed = 0.0;
				return;	
		}
		position += speed;
			
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
