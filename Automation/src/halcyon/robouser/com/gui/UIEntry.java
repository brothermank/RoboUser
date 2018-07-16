package halcyon.robouser.com.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import halcyon.robouser.com.Utility;
import halcyon.robouser.com.gui.elements.RoutineView;

//Contains GUI elements: Run routine, save routine, create routine, new routine, routine view.
public class UIEntry {
	
	JFrame root;
	JPanel mainPanel, buttonPanel;
	
	JButton runRoutine, loadRoutine, saveRoutine, newRoutine;
	RoutineView routineView;
	
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
		
		//Initialize components
		runRoutine = new JButton("Run routine");
		loadRoutine = new JButton("Load routine");
		saveRoutine  = new JButton("Save routine");
		newRoutine = new JButton("New routine");
		routineView = new RoutineView();
		
		//Add components to panels
		Utility.addToPanelAt(buttonPanel, newRoutine, 0, 0);
		Utility.addToPanelAt(buttonPanel, loadRoutine, 1, 0);
		Utility.addToPanelAt(buttonPanel, saveRoutine, 0, 1);
		Utility.addToPanelAt(buttonPanel, runRoutine, 1, 1);
		
		Utility.addToPanelAt(mainPanel, buttonPanel, 0,0);
		Utility.addToPanelAt(mainPanel, routineView, 0, 1);
		
		root.add(mainPanel);
		root.pack();
		
		//Set button functions
		runRoutine.addActionListener(e -> {runRoutine();});
		loadRoutine.addActionListener(e -> {loadRoutine();});
		saveRoutine.addActionListener(e -> {saveRoutine();});
		newRoutine.addActionListener(e -> {newRoutine();});
		
	}
	
	private void newRoutine() {
		
	}
	
	private void loadRoutine() {
		
	}
	
	private void saveRoutine() {
		
	}
	
	private void runRoutine() {
		
	}
	
}
