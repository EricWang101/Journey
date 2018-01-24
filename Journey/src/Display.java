import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * This class displays the environment, and draws all the view components of the application
 */
public class Display extends JPanel {
	
	//A dashed line for the middle lanes of the raod 
	private final static BasicStroke dashed = new BasicStroke (1.0f,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_MITER,10.0f, new float[]{10.0f},0.0f);
	private final static BasicStroke solid = new BasicStroke(2.0f); //A solid line for the edges of the road
	
	protected Environment environment; // The environment that the display object will show
	protected Dimension dimension; // The last known size of the display
	protected Graphics2D context; // The last known graphic context
	protected int xOffset = 100; // The x position offset of the JPanel
	protected int yOffset = 0 ; // The y position offset of the JPanel
	protected int laneWidth = 30; // The width of each lane 
	protected int carWidth = 20; // The width of each car
	
	/**
	 * Constructor, and updates the size of the dimension
	 * @param environment The environment that the display object will be showing
	 */
	public Display(Environment environment) {
		this.environment = environment;
		update();
	}
	
	/** Sets the size of the dimension based off the number of lanes*/
	public void update() {
		this.setPreferredSize(new Dimension(xOffset*2 + (this.environment.getLanes() * this.laneWidth), 650));
	}
	
	/** Sets the end of the road */
	public void settingEndBoundary(int lastCar) {
		if(dimension ==null) return;
	
		if((yOffset + lastCar) + (dimension.height / 8) > dimension.height) {
			yOffset -= dimension.height/2;
		}
	}
	
	/** Draws the components of the environment */
	public  void  paint(Graphics graphic) {
		
		this.context = (Graphics2D) graphic;
		this.dimension = getSize();
		
		//Drawing the main road lines
		context.setStroke(solid);
		context.setColor(Color.BLACK);
		context.drawLine(xOffset, 0 , xOffset, (int) dimension.getHeight());
		context.drawLine(xOffset + environment.getLanes()*laneWidth,0,xOffset + environment.getLanes()*laneWidth,(int)dimension.getHeight());
		
		//Drawing the dashed road lines
		context.setStroke(dashed);
		for(int i = 0; i< environment.getLanes() ; i++) {
			context.drawLine(xOffset + i*laneWidth, 0, xOffset + i*laneWidth, (int)dimension.getHeight());
		}
		//Drawing the vehicles in the environment
		environment.draw();
	}
	
	/**
	 * Draw's a car object 
	 * @param position The position of the car
	 * @param lane The lane of the car 
	 * @param color The color of the car 
	 * @param crashed Whether or not the car is in a crashed state
	 */
	public void drawCar(int position, int lane, Color color, boolean crashed) {
		int padding = laneWidth - carWidth;
		context.setColor(Color.BLACK);
		context.drawRect(xOffset + lane*laneWidth + padding/2, yOffset + position, laneWidth - padding, Car.carHeight);
		context.setColor(color);
		if(crashed) {
			context.setColor(Color.BLACK);//Kinda boring , maybe change up later
		}
		context.fillRect(xOffset + lane*laneWidth + padding/2, yOffset + position, laneWidth - padding, Car.carHeight);
	}
	
	/**  Resets to the initial value */
	public void reset() {
		yOffset = 0;
	}

}
