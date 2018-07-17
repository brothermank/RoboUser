package halcyon.robouser.com.gui.elements.customizers;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import halcyon.robouser.com.actionEngine.Action;
import halcyon.robouser.com.actionEngine.actionTypes.ClickAction;
import manke.automation.com.engine.Operator;

public class ClickCustomizer extends ActionCustomizer {

	JTextField fposx, fposy, fclickDuration, frepeats, frepeatDelay;
	JLabel lposx, lposy, lclickDuration, lrepeats, lrepeatDelay;
	JButton recordClick;
	
	public ClickCustomizer(Operator o, ClickAction cust) {
		super(o);
		setLayout(new GridLayout(0,2));
		
		
		//Instantiate components
		fposx = new JTextField("" + cust.posx);
		fposy= new JTextField("" + cust.posy);
		fclickDuration = new JTextField("" + cust.clickDuration);
		frepeats= new JTextField("" + cust.repeats);
		frepeatDelay= new JTextField("" + cust.repeatDelay);
		lposx = new JLabel("Pos x");
		lposy = new JLabel("Pos y");
		lclickDuration = new JLabel("Press duration");
		lrepeats = new JLabel("Repeats");
		lrepeatDelay = new JLabel("Time between clicks");
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
		add(frepeats);
		add(lrepeats);
		add(frepeatDelay);
		add(lrepeatDelay);
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
				Integer.parseInt(fclickDuration.getText()),
				Integer.parseInt(frepeatDelay.getText()), 
				Integer.parseInt(frepeats.getText()));
	}
}
