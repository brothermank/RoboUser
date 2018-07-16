package halcyon.robouser.com.gui.elements.customizers;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import halcyon.robouser.com.actionEngine.Action;
import halcyon.robouser.com.actionEngine.actionTypes.TypeAction;
import manke.automation.com.engine.Operator;

public class TypeCustomizer extends ActionCustomizer {

	public JTextField finputToType, ftypeBreakDuration, fpressDuration;
	public JLabel linputToType, ltypeBreakDuration, lpressDuration;
	
	public TypeCustomizer(Operator o) {
		super(o);
		
		setLayout(new GridLayout(0,2));
		
		//Instantiate components
		finputToType = new JTextField("1");
		ftypeBreakDuration= new JTextField("2");
		fpressDuration = new JTextField("3");
		linputToType = new JLabel("Input");
		ltypeBreakDuration = new JLabel("Time between characters (ms)");
		lpressDuration = new JLabel("Button press time (ms)");
		
		//Add components
		add(finputToType);
		add(linputToType);
		add(ftypeBreakDuration);
		add(linputToType);
		add(ltypeBreakDuration);
		add(fpressDuration);
		add(lpressDuration);
		
	}

	@Override
	public Action getAction() {
		return new TypeAction(o,
				finputToType.getText(), 
				Integer.parseInt(ftypeBreakDuration.getText()), 
				Integer.parseInt(fpressDuration.getText()));
	}
	
}
