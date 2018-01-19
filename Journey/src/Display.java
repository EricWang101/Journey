import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class Display extends JPanel {
	
	private final static BasicStroke dashed = new BasicStroke (1.0f,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_MITER,10.0f,new float[]{10.0f},0.0f);
	private final static BasicStroke solid = new BasicStroke(2.0f);
	
	protected Environment environment; 
	protected Dimension dimension;
	protected Graphics2D context; 
	protected int numberOfLanes; 
	protected int xOffset = 100;
	protected int yOffset = 0 ;
	protected int laneWidth = 30;
	protected int carWidth = 20;
	
	
	public Display(Environment environment) {
		this.environment = environment;
		this.numberOfLanes = 4;
		update();
		
	}
	
	public void update() {
		this.setPreferredSize(new Dimension(xOffset*2 + (this.environment.getLanes() * this.laneWidth),650));
	}
	
	public void settingEndBoundary(int lastCar) {
		if(dimension ==null) return;
		
		if((yOffset + lastCar) + (dimension.height / 8) > dimension.height) {
			yOffset -= dimension.height/2;
		}
		
		
	}
	
	public  void  paint(Graphics graphic) {
		
		//super.paintComponent(graphic);
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
		
		environment.draw();
		
		
	}
	
	public void drawCar(int position, int lane, Color color, boolean crashed) {
		int padding = laneWidth - carWidth;
		context.setColor(Color.BLACK);
		context.drawRect(xOffset + lane*laneWidth + padding/2, yOffset + position, laneWidth - padding, Car.carHeight);
		context.setColor(color);
		context.fillRect(xOffset + lane*laneWidth + padding/2, yOffset + position, laneWidth - padding, Car.carHeight);
	}
	
	
	
	

}
