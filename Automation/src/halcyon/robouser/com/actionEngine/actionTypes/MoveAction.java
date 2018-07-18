package halcyon.robouser.com.actionEngine.actionTypes;

import halcyon.robouser.com.actionEngine.Action;
import manke.automation.com.engine.Operator;

public class MoveAction  extends Action {
	
	public int posx, posy;
	
	public MoveAction(Operator o, int posx, int posy) {
		super(o, AType.click);
		this.posx = posx;
		this.posy = posy;
	}

	@Override
	public String getDescription() {
		return "Moves cursor to " + posx + ", " + posy;
	}

	@Override
	public void execute() {
		Operator.mouseMove(posx, posy);
	}
}
