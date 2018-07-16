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
		System.out.println("Clicking key: " + x);
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
		clipboard.setContents(stringSelection, stringSelection);
		
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
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
