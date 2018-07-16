package halcyon.robouser.com.actionEngine.actionTypes;

import halcyon.robouser.com.actionEngine.Action;

public class TypeAction extends Action {
	
	public int typeBreakDuration, pressDuration;
	public String inputToType;
	
	public TypeAction(String inputToType, int typeBreakDuration, int pressDuration) {
		this.inputToType = inputToType;
		this.typeBreakDuration = typeBreakDuration;
		this.pressDuration = pressDuration;
	}

	@Override
	public String getDescription() {
		return "Types the string: \"" + inputToType + "\". Press Dur: " + pressDuration + " Break dur: " + typeBreakDuration;
	}
	
}
