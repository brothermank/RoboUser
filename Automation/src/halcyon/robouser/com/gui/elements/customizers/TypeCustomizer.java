package halcyon.robouser.com.gui.elements.customizers;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import halcyon.robouser.com.Utility;
import halcyon.robouser.com.actionEngine.Action;
import halcyon.robouser.com.actionEngine.actionTypes.TypeAction;
import manke.automation.com.engine.Operator;

public class TypeCustomizer extends ActionCustomizer {

	public JTextField finputToType, ftypeBreakDuration, fpressDuration;
	public JLabel linputToType, ltypeBreakDuration, lpressDuration;
	
	public TypeCustomizer(Operator o, TypeAction cust) {
		super(o);

		setLayout(new GridBagLayout());
		
		//Instantiate components
		finputToType = new JTextField("" + cust.inputToType);
		ftypeBreakDuration= new JTextField("" + cust.typeBreakDuration);
		fpressDuration = new JTextField("" + cust.pressDuration);
		linputToType = new JLabel("Input");
		ltypeBreakDuration = new JLabel("Time between characters (ms)");
		lpressDuration = new JLabel("Button press time (ms)");
		
		finputToType.setPreferredSize(new Dimension(100, 30));
		ftypeBreakDuration.setPreferredSize(new Dimension(100, 30));
		fpressDuration.setPreferredSize(new Dimension(100, 30));
		
		lpressDuration.setPreferredSize(new Dimension(200, 30));
		
		//Add components
		Utility.addToPanelAt(this, finputToType, 1, 0);
		Utility.addToPanelAt(this, linputToType, 0, 0);
		Utility.addToPanelAt(this, ftypeBreakDuration, 1, 1);
		Utility.addToPanelAt(this, ltypeBreakDuration, 0, 1);
		Utility.addToPanelAt(this, fpressDuration, 1, 2);
		Utility.addToPanelAt(this, lpressDuration, 0, 2);
		
	}

	@Override
	public Action getAction() {
		return new TypeAction(o,
				finputToType.getText(), 
				Integer.parseInt(ftypeBreakDuration.getText()), 
				Integer.parseInt(fpressDuration.getText()));
	}
	
}
