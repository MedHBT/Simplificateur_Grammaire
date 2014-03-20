import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.scilab.forge.jlatexmath.TeXFormula;



public class Algorithm3 extends SyntaxAnalyser {
	
	
	public Algorithm3() throws CustomException{
		
		try {
			new Algorithm2();
		} catch (CustomException e) {
			throw new CustomException("[ERREUR ALGORITHME2] execution impossible.\n" + e.getMessage());
		}
		Hashtable copy = new Hashtable(grammarTable);
		try {
			process();
		} catch (Exception e) {
			throw new CustomException("[ERREUR ALGORITHME3] impossible d'eliminer les productions unitaires.\n");
		}
		if(grammarTable.equals(copy)){
			msg = "Algorithme 3 n'a effectué aucun changement.\nLa grammaire ne contient aucune production unitaire.\n";
		}
		else{
			msg = "Algorithme 3 executé avec succés.\n";
		}
		
	}
	
	public void process(){
		for (Enumeration e1 = grammarTable.elements(), e2 = grammarTable.keys(); e1.hasMoreElements() ;){
			Vector<Vector<String>> tempVector1 = (Vector<Vector<String>>) e1.nextElement();
			String key = (String) e2.nextElement();
			for(int k=0;k<tempVector1.size();k++){
				Vector<String> tempVector2 = tempVector1.elementAt(k);
				for(int i = 0 ;i<tempVector2.size();i++){
					String str = tempVector2.elementAt(i);
					if(checkGrammar(str, PATTERN4)){
						addVectors(str,tempVector2);
					}
				}
			}
		}
		System.gc();
	}
	
	public void addVectors(String key,Vector<String> desVector){
		desVector.removeElement(key);
		Vector<Vector<String>> tempVector1 = grammarTable.get(key);
		for(int i = 0;i<tempVector1.size();i++){
			Vector<String> tempVector2 = tempVector1.elementAt(i);
			for(int j = 0 ;j<tempVector2.size();j++){
				String str = tempVector2.elementAt(j);
				if(checkGrammar(str, PATTERN4))
					addVectors(str, desVector);
				else
					desVector.add(tempVector2.elementAt(j));
			}
		}
	}
}
