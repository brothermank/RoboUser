package Autoclicker;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import manke.automation.com.engine.GlobalKeyboardHook;
import manke.automation.com.engine.GlobalKeyboardListener;

public class AutoclickerEntry {

	JFrame root;
	JPanel mainPanel;
	JTextField contentText;
	
	boolean hotkeyLinked = false;
	
	AutoClickerEngine engine = new AutoClickerEngine();
	
	public void run() {
		root = new JFrame("Auto Clicker 5k");
		mainPanel = new JPanel();
		
		root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		root.setBounds(0,0,500, 500);
		
		root.setVisible(true);
		root.setLocationRelativeTo(null);
		
		root.setLayout(new GridLayout(1,0));
		
		GridBagConstraints gcParent = new GridBagConstraints();
		mainPanel.setLayout(new GridBagLayout());

		gcParent.gridx = 0;
		gcParent.gridy = 0;
		gcParent.anchor = GridBagConstraints.FIRST_LINE_START;
        gcParent.insets = new Insets(2, 4, 0, 0);
        gcParent.weightx = 0;
        gcParent.weighty = 0;
        
        //Buttons
		GridBagConstraints gcButtons = new GridBagConstraints();
		gcButtons.gridx = 0;
		gcButtons.gridy = 0;
		gcButtons.anchor = GridBagConstraints.WEST;
		gcButtons.insets = new Insets(2, 4, 0, 0);
		gcButtons.weightx = 0.5;
		gcButtons.weighty = 1;

		contentText = new JTextField("This is an autoclicker. Press F10 to toggle.");
		
		mainPanel.add(contentText, gcButtons);
		gcButtons.gridx++;
		
		root.add(mainPanel);
		root.pack();
		
		System.out.println("Done setting up gui");
		
		root.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
            	stopClicking();
                e.getWindow().dispose();
            }
        });
		
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
            	if(engine.running) {
            		stopClicking();
            	}else {
            		startClicking();
            	}
            	//System.out.println("Pressed - stopswiping is now: " + stopswiping);
            }
        });
		
	}
	
	void stopClicking() {
		engine.stopRunning();
	}
	void startClicking() {
		Thread t = new Thread(engine);
		t.start();
	}
}
