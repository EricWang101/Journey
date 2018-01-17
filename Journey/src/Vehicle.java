
public interface Vehicle {
	
	public void tick() ; 
	
	public VehicleState getState();
	
	public void setState(VehicleState state); 
	
	public int getLane();
	
	public void setLane(int lane);
	
	public int getPosition();
	
	public boolean isCrashed();
	
	public void getColor();
		
}
