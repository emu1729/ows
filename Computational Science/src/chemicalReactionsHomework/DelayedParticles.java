package chemicalReactionsHomework;
/**
 * 
 * @author Emily Mu
 * Creates generator of delayed particles
 *
 */
public class DelayedParticles {
	
	private RandomGenerator generator;
	private int number;
	private String name;
	private double time;
	
	public DelayedParticles(RandomGenerator generator, int number, String name, double time){
		this.generator = generator;
		this.number = number;
		this.name = name;
		this.time = time;
	}
	
	public double getTimeDelay(){
		return time;
	}
	
	public RandomGenerator getGenerator(){
		return generator;
	}
	
	public int getNumber(){
		return number;
	}
	
	public String getName(){
		return name;
	}

}
