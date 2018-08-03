package halcyon.robouser.com.gui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import halcyon.robouser.com.Utility;

//A board to view and activate all saved routines. Contians buttons: newRoutine, loadRoutines, saveRoutines. Contains a board showing loaded routines.
public class RoutineBoard extends JPanel {

	JButton loadRoutine, saveRoutines;
	JPanel routineBoard, buttonPanel;
	List<RoutineView> routineViews;
	
	public RoutineBoard() {
		setLayout(new GridBagLayout());
		
		//Initialize components 
		loadRoutine = new JButton("Load Routine");
		saveRoutines = new JButton("Save Routines");
		routineBoard = new JPanel();
		routineBoard.setLayout(new GridBagLayout());
		routineBoard.setPreferredSize(new Dimension(500, 500));
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		routineViews = new ArrayList<RoutineView>();
		
		//Add components
		Utility.addToPanelAt(buttonPanel, loadRoutine, 1, 0);
		Utility.addToPanelAt(buttonPanel, saveRoutines, 2, 0);
		
		Utility.addToPanelAt(this, buttonPanel, 0, 1);
		Utility.addToPanelAt(this, routineBoard, 0, 0);
		
		//Map methods to buttons
		loadRoutine.addActionListener(e -> {loadRoutine();});
		saveRoutines.addActionListener(e -> {saveRoutines();});
		
		setBackground(Color.LIGHT_GRAY);
	}
	
	private void loadRoutine() {
		File f = new File(System.getProperty("user.home") + "\\Work Folders\\Work\\My Documents\\AutoUser\\Routines");
		JFileChooser chooser = new JFileChooser(f);
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Saved Routines", "rou");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("You chose to open this file: " +
	            chooser.getSelectedFile().getName());
	    }
	}
	private void saveRoutines() {
		
	}
	
}
