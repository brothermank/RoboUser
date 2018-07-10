package manke.automation.com.engine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SnakeMouseEventListener implements MouseListener {
	
	public int x, y;
	public boolean clicked = false;
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		x = arg0.getX();
		y = arg0.getY();
		clicked = true;
	}

}
