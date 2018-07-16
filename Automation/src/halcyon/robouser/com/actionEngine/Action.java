package halcyon.robouser.com.actionEngine;

import java.util.Arrays;

public abstract class Action {

	public enum AType {click, type}
	
	public abstract String getDescription();
}
