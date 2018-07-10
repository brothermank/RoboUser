package tinderbot;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import manke.automation.com.engine.Operator;

public class CalibrateDialogue extends JPanel {
	
	private enum ActionType {none, like, dislike, image, text};
	
	JTextField instructions;
	JButton proceedeB, calibrateLikeB, calibrateDislikeB, calibrateImageB, calibrateTextB;
	
	boolean proceedeBoolean = false;
	
	public int likex, likey, dislikex, dislikey, textselecty, textselectx, picturex, picturey;
	
	Operator o;
	
	ActionType queuedActiontype = ActionType.none;
	
	public CalibrateDialogue(Operator o) {
			this.o = o;
		
			instructions = new JTextField();
			instructions.setPreferredSize(new Dimension(300, 80));
			proceedeB = new JButton("Proceede");
			calibrateLikeB = new JButton("Calibrate Like Button");
			calibrateDislikeB = new JButton("Calibrate Dislike Button");
			calibrateImageB = new JButton("Calibrate Image");
			calibrateTextB = new JButton("Calibrate Text Select");			

			proceedeB.addActionListener(e -> {proceede();});
			calibrateLikeB.addActionListener(e -> {getLikePos();});
			calibrateDislikeB.addActionListener(e -> {getDislikePos();});
			calibrateImageB.addActionListener(e -> {getImagePos();});
			calibrateTextB.addActionListener(e -> {getTextPos();});
			
			setLayout(new GridLayout(0, 2));
			
			add(instructions);
			add(proceedeB);
			add(calibrateLikeB);
			add(calibrateDislikeB);
			add(calibrateImageB);
			add(calibrateTextB);
			
	}
	
	private void proceede() {
		proceedeBoolean = true;
		switch(queuedActiontype) {
		case like:
			getLikePos2();
			break;
		case dislike:
			getDislikePos2();
			break;
		case image:
			getImagePos2();
			break;
		case text:
			getTextPos2();
			break;
		default:
			break;
		}
		queuedActiontype = ActionType.none;
	}
	
	private void getLikePos(){
		proceedeBoolean = false;
		instructions.setText("When you press Proceede, you have two seconds to put your mouse over the LIKE BUTTON to record the position.");
		queuedActiontype = ActionType.like;
	}
	private void getLikePos2() {
		o.wait(2000);
		Point p = o.getMousePos();
		
		likex = p.x;
		likey = p.y;
		instructions.setText("Good job!");
	}
	private void getDislikePos() {
		proceedeBoolean = false;
		instructions.setText("When you press Proceede, you have two seconds to put your mouse over the DISLIKE BUTTON to record the position.");
		queuedActiontype = ActionType.dislike;
	}
	private void getDislikePos2() {
		o.wait(2000);
		Point p = o.getMousePos();
		
		dislikex = p.x;
		dislikey = p.y;
		instructions.setText("You got it!");
		System.out.println("X: " +  p.x + "  Y: " +p.y);
	}
	private void getImagePos() {
		proceedeBoolean = false;
		instructions.setText("When you press Proceede, you have two seconds to put your mouse over the APPROXIMATE MIDDLE OF THE GIRLS IMAGE in browsing mode to record the position.");
		queuedActiontype = ActionType.image;
	}
	private void getImagePos2() {
		o.wait(2000);
		Point p = o.getMousePos();
		
		picturex = p.x;
		picturey = p.y;
		instructions.setText("<3");
		
	}
	private void getTextPos() {
		proceedeBoolean = false;
		instructions.setText("When you press Proceede, you have three seconds to put your mouse over the FIRST CHARACTER OF THE GIRLS NAME in inpsection mode to record the position.");
		queuedActiontype = ActionType.text;
	} private void getTextPos2() {
		o.wait(3000);
		Point p = o.getMousePos();
		
		textselectx = p.x;
		textselecty = p.y;
		instructions.setText("Ahh yes, thats the spot. Right there.");
	}
		
	
	
	private void waitForProceede() {
		while(!proceedeBoolean) {
			o.wait(200);
		}
	}

}
