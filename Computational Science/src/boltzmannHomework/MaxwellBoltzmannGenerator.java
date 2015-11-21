package boltzmannHomework;

import java.awt.*;
import java.util.*;

public class MaxwellBoltzmannGenerator extends RandomGenerator{
	
	public final double BOLTZMANN_CONSTANT = 1.38 * Math.pow(10, -23);
	
	private double temperature;
	private double maxVelocity;
	private double maxProbability;
	
	public MaxwellBoltzmannGenerator (double mass, Color color, double meanFreeTime, ParticleContainer grid, double temperature)
	{
		super(mass, color, meanFreeTime, grid);
		this.temperature = temperature;
		maxVelocity = 10 * Math.sqrt(2 * BOLTZMANN_CONSTANT * temperature / getMass());
		maxProbability = calculateProbability(maxVelocity/10);
		
	}
	
	protected double getSpeed() {
		double probability = 0;
		double velocity = 0;
		//pick points if within distribution
		while (getRandom().nextDouble() > probability)
		{
			velocity = getRandom().nextDouble() * maxVelocity;
			//scales box to allow for more points: same distribution
			probability = calculateProbability(velocity)/(maxProbability);
		}
		return velocity;
	}
	
	private double calculateProbability(double velocity)
	{
		//P(v) = 4 pi (m/(2pikT))^(3/2) v^2 e^(mv^2/(2kT))
		return 4 * Math.PI * Math.pow(getMass()/(2 * Math.PI * BOLTZMANN_CONSTANT * temperature), ((double) 3/2)) * Math.pow(velocity, 2) * Math.exp(-1 * getMass() * Math.pow(velocity, 2) / (2 * temperature * BOLTZMANN_CONSTANT));
	}
}
