package boltzmannHomework;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

/**
 * @author Peter Dong
 * A whole bunch of Particles in a box with rigid walls
 */
public class ParticleContainer {
	private final double BOLTZMANN_CONSTANT = 1.38 * Math.pow (10, -23);
	
	private Vector3 size;
	private List<Projectile> particles = new ArrayList<Projectile>();
	private double averageKineticEnergy;
	private boolean partialContainerSwitch;
	private double time;
	
	/**
	 * @param xSize The size of the box in the x direction
	 * @param ySize The size of the box in the y direction
	 * @param zSize The size of the box in the z direction
	 * @param partialContainerSwitch divides box into two
	 */
	public ParticleContainer(double xSize, double ySize, double zSize) {
		size = new Vector3(xSize, ySize, zSize);
		partialContainerSwitch = false;
		time = 0;
	}
	
	public void setPartialContainer(Vector3 starting, Vector3 ending)
	{
		partialContainerSwitch = true;
	}
	
	public double getAverageKineticEnergy()
	{
		return averageKineticEnergy;
	}
	
	public double getXSize() {
		return size.getX();
	}

	public double getYSize() {
		return size.getY();
	}
	
	public double getZSize() {
		return size.getZ();
	}
	
	/**
	 * @param part The Particle to add to the container
	 */
	public void addParticle(Projectile part) {
		particles.add(part);
	}
	
	/**
	 * @param generator The RandomGenerator used to create the random distribution
	 * @param number The number of particles to create
	 */
	public void addRandomParticles(RandomGenerator generator, int number) {
		for (int i = 0; i < number; ++i) {
			addParticle(generator.getNextProjectile());
		}
	}
	
	public List<Projectile> getParticles() {
		return particles;
	}
	
	public double getPartialKineticEnergy()
	{
		double totalKineticEnergy = 0;
		for (Projectile proj: particles)
		{
				if (proj.getPosition().getZ() <= size.getZ()/2)
					{
						totalKineticEnergy += proj.getMass() *  Math.pow(proj.getVelocity().magnitude(), 2)/2;
					}
		}
		return totalKineticEnergy;
	}
	
	public double getPartialParticles()
	{
		double totalParticles = 0;
		for (Projectile proj: particles)
		{
				if (proj.getPosition().getZ() <= size.getZ()/2)
					{
						totalParticles++;
					}
		}
		return totalParticles;
	}
	/**
	 * This advances all the particles by one time step
	 * @param deltaTime The amount of time to advance the particle collection by
	 */
	public void advanceParticles(double deltaTime) {
		time += deltaTime;
		double totalKineticEnergy = 0;
		double totalParticles = 0;
		for (Projectile proj : particles) {
			proj.update(deltaTime);
			checkParticle(proj);
			//KE = 1/2 m * v^2;
			totalKineticEnergy += proj.getMass() * Math.pow(proj.getVelocity().magnitude(), 2)/2;
			totalParticles ++;
		}
		if (partialContainerSwitch)
		{
			double partialKineticEnergy = getPartialKineticEnergy();
			double partialParticles = getPartialParticles();
			double secondPartialKineticEnergy = totalKineticEnergy - partialKineticEnergy;
			double temperature1 = (partialKineticEnergy / partialParticles)/ (BOLTZMANN_CONSTANT * 3/2);
			double temperature2 = (secondPartialKineticEnergy / (totalParticles - partialParticles))/ (BOLTZMANN_CONSTANT * 3/2);
			System.out.println(time + "\t" + temperature1 + "\t" + temperature2 + "\t" + (temperature2 - temperature1));
		}
		else
		{
			averageKineticEnergy = totalKineticEnergy/totalParticles;
			System.out.println(time + "\t" + averageKineticEnergy / (BOLTZMANN_CONSTANT * 3/2));
		}
	}
	
	private void checkParticle(Projectile particle) {
		if (particle.getPosition().getX() < 0 || particle.getPosition().getX() > size.getX()) {
			particle.getVelocity().setX(-particle.getVelocity().getX());
		}
		if (particle.getPosition().getY() < 0 || particle.getPosition().getY() > size.getY()) {
			particle.getVelocity().setY(-particle.getVelocity().getY());
		}
		if (particle.getPosition().getZ() < 0 || particle.getPosition().getZ() > size.getZ()) {
			particle.getVelocity().setZ(-particle.getVelocity().getZ());
		}
	}
	
	/**
	 * This runs the whole simulation
	 * @param deltaTime The amount of time to advance the simulation each step
	 */
	public void run(double deltaTime) {
		ParticleVisualizer viz = new ParticleVisualizer(this, deltaTime);
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(viz);
		frame.setSize(viz.getWidth(), viz.getHeight());

		frame.setVisible(true);		
	}
}
