package halcyon.robouser.com.actionEngine;

import manke.automation.com.engine.Operator;

public abstract class Action {
	
	protected Operator o;
	
	public Action(Operator o) {
		this.o = o;
	}
	
	public enum AType {click, type}
	
	public abstract String getDescription();
	public abstract void execute();
}
