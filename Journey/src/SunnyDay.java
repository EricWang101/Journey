import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class SunnyDay implements Environment {
	
	private static final int delay = 40;
	private static double speedLimit = 10.0;
	
	private static int numberOfLanes = 4;
	
	private ArrayList<Vehicle> totalVehicles = new ArrayList<Vehicle>();
	private Display display; 
	
	
	public Timer timer;

	@Override
	public void setDisplay(Display display) {
		this.display = display;
	    this.timer = new Timer(40, new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                tick();
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
			
			if(v.isCrashed()) {
				continue;
			}
			
			//Marking Crashed
			if(nextCar != null && (v.getPosition() + v.getHeight()) >= nextCar.getPosition()) {
				v.setState(VehicleState.CRASHED);
				nextCar.setState(VehicleState.CRASHED);
				continue;	
			}
			//Continue On
			
			
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
		
			v.tick(previousEnvironment);
		}
		
	}

	
	
}
