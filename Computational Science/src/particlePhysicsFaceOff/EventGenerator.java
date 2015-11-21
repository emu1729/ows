package particlePhysicsFaceOff;

import java.util.List;
import java.util.Random;

abstract public class EventGenerator {
	
	public class Particle {

		private double energy;
		private Vector3 momentum;
		
		public Particle(double energy, Vector3 momentum) {
			this.energy = energy;
			this.momentum = momentum;
		}
		
		public double getEnergy () {
			return energy;
		}
		
		public Vector3 getMomentum() {
			return momentum;
		}
	}
	
	static private Random random = new Random();
	
	public Event makeEvent(boolean signalEvent) {
		
		List<Particle> particles = makeParticles(signalEvent);
	
		// Let me worry about this part
		
		return null;
	}
	
	/**
	 * @param signalEvent Whether this is a signal event (true) or a background event (false)
	 * @return A list of all Particles in this event, where a Particle contains an energy (scalar) and a momentum (vector)
	 */
	abstract protected List<Particle> makeParticles(boolean signalEvent);

	/**
	 * @param event An input event, reduced to a list of particles
	 * @return True if it is a signal event, false if background.  This is based on your knowledge of the underlying algorithm;
	 * I don't expect it to be perfect, but it should be better than any machine learning algorithm can do.
	 */
	abstract public boolean discriminateEvent(List<Particle> event);

	/**
	 * @return The signal fraction, as a number between 0 and 1.  This must be kept a closely guarded secret
	 */
	abstract protected double signalFraction();
	
	/**
	 * @return The random number generator that every derived class will use
	 */
	protected Random getRandom() {
		return random;
	}

}
