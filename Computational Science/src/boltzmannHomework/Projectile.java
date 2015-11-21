package boltzmannHomework;

import java.awt.Color;

/**
 * @author Peter Dong
 * A generic particle class for general use
 */
public class Projectile {

	private Vector3 position;
	private Vector3 velocity;
	private double mass;
	private Color color;
	
	/**
	 * @param position The original position of the Particle
	 * @param velocity The initial velocity of the Particle
	 * @param mass The mass of the particle
	 * @param color The color of the particle in the display
	 */
	public Projectile(Vector3 position, Vector3 velocity, double mass, Color color) {
		this.position = position;
		this.velocity = velocity;
		this.mass = mass;
		this.color = color;
	}
	
	/**
	 * Changes the position based on velocity
	 * @param timeIncrement The increment for advancement
	 */
	protected void advancePosition(double timeIncrement) {
		position = Vector3.add(position, velocity.scale(timeIncrement));
	}
	
	public Vector3 getPosition() {
		return position;
	}
	
	public Vector3 getVelocity() {
		return velocity;
	}
	
	public double getMass() {
		return mass;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setPosition(Vector3 position) {
		this.position = position;
	}
	
	public void setVelocity(Vector3 velocity) {
		this.velocity = velocity;
	}
	
	/**
	 * Updates the position of the object based on its current velocity
	 * @param timeIncrement - the amount of time over which the force acts
	 */
	public void update(double timeIncrement) {
		advancePosition(timeIncrement);
	}
	
}
