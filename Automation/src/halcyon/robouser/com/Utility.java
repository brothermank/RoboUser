package halcyon.robouser.com;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Arrays;

import javax.swing.JPanel;

public abstract class Utility {

	
	public static void addToPanelAtWeighted(JPanel p, Component c, int posx, int posy, double weightx, double weighty) {
		addToPanelAt(p, c, posx, posy, GridBagConstraints.NORTHWEST, weightx, weighty);
	}
	public static void addToPanelAt(JPanel p, Component c, int posx, int posy) {
		addToPanelAt(p, c, posx, posy, GridBagConstraints.NORTHWEST);
	}
	public static void addToPanelAt(JPanel p, Component c, int posx, int posy, int anchor) {
		addToPanelAt(p, c, posx, posy, anchor, 1, 1);
	}
	public static void addToPanelAt(JPanel p, Component c, int posx, int posy, int anchor, double weightx, double weighty) {
		// Create grid bag constraint
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = posx;
		gbc.gridy = posy;
		gbc.anchor = anchor;
		gbc.insets = new Insets(2, 4, 2, 4);
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		
		p.add(c, gbc);
	}
}
