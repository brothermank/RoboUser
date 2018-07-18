package halcyon.robouser.com.gui.elements.customizers;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import halcyon.robouser.com.Utility;
import halcyon.robouser.com.actionEngine.Action;
import halcyon.robouser.com.actionEngine.actionTypes.ClickAction;
import manke.automation.com.engine.Operator;

public class ClickCustomizer extends ActionCustomizer {

	JTextField fposx, fposy, fclickDuration, frepeats, frepeatDelay;
	JLabel lposx, lposy, lclickDuration, lrepeats, lrepeatDelay;
	JButton recordClick;
	
	public ClickCustomizer(Operator o, ClickAction cust) {
		super(o);
		setLayout(new GridBagLayout());
		
		
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
		
		fposx.setPreferredSize(new Dimension(100, 30));
		fposy.setPreferredSize(new Dimension(100, 30));
		fclickDuration.setPreferredSize(new Dimension(100, 30));
		frepeats.setPreferredSize(new Dimension(100, 30));
		frepeatDelay.setPreferredSize(new Dimension(100, 30));
		
		lposy.setPreferredSize(new Dimension(200, 30));
		
		//Map buttons
		recordClick.addActionListener(e -> {recordClick();});
		
		//Add components
		Utility.addToPanelAt(this, fposx, 1, 0);
		Utility.addToPanelAt(this, lposx, 0, 0);
		Utility.addToPanelAt(this, fposy, 1, 1);
		Utility.addToPanelAt(this, lposy, 0, 1);
		Utility.addToPanelAt(this, fclickDuration, 1, 2);
		Utility.addToPanelAt(this, lclickDuration, 0, 2);
		Utility.addToPanelAt(this, frepeats, 1, 3);
		Utility.addToPanelAt(this, lrepeats, 0, 3);
		Utility.addToPanelAt(this, frepeatDelay, 1, 4);
		Utility.addToPanelAt(this, lrepeatDelay, 0, 4);
		Utility.addToPanelAt(this, recordClick, 0, 5);
		
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
