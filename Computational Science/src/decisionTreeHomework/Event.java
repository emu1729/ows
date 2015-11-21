package decisionTreeHomework;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

abstract public class Event implements Serializable {

	private static final long serialVersionUID = -4320527264765339431L;
	
	private int nVariables;
	private List<String> names = new ArrayList<>();
	private double weight;
	private boolean correctlyCategorized;
	
	public Event(int nvar, List<String> names) {
		if (names.size() != nvar) {
			throw new RuntimeException("Invalid list of names entered!");
		}
		
		nVariables = nvar;
		this.names = names;
		correctlyCategorized = true;
	}
	
	public String getName(int index) {
		return names.get(index);
	}
	
	public int getNVars() {
		return nVariables;
	}
	
	//sets if correctlyCategorized
	public void setCorrectlyCategorized(boolean newCorrectlyCategorized){
		correctlyCategorized = newCorrectlyCategorized;
	}
	
	//gets if correctlyCategorized
	public boolean getCorrectlyCategorized(){
		return correctlyCategorized;
	}
	
	//sets new weight for a single event
	public void setWeight(double newWeight){
		weight = newWeight;
	}
		
	//gets new weight for a single event
	public double getWeight(){
		return weight;
	}
	
	abstract public double[] getVars();
}
