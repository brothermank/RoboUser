package halcyon.robouser.com.gui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import halcyon.robouser.com.Utility;
import halcyon.robouser.com.actionEngine.Action;

//Contains gui elements: A list of actions to be performed during this routine, newAction button,
//  actions contain their own buttons for modifying. 
public class RoutineView extends JPanel {
	
//	JList<ActionView> actionList;
	ArrayList<ActionView> actionList;
	JButton addAction;
	Action action;
	
	JScrollPane listView;
	JPanel listPanel;
	
	public RoutineView() {
		setLayout(new GridBagLayout());
		
		//Initialize components 
		actionList = new ArrayList<ActionView>();
		addAction = new JButton("Add Action");
		listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
		listView = new JScrollPane(listPanel);
		listView.setPreferredSize(new Dimension(500, 500));
		
		//Add components
		Utility.addToPanelAt(this, listView, 0, 0);
		Utility.addToPanelAt(this, addAction, 0, 1);
		
		//Map methods to buttons
		addAction.addActionListener(e -> {addAction();});
		
		setBackground(Color.LIGHT_GRAY);

	}
	
	private void addAction() {
		AddActionDialogue dialogue = new AddActionDialogue();
		int result = JOptionPane.showConfirmDialog(null, dialogue, "Add Action",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
		action = dialogue.getAction();
		actionList.add(new ActionView(action));
		listPanel.add(actionList.get(actionList.size() - 1));
		
		validate();
		repaint();
		
	}
	
}
