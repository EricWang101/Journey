import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class Main extends JFrame{
	
	private Environment defaultEnvironment = new SunnyDay();
	private Display display = new Display(defaultEnvironment);
	private JMenuBar menuBar = new JMenuBar();
	private JMenu options = new JMenu("Options");
	private JMenu gameplay = new JMenu("Settings");
	
	
	public static void main(String[] args ) {
		Main main = new Main();
		main.setResizable(false);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setTitle("Journey");
		main.setVisible(true);
		main.setLocationRelativeTo(null);
		
	}
	
	public Main() {
		defaultEnvironment.setDisplay(display);
		addRandomCars(defaultEnvironment);
		
		JMenu item = new JMenu("Test");//CHANGE LATER
		options.add(item);   
		menuBar.add(options);
		this.setJMenuBar(menuBar);
	
		this.getContentPane().add(display);
		pack();
	
		
		
		
	}
	
	public void addVehicles(Environment environment) {
		
	}
	
	public void addRandomCars(Environment environment) {
		Random randomNumber = new Random();
		for(int i = 0; i< 6;i++) {
			environment.addVehicle(new Car(environment, 
					environment.getVehicleHeight() * 1.2*i,
					randomNumber.nextInt(environment.getLanes()-1),//MAYBE NOT MINUS ONE
					randomNumber.nextInt(10) / 4.0,
					1.001+(1.099-1.001) * randomNumber.nextDouble(),
				    0.95+(0.99-0.95) * randomNumber.nextDouble()));
		}
	}
	

}
