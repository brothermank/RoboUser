package halcyon.robouser.com;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class JListTest {

	
	public static void main(String[] args) {
		JFrame frame;       
		JPanel panel;       
		JList<JButton> list;
		JButton test2, t3, t4, t5;
		
		frame = new JFrame("RoboUser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0,0,500, 500);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		panel = new JPanel();
		test2 = new JButton("Test 2");
		t3 = new JButton("Test 3");
		t4 = new JButton("Test 4");
		t5 = new JButton("Test 5");
		//panel.setLayout(new GridLayout(0,1));
		
		list = new JList<JButton>();
		list.add(new JButton("Test"));
		list.add(t3);
		list.add(t4);
		list.add(t5);

		panel.add(test2);
		panel.add(list);
		frame.add(panel);
		
		frame.pack();
	}
	
}
