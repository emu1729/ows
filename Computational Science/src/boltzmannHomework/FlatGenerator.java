package boltzmannHomework;

import java.awt.Color;

/**
 * @author Peter Dong
 * A RandomGenerator that generates speeds evenly across a range
 */
public class FlatGenerator extends RandomGenerator {

	private double min;
	private double max;
	
	/**
	 * @param mass The mass of each particle
	 * @param color The color of the particle in the display
	 * @param meanFreeTime The mean free time for each particle
	 * @param grid The ParticleContainer to which the particle will be added
	 * @param min The minimum possible speed of the Particle
	 * @param max The maximum possible speed of the Particle
	 */
	public FlatGenerator(double mass, Color color, double meanFreeTime, ParticleContainer grid, double min, double max) {
		super(mass, color, meanFreeTime, grid);
		this.min = min;
		this.max = max;
	}
	
	@Override
	protected double getSpeed() {
		return getRandom().nextDouble() * (max - min) + min;
	}
}
