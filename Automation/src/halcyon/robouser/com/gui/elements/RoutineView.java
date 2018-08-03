package halcyon.robouser.com.gui.elements;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import halcyon.robouser.com.Utility;
import halcyon.robouser.com.gui.UIEntry;

//Overview of a routine. Contains buttons: doRoutine, editRoutine, and the name of the routine.
public class RoutineView extends JPanel {
	
	JButton doRoutine, editRoutine;
	JLabel routineName;
	UIEntry parentPanel;
	
	public RoutineView(UIEntry parentPanel) {
		setLayout(new GridBagLayout());
		
		//Initialize components 
		doRoutine = new JButton("Do Routine");
		editRoutine = new JButton("Edit Routine");
		routineName = new JLabel();
		this.parentPanel = parentPanel;
		
		//Add components
		Utility.addToPanelAt(this, routineName, 0, 0);
		Utility.addToPanelAt(this, doRoutine, 1, 0);
		Utility.addToPanelAt(this, editRoutine, 2, 0);
		
		
		//Map methods to buttons
		doRoutine.addActionListener(e -> {doRoutine();});
		editRoutine.addActionListener(e -> {editRoutine();});
		
		setBackground(Color.LIGHT_GRAY);
	}
	
	private void doRoutine() {
		
	}
	
	private void editRoutine() {
		
	}
	
}
