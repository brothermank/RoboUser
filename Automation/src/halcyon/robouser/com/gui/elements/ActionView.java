package halcyon.robouser.com.gui.elements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import halcyon.robouser.com.Utility;
import halcyon.robouser.com.actionEngine.Action;

// Contains GUI elements: Edit action, delete action, action description
public class ActionView extends JPanel {
	
	RoutineView parentPanel;
	JButton editAction, deleteAction;
	JLabel actionDescription;
	Action action;
	
	//AddActoinDialogue
	public ActionView(Action a, RoutineView parentPanel) {
		this.parentPanel = parentPanel;
		this.action = a;
		setLayout(new GridBagLayout());
		
		//Initialize components 
		editAction = new JButton("Edit Action");
		deleteAction = new JButton("Delete Action");
		actionDescription = new JLabel(action.getDescription());
		
		//Add components
		Utility.addToPanelAt(this, actionDescription, 0,0);
		Utility.addToPanelAt(this, editAction, 1,0);
		Utility.addToPanelAt(this, deleteAction, 2,0);

		//Map methods to buttons
		editAction.addActionListener(e -> {editAction();});
		deleteAction.addActionListener(e -> {deleteAction();});
		
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setBackground(Color.LIGHT_GRAY);
	}
	
	private void editAction() {
		AddActionDialogue dialogue = new AddActionDialogue();
		int result = JOptionPane.showConfirmDialog(null, dialogue, "Edit Action",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	}
	
	private void deleteAction() {
		parentPanel.deleteActioNView(this);
	}
	public Action getAction() {
		return action;
	}
}
