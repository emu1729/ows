package chemicalReactionsHomework;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.*;

import javax.swing.JFrame;

/**
 * @author Peter Dong
 * A whole bunch of Particles in a box with rigid walls
 */
public class ParticleContainer {

	private Vector3 size;
	
	private List<Particle> particles = new CopyOnWriteArrayList<Particle>();
	private List<Particle> toBeAdded = new ArrayList<Particle>();
	private ParticleDictionary dict = new ParticleDictionary();
	private ReactionDictionary reactDict = new ReactionDictionary();
	private List<DelayedParticles> delayedEvents = new ArrayList<DelayedParticles>();
	
	private double reactionRadius = 0;
	private double kineticBarrier = 0;
	
	private double time;
	private double volume = 2.74;
	
	private static Random random = new Random();
	
	/**
	 * @param xSize The size of the box in the x direction
	 * @param ySize The size of the box in the y direction
	 * @param zSize The size of the box in the z direction
	 */
	public ParticleContainer(double xSize, double ySize, double zSize, double reactionRadius) {
		size = new Vector3(xSize, ySize, zSize);
		this.reactionRadius = reactionRadius;
	}
	
	public void setKineticBarrier(double kineticBarrier)
	{
		this.kineticBarrier = kineticBarrier;
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
	
	public ParticleDictionary getDictionary() {
		return dict;
	}
	
	public ReactionDictionary getReactionDictionary() {
		return reactDict;
	}
	
	public double getTime(){
		return (time*20);
	}
	public double getTemperature() {
		double sumKE = 0;
		for (Particle part : particles) {
			double speed = part.getVelocity().magnitude();
			sumKE += .5 * part.getMass() * speed * speed;
		}
		
		return sumKE / particles.size() * 2 / 3 / 1.38E-23;
	}
	
	public double getpH(){
		double pH = 0;
		int hydrogenParticles = getParticleMap().get("H");
		double concentration = hydrogenParticles/(10000/6*volume);
		pH = -Math.log10(concentration);
		if (pH*4 + 0.5 <= 13){
		return (pH*4 + 0.5);
		}
		else{
			return (14-getpOH());
		}
	}
	
	public double getpOH(){
		double pOH = 0;
		int hydroxideParticles = getParticleMap().get("OH");
		double concentration = hydroxideParticles/(10000/6*volume);
		pOH = -Math.log10(concentration);
		return (pOH*4 + 2);
	}
	/**
	 * @param part The Particle to add to the container
	 */
	public void addParticle(Particle part) {
		particles.add(part);
	}
	
	public void removeParticle(Particle part) {
		particles.remove(part);
	}
	
	/**
	 * @param generator The RandomGenerator used to create the random distribution
	 * @param number The number of particles to create
	 */
	public void addRandomParticles(RandomGenerator generator, int number, String name) {
		for (int i = 0; i < number; ++i) {
			addParticle(generator.getNextParticle(name));
		}
	}
	
	public void addRandomParticlesDelay(RandomGenerator generator, int number, String name, double time){
		DelayedParticles de = new DelayedParticles(generator, number, name, time);
		delayedEvents.add(de);
	}
	
	private void checkCollisions(Particle particle) {
		List<Particle> closeParticles = new ArrayList<Particle>();
		closeParticles.add(particle);
		
		for (Particle part : particles) {
			if (Math.abs(particle.getPosition().getX() - part.getPosition().getX()) < reactionRadius
					|| Math.abs(particle.getPosition().getY() - part.getPosition().getY()) < reactionRadius 
					|| Math.abs(particle.getPosition().getZ() - part.getPosition().getZ()) < reactionRadius) {
				Vector3 diff = Vector3.subtract(particle.getPosition(), part.getPosition());
				if (diff.magnitude() < reactionRadius) {
					closeParticles.add(part);
				}
			}
				
		}
		
		if (closeParticles.size() > 1) {
			//doReaction(closeParticles, particle);
			doReaction1(closeParticles, particle);
		}
	}
	
	public Map<String, Integer> getParticleMap() {
		Map<String, Integer> response = new HashMap<String, Integer>();
		
		// TODO Fill in the map here
		Set<String> names = dict.getList();
		for(String s: names)
		{
			int count = 0;
			for(Particle p: getParticles())
			{
				if (s.equals(p.getName()))
				{
					count++;
				}
			}
			response.put(s, count);
		}
		return response;
	}
	
	private boolean doReaction1(List<Particle> list, Particle particle) {
		Set<Particle> reactants = new HashSet<Particle>();
		List<Particle> products = new ArrayList<Particle>();
		List<Reaction> potentialReaction = getReactionDictionary().getReaction(particle);
		//if (particle.getName().equals("Mg")){
			//potentialReaction = null;
		//}
		if (potentialReaction == null){
			return false; }
		else {
			for(Reaction react: potentialReaction){
				kineticBarrier = react.getActivationEnergy();
				//check presence of catalyst
				if (react.getCatalyst() != null) {
					for(Particle part: list){
						if(part.getName().equals(react.getCatalyst())){
							kineticBarrier = 0;
						}
					}
				}
				//if exists a equilibrium utilize percentageReaction instead
				if (react.getPercentReaction() != 1){
					kineticBarrier = 0;
				}
				//sees if reaction occurs
				if (Math.random() > react.getPercentReaction()){
					return false;
				}
				//add reactants to list
				for (String reactantName: react.getReactants()) {
					for (Particle part: list) {
						if(part.getName().equals(reactantName) && !(reactants.contains(part))) {
								reactants.add(part); 
								break;
						}
					}
				}
				//if enough reactacts, complete reaction
				if(reactants.size() == react.getNumReactants()){
					Vector3 totalMomentum = new Vector3();
					double kineticEnergy = 0;
					//calculates KE and momentum
					for(Particle part: reactants) {
						totalMomentum = totalMomentum.add(Vector3.scale(part.getVelocity(), part.getMass()));
						double speed = part.getVelocity().magnitude();
						kineticEnergy += speed*speed*part.getMass();
					}
					if(kineticEnergy > kineticBarrier){
						for(Particle part: reactants) {
							removeParticle(part);
						}
						Vector3 partialMomentum = totalMomentum.scale(1./react.getNumReactants());
						for(String productName: react.getProducts()) {
							Vector3 newVelocity = partialMomentum.scale(1./dict.getMass(productName));
							products.add(dict.makeParticle(particle.getPosition(), newVelocity, productName));
						}
						for(Particle part: products) {
							toBeAdded.add(part);
						}
					return true;
					}
					else {
					reactants.clear();
					}
				}
				else{
					reactants.clear();
				}
			}
			return false;
		}
	}
	
	public List<Particle> getParticles() {
		return particles;
	}
	
	/**
	 * This advances all the particles by one time step
	 * @param deltaTime The amount of time to advance the particle collection by
	 */
	public void advanceParticles(double deltaTime) {
		for (Particle proj : particles) {
			proj.update(deltaTime);
		}
		for (Particle proj : particles) {
			if (particles.contains(proj)) {
				checkParticle(proj);
				checkCollisions(proj);
			}
		}
		for(Particle part: toBeAdded) {
			addParticle(part);
		}
		toBeAdded.clear();
		time += deltaTime;
		//check delayedEvents to see if particles added
		for(DelayedParticles de: delayedEvents){
			if((Math.abs(getTime() - de.getTimeDelay()) < deltaTime*0.01)){
				for (int i = 0; i < de.getNumber(); ++i) {
					addParticle(de.getGenerator().getNextParticle(de.getName()));
				}		
			}
		}
		System.out.println(getTime() + "\t" + getParticleMap() + "\t" + getpH());
	}
	
	private void checkParticle(Particle particle) {
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
