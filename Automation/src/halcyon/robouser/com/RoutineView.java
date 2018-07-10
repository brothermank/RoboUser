package halcyon.robouser.com;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JList;
import javax.swing.JPanel;

public class RoutineView extends JPanel {
	
	JList<ActionView> actionList;
	
	public RoutineView() {
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 4, 0, 0);
		gbc.weightx = 0.5;
		gbc.weighty = 1;
		
		//Initialize components 
		actionList = new JList<ActionView>();
		
		//Add components
		
	}
	
}
