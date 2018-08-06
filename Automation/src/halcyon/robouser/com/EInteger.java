package halcyon.robouser.com;

public class EInteger {
	int val = 0;
	public EInteger() {}
	public EInteger(int i) {this.val = i;}
	public EInteger(EInteger e) {val = e.val;}
	
	public boolean equals(int arg) {
		return val == arg;
	}
	public void set(int arg) {
		val = arg;
	}
	public int get() {
		return val;
	}
}
