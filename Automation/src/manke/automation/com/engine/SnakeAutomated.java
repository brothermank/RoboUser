package manke.automation.com.engine;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class SnakeAutomated {
	
	Operator o;
	SnakeMouseEventListener l;
	
	private final int pressTime = 50;
	private final int waitTime = 0;
	private final int waitTime2 = 70;
	private final int waitTime3 = 50;
	private final int sleepTime = 5;
	
	int x1 = 515, y1 = 190, x2 = 845, y2 = 455;

	int sx = 17, sy = 13;
	int d = 19;
	
	Color jokerReference;

	//0 = going up, 1 = going right, 2 = going down, 3 = going left
	/*int keyPoints[][] = {{0, 12, 0, 3}, {0,0, 1, 0}, {1,0, 2, 1}, {1,11, 1, 2}, {2,11, 0, 1}, {2,0, 1, 0}, {3,0, 2, 1}, {3,11, 1, 2}, 
						 {4,11, 0, 1}, {4,0, 1, 0}, {5,0,2, 1}, {5,11,1, 2}, {6,11,0, 1}, {6,0,1, 0},{7,0,2, 1}, {7,11,1, 2}, {8,11,0, 1}, 
						 {8,0,1, 0}, {9,0,2, 1}, {9,11,1, 2}, {10,11,0, 1}, {10,0,1, 0}, {11,0,2, 1}, {11,11,1, 2}, {12, 11,0, 1}, {12,0,1, 0}, 
						 {13,0,2, 1}, {13,11,1, 2}, {14,11,0, 1}, {14,0,1, 0}}; 
	*/

	int keyPoints[][] = {{0, 12, 0, 3}, {0,0, 1, 0}, {1,11, 1, 2}, {2,0, 1, 0}, {3,11, 1, 2}, 
						 {4,0, 1, 0}, {5,11,1, 2}, {6,0,1, 0},{7,11,1, 2}, 
						 {8,0,1, 0}, {9,11,1, 2}, {10,0,1, 0}, {11,11,1, 2}, {12,0,1, 0}, 
						 {13,11,1, 2}, {14,0,1, 0}}; //L = 16
	
	int pos = -1;
	int complicatedPos = 0;
	
	Gct gridContent[][] = new Gct[sx][sy];
	int colors[][] = new int[sx][sy];
	
	enum Gct{empty, snake, food, obstacle}
	
	public SnakeAutomated(Operator o) {
		this.o = o;
	}
	
	public void mainLoop() {
	
		//Give time to go to snake screen
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Get joker reference
		jokerReference = getKeyColor(15,11, -1);
		System.out.println("Reference color: " + jokerReference.getRed() + "," + jokerReference.getGreen() + "," + jokerReference.getBlue());
		
		//Wait for key color to change
		while(0 < 1) {	
			while(waitForNextKeyChange()) {
				//Turn the correct direction
				performTurn();
			}
			o.wait(waitTime2);
			//At the end of key points
			while(waitForComplicatedKeyChange()) {
				performComplicatedTurn();
			}
			System.out.println("Exited");
			
			//Handle joker corner
			handleJokerCorner();
			o.pressTerminate();
		}
		
		//Determine 
	}
	
	//0 = going up, 1 = going right, 2 = going down, 3 = going left
	private Color getKeyColor(int x, int y, int direction) {
		return getKeyColor(x, y, direction, 0);
	}

	private Color getKeyColor(int x, int y, int direction, int previous) {
		double dx = 0.5, dy = 0.5;
		if(direction == 2) {
			dx = 0.5;
			dy = 0.1 - previous;
		}
		if(direction == 0) {
			dx = 0.5;
			dy = 0.5 + previous;
		}
		if(direction == 3) {
			dx = 0.7 + previous;
			dy = 0.5;
		}
		if(direction == 1) {
			dx = 0.3 - previous;
			dy = 0.5;
		}
		
		return o.r.getPixelColor((int)(x1 + x * d + d * dx), (int)(y1 + y * d + d * dy));
	}
	
	private boolean waitForNextKeyChange() {
		pos++;
		if(pos >= keyPoints.length) {
			complicatedPos = -1;
			return false;
		}
		
		System.out.println("Waiting for position: " + (int)(x1 + keyPoints[pos][0] * d + d * 0.5) + "," 
					+  (int)(y1 + keyPoints[pos][1] * d + d * 0.5) + "   index: " + keyPoints[pos][0] + "," +  keyPoints[pos][1]
							+ "   direction: " + keyPoints[pos][3]);
		
		o.wait(waitTime);
		Color c = getKeyColor(keyPoints[pos][0], keyPoints[pos][1], keyPoints[pos][3]);
		Color c2 = getKeyColor(keyPoints[pos][0], keyPoints[pos][1], keyPoints[pos][3], 1);
		
		boolean foodAtCheckpoint = false;
		while(c.equals(getKeyColor(keyPoints[pos][0], keyPoints[pos][1], keyPoints[pos][3]))) {
			o.wait(sleepTime);
		}
		if(c2.equals(getKeyColor(keyPoints[pos][0], keyPoints[pos][1], keyPoints[pos][3], 1))) {
				//There is food at the checkpoint
				foodAtCheckpoint = true;
				System.out.println("Food at checkpoint");
				while(c.equals(getKeyColor(keyPoints[pos][0], keyPoints[pos][1], keyPoints[pos][3])) ||
						c2.equals(getKeyColor(keyPoints[pos][0], keyPoints[pos][1], keyPoints[pos][3], 1))) {
					o.wait(sleepTime);
				}
		}
		
		
		if(foodAtCheckpoint) {
			if(pos == 0) {
				o.wait(100);
			}
			else {
				o.wait(110);
			}
		}
		return true;
	}

	private boolean waitForComplicatedKeyChange() {
		complicatedPos++;
		System.out.println("Complicatedpos: " + complicatedPos);
		if(complicatedPos >= 21) {
			pos = -1;
			return false;
		}
		
		int x, y, direction;
		if(complicatedPos % 4 == 0) {
			direction = 1; //Going right
			x = 16;
			y = complicatedPos / 2;
		}
		else if(complicatedPos % 4 == 1) {
			direction = 2; //Going down
			x = 16;
			y = complicatedPos / 2 + 1;
		}
		else if(complicatedPos % 4 == 2) {
			direction = 3; //Going left
			x = 15;
			y = complicatedPos / 2;
		}
		else {
			direction = 2; //Going down
			x = 15;
			y = complicatedPos / 2 + 1;
		}
		System.out.println("Waiting for position: " + (int)(x1 + x * d + d * 0.5) + "," 
				+  (int)(y1 + y * d + d * 0.5) + "   index: " + x + "," +  y + "   direction: " + direction);
		
		//o.wait(waitTime);
		/*Color c = getKeyColor(x, y, direction);
		Color c2 = getKeyColor(x, y, direction, 1);
		
		while(c.equals(getKeyColor(x, y, direction)) ||
				c.equals(getKeyColor(x, y, direction, 1))) {
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		o.wait(waitTime2);
		
		return true;
		
	}
	
	private void waitAndPerformSpecificTurn(int x, int y, int direction, int viewSide) {
		System.out.println("Waiting for position: " + (int)(x1 + x * d + d * 0.5) + "," 
				+  (int)(y1 + y * d + d * 0.5) + "   index: " + x + "," +  y);
		
		o.wait(waitTime);
		Color c = getKeyColor(x, y, viewSide);
		Color c2 = getKeyColor(x, y, viewSide, 1);
		
		while(c.equals(getKeyColor(x, y, viewSide)) ||
				c2.equals(getKeyColor(x, y, viewSide, 1))) {
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		o.keyClick(direction, pressTime);
	}

	private void performTurn() {
		System.out.println("Performing turn");

		if(keyPoints[pos][2] == 0) {//Turn up
			o.keyClick(KeyEvent.VK_UP, pressTime); //Press up arrow
		}
		if(keyPoints[pos][2] == 1) { //Turn right
			o.keyClick(KeyEvent.VK_RIGHT, pressTime); //Press right arrow
			o.wait(waitTime3);
			System.out.println("POS IS: " + pos);
			if(pos % 2 == 1 && pos != keyPoints.length - 1) {
				System.out.println("Clicking down");
				o.keyClick(KeyEvent.VK_DOWN, pressTime);
			}
			else if(pos % 2 == 0 && pos != keyPoints.length - 1) {
				System.out.println("Clicking up");
				o.keyClick(KeyEvent.VK_UP, pressTime);
			}
		}
		if(keyPoints[pos][2] == 2) {//Turn down
			o.keyClick(KeyEvent.VK_DOWN, pressTime); //Press down arrow
		}
	}
	private void performComplicatedTurn() {
		if(complicatedPos % 2 == 0) {
			o.keyClick(KeyEvent.VK_DOWN, pressTime); //Press down arrow
		}
		if(complicatedPos % 4 == 1) {
			o.keyClick(KeyEvent.VK_LEFT, pressTime); //Press left arrow
		}
		if(complicatedPos % 4 == 3) {
			o.keyClick(KeyEvent.VK_RIGHT, pressTime); //Press right arrow
		}
		
	}
	
	private boolean isJokerFood(){
		System.out.println("Reference color: " + jokerReference.getRed() + "," + jokerReference.getGreen() + "," + jokerReference.getBlue());
		Color c = getKeyColor(15, 11, -1);
		System.out.println("Joker color: " + c.getRed() +"," + c.getGreen() +"," + c.getBlue());
		
		System.out.println("Is joker food?: " + !(jokerReference.equals(getKeyColor(15, 11, -1))) + " Equals: " 
					+ jokerReference.equals(getKeyColor(15, 11, -1)));
		return !(jokerReference.equals(c));
	}
	
	private void handleJokerCorner() {
		if(isJokerFood()) {
			o.wait(waitTime2);
			o.keyClick(KeyEvent.VK_LEFT);
			o.wait(waitTime2);
			o.keyClick(KeyEvent.VK_DOWN);
			o.wait(waitTime2);
			o.keyClick(KeyEvent.VK_LEFT);
			/*waitAndPerformSpecificTurn(16,11, KeyEvent.VK_LEFT, 2);
			waitAndPerformSpecificTurn(15,11, KeyEvent.VK_DOWN, 3);
			waitAndPerformSpecificTurn(15,12, KeyEvent.VK_LEFT, 2);*/
		}
		else {
			//o.wait(waitTime2);
			o.wait(waitTime2 * 2);
			o.keyClick(KeyEvent.VK_LEFT);
			//waitAndPerformSpecificTurn(16,12, KeyEvent.VK_LEFT, 2);
		}
	}
	
	
}
