import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SyntaxAnalyser extends StartFrame {
	
	private static final String PATTERN = "(^[A-Z]->(.)+(|(.)*)*)+";
	protected static String PATTERN1 = "((?![A-Z]).)*";
	protected static String PATTERN2 = "([A-Z])*";
	protected static String PATTERN3 = "([a-z])*";
	protected static String PATTERN4 = "[A-Z]";
	protected static String PATTERN5 = "(.)*[A-Z]{2,}";
	
	
	public static void main() throws CustomException{
		fillGrammarTable();
	}
	
	
	public static boolean checkGrammar(String grammar,String pat){
		Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(grammar);
        return matcher.matches();          
	}
	
	public void copyHashtable(Hashtable<String, Vector<Vector<String>>> src,Hashtable<String, Vector<Vector<String>>> dst){
		for(Enumeration e1 = src.keys(),e2 = src.elements();e1.hasMoreElements();){
			String key = new String((String) e1.nextElement());
			Vector<Vector<String>> value = new Vector<Vector<String>>((Vector<Vector<String>>) e2.nextElement());
			dst.put(key, value);
		}
	}
	
	public static String[] getProduction(){
		String[] str;
		if(jTActive)
			str = grammarSave.split("\n");
		else
			str = grammar.split("\n");
		return str;
	}
	
	public static Vector<String> getAxiome(String production){
		Vector<String> tempVector = new Vector<String>();
		String[] str = (production.substring(3,production.length())).split("\\|");
		for(int i = 0; i<str.length;i++){
			tempVector.add(str[i]);
		}
		return tempVector;
		
	}
	
	public static void fillGrammarTable() throws CustomException{
		String[] production = getProduction();
		for(int i = 0; i<production.length;i++){
			if(checkGrammar(production[i],PATTERN)){
				String key = production[i].substring(0,1);
				getFirstKey(i,key);
				grammarTable.put(key,fillVector(key, getAxiome(production[i])));
			}
			else
				throw new CustomException("ERREUR Syntaxique : Verifier la grammaire si elle est entré correctement.\n"
						+ "'Quelque Chose'->'Quelque Chose'|Quelque Chose'");
				
		}
	}
	
	public static void getFirstKey(int i,String key){
		if(i==0)
			firstKey = key;
	}
	
	public static Vector<Vector<String>> fillVector(String key,Vector<String> axiome){
		if(grammarTable.containsKey(key)){
			Vector<Vector<String>> tempVector = grammarTable.get(key);
			tempVector.add(axiome);
			return tempVector;
		}
		else{
			Vector<Vector<String>> tempVector = new Vector<Vector<String>>();
			tempVector.add(axiome);
			return tempVector;
		}
		
	}
	


}
