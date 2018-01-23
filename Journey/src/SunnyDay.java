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


	@Override
	public void draw() {
		for(Vehicle v: this.totalVehicles) {
			if(v instanceof Car) {
				display.drawCar((int) v.getPosition(), v.getLane(), v.getColor(), v.isCrashed());
			}
		}
	}
	
	public SunnyDay clone() {
		SunnyDay environment = new SunnyDay();
		for(Vehicle v : totalVehicles) {
			if(v instanceof Car) {
				environment.addVehicle(((Car) v).clone());
			}
		}
		return environment;
	}

	@Override
	public Vehicle nextVehicle(Vehicle behind) {
		Vehicle closest = null;
		for(Vehicle v : totalVehicles) {
			if(v.getLane() == behind.getLane() && 
			   !v.equals(behind) &&//MAYBE CHANGE
			   v.getPosition() > behind.getPosition() && 
			     (closest == null || v.getPosition() < behind.getPosition())) {
				closest = v;
			}
		}
		return closest;
	}
	
	public boolean laneClear(double startingPos, double endingPos, int targetLane) {
		for(Vehicle v: totalVehicles) {
			if(v.getLane() != targetLane) continue; 
			if((startingPos <= v.getPosition()) && v.getPosition() <= endingPos ) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void tick() {
		SunnyDay previousEnvironment = SunnyDay.this.clone();
		for(Vehicle v : totalVehicles) {
			
			Vehicle nextCar = nextVehicle(v);
			
			
			
			//Marking Crashed
			if(nextCar != null && (v.getPosition() + v.getHeight()) >= nextCar.getPosition()) {
				v.setState(VehicleState.CRASHED);
				nextCar.setState(VehicleState.CRASHED);
				continue;	
			}
			//Continue On
			if(v.isCrashed()) {
				continue;
			}
			
			
			//attempt passing- stays one car length behind 
			//NOTE- Crash when only within car distance, and the braking speed is still greater than next car's speed, and cant change lanes
			if(nextCar!= null) {
				if(nextCar.getPosition() - v.getPosition() < (2* Car.carHeight)) {
					v.setState(VehicleState.BRAKING);
					
					if(!v.isPassing() && 
					(v.getLane() + 1) < getLanes() &&
					v.getTopSpeed() > nextCar.getSpeed() &&
					laneClear(v.getPosition()-(2*Car.carHeight), nextCar.getPosition()+(2*Car.carHeight), v.getLane()+1)) {
						
						v.setLane(v.getLane()+1);
						v.setState(VehicleState.ACCELERATING);
						v.setPassing(true);
						v.setPassedVehicle(nextCar);
						
					}
				//Accelerate until equal to speed of the car in front
				}else {
					 v.setState((v.getSpeed() >= nextCar.getSpeed()) ? VehicleState.CONSTANT : VehicleState.ACCELERATING); 
				}
			}
			//Bug, cars change lanes back and crash if there is a car in that lane not the one they passed
			if(v.isPassing() && v.getPosition() > v.getPassedVehicle().getPosition() + (2* Car.carHeight) && 
				laneClear((v.getPassedVehicle().getPosition()+Car.carHeight), v.getPassedVehicle().getPosition() + (2*Car.carHeight), v.getLane()-1)){
				v.setLane(v.getLane()-1);
				v.setPassing(false);
				v.setPassedVehicle(null);
			}
		
			v.tick(previousEnvironment);
		}
		
	}
	
	public void clear() {
		totalVehicles.clear();
	}


	@Override
	public void resetTimer() {
		this.timer.restart();
		
	}


	@Override
	public void stopTimer() {
		this.timer.stop();
		
	}


	@Override
	public boolean isRunning() {
		return timer.isRunning();
	}


	@Override
	public void startTimer() {
		timer.start();
		
	}
	
	@Override
	public void addVehicle(Vehicle vehicle) {
		totalVehicles.add(vehicle);
		
	}

	//Figure this out
	@Override
	public double getVehicleHeight() {
		return 0.0;
	}

	
	public ArrayList<Vehicle> getAllVehicles() {
		return this.totalVehicles;
	}

	@Override
	public int getLanes() {
		return numberOfLanes;
	}
	
	@Override
	public void setLanes(int lanes) {
		numberOfLanes = lanes;
		
	}

	@Override
	public double getSpeedLimit() {
		return this.speedLimit;
	}

	@Override
	public void setSpeedLimit(double limit) {
		speedLimit = limit;
		
	}


	
	
}
