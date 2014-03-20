import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


public class Algorithm1 extends SyntaxAnalyser {
	
	//private boolean successExecution1 = false;
	
	public Algorithm1() throws CustomException{
			Hashtable copy = new Hashtable(grammarTable);
			try {
				process1();
			} catch (Exception e) {
				throw new CustomException("[ERREUR ALGORITHME1] impossible d'eliminer les symboles unitiles.\n");
			}
			if(!grammarTable.equals(copy)){
				try {
					process2();
				} catch (Exception e) {
					throw new CustomException("[ERREUR ALGORITHME1] impossible de verifier les symboles accessibles.\n");
				}
			}
			if(grammarTable.equals(copy)){
				msg = "Algorithme 1 n'a effectué aucun changement.\nLa grammaire ne contient aucun symbole unitile\n";
			}
			else{
				msg = "Algorithme 1 executé avec succés.\n";
			}
			
	}
	
	public void process1(){		
		for (Enumeration e1 = grammarTable.elements() , e2 = grammarTable.keys(); e1.hasMoreElements() ;){
			Vector<Vector<String>> tempVector1 = (Vector<Vector<String>>) e1.nextElement();
			String key = (String) e2.nextElement();
			for(int j = 0;j<tempVector1.size();j++){
				Vector<String> tempVector2 = tempVector1.elementAt(j);
				for(int i = 0;i<tempVector2.size();i++){
					if(!checkGrammar(tempVector2.elementAt(i), PATTERN3)){						
						if(!subProcess1(tempVector2.elementAt(i))){
							tempVector2.remove(i);
							i--;
							if(tempVector2.isEmpty())
								tempVector1.remove(tempVector2);
							if(tempVector1.isEmpty())
								grammarTable.remove(key);
						}
					}
				}
			}
		}
		System.gc();
	}
	
	
	public boolean subProcess1(String axiome){
		boolean boolResult = true;
			for(int i = 0;i<axiome.length();i++){
				String str = axiome.substring(i,i+1);
				if(checkGrammar(str, PATTERN4) && !str.equals(firstKey)){
					if(exist(str)){
						boolResult =  boolResult && deepSearch(2,str);
						if(!boolResult){
							grammarTable.remove(str);
							return boolResult;
						}
						
					}
					else
						return false;
				}
			}
		return boolResult;
		
	}
	
	public boolean deepSearch(int niveau,String NonTerminaux){
		boolean boolResult = true;
		if(niveau<=0){
			boolResult = false;
			Vector<Vector<String>> tempVector1 = grammarTable.get(NonTerminaux);
			for(int i = 0;i<tempVector1.size();i++){
				Vector<String> tempVector2 = tempVector1.elementAt(i);
				for(int j = 0;j<tempVector2.size();j++){
					boolResult = boolResult || !checkGrammar(tempVector2.elementAt(j), PATTERN2);
				}
			}
			return boolResult;
		}
		else{
			Vector<Vector<String>> tempVector1 = grammarTable.get(NonTerminaux); 
			for(int i = 0;i<tempVector1.size();i++){
				Vector<String> tempVector2 = tempVector1.elementAt(i);
				for(int j = 0;j<tempVector2.size();j++){
					String str = tempVector2.elementAt(j);
					for(int k = 0;k<str.length();k++){
						String subStr = str.substring(k, k+1);
						if(!checkGrammar(subStr, PATTERN1) && exist(subStr)){
							boolResult = boolResult && deepSearch(niveau-1, subStr);
						}
					}
				}
			}
		}
		return boolResult;
		
	}
	
	public boolean exist(String str){
		for(Enumeration e = grammarTable.keys();e.hasMoreElements();){
			String key = (String) e.nextElement();
			if(str.equals(key))
				return true;
		}
		return false;
	}
	
	public void process2(){
		Vector<String> reachable = new Vector<String>();
		reachable.add(firstKey);
		searchReachable(firstKey,reachable);
		removeInreachable(reachable);
		System.gc();
	}
	
	public void searchReachable(String key,Vector<String> reachable){
			Vector<Vector<String>> tempVector1 = grammarTable.get(key);
			for(int i = 0;i<tempVector1.size();i++){
				Vector<String> tempVector2 = tempVector1.elementAt(i);
				for(int j = 0;j<tempVector2.size();j++){
					String str = tempVector2.elementAt(j);
					for(int k = 0;k<str.length();k++){
						String ch = str.substring(k, k+1);
						if(!checkGrammar(ch, PATTERN1) && !ch.equals(firstKey)){
							if(!reachable.contains(ch)){
								reachable.add(ch);
							}
							searchReachable(ch,reachable);
						}
					}
				}
			}	
	}
	
	public void removeInreachable(Vector<String> reachable){
		for(Enumeration e = grammarTable.keys();e.hasMoreElements();){
			String key = (String) e.nextElement();
			if(!reachable.contains(key))
				grammarTable.remove(key);
		}
	}
	
}
