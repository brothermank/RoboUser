package halcyon.robouser.com.gui.elements.customizers;

import javax.swing.JPanel;

import halcyon.robouser.com.actionEngine.Action;
import halcyon.robouser.com.actionEngine.actionTypes.ClickAction;
import halcyon.robouser.com.actionEngine.actionTypes.TypeAction;
import halcyon.robouser.com.actionEngine.actionTypes.WaitAction;
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
			return new ClickCustomizer(o, new ClickAction(o, 0, 0, 50, 50, 1));
		case type:
			return new TypeCustomizer(o, new TypeAction(o, "", 30, 30));
		case wait:
			return new WaitCustomizer(o, new WaitAction(o, 50));
		default:
			return null;
	}
	}
	
	public static ActionCustomizer getCustomizer(Operator o, Action a) {
		switch (a.type) {
			case click:
				return new ClickCustomizer(o, (ClickAction)a);
			case type:
				return new TypeCustomizer(o, (TypeAction)a);
			case wait:
				return new WaitCustomizer(o, (WaitAction)a);
			default:
				return null;
		}
	}
	
	public abstract Action getAction();
}
