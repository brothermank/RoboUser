package overwatch_frame_test;

import java.awt.event.KeyEvent;

import manke.automation.com.engine.GlobalKeyboardHook;
import manke.automation.com.engine.GlobalKeyboardListener;
import manke.automation.com.engine.Operator;

public class OverwatchFT {
	
	boolean hotkeyLinked = false;
	boolean testRunning = false;
	Operator o;
	
	public OverwatchFT(Operator o) {
		this.o = o;
		
		GlobalKeyboardHook hook = new GlobalKeyboardHook();

        int vitrualKey = KeyEvent.VK_F10;
        boolean CTRL_Key = false;
        boolean ALT_Key = false;
        boolean SHIFT_Key = false;
        boolean WIN_Key = false;
        
        try {
        	hook.setHotKey(vitrualKey, ALT_Key, CTRL_Key, SHIFT_Key, WIN_Key);
        	hotkeyLinked = true;
        } catch(UnsatisfiedLinkError e) {
        	hotkeyLinked = false;
        }
        hook.startHook();
        
        hook.addGlobalKeyboardListener(new GlobalKeyboardListener() {
            public void onGlobalHotkeysPressed() {
            	if(!testRunning) {
            		testRunning = true;
            		Thread t = new Thread(new Runnable() {

						@Override
						public void run() {
							doTest();
						}
            			
            		});
            		t.start();
            	}else {
            		testRunning = false;
            	}
            }
        });
	}
	
	private void doTest() {
		while(testRunning) {
			o.wait(500);
			for(int i = 0; i < 100; i++) {
				o.r.mouseMove(0, 400);
				o.wait(1);
			}
			o.wait(500);
			for(int i = 99; i >= 0; i--) {
				o.r.mouseMove(1300, 400);
				o.wait(1);
			}
		}
	}
}
