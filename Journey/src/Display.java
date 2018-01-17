import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Display extends JPanel {
	
	protected Environment environment; 
	protected Dimension dimension;
	protected Graphics2D context; 
	protected int numberOfLanes; 
	protected int xOffset = 150;
	protected int yOffset = 0 ;
	protected int laneWidth = 30;
	
	public Display(Environment environment) {
		this.environment = environment;
		this.numberOfLanes = 4;
		update();
		
	}
	
	public void update() {
		this.setPreferredSize(new Dimension(xOffset*2 + (this.environment.getLanes() * this.laneWidth),600));
	}
	
	public  void  paint(Graphics graphic) {
		//super.paintComponent(graphic);
		
	}
	
	
	
	

}
