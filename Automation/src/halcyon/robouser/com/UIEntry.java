package halcyon.robouser.com;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UIEntry {

	JFrame root;
	JPanel mainPanel;
	
	JButton runRoutine, loadRoutine;
	RoutineView routineView;
	
	public UIEntry() {

		//Init root frame
		root = new JFrame("RoboUser");
		root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		root.setBounds(0,0,500, 500);
		root.setVisible(true);
		root.setLocationRelativeTo(null);
		
		//Init main panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		
		//root.setLayout(new GridLayout(1,0));
		
		// Create grid bag constraint
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 4, 0, 0);
		gbc.weightx = 0.5;
		gbc.weighty = 1;
		
		//Initialize components
		runRoutine = new JButton("Run routine");
		loadRoutine = new JButton("Load routine");
		
		//Add components
		
		
	
	}
}
