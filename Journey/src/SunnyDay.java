import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class SunnyDay implements Environment {
	
	private static final int delay = 40;
	private static double speedLimit = 10.0;
	private static final double carHeight = 45; // CHANGE LATER 
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

	@Override
	public double getVehicleHeight() {
		return carHeight;
	}

	@Override
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void tick() {
		SunnyDay previousEnvironment = SunnyDay.this.clone();
		for(Vehicle v : totalVehicles) {
			v.tick(previousEnvironment);
		}
		
	}

	
	
}
