package Autoclicker;

import java.awt.Point;

import manke.automation.com.engine.Operator;

public class AutoClickerEngine implements Runnable {

	public volatile boolean running = false;
	boolean stopRunning = false;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		running = true;
		System.out.println("Started");
		while(!stopRunning) {
			Point p = Operator.getMousePos();
			Operator.mouseClickAt(p.x, p.y, 10);
			Operator.wait(10);
		}
		running = false;
	}
	
	public void stopRunning() {
		stopRunning = true;
		while(running) {}
		System.out.println("Stopped");
		stopRunning = false;
	}

}
