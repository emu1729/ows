package chemicalReactionsHomework;

import java.util.*;

public class Reaction {
	
	private List<String> reactants;
	private List<String> products;
	private double activationEnergy = 0;
	private String catalyst;
	private double percentReaction = 0;
	
	
	public Reaction(List<String> reactants, List<String> products, double activationEnergy, String catalyst, double percentReaction)
	{
		this.reactants = reactants;
		this.products = products;
		this.activationEnergy = activationEnergy;
		this.catalyst = catalyst;
		this.percentReaction = percentReaction;
		
	}
	
	public int getNumReactants()
	{
		return reactants.size();
	}
	
	public int getNumProducts()
	{
		return products.size();
	}
	
	public List<String> getReactants()
	{
		return reactants;
	}
	
	public List<String> getProducts()
	{
		return products;
	}
	
	public double getActivationEnergy()
	{
		return activationEnergy;
	}
	
	public String getCatalyst()
	{
		return catalyst;
	}
	
	public double getPercentReaction()
	{
		return percentReaction;
	}

}
