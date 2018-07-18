package halcyon.robouser.com.actionEngine;

import java.io.Serializable;

import manke.automation.com.engine.Operator;

public abstract class Action implements Serializable{
	
	protected static Operator o;
	public final AType type;
	
	public Action(Operator operator, AType type) {
		o = operator;
		this.type = type;
	}
	
	public enum AType {click, type, wait, move}
	
	public abstract String getDescription();
	public abstract void execute();
}