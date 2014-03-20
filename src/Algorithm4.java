import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;



public class Algorithm4 extends SyntaxAnalyser {
	
	private char flag = 'A';
	private String keyFlag = null;

	
	public Algorithm4() throws CustomException{
			try {
				new Algorithm3();
			} catch (CustomException e) {
				throw new CustomException("[ERREUR ALGORITHME3] execution impossible.\n" + e.getMessage());
			}
			Hashtable copy = new Hashtable(grammarTable);
			try {
				process();
			} catch (Exception e) {
				throw new CustomException("[ERREUR ALGORITHME4] impossible de trouver la forme normal de Chomsky.\n");
			}
			if(grammarTable.equals(copy)){
				msg = "Algorithme 4 n'a effectué aucun changement.\nLa grammaire n'est pas la grammaire normale de Chomsky.\n";
			}
			else{
				msg = "Algorithme 4 executé avec succés.\n";
			}
			
	}

	public void process() throws CustomException{
	
		try {
			step1();
		} catch (Exception e) {
			throw new CustomException("[ERREUR ALGORITHME4] impossible d'ajouter le Non-Terminal.\n");
		}
		try {
			step2();
		} catch (Exception e) {
			throw new CustomException("[ERREUR ALGORITHME4] impossible de remplacer les occurences des Non-Terminaux.\n");
		}
		System.gc();
	}
	
	public void step1(){
		for(Enumeration e = grammarTable.elements();e.hasMoreElements();){
			Vector<Vector<String>> tempVector1 = (Vector<Vector<String>>) e.nextElement();
			for(int i = 0;i<tempVector1.size();i++){
				Vector<String> tempVector2 = tempVector1.elementAt(i);
				for(int j = 0;j<tempVector2.size();j++){
					String str = tempVector2.elementAt(j);
					if(!checkGrammar(str,PATTERN1)){
						StringBuilder sb = new StringBuilder(str);
						for(int k = 0;k<sb.length();k++){
							String subStr = sb.substring(k, k+1);
							if(checkGrammar(subStr, PATTERN3)){
								if(!isExist(subStr)){
									String key = getChar(false) + "_" + subStr;
									if(!grammarTable.containsKey(key)){
										Vector<Vector<String>> tempToAdd = new Vector<Vector<String>>();
										Vector<String> tempSubToAdd = new Vector<String>();
										tempSubToAdd.add(subStr);
										tempToAdd.add(tempSubToAdd);
										grammarTable.put(key, tempToAdd);
									}
									sb.replace(k, k+1, key);
									k+=3;
								}
								else{
									sb.replace(k, k+1, keyFlag);
									k+=3;
								}
							}
						}
						str = sb.toString();
					}
					tempVector2.set(j, str);
				}
			}
		}
	}
	
	public void step2(){
		for(Enumeration e = grammarTable.elements();e.hasMoreElements();){
			Vector<Vector<String>> tempVector1 = (Vector<Vector<String>>) e.nextElement();
			for(int i = 0;i<tempVector1.size();i++){
				Vector<String> tempVector2 = tempVector1.elementAt(i);
				for(int j = 0;j<tempVector2.size();j++){
					String str = tempVector2.elementAt(j);
					if(checkGrammar(str,PATTERN5)){
						StringBuilder sb = new StringBuilder(str);
						substitute(sb,sb.substring(sb.length()-1, sb.length()));
						tempVector2.set(j, sb.toString());
						
					}
				}
			}
		}
	}
	
	public boolean isExist(String value){
		for(Enumeration e1 = grammarTable.elements(),e2 = grammarTable.keys();e1.hasMoreElements();){
			Vector<Vector<String>> tempVector1 = (Vector<Vector<String>>) e1.nextElement();
			String key = (String) e2.nextElement();
			if(tempVector1.size() == 1){
				for(int i = 0;i<tempVector1.size();i++){
					Vector<String> tempVector2 = tempVector1.elementAt(i);
					if(tempVector2.size() == 1){
						for(int j = 0;j<tempVector2.size();j++){
							String str = tempVector2.elementAt(j);
							if(str.equals(value)){
								keyFlag = key;
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public String getChar(boolean inc){
		if(inc)
			flag++;
		for(char ch = flag;ch<='Z';ch++){
			String key = "" + ch;
			if(!grammarTable.containsKey(key)){
				flag = ch;
				return key;
			}
		}
		return null;
	}
	
	public void substitute(StringBuilder sb,String flag){
		if(sb.length()>2){
			if(sb.substring(sb.length()-2, sb.length()-1).toString().equals(flag)){
				String key = getChar(true);
				String str = sb.substring(sb.length()-2, sb.length()).toString();
				sb.replace(sb.length()-2, sb.length(), key);
				Vector<Vector<String>> tempToAdd = new Vector<Vector<String>>();
				Vector<String> tempSubToAdd = new Vector<String>();
				tempSubToAdd.add(str);
				tempToAdd.add(tempSubToAdd);
				grammarTable.put(key, tempToAdd);
				substitute(sb,flag);
			}
		}
	}
}
