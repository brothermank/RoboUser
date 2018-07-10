package manke.automation.com.engine;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
	
}
