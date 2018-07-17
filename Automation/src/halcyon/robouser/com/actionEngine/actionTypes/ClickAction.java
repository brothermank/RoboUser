package halcyon.robouser.com.actionEngine.actionTypes;

import java.io.Serializable;

import halcyon.robouser.com.actionEngine.Action;
import manke.automation.com.engine.Operator;

public class ClickAction extends Action implements Serializable {

	public int posx, posy, clickDuration = 50, repeatDelay = 50, repeats = 1;
	
	public ClickAction(Operator o, int posx, int posy, int clickDuration, int repeatDelay, int repeats) {
		super(o, AType.click);
		this.posx = posx;
		this.posy = posy;
		this.clickDuration = clickDuration;
		this.repeatDelay = repeatDelay;
		this.repeats = repeats;
	}

	@Override
	public String getDescription() {
		return "Performs a click at " + posx + ", " + posy + "   Click dur: " + clickDuration + ". Repeats " + repeats + " times";
	}

	@Override
	public void execute() {
		for(int i = 0; i < repeats; i++) {
			Operator.mouseClickAt(posx, posy, clickDuration);
			Operator.wait(repeatDelay);
		}
	}

	
	
}
