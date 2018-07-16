package halcyon.robouser.com.gui.elements;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import halcyon.robouser.com.Main;
import halcyon.robouser.com.actionEngine.Action;
import halcyon.robouser.com.actionEngine.Action.AType;
import halcyon.robouser.com.actionEngine.actionTypes.ClickAction;
import halcyon.robouser.com.gui.elements.customizers.ActionCustomizer;

public class AddActionDialogue extends JPanel {

	ActionCustomizer customizer;
	Action.AType aType;
	
	JLabel actionTypeLabel;
	JComboBox actionTypes;
	
	public AddActionDialogue() {
		setLayout(new GridLayout(0,1));
		
		aType = Action.AType.click;
		
		//Instantiate components
		actionTypes = new JComboBox<Action.AType>(Action.AType.values());
		customizer = ActionCustomizer.getCustomizer(aType, Main.o);
		
		//Map buttons
		actionTypes.addActionListener(e -> {changeActionType();});
		
		//Add components
		add(actionTypes);
		add(customizer);
		
	}
	
	private void changeActionType() {
		remove(customizer);
		aType = (AType) actionTypes.getSelectedItem();
		customizer = ActionCustomizer.getCustomizer(aType, Main.o);
		add(customizer);
		revalidate();
	}
	
	public Action getAction() {
		return customizer.getAction();
	}
	
	
	
}
