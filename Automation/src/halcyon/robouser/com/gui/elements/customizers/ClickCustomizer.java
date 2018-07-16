package halcyon.robouser.com.gui.elements.customizers;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import halcyon.robouser.com.actionEngine.Action;
import halcyon.robouser.com.actionEngine.actionTypes.ClickAction;
import manke.automation.com.engine.Operator;

public class ClickCustomizer extends ActionCustomizer {

	JTextField fposx, fposy, fclickDuration;
	JLabel lposx, lposy, lclickDuration;
	JButton recordClick;
	
	public ClickCustomizer(Operator o) {
		super(o);
		setLayout(new GridLayout(0,2));
		
		
		//Instantiate components
		fposx = new JTextField("0");
		fposy= new JTextField("0");
		fclickDuration = new JTextField("50");
		lposx = new JLabel("Pos x");
		lposy = new JLabel("Pos y");
		lclickDuration = new JLabel("Click duration (ms)");
		recordClick = new JButton("Record click");
		
		//Map buttons
		recordClick.addActionListener(e -> {recordClick();});
		
		//Add components
		add(fposx);
		add(lposx);
		add(fposy);
		add(lposy);
		add(fclickDuration);
		add(lclickDuration);
		add(recordClick);
		
	}
	
	private void recordClick() {
		Operator.getPositionOfNextClick(fposx, fposy);
	}

	@Override
	public Action getAction() {
		return new ClickAction(o,
				Integer.parseInt(fposx.getText()), 
				Integer.parseInt(fposy.getText()), 
				Integer.parseInt(fclickDuration.getText()));
	}
}
