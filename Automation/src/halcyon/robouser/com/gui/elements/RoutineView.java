package halcyon.robouser.com;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

//Contains gui elements: A list of actions to be performed during this routine, newAction button,
//  actions contain their own buttons for modifying. 
public class RoutineView extends JPanel {
	
	JList<ActionView> actionList;
	JButton addAction;
	
	public RoutineView() {
		
		
		//Initialize components 
		actionList = new JList<ActionView>();
		addAction = new JButton("Add Action");
		
		//Add components
		Utility.addToPanelAt(this, actionList, 0, 0);
		Utility.addToPanelAt(this, addAction, 0, 1);
		
		setBackground(Color.LIGHT_GRAY);

		addAction.addActionListener(e -> {addAction();});
	}
	
	private void addAction() {
		
	}
	
}
