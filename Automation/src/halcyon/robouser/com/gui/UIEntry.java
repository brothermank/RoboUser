package halcyon.robouser.com.gui;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import halcyon.robouser.com.Utility;
import halcyon.robouser.com.actionEngine.Action;
import halcyon.robouser.com.gui.elements.RoutineView;
import manke.automation.com.engine.GlobalKeyboardHook;
import manke.automation.com.engine.GlobalKeyboardListener;

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
	
	@SuppressWarnings("unchecked")
	private void loadRoutine() {
	      try {
	         FileInputStream fileIn = 
	        		 new FileInputStream("routines/routine1.rou");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         routineView.setActions((ArrayList<Action>) in.readObject());
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
	         out.writeObject(routineView.getActions());
	         out.close();
	         fileOut.close();
	        
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
	}
	
	private void runRoutine() {
		routineView.executeActions();
	}
	
}
