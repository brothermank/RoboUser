package halcyon.robouser.com.actionEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import halcyon.robouser.com.EInteger;
import halcyon.robouser.com.RoboUserMain;
import halcyon.robouser.com.actionEngine.actionTypes.ClickAction;
import halcyon.robouser.com.actionEngine.actionTypes.TypeAction;
import halcyon.robouser.com.actionEngine.actionTypes.WaitAction;
import manke.automation.com.engine.Operator;

public class SheetEditor {
	
	private enum AC_TYPE {number, char_, value};
	
	File sourceFile;
	int startLine, endLine;
	String allText;
	
	Operator o = RoboUserMain.o;
	
	final static String[] Items = {"FunctionE", "FunctionB", "Var", "Char"};
	final static int AC_Y = 290, AC_HEIGHT = 21, AC_SELECT_X = 215, AC_NUMBER_X = 230, AC_CHAR_X = 265, AC_VAL_X = 540, ITEMS_ON_PAGE = 29;

	List<String> characteristics = new ArrayList<String>();
	List<String> values = new ArrayList<String>();

	public SheetEditor(File sourceFile) {
		this.sourceFile = sourceFile;
		
	}
	
	private void initializeReader() {
		readFile();
		startLine = getKeywordInt("StartLine", allText);
		endLine = getKeywordInt("EndLine", allText);
	}

	public void editPISheet() {
		initializeReader();
		parsePISheet();
		performEdit();
	}
	
	private void readFile() {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(sourceFile));
			StringBuilder sb = new StringBuilder();
		    String line;
			try {
				
				line = br.readLine();
				
				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
				allText = sb.toString();
			    br.close();
			    
			} catch (IOException e) {}
			br.close();
		} catch (IOException e) {}
		    		    
	}
	
	private void parsePISheet() {
		characteristics = new ArrayList<String>();
		values = new ArrayList<String>();
		EInteger pos = new EInteger(0);
		EInteger itemPos = new EInteger(0);
		String line, item;
		
		while(!pos.equals(-1)) {
			line = getNextLine(allText, pos);
			itemPos.set(0);
			while(!itemPos.equals(-1)) {	
				item = getItem(line, itemPos);
				if(!itemPos.equals(-1)) {
					switch(item) {
					case "FunctionE":
						handleFunction(line, itemPos, 'E');
						break;
					case "FunctionB":
						handleFunction(line, itemPos, 'B');
						break;
					case "Var":
						handleVar(line, itemPos);
						break;
					case "Char":
						handleChar(line, itemPos);
						break;
					default:
						break;
					}
				}
			}	
		}
	}

	private void performEdit() {
		overWriteSection(startLine, endLine, characteristics, values);
	}
	
	private void writeToAC(AC_TYPE type, String value, int index) {

		int x = 0;
		switch (type) {
		case number:
			x = AC_NUMBER_X;
			break;
		case char_:
			x = AC_CHAR_X;
			break;
		case value:
			x = AC_VAL_X;
			break;
		default:
			break;
		}
		ClickAction selectField = new ClickAction(o, x, AC_Y + AC_HEIGHT * index, 15, 15, 1);
		TypeAction selectText = new TypeAction(o, "\\a", 15, 15);
		TypeAction writeText = new TypeAction(o, value, 0, 0);
		
		selectField.execute();
		selectText.execute();
		writeText.execute();
	}
	
	private void scrollToTop() {
		int nextNum = probeNum(0);
		int currentNum;
		do {
			currentNum = nextNum;
			pageUp();
		} while ((nextNum = probeNum(0)) != currentNum);
	}
	private void scrollToBottom() {
		int nextNum = probeNum(0);
		int currentNum;
		do {
			currentNum = nextNum;
			pageDown();
		} while ((nextNum = probeNum(0)) != currentNum);
	}
	
	private void pageUp() {
		TypeAction selectText = new TypeAction(o, "\\u", 30, 30);
		selectText.execute();
	}
	private void pageDown() {
		TypeAction selectText = new TypeAction(o, "\\d", 30, 30);
		selectText.execute();
	}
	
	private void clearSelection() {
		ClickAction pressClearSelection = new ClickAction(o, 240, 120, 30, 30, 1);
		pressClearSelection.execute();
	}
	
	private void deleteSelectedEntries() {
		ClickAction pressDeleteSelection = new ClickAction(o, 140, 120, 30, 30, 1);
		WaitAction wait100 = new WaitAction(o, 100);
		WaitAction wait500 = new WaitAction(o, 1000);
		TypeAction pressEnter = new TypeAction(o, "\\e", 30, 30);
		pressDeleteSelection.execute();
		wait100.execute();
		pressEnter.execute();
		wait500.execute();
	}
	private void saveChanges() {
		ClickAction pressSave = new ClickAction(o, 230, 55, 30, 30, 1);
		WaitAction wait500 = new WaitAction(o, 1000);
		pressSave.execute();
		wait500.execute();
		
	}
	
	private void toggleEntrySelected(int index) {
		ClickAction selectField = new ClickAction(o, AC_SELECT_X, AC_Y + AC_HEIGHT * index, 30, 30, 1);
		selectField.execute();
	}
	
	private void pasteEntries(String paste, boolean fullPaste) {
		ClickAction selectNum = new ClickAction(o, AC_NUMBER_X, AC_Y, 30, 30, 1);
		TypeAction pressEnter = new TypeAction(o, "\\e", 30, 30);
		WaitAction wait = new WaitAction(o, 1000);
		WaitAction wait2 = new WaitAction(o, 100);
		
		selectNum.execute();
		Operator.pasteString(paste);
		pressEnter.execute();
		wait.execute();
		if(fullPaste) {
			pageUp();
			wait2.execute();
			selectNum.execute();
		}
		Operator.pasteString(paste);
		wait2.execute();
		if(fullPaste) {
			pageDown();
		}
	}
	
	private void goToMakeNewEntries() {
		ClickAction selectField = new ClickAction(o, 90, 125, 30, 100, 1);
		selectField.execute();
	}
	
	private void overWriteSection(int startNum, int endNum, List<String> characs, List<String> vals) {
		TypeAction pressEnter = new TypeAction(o, "\\e", 30, 30);
		WaitAction wait1 = new WaitAction(o, 100);
		WaitAction wait2 = new WaitAction(o, 10);
		
		clearSelection();
		scrollToTop();
		int nextNum = probeNum(0);
		int currentNum = -1;
		boolean entriesSelected = false;
		do {
			for(int i = 0; i < ITEMS_ON_PAGE && currentNum != nextNum; i++) {
				currentNum = nextNum;
				if(currentNum >= startNum && currentNum <= endNum) {
					toggleEntrySelected(i);
					entriesSelected = true;
					wait2.execute();
				}
				nextNum = probeNum(i + 1);
			}
			pageDown();
			if(currentNum == nextNum) break;
		} while ((nextNum = probeNum(0)) != currentNum);
		if(entriesSelected) {
			deleteSelectedEntries();
			saveChanges();
		}
		
		int interval = 0;
		int range = endNum - startNum;
		if(range / 10 >= characs.size()) interval = 10;
		else if (range / 5 >= characs.size()) interval = 5;
		else if (range >= characs.size()) interval = range / characs.size();
		else {
			System.out.println("No space in interval");
			return;
		}
		
		goToMakeNewEntries();
		int index = 0;
		String paste;
		for(int i = 0; i < characs.size(); i += ITEMS_ON_PAGE - 1, index++) {
			if(characs.size() >= i + ITEMS_ON_PAGE) {
				paste = createPaste(startNum + interval * i, interval, characs.subList(i, i + ITEMS_ON_PAGE), values.subList(i, i + ITEMS_ON_PAGE));
				pasteEntries(paste, true);
			}
			else {
				paste = createPaste(startNum + interval * i, interval, characs.subList(i, characs.size()), 	  values.subList(i, values.size()));
				pasteEntries(paste, false);
			}
			
			
//			String num = "" + (startNum + interval * i);
//			writeToAC(AC_TYPE.number, num, index);
//			writeToAC(AC_TYPE.char_, characs.get(i), index);
//			if((i - 1) % (ITEMS_ON_PAGE - 1) == 0 && (i - 1 != 0)) {
//				pressEnter.execute();
//				index = 0;
//			}
		}
		pressEnter.execute();
		
	}
	
	private int probeNum(int index) {
		ClickAction selectField = new ClickAction(o, AC_NUMBER_X, AC_Y + AC_HEIGHT * index, 30, 30, 1);
		TypeAction copyTextToClipboard = new TypeAction(o, "\\a\\c", 30, 30);
		
		selectField.execute();
		copyTextToClipboard.execute();
		
		try {
			return Integer.parseInt(Operator.getClipboard());
		} catch (NumberFormatException e) {
		    return -1;
		}
	}
	
	private String createPaste(int startIndex, int interval, List<String> characs, List<String> values) {
		String s = "";
		for(int i = 0; i < characs.size(); i++) {
			if(i != 0) s += "\n";
			s += (startIndex + interval * i) + "\t" + characs.get(i) + "\t" + values.get(i);
		}
		return s;
	}
	
	private void addEntry(String characteristic, String value) {
		characteristics.add(characteristic.trim());
		values.add(value.trim());
	}
	
	private void handleChar(String cha, EInteger itemPos) {
		//int argumentStart = itemPos.get();
		String argument = getParenthesisContent(cha, itemPos);
		
		String[] split = splitString("=", argument);
		addEntry(split[0], split[1]);
	}
	private void handleVar(String var, EInteger itemPos) {
		int argumentStart = itemPos.get();
		String argument = getParenthesisContent(var, itemPos);

		addEntry("PPPI_VARIABLE", getKeywordString("Name", var));
		
		EInteger index = new EInteger(0);
		String type = getKeywordString("Type", var, index);
		if(type != "" && !index.equals(-1)) {
			addEntry(type, "");
		}else {
			index.set(0);
			String defaultValue = getKeywordString("DVal", var, index);
			if(defaultValue != "" && !index.equals(-1)) {
				addEntry("PPPI_DEFAULT_VALUE", defaultValue);
			} else {
				addEntry("PPPI_DEFAULT_VALUE", "0");
			}
		}
		
	}
	
	private void handleFunction(String func, EInteger itemPos, char type) {
		int argumentStart = itemPos.get();
		String argument = getParenthesisContent(func, itemPos);
		
		String name = getKeywordString("Name", func);
		addEntry("PPPI_FUNCTION_NAME", name);
		
		switch(type) {
		case 'E':
			String event = getKeywordString("Event", func);
			addEntry("PPPI_EVENT", event);
			break;
		case 'B':
			String buttonName = getKeywordString("Button", func);
			addEntry("PPPI_BUTTON_TEXT", buttonName);
		}
		
		EInteger pos = new EInteger(argumentStart);
		List<String[]> variables = new ArrayList<String[]>();
		
		while(!pos.equals(-1)) {
			String keyword = getKeywordString("Evar", func, pos);
			String[] temp = {"E", keyword};
			variables.add(temp);
		}
		pos.set(0);
		while(!pos.equals(-1)) {
			String keyword = getKeywordString("Ivar", func, pos);
			String[] temp = {"I", keyword};
			variables.add(temp);
		}
		pos.set(0);
		while(!pos.equals(-1)) {
			String keyword = getKeywordString("Ovar", func, pos);
			String[] temp = {"O", keyword};
			variables.add(temp);
		}
		
		for(int i = 0; i < variables.size(); i++) {

			String[] split = splitString("=", variables.get(i)[1]);
			addEntry("PPPI_EXPORT_PARAMETER", split[0]);
			String var = "PPPI_";
			switch(split[1].substring(0, 1)) {
				case "S": var += "STRING_"; break;
				case "F": var += "FLOAT_"; break;
				case "D": var += "DATE_"; break;
				case "T": var += "TIME_"; break;
				default: break;
			}
			switch(split[1].substring(1, 2)) {
				case "C": var += "CONSTANT"; break;
				case "V": var += "VARIABLE"; break;
				default: break;
			}

			switch(variables.get(i)[0]) {
				case "E": 
					addEntry("PPPI_EXPORT_PARAMETER", split[0]); 
					addEntry(var, split[1].substring(3)); 
					break;
				case "I": 
					addEntry("PPPI_IMPORT_PARAMETER", split[0]); 
					addEntry(var, split[1].substring(3)); 
					break;
				case "O": 
					addEntry("PPPI_EXPORT_PARAMETER", split[0]);  
					addEntry(var, split[1].substring(3)); 
					addEntry("PPPI_OPTIONAL_PARAMETER", "Parameter transfer optional");
					break;
			}
		}
	}

	private String[] splitString(String split, String target) {
		String[] output = new String[2];
		int a = target.indexOf(split);
		if(a == -1) {
			output[0] = target;
			output[1] = "";
			return output;
		}
		output[0] = target.substring(0, a);
		output[1] = target.substring(a + split.length());
		return output;
	}
	
	private String getParenthesisContent(String func, EInteger pStart) {
		System.out.println("Func: " + func);
		String content = "";
		pStart.set(pStart.get() + 1);
		EInteger i = pStart;
		int end;
		i.set(func.indexOf("(") + 1);
		if(func.indexOf(")",i.get()) == 0) {
			content += func.substring(pStart.get(), i.get());
			content += getParenthesisContent(func, i);
			end = func.indexOf(")", i.get());
			content += func.substring(i.get(), end);
		}
		else {
			end = func.indexOf(")", pStart.get());
			content += func.substring(pStart.get(), end);
		}
		pStart.set(end + 1);
		return content;
		
	}
	
	private String getItem(String target, EInteger pos){
		int closest = Integer.MAX_VALUE;
		String item = "";
		int index;
		for(int i = 0; i < Items.length; i++) {
			index = target.indexOf(Items[i], pos.get()); 
			if(index < closest && index != -1) {
				closest = index;
				item = Items[i];
			}
		}
		if(closest == Integer.MAX_VALUE) closest = -1;
		pos.set(closest);
		return item;
	}
	
	private String getNextLine(String target, EInteger startIndex) {
		int i = startIndex.get();
		startIndex.set(target.indexOf(";", startIndex.get()) + 1);
		if(!startIndex.equals(0)) return target.substring(i, startIndex.get());
		else {
			startIndex.set(-1);
			return "";
		}
	}
	
	public int[] getKeywordBoundaries(String keyword, String target, EInteger start) {
		int[] res = new int[2];
		res[0] = target.indexOf("<" + keyword + ":", start.get()) + keyword.length() + 2;
		res[1] = target.indexOf(">", res[0]);
		if(res[0] == -1 || res[1] == -1) start.set(-1);
		else start.set(res[1] + 1);
		return res;
	}
	public int[] getKeyworContentBoundaries(String keyword, String target) {
		int[] res = new int[2];
		res[0] = target.indexOf("<" + keyword + ":") + keyword.length() + 2;
		res[0] = target.indexOf(">", res[0]) + 1;
		res[1] = target.indexOf("</" + keyword + ">", res[0]);
		return res;
		
	}
	public int getKeywordInt(String keyword, String target) {
		int[] limit = getKeywordBoundaries(keyword, target, new EInteger(0));
		return Integer.parseInt(target.substring(limit[0], limit[1]));
	}
	public String getKeywordString(String keyword, String target) {
		int[] limit = getKeywordBoundaries(keyword, target, new EInteger(0));
		return target.substring(limit[0], limit[1]);
	}
	public String getKeywordString(String keyword, String target, EInteger start) {
		int[] limit = getKeywordBoundaries(keyword, target, start);
		return target.substring(limit[0], limit[1]);
	}
	public double getKeywordDouble(String keyword, String target) {
		int[] limit = getKeywordBoundaries(keyword, target, new EInteger(0));
		return Double.parseDouble(target.substring(limit[0], limit[1]));
	}
	
	public String getKeywordContentString(String keyword, String target) {
		int[] limit = getKeyworContentBoundaries(keyword, target);
		return target.substring(limit[0], limit[1]);
	}
	
	
}
