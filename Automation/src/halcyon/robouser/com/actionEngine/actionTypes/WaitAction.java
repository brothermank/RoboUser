package halcyon.robouser.com.actionEngine.actionTypes;

import java.io.Serializable;

import halcyon.robouser.com.actionEngine.Action;
import manke.automation.com.engine.Operator;

public class WaitAction extends Action implements Serializable {

	public int duration = 50;
	
	public WaitAction(Operator o, int duration) {
		super(o, AType.wait);
		this.duration = duration;
	}

	@Override
	public String getDescription() {
		return "Waits for " + duration + "ms";
	}

	@Override
	public void execute() {
		Operator.wait(duration);
	}
}
