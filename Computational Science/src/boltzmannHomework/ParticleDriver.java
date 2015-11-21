package boltzmannHomework;

import java.awt.Color;

public class ParticleDriver {

	private static final double containerSize = 50;
	private static final double minSpeed = 400;
	private static final double maxSpeed = 600;
	private static final double eachMass = 4.9 * Math.pow(10, -26);
	private static final Color eachColor = Color.BLUE;
	private static final int nParticles = 1000;
	private static final double deltaTime = 0.001;
	private static final double meanFreeTime = 2;
	private static final double temperature = 293.15;
	private static final double boltzmannConstant = 1.38 * Math.pow(10, -23);
	//level3 bounds
	private static final double temperature1 = 273.15;
	private static final double temperature2 = 373.15;
	private static final Vector3 boundary1 = new Vector3 (0, 0, 0);
	private static final Vector3 boundary2 = new Vector3 (50, 50, 25);
	private static final Vector3 boundary3 = new Vector3 (0, 0, 25);
	private static final Vector3 boundary4 = new Vector3 (50, 50, 50);
	//challenge bounds
	private static final double largeMass = eachMass * 10;
	
	public static void main(String[] args) {
		//level1();
		//level2();
		//level3();
		challenge();
	}
	
	public static void level1()
	{
		ParticleContainer container = new ParticleContainer(containerSize, containerSize, containerSize);
		RandomGenerator generator = new FlatGenerator(eachMass, eachColor, meanFreeTime, container, minSpeed, maxSpeed);
		container.addRandomParticles(generator, nParticles);
		container.run(deltaTime);
	}
	
	public static void level2()
	{
		ParticleContainer container = new ParticleContainer(containerSize, containerSize, containerSize);
		RandomGenerator generator = new MaxwellBoltzmannGenerator(eachMass, eachColor, meanFreeTime, container, temperature);
		container.addRandomParticles(generator, nParticles);
		container.run(deltaTime);
	}
	
	public static void level3()
	{
		ParticleContainer container = new ParticleContainer(containerSize, containerSize, containerSize);
		RandomGenerator generator1 = new MaxwellBoltzmannGenerator(eachMass, eachColor, meanFreeTime, container, temperature1);
		generator1.setRandomOff();
		generator1.setBounds(boundary1, boundary2);
		RandomGenerator generator2 = new MaxwellBoltzmannGenerator(eachMass, eachColor, meanFreeTime, container, temperature2);
		generator2.setRandomOff();
		generator2.setBounds(boundary3, boundary4);
		container.addRandomParticles(generator1, nParticles/2);
		container.addRandomParticles(generator2, nParticles/2);
		container.setPartialContainer(boundary1, boundary2);
		container.run(deltaTime);
	}
	
	public static void challenge()
	{
		ParticleContainer container = new ParticleContainer(containerSize, containerSize, containerSize);
		RandomGenerator generator1 = new MaxwellBoltzmannGenerator(eachMass, eachColor, meanFreeTime, container, temperature);
		RandomGenerator generator2 = new MaxwellBoltzmannGenerator(largeMass, eachColor, meanFreeTime, container, temperature);
		container.addRandomParticles(generator1, nParticles/2);
		container.addRandomParticles(generator2, nParticles/2);
		container.run(deltaTime);
	}

}
