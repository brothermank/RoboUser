package halcyon.robouser.com.gui.elements.customizers;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import halcyon.robouser.com.actionEngine.Action;
import halcyon.robouser.com.actionEngine.actionTypes.TypeAction;
import halcyon.robouser.com.actionEngine.actionTypes.WaitAction;
import manke.automation.com.engine.Operator;

public class WaitCustomizer extends ActionCustomizer{
	
	public JTextField fduration;
	public JLabel lduration;
	
	public WaitCustomizer(Operator o, WaitAction cust) {
		super(o);
		
		setLayout(new GridLayout(0,2));
		
		//Instantiate components
		fduration = new JTextField("" + cust.duration);
		lduration = new JLabel("Duration");
		
		//Add components
		add(fduration);
		add(lduration);
		
	}

	@Override
	public Action getAction() {
		return new WaitAction(o,
				Integer.parseInt(fduration.getText()));
	}
}
