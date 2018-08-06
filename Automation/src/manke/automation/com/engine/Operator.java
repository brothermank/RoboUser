package manke.automation.com.engine;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.AWTEventListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JTextField;


public class Operator {
	
	public static Robot r;
	
	public Operator() {
        try {
			r = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void wait(int x) {
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void mouseMove(int x, int y) {
		r.mouseMove(x, y);
	}
	
	public static void mouseClick() {
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	public static void mouseClick(int x) {
		r.mousePress(InputEvent.BUTTON1_MASK);
		wait(x);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	public static void mouseClickAt(int posx, int posy, int time) {
		r.mouseMove(posx, posy);
		r.mousePress(InputEvent.BUTTON1_MASK);
		wait(time);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	public static void keyClick(int x) {
		r.keyPress(x);
		r.keyRelease(x);
	}
	public static void keyClick(int x, int time) { //Time in milliseconds
		r.keyPress(x);
		wait(time);
		r.keyRelease(x);
	}
	public static void keyClick(int[] keys, int time) { //Time in milliseconds
		for(int i = 0; i < keys.length; i++) {
			r.keyPress(keys[i]);
		}
		wait(time);
		for(int i = 0; i < keys.length; i++) {
			r.keyRelease(keys[i]);
		}
	}
	
	public static void pasteString(String s) {
		StringSelection stringSelection = new StringSelection(s);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		try {
		clipboard.setContents(stringSelection, stringSelection);
		} catch (IllegalStateException e){
			System.out.println("Cannot set cliboard contents with String: " + s + " (String selection: " + stringSelection + ")");
		}
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	public static void typeString(String s, int pressTime, int delayTime) {
		for(int i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
			case '\\':
				switch(s.charAt(i + 1)) {
				case 'a':
					r.keyPress(KeyEvent.VK_CONTROL);
					keyClick('A', pressTime);
					r.keyRelease(KeyEvent.VK_CONTROL);
					i++;
					break;
				case 'c':
					r.keyPress(KeyEvent.VK_CONTROL);
					keyClick('C', pressTime);
					r.keyRelease(KeyEvent.VK_CONTROL);
					i++;
					break;
				case 'd':
					keyClick(KeyEvent.VK_PAGE_DOWN, pressTime);
					i++;
					break;
				case 'e':
					keyClick(KeyEvent.VK_ENTER, pressTime);
					i++;
					break;
				case 'u':
					keyClick(KeyEvent.VK_PAGE_UP, pressTime);
					i++;
					break;
				case 'v':
					r.keyPress(KeyEvent.VK_CONTROL);
					keyClick('V', pressTime);
					r.keyRelease(KeyEvent.VK_CONTROL);
					i++;
					break;
				default:
					altClick(KeyEvent.VK_NUMPAD0, KeyEvent.VK_NUMPAD0, KeyEvent.VK_NUMPAD9, KeyEvent.VK_NUMPAD2);
					break;
				}
				break;
			case '/':
				altClick(KeyEvent.VK_NUMPAD0, KeyEvent.VK_NUMPAD0, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD7);
				break;
			default:
				typeCharacter(s.charAt(i), pressTime);
				break;
			}
			wait(delayTime);
		}
	}
	public static void typeCharacter(char c, int pressTime) {
        if (Character.isUpperCase(c)) {
            r.keyPress(KeyEvent.VK_SHIFT);
        }
        r.keyPress(Character.toUpperCase(c));
        wait(pressTime);
        r.keyRelease(Character.toUpperCase(c));
        if (Character.isUpperCase(c)) {
            r.keyRelease(KeyEvent.VK_SHIFT);
        }
	}
	public static void altClick(int event1, int event2, int event3, int event4){

	        r.keyPress(KeyEvent.VK_ALT);

	            r.keyPress(event1);
	            r.keyRelease(event1);

	            r.keyPress(event2);
	            r.keyRelease(event2);

	            r.keyPress(event3);
	            r.keyRelease(event3);

	            r.keyPress(event4);
	            r.keyRelease(event4);

	        r.keyRelease(KeyEvent.VK_ALT);

	}

	public static Point getMousePos() {
		Point p = MouseInfo.getPointerInfo().getLocation();
		return p;
	}
	
	public static BufferedImage takeScreenshot() {
		BufferedImage image = r.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		return image;
	}
	
	public static void pressTerminate() {
		r.mouseMove(837, 612);
		mouseClick(20);
	}
	
	public static String getClipboard() {

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		String result;
		try {
			result = (String) clipboard.getData(DataFlavor.stringFlavor);
			return result;
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static void getPositionOfNextClick(JTextField posxDest, JTextField posyDest) {
		posxDestination = posxDest;
		posyDestination = posyDest;	

		Toolkit.getDefaultToolkit().addAWTEventListener(
		          new ListenerOutsideClick(), AWTEvent.FOCUS_EVENT_MASK);
//		Toolkit.getDefaultToolkit().addAWTEventListener(
//		          new ListenerInsideClick(), AWTEvent.MOUSE_EVENT_MASK);
		
		count = 0;
	}
	
	private static JTextField posxDestination;
	private static JTextField posyDestination;
	private static int count = 0;
	
	private static class ListenerOutsideClick implements AWTEventListener{
		public void eventDispatched(AWTEvent event) {
			if(posxDestination != null && posyDestination != null) {
				posxDestination.setText("" + (int)(MouseInfo.getPointerInfo().getLocation().getX()));
				posyDestination.setText("" + (int)(MouseInfo.getPointerInfo().getLocation().getY()));
				posxDestination = null;
				posyDestination = null;
			}
		}
	}
	
	private boolean mouseClicked = false;
	
//	private static class ListenerInsideClick implements AWTEventListener{
//		public void eventDispatched(AWTEvent event) {
//			if(posxDestination != null && posyDestination != null && count == 10 && mouseClicked != (MouseEvent ) {
//				posxDestination.setText("" + (int)(MouseInfo.getPointerInfo().getLocation().getX()));
//				posyDestination.setText("" + (int)(MouseInfo.getPointerInfo().getLocation().getY()));
//				posxDestination = null;
//				posyDestination = null;
//			}
//			else count++;
//		}
//	}
	
}
