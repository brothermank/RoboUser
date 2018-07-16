package halcyon.robouser.com;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Contains GUI elements: Edit action, delete action, action description
public class ActionView extends JPanel {
	
	JButton editAction, deleteAction;
	JLabel actionDescription;
	
	public ActionView() {
		//Initialize components 
		editAction = new JButton("Edit Action");
		deleteAction = new JButton("Delete Action");
		actionDescription = new JLabel("Description");
		
		//Add components
		Utility.addToPanelAt(this, actionDescription, 0,0);
		Utility.addToPanelAt(this, editAction, 0,1);
		Utility.addToPanelAt(this, deleteAction, 0,2);

		//Map methods to buttons
		editAction.addActionListener(e -> {editAction();});
		deleteAction.addActionListener(e -> {deleteAction();});
		
		setBackground(Color.LIGHT_GRAY);

	}
	
	private void editAction() {
		
	}
	
	private void deleteAction() {
		
	}
}
