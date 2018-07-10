package manke.automation.com.engine;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.event.KeyEvent;

import Autoclicker.AutoclickerEntry;
import overwatch_frame_test.OverwatchFT;
import tinderbot.TinderBotEntry;


//Pwd for Jeff Travels: 4352

public class Main {

	public static int currentPos = 0, currentID = 0;
	static boolean stop = false, hotkeyLinked;
	
	
	public static Operator o;
	
	public static void main( String[] args ) throws AWTException, InterruptedException {
        System.out.println( "Hello World!" );
        o = new Operator();
        
        //getMousePos(1500);
        //runSnake();
        //runTinderBot();  
       // script1(0);
       //runOverwatchFT(); 
        runAutoclicker();
    }
	
	public static void getMousePos(int delay) {
		Point p = o.getMousePos();
		System.out.println("Pos: " + p.x + "," + p.y);
	}
	
	public static void runAutoclicker() {
		AutoclickerEntry ace = new AutoclickerEntry();
		ace.run();
	}
	public static void runOverwatchFT() {
		OverwatchFT oft = new OverwatchFT(o);
		while(0 < 1) {}
	}
	
	public static void runSnake() {
		SnakeAutomated s = new SnakeAutomated(o);
		s.mainLoop();
	}
	
	public static void runTinderBot() {
		TinderBotEntry tb = new TinderBotEntry(o);
		tb.run();
	}
	
	public static void script1(int startID) {
		GlobalKeyboardHook hook = new GlobalKeyboardHook();

        int vitrualKey = KeyEvent.VK_F10;
        boolean CTRL_Key = false;
        boolean ALT_Key = false;
        boolean SHIFT_Key = false;
        boolean WIN_Key = false;
        
        try {
        	hook.setHotKey(vitrualKey, ALT_Key, CTRL_Key, SHIFT_Key, WIN_Key);
        	hotkeyLinked = true;
        	System.out.println("Hotkey Linked");
        } catch(UnsatisfiedLinkError e) {
        	System.out.println("Hotkey Not Linked");
        	hotkeyLinked = false;
        }
        hook.startHook();
        
        hook.addGlobalKeyboardListener(new GlobalKeyboardListener() {
			@Override
			public void onGlobalHotkeysPressed() {
            	System.out.println("Press");
            	System.exit(0);
				
			}
        });
		
		currentID = startID;
		
		for(int i = 0; i < 10 - (startID % 10); i++) {
			acceptEntry();
			incrementNr(1);
		}
		for(int k = 0; k < 10 - ((startID / 10) % 10); k++) {
			o.pressTerminate();
			incrementNr(2);
			for(int i = 0; i < 10; i++) {
				acceptEntry();
				incrementNr(1);
			}
		}

		for(int j = 0; j < 10 - ((startID / 100) % 10); j++) {
			incrementNr(3);
			for(int k = 0; k < 10 - ((startID / 10) % 10); k++) {
				o.pressTerminate();
				incrementNr(2);
				for(int i = 0; i < 10; i++) {
					acceptEntry();
					incrementNr(1);
				}
			}
		}
		script1();
	}
	
	public static void script1() {
		for(int k = 0; k < 10; k++) {
			for(int l = 0; l < 10; l++) {
				for(int j = 0; j < 10; j++) {
					for(int i = 0; i < 10; i++) {
						acceptEntry();
						incrementNr(1);
					}
					o.pressTerminate();
					incrementNr(2);
				}
				incrementNr(3);
			}
			incrementNr(4);
		}
		
	}
	
	public static void incrementNr(int x) {
		//int xn1 = 640, xn2 = 665, xn3 = 690, xn4 = 715, y1 = 550,
		//		xa1 = 610, xa2 = 660, y2 = 500;
		int xn1 = 890, xn2 = 935, xn3 = 970, xn4 = 995, y1 = 655,
				xa1 = 865, xa2 = 920, y2 = 685;
		o.wait(220);
		if(currentPos != x) {
		switch(x) {
			case 1: 	o.r.mouseMove(xn1, y1);
						break;
			case 2: 	o.r.mouseMove(xn2, y1);
						break;
			case 3: 	o.r.mouseMove(xn3, y1);
						break;
			case 4: 	o.r.mouseMove(xn4, y1);
						break;
			}
			o.wait(50);
			o.mouseClick(50);
			o.wait(50);
			currentPos = x;
		}

		o.r.mouseMove(xa2, y2);
		o.mouseClick(50);
		
		System.out.println("" + currentID);
		currentID++;
	}
	public static void acceptEntry() {
		int xa3 = 990, y2 = 591;
		o.r.mouseMove(xa3, y2);
		o.mouseClick(50);
		o.keyClick(87, 50);
		if(!hotkeyLinked) System.exit(1);
	}
	
	
	
	
}
