package halcyon.robouser.com.actionEngine.actionTypes;

import halcyon.robouser.com.actionEngine.Action;
import manke.automation.com.engine.Operator;

public class TypeAction extends Action {
	
	public int typeBreakDuration, pressDuration;
	public String inputToType;
	
	public TypeAction(Operator o, String inputToType, int typeBreakDuration, int pressDuration) {
		super(o);
		this.inputToType = inputToType;
		this.typeBreakDuration = typeBreakDuration;
		this.pressDuration = pressDuration;
	}

	@Override
	public String getDescription() {
		return "Types the string: \"" + inputToType + "\". Press Dur: " + pressDuration + " Break dur: " + typeBreakDuration;
	}

	@Override
	public void execute() {
		Operator.pasteString(inputToType);
		
//		char[] chars = inputToType.toCharArray();
//		for(int i = 0; i < chars.length; i++) {
//			Operator.keyClick((int)(chars[i]), pressDuration);
//		}
//		Operator.wait(typeBreakDuration);
		
	}
	
}
