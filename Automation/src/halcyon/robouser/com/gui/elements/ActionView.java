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
	
	RoutineCustomizer parentPanel;
	JButton editAction, deleteAction;
	JLabel actionDescription;
	private Action action;
	
	//AddActoinDialogue
	public ActionView(Action a, RoutineCustomizer parentPanel) {
		this.parentPanel = parentPanel;
		setLayout(new GridBagLayout());
		
		//Initialize components 
		editAction = new JButton("Edit Action");
		deleteAction = new JButton("Delete Action");
		actionDescription = new JLabel();
		
		setAction(a);
		
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
		AddActionDialogue dialogue = new AddActionDialogue(action);
		int result = JOptionPane.showConfirmDialog(null, dialogue, "Edit Action",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
		parentPanel.editAction(this, dialogue.getAction());
	}
	
	private void deleteAction() {
		parentPanel.deleteActioNView(this);
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
		actionDescription.setText(action.getDescription());
	}
}
