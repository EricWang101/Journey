import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;
/**
 * A concrete class that implements the environment interface;
 * Stores data of all the vehicles placed in the environment;
 * Updates the display object depending on the behavior of the vehicles;
 */
public class SunnyDay implements Environment {
	
	private static final int delay = 40; // The Timer delay
	private static double speedLimit = 10.0; // The maximum speed a Vehicle "should" travel
	private static int numberOfLanes = 4; // The amount of driving lanes in the environment 
	
	private ArrayList<Vehicle> totalVehicles = new ArrayList<Vehicle>(); // Stores all the vehicles in the environment
	private Display display; // The display object that will be showing the environment 
	private Timer timer; // The main timer of the animation

	/**
	 * Sets the display;
	 * Initializes the timer, and updates the state of the environment;
	 * Starts the timer object, and repaints the display;
	 * @param display The display object that will be showing the environment 
	 */
	@Override
	public void setDisplay(Display display) {
		this.display = display;
	    this.timer = new Timer(delay, new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                tick(); //Updates the environment, and specfically the behavior of the vehicles on the road
	                double furthest = 0;
	                for (Vehicle v : totalVehicles) { 
	                    if (v.getPosition() > furthest) {
	                        furthest = v.getPosition();
	                    }
	                }
	                display.settingEndBoundary((int)furthest);
	                display.repaint();
	            }
	        });
	        this.timer.start();
	    }

	/** Draws the vehicles by calling the specfic draw function in the Display class (More vehicles will be added in the future!)*/
	@Override
	public void draw() {
		for(Vehicle v: this.totalVehicles) {
			if(v instanceof Car) {
				display.drawCar((int) v.getPosition(), v.getLane(), v.getColor(), v.isCrashed());
			}
		}
	}
	
	/** Returns a copy of the current environment */
	public SunnyDay clone() {
		SunnyDay environment = new SunnyDay();
		for(Vehicle v : totalVehicles) {
			if(v instanceof Car) {
				environment.addVehicle(((Car) v).clone());
			}
		}
		return environment;
	}
	
	/**
	 * Returns the closest in-front vehicle in the same lane; 
	 * @param behind The car that is behind the closest in-front vehicle
	 */
	@Override
	public Vehicle nextVehicle(Vehicle behind) {
		Vehicle closest = null;
		for(Vehicle v : totalVehicles) {
			if(v.getLane() == behind.getLane() && 
			   !v.equals(behind) &&
			   v.getPosition() > behind.getPosition() && 
			   (closest == null || v.getPosition() < behind.getPosition())) {
				closest = v;
			}
		}
		return closest;
	}
	
	/**
	 * Returns a boolean value describing the clearance state of a specified lane within the start and end positions.  
	 * @param startingPos The starting position of the lane
	 * @param endingPos The ending position of the lane
	 * @param targetLane The specified lane 
	 */
	public boolean laneClear(double startingPos, double endingPos, int targetLane) {
		for(Vehicle v: totalVehicles) {
			if(v.getLane() != targetLane) continue; 
			if((startingPos <= v.getPosition()) && v.getPosition() <= endingPos ) {
				return false;
			}
		}
		return true;
	}
	
	/** Updates the state of the environment, and specifically the behavior of the vehicles after one unit of time*/
	@Override
	public void tick() {
		SunnyDay previousEnvironment = SunnyDay.this.clone();
		for(Vehicle v : totalVehicles) {
			
			Vehicle nextCar = nextVehicle(v);
			
			//Mark vehicles as crashed if a collision occurs
			if(nextCar != null && (v.getPosition() + v.getHeight()) >= nextCar.getPosition()) {
				v.setState(VehicleState.CRASHED);
				nextCar.setState(VehicleState.CRASHED);
				continue;	
			}
			
			//Dont update the vehicle if it was in a collision
			if(v.isCrashed()) {
				continue;
			}
			
			//Try to avoid crashing by braking when within one car distance
			//NOTE: Crash occurs when the braking velocity is greater than the in-front vehicle, and can not change lanes
			if(nextCar!= null) {
				if(nextCar.getPosition() - v.getPosition() < (2* Car.carHeight)) {
					v.setState(VehicleState.BRAKING);
					
					//Attempt passing the vehicle by changing lanes
					if (!v.isPassing() && 
					(v.getLane() + 1) < getLanes() &&
					(v.getTopSpeed() > nextCar.getSpeed()) &&
					(laneClear(v.getPosition()-(2*Car.carHeight), nextCar.getPosition()+(2*Car.carHeight), v.getLane()+1))){
				
						v.setLane(v.getLane()+1); 
						v.setState(VehicleState.ACCELERATING); 
						v.setPassing(true); 
						v.setPassedVehicle(nextCar); 
						
					}
				// Accelerate until behind the "in-front" car (if possible)
				}else {
					 v.setState((v.getSpeed() >= nextCar.getSpeed()) ? VehicleState.CONSTANT : VehicleState.ACCELERATING); 
				}
			}
			//Return back to inside lane after passing the vehicle
			if(v.isPassing() && v.getPosition() > v.getPassedVehicle().getPosition() + (2* Car.carHeight) && 
				laneClear((v.getPassedVehicle().getPosition()+Car.carHeight), v.getPassedVehicle().getPosition() + (2*Car.carHeight), v.getLane()-1)){
				v.setLane(v.getLane()-1);
				v.setPassing(false);
				v.setPassedVehicle(null);
			}
			
			//Updates the state of the vehicle
			v.tick(previousEnvironment);
		}	
	}
	
	/** Clears all the vehicles in the field totalVehicles */
	public void clear() {
		totalVehicles.clear();
	}

	/** Resets the timer */
	@Override
	public void resetTimer() {
		this.timer.restart();	
	}

	/** Stops the timer */
	@Override
	public void stopTimer() {
		this.timer.stop();	
	}
	
	/** Returns a boolean value whether or not the timer is running */ 
	@Override
	public boolean isRunning() {
		return timer.isRunning();
	}
	
	/** Starts the timer */
	@Override
	public void startTimer() {
		timer.start();	
	}
	
	/** Adds a vehicle to the arraylist totalVehicles */
	@Override
	public void addVehicle(Vehicle vehicle) {
		totalVehicles.add(vehicle);	
	}
	
	/** Returns the ArrayList of vehicles in the environment*/
	public ArrayList<Vehicle> getAllVehicles() {
		return this.totalVehicles;
	}
	
	/** Returns the numberOfLanes in the environment */
	@Override
	public int getLanes() {
		return numberOfLanes;
	}
	
	/** Sets the numberOfLanes of the environment */
	@Override
	public void setLanes(int lanes) {
		numberOfLanes = lanes;
	}
	
	/** Returns the speed limit of the environment */
	@Override
	public double getSpeedLimit() {
		return speedLimit;
	}
	
	/** Sets the speed limit of the environment */
	@Override
	public void setSpeedLimit(double limit) {
		speedLimit = limit;	
	}
	
}
