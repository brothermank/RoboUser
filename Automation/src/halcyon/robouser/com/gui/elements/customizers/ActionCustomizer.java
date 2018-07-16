package halcyon.robouser.com.gui.elements.customizers;

import java.awt.Robot;

import javax.swing.JPanel;

import halcyon.robouser.com.actionEngine.Action;
import manke.automation.com.engine.Operator;

public abstract class ActionCustomizer extends JPanel {

	Action a;
	Operator o;
	
	public ActionCustomizer(Operator o) {
		this.o = o;		
	}
	
	public void execute() {
		
	}
	
	public static ActionCustomizer getCustomizer(Action.AType type, Operator o) {
		switch (type) {
			case click:
				return new ClickCustomizer(o);
			case type:
				return new TypeCustomizer(o);
			default:
				return null;
		}
	}
	
	public abstract Action getAction();
}
