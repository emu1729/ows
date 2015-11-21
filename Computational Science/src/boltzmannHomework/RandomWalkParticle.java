package boltzmannHomework;

import java.awt.Color;
import java.util.Random;

/**
 * @author Peter Dong
 * A Projectile that moves randomly every mean free time
 */
public class RandomWalkParticle extends Projectile {

	static private Random random = new Random();
	
	private double meanFreeTime;
	private double timeCounter = 0;
	private ParticleContainer grid;
	
	/**
	 * @param position The original position of the Particle
	 * @param velocity The initial velocity of the Particle
	 * @param mass The mass of the particle
	 * @param color The color of the particle in the display
	 * @param meanFreeTime The average time between collisions
	 */
	public RandomWalkParticle(Vector3 position, Vector3 velocity, double mass, Color color, double meanFreeTime, ParticleContainer grid) {
		super(position, velocity, mass, color);
		this.meanFreeTime = meanFreeTime;
		this.grid = grid;
	}

	private void collision() {
		double speed = getVelocity().magnitude();
		
		int index = random.nextInt(grid.getParticles().size());
		Projectile randomParticle = grid.getParticles().get(index);
		
		double ranPhi = 0;
		double ranTheta = 0;
		
		if(randomParticle.getMass() < getMass())
		{
			double massRatio = randomParticle.getMass()/getMass();
			ranPhi = random.nextDouble() * 2 * Math.PI;
			ranTheta = random.nextDouble() * Math.PI * massRatio;
		}
		else
		{
			ranPhi = random.nextDouble() * 2 * Math.PI;
			ranTheta = random.nextDouble() * Math.PI;
		}
		
		setVelocity(Vector3.sphericalVector(speed, ranPhi, ranTheta));
	}
	
	@Override
	protected void advancePosition(double timeIncrement) {
		timeCounter += timeIncrement;
		if (timeCounter > meanFreeTime) {
			collision();
			timeCounter = 0;
		}
		super.advancePosition(timeIncrement);
	}

}
