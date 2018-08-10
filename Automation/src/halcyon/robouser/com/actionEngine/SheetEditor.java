package halcyon.robouser.com.actionEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	final static String[] Items = {"FunctionE", "FunctionB", "Var", "Char", "Repeat", "Integer"};
	private List<String> keyWords;
	final static int AC_Y = 290, AC_HEIGHT = 21, AC_SELECT_X = 215, AC_NUMBER_X = 230, AC_CHAR_X = 265, AC_VAL_X = 540, ITEMS_ON_PAGE = 29;

	Map<String, Integer> variables = new HashMap<String, Integer>();
	List<String> keys = new ArrayList<String>();
	
	List<String> characteristics = new ArrayList<String>();
	List<String> values = new ArrayList<String>();

	public SheetEditor() {}
	public SheetEditor(File sourceFile) {
		this.sourceFile = sourceFile;	
	}
	
	private void initializeReader() {
		readFile();
		startLine = getKeywordInt("StartLine", allText);
		endLine = getKeywordInt("EndLine", allText);
		keyWords = new ArrayList<String>(Arrays.asList(Items));
	}

	public void editPISheet() {
		initializeReader();
		parsePISheet();
		performEdit();
	}
	
	public void clearRangeFromFile() {
		initializeReader();
		parsePISheet();
		clearRange(startLine, endLine);
	}
	
	public void enterFromFile() {
		initializeReader();
		parsePISheet();
		enterFromFile(startLine, endLine, characteristics, values);
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
		parseString(allText);
	}

	private void parseString(String s) {
		EInteger pos = new EInteger(0);
		EInteger itemPos = new EInteger(0);
		String line, item;
		
		while(!pos.equals(-1)) {
			line = getNextLine(s, pos);
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
					case "Repeat":
						handleRepeat(line, itemPos);
						break;
					case "Integer":
						handleInteger(line, itemPos);
						break;
					default:
						handleVariable(item, line, itemPos);
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
		WaitAction wait = new WaitAction(o, 2000);
		wait.execute();
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
		WaitAction wait500 = new WaitAction(o, 2000);
		TypeAction pressEnter = new TypeAction(o, "\\e", 30, 30);
		wait100.execute();
		pressDeleteSelection.execute();
		wait100.execute();
		pressEnter.execute();
		wait500.execute();
	}
	private void saveChanges() {
		ClickAction pressSave = new ClickAction(o, 230, 55, 30, 30, 1);
		WaitAction wait500 = new WaitAction(o, 2000);
		pressSave.execute();
		wait500.execute();
		
	}
	
	private void toggleEntrySelected(int index) {
		ClickAction selectField = new ClickAction(o, AC_SELECT_X, AC_Y + AC_HEIGHT * index, 10, 20, 1);
		selectField.execute();
	}
	
	private void pasteEntries(String paste, boolean fullPaste) {
		ClickAction selectNum = new ClickAction(o, AC_NUMBER_X, AC_Y, 30, 30, 1);
		TypeAction pressEnter = new TypeAction(o, "\\e", 30, 30);
		WaitAction wait = new WaitAction(o, 2000);
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
	
	public void clearRange(int startNum, int endNum) {
		WaitAction wait = new WaitAction(o, 10);
		WaitAction wait2 = new WaitAction(o, 100);
		int nextNum = probeNum(0);
		int currentNum = -1;
		boolean entriesSelected = false;
		wait2.execute();
		do {
			for(int i = 0; i < ITEMS_ON_PAGE - 1 && currentNum != nextNum; i++) {
				currentNum = nextNum;
				if(currentNum >= startNum && currentNum <= endNum) {
					toggleEntrySelected(i);
					entriesSelected = true;
					wait.execute();
				}
				if(currentNum >= endNum) break;
				nextNum = probeNum(i + 1);
			}
			pageDown();
			wait2.execute();
			if(			currentNum == nextNum
					|| 	currentNum >= endNum) break;
		} while ((nextNum = probeNum(0)) != currentNum && currentNum < endNum);
		if(entriesSelected) {
			deleteSelectedEntries();
		}
	}
	
	private void enterFromFile(int startNum, int endNum, List<String> characs, List<String> vals) {
		TypeAction pressEnter = new TypeAction(o, "\\e", 30, 30);
		int index = 0;

		int interval = 0;
		int range = endNum - startNum;
		if(range / 10 >= characs.size()) interval = 10;
		else if (range / 5 >= characs.size()) interval = 5;
		else if (range >= characs.size()) interval = range / characs.size();
		else {
			System.out.println("No space in interval");
			return;
		}
		
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
	
	private void overWriteSection(int startNum, int endNum, List<String> characs, List<String> vals) {
		
		//clearSelection();
		scrollToTop();
		clearRange(startNum, endNum);
		saveChanges();
		
		goToMakeNewEntries();
		
		enterFromFile(startNum, endNum, characs, vals);
		
	}
	
	private int probeNum(int index) {
		ClickAction selectField = new ClickAction(o, AC_NUMBER_X, AC_Y + AC_HEIGHT * index, 10, 15, 1);
		TypeAction copyTextToClipboard = new TypeAction(o, "\\a\\c", 10, 1);
		
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
		for(int i = 0; i < keys.size(); i++) {
			characteristic.replaceAll(keys.get(i), "" + variables.get(keys.get(i)));
			value.replaceAll(keys.get(i), "" + variables.get(keys.get(i)));
		}
		
		characteristics.add(characteristic.trim());
		values.add(value.trim());
	}
	
	private void addVar(String varName) {
		
	}
	
	private void handleChar(String cha, EInteger itemPos) {
		//int argumentStart = itemPos.get();
		String argument = getParenthesisContent(cha, itemPos);
		
		String[] split = splitString("=", argument);
		addEntry(split[0], split[1]);
	}

	private void handleInteger(String integer, EInteger itemPos) {
		//int argumentStart = itemPos.get();
		String argument = getParenthesisContent(integer, itemPos);
		keyWords.add(argument);
		keys.add(argument);
		variables.put(argument, 0);
	}
	
	private void handleVariable(String variable, String vContent, EInteger itemPos) {
		String argument = getParenthesisContent(vContent, itemPos);
		EInteger index = new EInteger(0);
		int value;
		value = getKeywordInt("=", argument, index);
		if(index.get() != -1) {
			variables.put(variable, value);
		}
		value = getKeywordInt("+", argument, index);
		if(index.get() != -1) {
			variables.put(variable, variables.get(variable) + value);
		}
		value = getKeywordInt("-", argument, index);
		if(index.get() != -1) {
			variables.put(variable, variables.get(variable) - value);
		}
		value = getKeywordInt("*", argument, index);
		if(index.get() != -1) {
			variables.put(variable, variables.get(variable) * value);
		}
		value = getKeywordInt("/", argument, index);
		if(index.get() != -1) {
			variables.put(variable, variables.get(variable) / value);
		}
	}
	
	private void handleRepeat(String repeat, EInteger itemPos) {
		//int argumentStart = itemPos.get();
		String argument = getParenthesisContent(repeat, itemPos);
		int times = getKeywordInt("Times", argument);
		String task = getCurlyBracesContent(repeat, itemPos);
		
		for(int i = 0; i < times; i++) {
			parseString(task);
		}
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
			if(!pos.equals(-1))	variables.add(temp);
		}
		pos.set(0);
		while(!pos.equals(-1)) {
			String keyword = getKeywordString("Ivar", func, pos);
			String[] temp = {"I", keyword};
			if(!pos.equals(-1))	variables.add(temp);
		}
		pos.set(0);
		while(!pos.equals(-1)) {
			String keyword = getKeywordString("Ovar", func, pos);
			String[] temp = {"O", keyword};
			if(!pos.equals(-1))	variables.add(temp);
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
		return getEnclosedContent("(", ")", func, pStart);
	}
	
	private String getCurlyBracesContent(String func, EInteger pStart) {
		return getEnclosedContent("{", "}", func, pStart);
	}
	
	private String getEnclosedContent(String beginKey, String endKey, String target, EInteger pStart) {
		String content = "";
		EInteger i = pStart;
		pStart.set(target.indexOf(beginKey) + 1);
		
		int otherBegin = target.indexOf(beginKey, pStart.get());
		int end = target.indexOf(endKey, pStart.get());
		if(otherBegin != -1
				&& otherBegin < end 
				&& !beginKey.equals(endKey)) {
			
			content += target.substring(pStart.get(), otherBegin);
			content += getEnclosedContent(beginKey, endKey, target, pStart);
			end = target.indexOf(endKey, pStart.get());
			content += target.substring(pStart.get(), end);
		}
		else {
			end = target.indexOf(endKey, pStart.get());
			content += target.substring(pStart.get(), end);
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
		int lineIndex = target.indexOf(";", i);
		if(lineIndex != -1) {
			int curlyIndex = target.indexOf("{", i);
			if(lineIndex >  curlyIndex && curlyIndex != -1){
				getCurlyBracesContent(target, startIndex); //This sets startIndex to the endex after the closing curly bracket
			}
			else startIndex.set(lineIndex + 1);
			return target.substring(i, startIndex.get());
		}
		else {
			startIndex.set(-1);
			return "";
		}
	}
	
	public int[] getKeywordBoundaries(String keyword, String target, EInteger start) {
		int[] res = new int[2];
		res[0] = target.indexOf("<" + keyword + ":", start.get()) + keyword.length() + 2;
		res[1] = target.indexOf(">", res[0]);
		if(res[0] == (keyword.length() + 1) || res[1] == -1) start.set(-1);
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
	public int getKeywordInt(String keyword, String target, EInteger start) {
		int[] limit = getKeywordBoundaries(keyword, target, start);
		if(start.get() != -1)return Integer.parseInt(target.substring(limit[0], limit[1]));
		return 0;
	}
	public String getKeywordString(String keyword, String target) {
		int[] limit = getKeywordBoundaries(keyword, target, new EInteger(0));
		return target.substring(limit[0], limit[1]);
	}
	public String getKeywordString(String keyword, String target, EInteger start) {
		int[] limit = getKeywordBoundaries(keyword, target, start);
		if(start.get() != -1) return target.substring(limit[0], limit[1]);
		return "";
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
