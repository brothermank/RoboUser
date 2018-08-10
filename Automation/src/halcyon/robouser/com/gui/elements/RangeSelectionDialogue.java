package halcyon.robouser.com.gui.elements;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import halcyon.robouser.com.RoboUserMain;
import halcyon.robouser.com.Utility;
import halcyon.robouser.com.actionEngine.Action;
import halcyon.robouser.com.gui.elements.customizers.ActionCustomizer;

public class RangeSelectionDialogue extends JPanel {

	private JTextField min, max;
	
	private JLabel lMin, lMax;
	
	public RangeSelectionDialogue() {
		initialize();
	}
	
	private void initialize() {
		setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(300, 180));
		
		
		//Instantiate components
		min = new JTextField("0");
		max = new JTextField("0");
		min.setPreferredSize(new Dimension(100, 30));
		max.setPreferredSize(new Dimension(100, 30));
		lMin = new JLabel("Range Start");
		lMax = new JLabel("Range End");
		
		//Map buttons
		//actionTypes.addActionListener(e -> {changeActionType();});
		
		//Add components
		Utility.addToPanelAt(this, lMin, 0, 0);
		Utility.addToPanelAt(this, min, 1, 0);
		Utility.addToPanelAt(this, lMax, 0, 1);
		Utility.addToPanelAt(this, max, 1, 1);
	}
	
	public int getMin() {
		try {
			return Integer.parseInt(min.getText());
		} catch (NumberFormatException e){
			System.out.println("Could not parse integer from string: "  + min.getText());
			return 10000;
		}
	}

	public int getMax() {
		try {
			return Integer.parseInt(max.getText());
		} catch (NumberFormatException e){
			System.out.println("Could not parse integer from string: "  + max.getText());
			return -1;
		}
	}
}
