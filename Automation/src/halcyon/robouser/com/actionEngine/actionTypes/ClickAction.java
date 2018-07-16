package halcyon.robouser.com.actionEngine.actionTypes;

import halcyon.robouser.com.actionEngine.Action;
import manke.automation.com.engine.Operator;

public class ClickAction extends Action {

	public int posx, posy, clickDuration;
	
	public ClickAction(Operator o, int posx, int posy, int clickDuration) {
		super(o);
		this.posx = posx;
		this.posy = posy;
		this.clickDuration = clickDuration;
	}

	@Override
	public String getDescription() {
		return "Performs a click at " + posx + ", " + posy + "   Click dur: " + clickDuration;
	}

	@Override
	public void execute() {
		Operator.mouseClickAt(posx, posy, clickDuration);
		Operator.wait(30);
		
	}
	
}
