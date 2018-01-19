import java.awt.Color;

public interface Vehicle {
	
	public void tick(Environment environment) ; 
	
	public VehicleState getState();
	
	public void setState(VehicleState state); 
	
	public int getLane();
	
	public void setLane(int lane);
	
	public double getPosition();
	
	public boolean isCrashed();
	
	public Color getColor();
	
	public double getHeight();
	
	
	
		
}
