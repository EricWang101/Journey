import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * The main frame of the program, and the most outer container;
 * @author Eric Wang
 */
public class Main extends JFrame{
	
	private Environment defaultEnvironment = new SunnyDay(); // The default environment the Display object will show
	private Display display = new Display(defaultEnvironment); // The display object, which takes an environment as a parameter
	private JMenuBar menuBar = new JMenuBar(); // The JMenuBar which will contain the two JMenu components: "Options", and "Settings" 
	private JMenu options = new JMenu("Options"); // The JMenu item that will contain the functions reset, pause, and quit 
	
	private JMenu settings = new JMenu("Settings"); // Currently not implemented, but will contain the functions related to the environment
	
	/** Initializes the main frame */
	public static void main(String[] args ) {
		Main main = new Main();
		main.setResizable(false);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setTitle("Journey");
		main.setVisible(true);
		main.setLocationRelativeTo(null);	
	}
	
	/** The class constructor, which sets up the menu bar, and the display */
	public Main() {
		defaultEnvironment.setDisplay(display);
		addRandomCars(defaultEnvironment);
		setUpOptions();
		setUpSettings();
		this.setJMenuBar(menuBar);
		this.getContentPane().add(display);
		pack();
	}
	
	/**
	 * Adds the car objects to the environment;
	 * Each car is initialized with different speeds, acceleration, and braking power; 
	 * @param environment The environment in which the cars will be added to
	 */
	public void addRandomCars(Environment environment) {
		Random randomNumber = new Random();
		for(int i = 0; i< 9;i++) {
			environment.addVehicle(new Car(environment, // The environment the car will be added to 
					Car.carHeight *1.2*i, // The starting position of the car
					randomNumber.nextInt(environment.getLanes()-1), // The starting lane of the car 
					randomNumber.nextInt(10) / 4.0, // The speed 
					1.001+(.098) * randomNumber.nextDouble(), // The acceleration
				    0.95+ (.04) * randomNumber.nextDouble())); // The braking efficiency 
		}
	}
	
	/** Sets up the field "options" */
	public void setUpOptions() {
		JMenuItem resetOption = new JMenuItem("Reset"); // Creating a reset function
		
		resetOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultEnvironment.clear(); // Clears the environment of its cars
				display.reset(); // Clears the yOffset of the display
				addRandomCars(defaultEnvironment); // Adds random cars to the environment agian
				defaultEnvironment.resetTimer();	 // Resets the timer
			}	
		});
	
		options.add(resetOption); // Adding the JMenuItem "reset" to options
		JMenuItem pause = new JMenuItem("Pause"); // Creating a pause function
		
		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(defaultEnvironment.isRunning()) {
					defaultEnvironment.stopTimer();
					pause.setText("Unpause");
				}else {
					defaultEnvironment.startTimer();
					pause.setText("Pause");
				}
				
			}
			
		});
		
		options.add(pause); // Adding the JMenuItem "pause" to options
		JMenuItem quit = new JMenuItem("Quit"); // Creating a quit function
		
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);	
			}	
		});
		
		options.add(quit); // Adding the JMenuItem "quit" to options
		menuBar.add(options); // Adding the JMenu "options" to the JMenuBar
	}
	
	/**
	 * Will be implemented in the future when there are multiple types of vehicles 
	 * @param environment The environment the vehicles will be added to
	 */
    public void addVehicles(Environment environment) { }
    
    /** Will be implemented in the future!*/
    public void setUpSettings() { }
    
    
}
