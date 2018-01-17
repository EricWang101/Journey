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
		
		
		this.getContentPane().add(display);
		pack();
	
		
		
		
	}
	
	public void addVehicles(Environment environment) {
		
	}
	

}
