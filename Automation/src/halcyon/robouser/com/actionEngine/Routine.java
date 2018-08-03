package halcyon.robouser.com.actionEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Routine implements Serializable {

	List<Action> actions;
	
	public Routine() {
		actions = new ArrayList<Action>();
	}
	public Routine(List<Action> actions) {
		this.actions = actions;
	}
	
	public void doRoutine() {
		for(int i = 0; i < actions.size(); i++) {
			actions.get(i).execute();
		}
	}
	
	public void insertAction(int i, Action a) {
		actions.add(i, a);
	}
	
	public void removeAction(Action a) {
		actions.remove(a);
	}
	public void removeAction(int i) {
		actions.remove(i);
	}
	
	public void addAction(Action a) {
		actions.add(a);
	}
	
	public Action getAction(int i) {
		return actions.get(i);
	}
	
	public void setRoutine(List<Action> l) {
		actions = l;
	}
	
	public int actionCount() {
		return actions.size();
	}
	
	public int indexOfAction(Action a) {
		return actions.indexOf(a);
	}
	
	public void replaceAction(Action target, Action newAction) {
		actions.add(actions.indexOf(target), newAction);
		actions.remove(target);
	}
}
