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
import halcyon.robouser.com.actionEngine.Routine;

//Contains gui elements: A list of actions to be performed during this routine, newAction button,
//  actions contain their own buttons for modifying. 
public class RoutineCustomizer extends JPanel {
	
//	JList<ActionView> actionList;
	private Routine routine;
	private JButton addAction;
	private Action action;
	
	private JScrollPane listView;
	private JPanel listPanel;
	
	public RoutineCustomizer() {
		initialize();

	}
	
	public RoutineCustomizer(Routine r) {
		initialize();
		setRoutine(r);
	}
	
	private void initialize() {
		setLayout(new GridBagLayout());
		
		//Initialize components 
		routine = new Routine();
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
		
		addAction(dialogue.getAction(), true);
	}
	private void addAction(Action a, boolean repaint) {
		action = a;
		routine.addAction(a);
		listPanel.add(new ActionView(action, this));
		
		if(repaint) {
			validate();
			repaint();
		}	
	}
	
	public void executeActions() {
		routine.doRoutine();
	}
	
	public void deleteActioNView(ActionView view) {
		routine.removeAction(view.getAction());
		listPanel.remove(view);
		
		validate();
		repaint();
	}
	
	public Routine getRoutine(){
		return routine;
	}
	public void setRoutine(Routine r) {
		routine = r;
		
		listPanel.removeAll();
		
		for(int i = 0; i < routine.actionCount(); i++) {
			listPanel.add(new ActionView(routine.getAction(i), this));
		}
		
		validate();
		repaint();
	}
	public void editAction(ActionView target, Action newAction) {
		routine.replaceAction(target.getAction(), newAction);
		target.setAction(newAction);
		
	}
}
