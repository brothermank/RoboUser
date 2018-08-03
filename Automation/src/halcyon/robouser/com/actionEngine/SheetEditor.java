package halcyon.robouser.com.actionEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SheetEditor {
	
	File sourceFile;
	int startLine, endLine;
	String allText;
	
	final static String[] Items = {"FunctionE", "FunctionB", "Var", "Char"};

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
				allText = allText.toString();
			    br.close();
			    
			} catch (IOException e) {}
			br.close();
		} catch (IOException e) {}
		    		    
	}
	
	public void editPISheet() {
		Integer pos = new Integer(0);
		Integer itemPos = new Integer(0);
		String line, item;
		while(pos != -1) {
			line = getNextLine(allText, pos);
			itemPos = 0;
			while(itemPos != -1) {
				item = getItem(line, itemPos);
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
	
	private void addEntry(String characteristic, String value) {
		characteristics.add(characteristic.trim());
		values.add(value.trim());
	}
	
	private void handleChar(String cha, Integer itemPos) {
		int argumentStart = itemPos;
		String argument = getParenthesisContent(cha, itemPos);
		
		String[] split = splitString("=", cha);
		addEntry(split[0], split[1]);
	}
	private void handleVar(String var, Integer itemPos) {
		int argumentStart = itemPos;
		String argument = getParenthesisContent(var, itemPos);

		addEntry("PPPI_VARIABLE", getKeywordString("Name", var));
		
		Integer index = new Integer(0);
		String type = getKeywordString("Type", var, index);
		if(type != "" && index != -1) {
			addEntry(type, "");
		}else {
			index = 0;
			String defaultValue = getKeywordString("DVal", var, index);
			if(defaultValue != "" && index != -1) {
				addEntry("PPPI_DEFAULT_VALUE", defaultValue);
			} else {
				addEntry("PPPI_DEFAULT_VALUE", "0");
			}
		}
		
	}
	
	private void handleFunction(String func, Integer itemPos, char type) {
		int argumentStart = itemPos;
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
		
		Integer pos = new Integer(argumentStart);
		List<String[]> variables = new ArrayList<String[]>();
		
		while(pos != -1) {
			String keyword = getKeywordString("Evar", func, pos);
			String[] temp = {"E", keyword};
			variables.add(temp);
		}
		pos = 0;
		while(pos != -1) {
			String keyword = getKeywordString("Ivar", func, pos);
			String[] temp = {"I", keyword};
			variables.add(temp);
		}
		pos = 0;
		while(pos != -1) {
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
			return output;
		}
		output[0] = target.substring(0, a);
		output[1] = target.substring(a + split.length());
		return output;
	}
	
	private String getParenthesisContent(String func, Integer pStart) {
		String content = "";
		Integer i = pStart;
		int end;
		if((i = func.indexOf("(")) != -1) {
			content += func.substring(pStart, i);
			content += getParenthesisContent(func, i);
			end = func.indexOf(")", i);
			content += func.substring(i, end);
		}
		else {
			end = func.indexOf(")", pStart);
			content += func.substring(pStart, end);
		}
		pStart = end + 1;
		return content;
		
	}
	
	private String getItem(String target, Integer pos){
		int closest = Integer.MAX_VALUE;
		String item = "";
		for(int i = 0; i < Items.length; i++) {
			if(target.indexOf(Items[i], pos) < closest) {
				closest = target.indexOf(Items[i], pos);
				item = Items[i];
			}
		}
		if(closest == Integer.MAX_VALUE) closest = -1;
		pos = closest;
		return item;
	}
	
	private String getNextLine(String target, Integer startIndex) {
		int i = startIndex;
		startIndex = target.indexOf(";", startIndex);
		if(startIndex != -1) return target.substring(i, startIndex);
		return "";
	}
	
	public int[] getKeywordBoundaries(String keyword, String target, Integer start) {
		int[] res = new int[2];
		res[0] = target.indexOf("<" + keyword + ":", start) + keyword.length() + 2;
		res[1] = target.indexOf(">", res[0]);
		if(res[0] == -1 || res[1] == -1) start = -1;
		else start = res[1] + 1;
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
		int[] limit = getKeywordBoundaries(keyword, target, 0);
		return Integer.parseInt(target.substring(limit[0], limit[1]));
	}
	public String getKeywordString(String keyword, String target) {
		int[] limit = getKeywordBoundaries(keyword, target, 0);
		return target.substring(limit[0], limit[1]);
	}
	public String getKeywordString(String keyword, String target, Integer start) {
		int[] limit = getKeywordBoundaries(keyword, target, start);
		return target.substring(limit[0], limit[1]);
	}
	public double getKeywordDouble(String keyword, String target) {
		int[] limit = getKeywordBoundaries(keyword, target, 0);
		return Double.parseDouble(target.substring(limit[0], limit[1]));
	}
	
	public String getKeywordContentString(String keyword, String target) {
		int[] limit = getKeyworContentBoundaries(keyword, target);
		return target.substring(limit[0], limit[1]);
	}
	
	
}
