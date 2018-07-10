package tinderbot;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddKeywordDialogue extends JPanel {

	public JTextField 	keyword = new JTextField();
	
	public AddKeywordDialogue() {
		
		JLabel 	keywordL = new JLabel("Keyword: ");
		
		setLayout(new GridLayout(0, 2));
		
		add(keyword);
		
	}
}
