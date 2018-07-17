package halcyon.robouser.com.actionEngine.actionTypes;

import java.io.Serializable;

import halcyon.robouser.com.actionEngine.Action;
import manke.automation.com.engine.Operator;

public class TypeAction extends Action  implements Serializable {
	
	public int typeBreakDuration = 30, pressDuration = 30;
	public String inputToType;
	
	public TypeAction(Operator o, String inputToType, int typeBreakDuration, int pressDuration) {
		super(o, AType.type);
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
		if(typeBreakDuration <= 0 && pressDuration <= 0) {
			Operator.pasteString(inputToType);
			return;
		}
		Operator.typeString(inputToType, pressDuration, typeBreakDuration);
		
//		char[] chars = inputToType.toCharArray();
//		for(int i = 0; i < chars.length; i++) {
//			Operator.keyClick((int)(chars[i]), pressDuration);
//		}
//		Operator.wait(typeBreakDuration);
		
	}
	
}
