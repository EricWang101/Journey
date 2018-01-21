import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
		setUpOptions();
		
	
	 
		this.setJMenuBar(menuBar);
	
		this.getContentPane().add(display);
		pack();
	
	}
	
	public void addVehicles(Environment environment) {
		
	}
	
	public void addRandomCars(Environment environment) {
		Random randomNumber = new Random();
		for(int i = 0; i< 9;i++) {
			environment.addVehicle(new Car(environment, 
					Car.carHeight * 1.2*i,
					randomNumber.nextInt(environment.getLanes()-1),//MAYBE NOT MINUS ONE// Maybe have to add top speed to the class
					randomNumber.nextInt(10) / 4.0,
					1.001+(.098) * randomNumber.nextDouble(),
				    0.95+ (.04) * randomNumber.nextDouble()));
		}
	}
	
	public void setUpOptions() {
		
		JMenuItem resetOption = new JMenuItem("Reset");
		resetOption.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				defaultEnvironment.clear();
				display.reset();
				addRandomCars(defaultEnvironment);
				defaultEnvironment.resetTimer();
				
			}
			
		});
		
		options.add(resetOption);
		
		JMenuItem pause = new JMenuItem("Pause");
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
		options.add(pause);
		
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);	
			}	
		});
		options.add(quit);
		menuBar.add(options);
		
	}
	

}
