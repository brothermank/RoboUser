package halcyon.robouser.com.actionEngine.actionTypes;

import halcyon.robouser.com.actionEngine.Action;

public class ClickAction extends Action {

	public int posx, posy, clickDuration;
	
	public ClickAction(int posx, int posy, int clickDuration) {
		this.posx = posx;
		this.posy = posy;
		this.clickDuration = clickDuration;
	}

	@Override
	public String getDescription() {
		return "Performs a click at " + posx + ", " + posy + "   Click dur: " + clickDuration;
	}
	
}
