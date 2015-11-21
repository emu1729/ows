package chemicalReactionsHomework;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.*;

public class ReactionDictionary {
		
	
	static private Map<String, Reaction> map = new HashMap<String, Reaction>();
	
	public void addReaction(String equation, double activationEnergy, String catalyst, double percentReaction) {
		String[] eq = equation.split("=");
		List <String> reactants = Arrays.asList(eq[0].split("\\+"));
		List <String> products = Arrays.asList(eq[1].split("\\+"));
		Reaction info = new Reaction(reactants, products, activationEnergy, catalyst, percentReaction);
		map.put(equation, info);
	}
	
	public Set<String> getList() {
		return map.keySet();
	}
	
	public List<Reaction> getReaction(Particle p){
		List<Reaction> potentialReactions = new ArrayList<Reaction>();
		for(String s: getList()) {
			List<String> reactants = getReactants(s);
			if (reactants.contains(p.getName())) {
				potentialReactions.add(map.get(s));
			}
		}
		if (potentialReactions.size() == 0) {
			return null;
		}
		else {
			return potentialReactions;
		}
	}
	
	public List<String> getReactants(String equation) {
		return map.get(equation).getReactants();
	}
	
	public List<String> getProducts(String equation){
		return map.get(equation).getProducts();
	}
	
	public double getActivationEnergy(String equation){
		return map.get(equation).getActivationEnergy();
	}
	
	public String getCatalyst(String equation){
		return map.get(equation).getCatalyst();
	}
	
	public double getPercentReaction(String equation){
		return map.get(equation).getPercentReaction();
	}
	
}
