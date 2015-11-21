package kinematics;
/**
 * Creates a projectile with three vector components--position, velocity, acceleration
 * @author Emily Mu
 * @date 1.17.15
 */
public class Projectile {
	
	private Vector position;
	private Vector velocity;
	private Vector acceleration;
	private double mass;

	public Projectile(Vector position, Vector velocity, Vector acceleration, double mass)
	{
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.mass = mass;
	}
	
	public Vector getPosition()
	{
		return position;
	}
	
	public void setPosition(Vector newPosition)
	{
		position = newPosition;
	}
	
	public Vector getVelocity()
	{
		return velocity;
	}
	
	public void setVelocity(Vector newVelocity)
	{
		velocity = newVelocity;
	}
	
	public Vector getAcceleration()
	{
		return acceleration;
	}
	
	public void setAcceleration(Vector newAcceleration)
	{
		acceleration = newAcceleration;
	}
	
	public double getMass()
	{
		return mass;
	}
	
	public void setMass(double newMass)
	{
		mass = newMass;
	}
/**
 * 	
 * @param time
 * @return increments the velocity and position of the projectile in a set time
 */
	public Projectile increment(double time)
	{
		velocity = Vector.addVector(velocity, Vector.scaleVector(acceleration, time));
		position = Vector.addVector(position, Vector.scaleVector(velocity, time));
		return (new Projectile(position, velocity, acceleration, mass));
	}
	
	public Projectile setForce(Vector force)
	{
		acceleration = Vector.scaleVector(force, 1/mass);
		return (new Projectile(position, velocity, acceleration, mass));
	}
/**
 * 	
 * @param force
 * @param mass
 * @return new acceleration of projectile after adding a force
 */
	
	public Projectile addForce(Vector force)
	{
		acceleration = Vector.addVector(acceleration, Vector.scaleVector(force, 1/mass));
		return (new Projectile(position, velocity, acceleration, mass));
	}
	
	public String toString()
	{
		return (position.toString() + "\t" + velocity.toString() + "\t" + acceleration.toString());
	}
}
