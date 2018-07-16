package halcyon.robouser.com;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPanel;

public abstract class Utility {

	public static void addToPanelAt(JPanel p, Component c, int posx, int posy) {
		// Create grid bag constraint
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = posx;
		gbc.gridy = posy;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 4, 2, 4);
		gbc.weightx = 0.5;
		gbc.weighty = 1;
		
		p.add(c, gbc);
	}
	
}
