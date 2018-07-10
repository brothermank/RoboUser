package tinderbot;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import manke.automation.com.engine.GlobalKeyboardHook;
import manke.automation.com.engine.GlobalKeyboardListener;
import manke.automation.com.engine.Operator;

public class TinderBotEntry {
	
	
	Operator o;
	JPanel mainPanel;
	JFrame root;
	JButton calibrateB, startSwipeB, addKeywordB, removeKeywordB;
	JList keywords;
	DefaultListModel listModel;
	CalibrateDialogue calibration = new CalibrateDialogue(o);
	JComboBox actionNoMatch = new JComboBox(new String[] {"Like", "Dislike", "Pause"});
	JComboBox actionMatch = new JComboBox(new String[] {"Like", "Dislike", "Pause"});
	
	int[] ctrlc = {KeyEvent.VK_CONTROL, KeyEvent.VK_C};
	int[] ctrla = {KeyEvent.VK_CONTROL, KeyEvent.VK_A};
	
	volatile boolean stopswiping = false;
	volatile boolean swiping = false;
	boolean hotkeyLinked = false;
	
	int swipes = 0;
	int matches = 0;
	
	public TinderBotEntry(Operator o) {
		this.o = o;
	}
	
	public void run() {
		root = new JFrame("TinderBot9000");
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
        
		
		calibrateB = new JButton("Calibrate");
		startSwipeB = new JButton("Start Swiping!");
		addKeywordB = new JButton("Add Keyword!");
		removeKeywordB = new JButton("Remove Keyword!");
		

		calibrateB.addActionListener(e -> {calibrate();});
		startSwipeB.addActionListener(e -> {swipe();});
		addKeywordB.addActionListener(e -> {addKeyword();});
		removeKeywordB.addActionListener(e -> {removeKeyword();});
		
		listModel = new DefaultListModel();
		keywords = new JList(listModel);
		keywords.setVisibleRowCount(6);
		
		
		mainPanel.add(startSwipeB, gcButtons);
		gcButtons.gridx++;
		mainPanel.add(new JScrollPane(keywords));
		gcButtons.gridy++;
		mainPanel.add(removeKeywordB, gcButtons);
		gcButtons.gridx--;
		mainPanel.add(addKeywordB, gcButtons);
		gcButtons.gridy++;
		gcButtons.gridx++;
		mainPanel.add(calibrateB, gcButtons);
		gcButtons.gridy++;
		mainPanel.add(actionMatch, gcButtons);
		gcButtons.gridx--;
		mainPanel.add(new JLabel("Match Action:"), gcButtons);
		gcButtons.gridy++;
		mainPanel.add(new JLabel("No Match Action:"), gcButtons);
		gcButtons.gridx++;
		mainPanel.add(actionNoMatch, gcButtons);
		
		
		root.add(mainPanel);
		root.pack();
		
		removeKeywordB.setEnabled(false);
		
		System.out.println("Done setting up gui");
		root.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
            	savePreferences();
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
            	if(!swiping) {
            		Thread t = new Thread(new Runnable() {

						@Override
						public void run() {
							swipe();
						}
            			
            		});
            		t.start();
            	}else {
            		stopswiping = true;
            	}
            	System.out.println("Pressed - stopswiping is now: " + stopswiping);
            }
        });
        

		o.wait(100);

		loadPreferences();
		
	}
	
	public void calibrate() {

		System.out.println("Calibrating");
		int result = JOptionPane.showConfirmDialog(null, calibration, "Calibration",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
		//listModel.addElement(dialogue.keyword.getText().toLowerCase());
		//removeKeywordB.setEnabled(true);
	}
	
	public void addKeyword() {
		System.out.println("Adding keyword");
		AddKeywordDialogue dialogue = new AddKeywordDialogue();
		int result = JOptionPane.showConfirmDialog(null, dialogue, "Test",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			addKeyword(dialogue.keyword.getText());
		} 
	}
	public void addKeyword(String keyword) {
		listModel.addElement(keyword.toLowerCase());
		removeKeywordB.setEnabled(true);
	}
	
	public void removeKeyword() {
		int index = keywords.getSelectedIndex();
		listModel.remove(index);
		
		if(listModel.getSize() == 0) {
			removeKeywordB.setEnabled(false);
		}
		else { //Select an index.
	        if (index == listModel.getSize()) {
	            //removed item in last position
	            index--;
	        }
	        keywords.setSelectedIndex(index);
	        keywords.ensureIndexIsVisible(index);
	    }
	}
	
	public void swipe() {
		if(!swiping) {
			swiping = true;
			for(int i = 0; i < 10 || hotkeyLinked; i++){
				o.mouseClickAt(calibration.picturex, calibration.picturey, 100);
				o.wait(200);
				o.mouseClickAt(calibration.textselectx, calibration.textselecty, 100);
				o.mouseClickAt(calibration.textselectx, calibration.textselecty, 100);
				o.keyClick(ctrla, 100);
				o.keyClick(ctrlc, 100);
				String clipboard = o.getClipboard().toLowerCase();
				o.mouseClickAt(calibration.picturex, calibration.picturey, 100);
				o.wait(200);
				String s;
				if(hasKeyContents(clipboard) != -1) {
					System.out.println("Has key: " + listModel.getElementAt(hasKeyContents(clipboard)) + "   \nclipboard: " + clipboard);
					matches++;
				    if(actionMatch.getSelectedItem().equals("Like")) {
						o.mouseClickAt(calibration.likex, calibration.likey, 100);
				    } else if(actionMatch.getSelectedItem().equals("Dislike")) {
						o.mouseClickAt(calibration.dislikex, calibration.dislikey, 100);
					} else if(actionMatch.getSelectedItem().equals("Pause")) {
						stopswiping = true;
					}
				} else {
					swipes++;
				    if(actionNoMatch.getSelectedItem().equals("Like")) {
						o.mouseClickAt(calibration.likex, calibration.likey, 100);
				    } else if(actionNoMatch.getSelectedItem().equals("Dislike")) {
						o.mouseClickAt(calibration.dislikex, calibration.dislikey, 100);
					} else if(actionNoMatch.getSelectedItem().equals("Pause")) {
						stopswiping = true;
					}
				}
				
				if(stopswiping) {
					System.out.println("Stop swiping is true");
					break;
				}
				
			}
		}
		
		stopswiping = false;
		swiping = false;
	}
	
	public int hasKeyContents(String profile) {
		for(int i = 0; i < listModel.size(); i++) {
			if(profile.indexOf((String)(listModel.getElementAt(i))) != -1) {
				return i;
			}
		}
		return -1;
	}
	
	public void savePreferences() {
		PrintWriter writer;
		try {
			writer = new PrintWriter("TinderBot9000Preferences.txt", "UTF-8");
			writer.println(calibration.likex + ";" + calibration.likey + ";" + calibration.dislikex + ";" + calibration.dislikey + ";" + calibration.picturex + ";" + calibration.picturey
					+ ";" + calibration.textselectx + ";" + calibration.textselecty + ";" + swipes +";" + matches + ";" + actionMatch.getSelectedIndex() + ";" 
					+ actionNoMatch.getSelectedIndex() + ";");
			for(int i = 0; i < listModel.getSize(); i++) {
				if(i == listModel.getSize() - 1) {
					writer.print(listModel.getElementAt(i));
				}
				else {
					writer.println(listModel.getElementAt(i));
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loadPreferences() {
		// FileReader reads text files in the default encoding.
        FileReader fileReader;
		try {
			fileReader = new FileReader("./TinderBot9000Preferences.txt");
	        BufferedReader bufferedReader = 
	            new BufferedReader(fileReader);
	        
	        String line;
	        if((line = bufferedReader.readLine()) != null) {
	        	try {
		        	int a = 0; int b = line.indexOf(";");
		        	calibration.likex = Integer.parseInt(line.substring(a, b));
		        	a = b + 1; b = line.indexOf(";", a);
		        	calibration.likey = Integer.parseInt(line.substring(a, b));
		        	a = b + 1; b = line.indexOf(";", a);
		        	calibration.dislikex = Integer.parseInt(line.substring(a, b));
		        	a = b + 1; b = line.indexOf(";", a);
		        	calibration.dislikey = Integer.parseInt(line.substring(a, b));
		        	a = b + 1; b = line.indexOf(";", a);
		        	calibration.picturex = Integer.parseInt(line.substring(a, b));
		        	a = b + 1; b = line.indexOf(";", a);
		        	calibration.picturey = Integer.parseInt(line.substring(a, b));
		        	a = b + 1; b = line.indexOf(";", a);
		        	calibration.textselectx = Integer.parseInt(line.substring(a, b));
		        	a = b + 1; b = line.indexOf(";", a);
		        	calibration.textselecty = Integer.parseInt(line.substring(a, b));
		        	a = b + 1; b = line.indexOf(";", a);
		        	swipes = Integer.parseInt(line.substring(a, b));
		        	a = b + 1; b = line.indexOf(";", a);
		        	matches = Integer.parseInt(line.substring(a, b));
		        	a = b + 1; b = line.indexOf(";", a);
		        	actionMatch.setSelectedIndex(Integer.parseInt(line.substring(a, b)));
		        	a = b + 1; b = line.indexOf(";", a);
		        	actionNoMatch.setSelectedIndex(Integer.parseInt(line.substring(a, b)));
	        	} catch (StringIndexOutOfBoundsException e){
	        		System.out.println("Save file incomplete");
	        	}
	        }
	        while((line = bufferedReader.readLine()) != null) {
	        	addKeyword(line);
	        	System.out.println("Adding element: " + line);
	        }   
	        
	        bufferedReader.close();         
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        // Always close files.
	}
}
