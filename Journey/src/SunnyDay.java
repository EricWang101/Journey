import java.util.ArrayList;

public class SunnyDay implements Environment {
	
	private static double speedLimit = 10.0;
	
	private static final double carHeight = 40;// CHANGE LATER 
	
	private ArrayList<Vehicle> totalVehicles = new ArrayList<Vehicle>();
	private Display display; 
	
	private static int numberOfLanes = 4;


	@Override
	public void setDisplay(Display display) {
		this.display = display;
		
	}

	@Override
	public void draw() {
		for(Vehicle v: this.totalVehicles) {
			if(v instanceof Car) {
				display.drawCar((int) v.getPosition(), v.getLane(), v.getColor(), v.isCrashed());
			}
			
		}
		
	}

	@Override
	public void addVehicle(Vehicle vehicle) {
		totalVehicles.add(vehicle);
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getVehicleHeight() {
		return carHeight;
	}

	@Override
	public ArrayList<Vehicle> getAllVehicles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLanes() {
		return numberOfLanes;
	}

	@Override
	public void setLanes(int numberOfLanes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getSpeedLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSpeedLimit(double speedLimit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vehicle nextVehicle(Vehicle behind) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	
	
}
