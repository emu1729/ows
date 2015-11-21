package boltzmannHomework;

import java.awt.Color;
import java.util.Random;

/**
 * @author Peter Dong
 * A class to generate a random distribution of Particles according to a programmed rule
 */
abstract public class RandomGenerator {

	private double mass;
	private Color color;
	private double meanFreeTime;
	private ParticleContainer grid;
	private boolean randomSwitch;
	private Vector3 starting;
	private Vector3 ending;
	
	/**
	 * @param mass The mass of each particle
	 * @param color The color of the particle in the display
	 * @param meanFreeTime The mean free time for each particle
	 * @param grid The ParticleContainer to which the particle will be added
	 */
	public RandomGenerator(double mass, Color color, double meanFreeTime, ParticleContainer grid) {
		this.mass = mass;
		this.color = color;
		this.meanFreeTime = meanFreeTime;
		this.grid = grid;
		randomSwitch = true;
		starting = new Vector3(0, 0, 0);
		ending = new Vector3(grid.getXSize(), grid.getYSize(), grid.getZSize());
	}
	
	protected double getMass()
	{
		return mass;
	}
	
	static private Random random = new Random();

	/**
	 * @return A random number generator
	 */
	protected Random getRandom() {
		return random;
	}
	
	/**
	 * @param grid The container of Particles
	 * @return A randomly generated position vector within a ParticleContainer
	 */
	protected Vector3 randomPosition(ParticleContainer grid) {
		double x = getRandom().nextDouble() * grid.getXSize();
		double y = getRandom().nextDouble() * grid.getYSize();
		double z = getRandom().nextDouble() * grid.getZSize();
		
		return new Vector3(x, y, z);
	}
	
	/**
	 * @param starting and ending Sets bounds for particles
	 */
	public void setBounds(Vector3 starting, Vector3 ending)
	{
		this.starting = starting;
		this.ending = ending;
	}
	
	/**
	 * @param grid The container of Particles
	 * @return A randomly generated position vector in a set space within a ParticleContainer
	 */
	protected Vector3 setPosition(ParticleContainer grid, Vector3 starting, Vector3 ending)
	{
		double x = getRandom().nextDouble() * (ending.getX() - starting.getX()) + starting.getX();
		double y = getRandom().nextDouble() * (ending.getY() - starting.getY()) + starting.getY();
		double z = getRandom().nextDouble() * (ending.getZ() - starting.getZ()) + starting.getZ();
		
		return new Vector3(x, y, z);
	}
	
	/**
	 * @return A unit vector that points in a random direction
	 */
	protected Vector3 randomDirection() {
		double phi = getRandom().nextDouble() * 2 * Math.PI;
		double theta = getRandom().nextDouble() * Math.PI;
		
		return Vector3.sphericalVector(1, phi, theta);
	}
	
	/**
	 * Turns on random boolean
	 */
	protected void setRandomOff()
	{
		randomSwitch = false;
	}
	
	/**
	 * @return A randomly generated speed based on an algorithm in a derived class
	 */
	abstract protected double getSpeed();
	
	/**
	 * @return A randomly generated Projectile using the getSpeed() function
	 */
	public Projectile getNextProjectile() {
		Vector3 velocity = randomDirection().scale(getSpeed());
		if (randomSwitch)
		{
			return new RandomWalkParticle(randomPosition(grid), velocity, mass, color, meanFreeTime, grid);
		}
		else
		{
			return new RandomWalkParticle(setPosition(grid, starting, ending), velocity, mass, color, meanFreeTime, grid);
		}
	}
}
