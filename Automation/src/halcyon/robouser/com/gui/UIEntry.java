package halcyon.robouser.com.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import halcyon.robouser.com.Utility;
import halcyon.robouser.com.actionEngine.Action;
import halcyon.robouser.com.actionEngine.Routine;
import halcyon.robouser.com.actionEngine.SheetEditor;
import halcyon.robouser.com.gui.elements.RoutineBoard;
import halcyon.robouser.com.gui.elements.RoutineCustomizer;

//Contains GUI elements: Run routine, save routine, create routine, new routine, routine view.
public class UIEntry {
	
	JFrame root;
	JPanel mainPanel, buttonPanel;
	
	JButton runRoutine, loadRoutine, saveRoutine, newRoutine, saveRoutines, showRoutineBoard, editPISheetFromText;
	RoutineCustomizer routineCustomizer;
	RoutineBoard routineBoard;
	
	public UIEntry() {

		//Init root frame
		root = new JFrame("RoboUser");
		root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		root.setBounds(0,0,500, 500);
		root.setVisible(true);
		root.setLocationRelativeTo(null);
		root.setLayout(new GridLayout(1,0));
		
		//Init panels
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());		
		//buttonPanel.setBackground(new Color(255, 0, 0));
		
		//Initialize components
		runRoutine = new JButton("Run routine");
		loadRoutine = new JButton("Load routine");
		saveRoutine  = new JButton("Save routine");
		newRoutine = new JButton("New routine");
		saveRoutines = new JButton("Save routines");
		showRoutineBoard = new JButton("Show routine board");
		editPISheetFromText = new JButton("Edit PI Sheet from text");
		routineCustomizer = new RoutineCustomizer();
		routineBoard = new RoutineBoard();
		
		//Add components to panels
		Utility.addToPanelAt(buttonPanel, newRoutine, 0, 0);
		Utility.addToPanelAt(buttonPanel, editPISheetFromText, 3, 0, GridBagConstraints.NORTHEAST);
		Utility.addToPanelAt(mainPanel, buttonPanel, 0,0);
		
		root.add(mainPanel);
		
		//Set button functions
		runRoutine.addActionListener(e -> {runRoutine();});
		loadRoutine.addActionListener(e -> {loadRoutine();});
		saveRoutine.addActionListener(e -> {saveRoutine();});
		saveRoutines.addActionListener(e -> {saveAllRoutines();});
		showRoutineBoard.addActionListener(e -> {setMultiRoutineView();});
		newRoutine.addActionListener(e -> {newRoutine();});
		editPISheetFromText.addActionListener(e -> {editPISheetFromText();});
		
		setSingleRoutineView();
		
	}

	public void setSingleRoutineView() {
		setSingleRoutineView(new Routine());
	}
	public void setSingleRoutineView(Routine r) {
		mainPanel.remove(routineBoard);

		Utility.addToPanelAt(buttonPanel, loadRoutine, 1, 0);
		Utility.addToPanelAt(buttonPanel, saveRoutine, 0, 1);
		Utility.addToPanelAt(buttonPanel, runRoutine, 1, 1);
		Utility.addToPanelAt(buttonPanel, showRoutineBoard, 2, 0, GridBagConstraints.NORTHEAST);

		routineCustomizer.setRoutine(r);
		
		Utility.addToPanelAt(mainPanel, routineCustomizer, 0, 1);
		
		root.pack();
	}
	
	public void setMultiRoutineView() {
		buttonPanel.remove(loadRoutine);
		buttonPanel.remove(saveRoutine);
		buttonPanel.remove(runRoutine);
		buttonPanel.remove(showRoutineBoard);
		mainPanel.remove(routineCustomizer);

		//Utility.addToPanelAt(buttonPanel, saveRoutines, 0, 1);
		Utility.addToPanelAt(mainPanel, routineBoard, 0, 1);

		root.pack();
	}
	
	
	private void newRoutine() {
		
	}
	
	@SuppressWarnings("unchecked")
	private void loadRoutine() {
	      try {
	         FileInputStream fileIn = 
	        		 new FileInputStream("routines/routine1.rou");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         Routine r = new Routine((ArrayList<Action>) in.readObject());
	         routineCustomizer.setRoutine(r);
	         in.close();
	         fileIn.close();
	      } catch (IOException i) {
	         i.printStackTrace();
	         return;
	      } catch (ClassNotFoundException c) {
	         System.out.println("Actions list not found");
	         c.printStackTrace();
	         return;
	      }
	      
	}
	
	private void saveRoutine() {
		try {
	         FileOutputStream fileOut =
	        		 new FileOutputStream("routines/routine1.rou");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
//	         ArrayList<Action> actions = routineView.getActions();
//	         for(int i = 0; i < actions.size(); i++) {
//	        	 out.writeObject(actions.get(i));
//	         }
	         out.writeObject(routineCustomizer.getRoutine());
	         out.close();
	         fileOut.close();
	        
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
	}
	private void saveAllRoutines() {
		try {
	         FileOutputStream fileOut =
	        		 new FileOutputStream("routines/routine1.rou");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
//	         ArrayList<Action> actions = routineView.getActions();
//	         for(int i = 0; i < actions.size(); i++) {
//	        	 out.writeObject(actions.get(i));
//	         }
	         out.writeObject(routineCustomizer.getRoutine());
	         out.close();
	         fileOut.close();
	        
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
	}
	
	private void runRoutine() {
		routineCustomizer.executeActions();
	}
	
	private void editPISheetFromText() {

		File f = new File(System.getProperty("user.home") + "\\Work Folders\\Work\\My Documents\\AutoUser\\PI Sheets");
		JFileChooser chooser = new JFileChooser(f);
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Saved Routines", "pis");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(mainPanel);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("You chose to open this file: " +
	            chooser.getSelectedFile().getName());
	       SheetEditor se = new SheetEditor(chooser.getSelectedFile());
	       se.editPISheet();
	    }
	    
	    
	}
	
}
