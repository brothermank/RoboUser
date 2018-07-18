package halcyon.robouser.com.gui.elements.customizers;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import halcyon.robouser.com.Utility;
import halcyon.robouser.com.actionEngine.Action;
import halcyon.robouser.com.actionEngine.actionTypes.MoveAction;
import manke.automation.com.engine.Operator;

public class MoveCustomizer extends ActionCustomizer {
	
	JTextField fposx, fposy;
	JLabel lposx, lposy;
	JButton recordClickPosition;
	
	public MoveCustomizer(Operator o, MoveAction a) {
		super(o);
		setLayout(new GridBagLayout());
		
		
		//Instantiate components
		fposx = new JTextField("" + a.posx);
		fposy = new JTextField("" + a.posy);
		lposx = new JLabel("Pos x");
		lposy = new JLabel("Pos y");
		recordClickPosition = new JButton("Record click position");
		
		fposx.setPreferredSize(new Dimension(100, 30));
		fposy.setPreferredSize(new Dimension(100, 30));
		
		lposy.setPreferredSize(new Dimension(200, 30));
		
		
		//Map buttons
		recordClickPosition.addActionListener(e -> {recordClickPosition();});
		
		//Add components
		Utility.addToPanelAt(this, fposx, 1, 0);
		Utility.addToPanelAt(this, lposx, 0, 0);
		Utility.addToPanelAt(this, fposy, 1, 1);
		Utility.addToPanelAt(this, lposy, 0, 1);
		Utility.addToPanelAt(this, recordClickPosition, 0, 2);
		
	}
	
	private void recordClickPosition() {
		Operator.getPositionOfNextClick(fposx, fposy);
	}
	
	@Override
	public Action getAction() {
		return new MoveAction(o,
				Integer.parseInt(fposx.getText()), 
				Integer.parseInt(fposy.getText()));
	}
}
