import java.util.ArrayList;

public interface Environment {
	
	public void setDisplay(Display display);
	
	public void draw();
	
	public void addVehicle(Vehicle vehicle);
	
	public void clear();
	
	public double getVehicleLength();
	
	public ArrayList<Vehicle> getAllVehicles();
	
	public int getLanes();
	
	public void setLanes(int numberOfLanes);
	
	public double getSpeedLimit();
	
	public void setSpeedLimit(double speedLimit);
	
	public Vehicle nextVehicle(Vehicle behind);
	
	public void tick();
	
	
	

}
