import java.util.Collection;
import java.util.Enumeration;
import java.util.Vector;


public class Algorithm2 extends SyntaxAnalyser {
	
	private Vector<String> toDelete;
	
	public Algorithm2() throws CustomException{
		try {
			new Algorithm1();
		} catch (Exception e) {
			throw new CustomException("[ERREUR ALGORITHME1] execution impossible.\n" + e.getMessage());
		}
		
		if(isEpsProduction()){
			toDelete = new Vector<String>();
			fillToDelete();
			try {
				process();
			} catch (Exception e) {
				throw new CustomException("[ERREUR ALGORITHME2] impossible de trouver les cas possibles imaginables.\n");
			}
			msg = "Algorithme 2 executé avec succés.\n";
		}
		else{
			msg = "Algorithme 2 n'a effectué aucun changement.\nLa grammaire ne contient aucune epsilon.\n";
		}
	}
	
	public boolean isEpsProduction(){
		for (Enumeration e1 = grammarTable.elements(); e1.hasMoreElements() ;){
			Vector<Vector<String>> tempVector1 = (Vector<Vector<String>>) e1.nextElement();
			for(int j = 0;j<tempVector1.size();j++){
				Vector<String> tempVector2 = tempVector1.elementAt(j);
				if(tempVector2.contains("eps"))
					return true;
			}
		}
		return false;
	}
	
	public void fillToDelete(){
		for (Enumeration e1 = grammarTable.elements() , e2 = grammarTable.keys(); e1.hasMoreElements() ;){
			String toAdd = (String) e2.nextElement();
			Vector<Vector<String>> tempVector1 = (Vector<Vector<String>>) e1.nextElement();
			for(int j = 0;j<tempVector1.size();j++){
				Vector<String> tempVector2 = tempVector1.elementAt(j);
				for(int i = 0;i<tempVector2.size();i++){
					if(tempVector2.elementAt(i).equals("eps")){
						toDelete.add(toAdd);
						tempVector2.remove(i);
					}
				}
			}
		}
	}
	
	public void process(){
		for (Enumeration e1 = grammarTable.elements(), e2 = grammarTable.keys(); e1.hasMoreElements() ;){
			Vector<Vector<String>> tempVector1 = (Vector<Vector<String>>) e1.nextElement();
			String key = (String) e2.nextElement();
			for(int k=0;k<tempVector1.size();k++){
				Vector<String> toAdd = new Vector<String>();
				Vector<String> tempVector2 = tempVector1.elementAt(k);
				for(int i = 0 ;i<tempVector2.size();i++){
					String str = tempVector2.elementAt(i);
					StringBuilder sb = new StringBuilder(str);
					int j = 0;
					while(j<toDelete.size()){
						Vector<Integer> vPosition = stringPosition(toDelete.elementAt(j), sb);
						int g=1;
						while(g<=vPosition.size()){
							removeOcc(sb,toDelete.elementAt(j),g,toAdd);
							g++;
						}
						j++;
					}
				}
				saveVector(toAdd, tempVector2);
				if(tempVector2.isEmpty()){
					tempVector1.removeElement(tempVector2);
				}
			}
			if(tempVector1.isEmpty())
				grammarTable.remove(key);
		}
		addVoidProduction();
		System.gc();
	}
	
	public void addVoidProduction(){
		Vector<Vector<String>> tempVector = new Vector<Vector<String>>();
		Vector<String> subtempVector = new Vector<String>();
		subtempVector.add(firstKey);
		subtempVector.add("eps");
		firstKey = "T";
		tempVector.add(subtempVector);
		grammarTable.put(firstKey, tempVector);
	}
	
	public Vector<Integer> stringPosition(String toFind,StringBuilder str){
		Vector<Integer> vPosition = new Vector<Integer>();
		for(int i = 0;i<str.length();i++){
			if(str.substring(i, i+1).equals(toFind)){
				vPosition.add(i);
			}
		}
		return vPosition;
	}
	
	public void removeOcc(StringBuilder sb,String toDelete,int occ,Vector<String> toAdd){
		if(occ <= 0){
			if(exist(toAdd,sb) && !sb.toString().equals(""))
				toAdd.add(sb.toString());
		}
		else{
			Vector<Integer> vPosition = stringPosition(toDelete, sb);
			for(int i = 0;i<vPosition.size()-occ+1;i++){
				StringBuilder sbCopy = new StringBuilder(sb);
				sbCopy.deleteCharAt(vPosition.elementAt(i));
				removeOcc(sbCopy, toDelete, occ-1,toAdd);
			}
		}
		System.gc();
	}
    public void saveVector(Vector<String> srcVector,Vector<String> desVector){
    	if(!srcVector.isEmpty()){
    		for(int i = 0 ;i<srcVector.size();i++){
    			desVector.add(srcVector.elementAt(i));
    		}
    	}
    }
    public boolean exist(Vector<String> toAdd,StringBuilder sb){
    	for(int i=0; i<toAdd.size();i++){
    		if(toAdd.elementAt(i).equals(sb.toString())){
    			return false;
    		}
    	}
    	return true;
    }
}
