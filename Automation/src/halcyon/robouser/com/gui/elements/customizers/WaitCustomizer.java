package halcyon.robouser.com.gui.elements.customizers;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import halcyon.robouser.com.Utility;
import halcyon.robouser.com.actionEngine.Action;
import halcyon.robouser.com.actionEngine.actionTypes.TypeAction;
import halcyon.robouser.com.actionEngine.actionTypes.WaitAction;
import manke.automation.com.engine.Operator;

public class WaitCustomizer extends ActionCustomizer{
	
	public JTextField fduration;
	public JLabel lduration;
	
	public WaitCustomizer(Operator o, WaitAction cust) {
		super(o);

		setLayout(new GridBagLayout());
		
		//Instantiate components
		fduration = new JTextField("" + cust.duration);
		lduration = new JLabel("Duration");
		
		fduration.setPreferredSize(new Dimension(100, 30));
		
		lduration.setPreferredSize(new Dimension(200, 30));
		
		//Add components
		Utility.addToPanelAt(this, fduration, 1, 0);
		Utility.addToPanelAt(this, lduration, 0, 0);
		
	}

	@Override
	public Action getAction() {
		return new WaitAction(o,
				Integer.parseInt(fduration.getText()));
	}
}
