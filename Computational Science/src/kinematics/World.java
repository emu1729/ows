package kinematics;
/**
 * Engine class of Vector Kinematics
 */
import java.util.ArrayList;

public class World {

	private ArrayList<Projectile> projectiles;
	private boolean airResistance;
	private boolean springForce;
	private double C;
	private Vector g;
	private double k;
	private double floor;
	
	public World(double C, Vector g, double k, double floor)
	{
		projectiles = new ArrayList<Projectile>();
		airResistance = false;
		springForce = false;
		this.C = C;
		this.g = g;
		this.k = k;
		this.floor = floor;
	}
	
	public void setAirResistance()
	{
		airResistance = true;
	}
	
	public void setSpringForce()
	{
		springForce = true;
	}
	
	public void addProjectile(Projectile p)
	{
		projectiles.add(p);
	}
/**
 * Running projectiles using FIAT oops
 * @param timeIncrement
 * @return running of world using FIAT oops
 */
	public double getValues(double timeIncrement)
	{ 
		double range = 0;
		for (Projectile p: projectiles)
		{
			System.out.println(0.0 +"\t" + p.toString());
			int i = 0; 
			while(p.getPosition().getZ() >= floor)
			{
				i++;
				if (airResistance)
				{
					if (p.getVelocity().getMagnitude() != 0)
					{
						p = p.addForce(Vector.scaleVector(Vector.unitVector(p.getVelocity()), p.getVelocity().getMagnitude()*p.getVelocity().getMagnitude()*C));
					}
				}
				if (springForce)
				{
					if (p.getPosition().getMagnitude() != 0)
					{
						p = p.addForce(Vector.scaleVector(p.getPosition(), -k));
					}
				}
				p = p.increment(timeIncrement);
				if (p.getPosition().getZ() >= floor)
				{
					System.out.println(i*timeIncrement + "\t" + p.toString());
					range = p.getPosition().getX();
				}
				p = p.setForce(Vector.scaleVector(g, p.getMass()));
			}
		}
		return range;
	}
/**
 * Solves for values of a hanging pendulum
 * @param timeIncrement
 * @param totalTime or total amount of time
 * @param hanging mass
 * @param pendulumLength length of pendulum string
 */
	public void getPendulumValues(double timeIncrement, double totalTime, double pendulumLength)
	{ 	
		boolean tension = false;
		for (Projectile p: projectiles)
		{
			System.out.println(0.0 +"\t" + p.toString());
			for (int i = 1; i <= totalTime/timeIncrement; i++)
			{
				if (p.getPosition().getMagnitude() >= pendulumLength)
				{
					tension = true;
				}
				else
				{
					tension = false;
				}				
				if (tension)
				{
					/**
					 * Set initial force to just gravity.
					 */
					p = p.setForce(Vector.scaleVector(g, p.getMass()));
					/**
					 * This is the "normal" force calculation because the string is not elastic
					 */
					if(p.getVelocity().getMagnitude() != 0 && p.getPosition().getMagnitude() != 0)
					{
						double angle1 = Vector.getAngle(p.getVelocity(), p.getPosition());
						p = p.addForce(Vector.scaleVector(Vector.unitVector(p.getPosition()), -(p.getVelocity().getMagnitude()*p.getMass()*Math.cos(angle1))/timeIncrement));
					}
					double angle = Vector.getAngle(g, p.getPosition());
					/**
					 * The following two formulas calculate tension and centripetal force respectively
					 */
					p = p.addForce(Vector.scaleVector(Vector.unitVector(p.getPosition()), -(g.getMagnitude()*p.getMass()*Math.cos(angle))));
					p = p.addForce(Vector.scaleVector(Vector.unitVector(p.getPosition()), -(p.getVelocity().getMagnitude()*p.getVelocity().getMagnitude()*p.getMass()/pendulumLength)));
					
				}
				else
				{
					p = p.setForce(Vector.scaleVector(g, p.getMass()));
				}
				if (airResistance)
				{
					if (p.getVelocity().getMagnitude() != 0)
					{
						p = p.addForce(Vector.scaleVector(Vector.unitVector(p.getVelocity()), p.getVelocity().getMagnitude()*p.getVelocity().getMagnitude()*C));
					}
				}
				
				p = p.increment(timeIncrement);
				System.out.println(i*timeIncrement + "\t" + p.toString());
			}
		}
	}
	
	public void getHeader()
	{
		System.out.println("Time \t XPosition \t YPosition \t ZPosition \t Displacement \t XVelocity \t YVelocity \t ZVelocity \t Speed \t XAcceleration \t YAcceleration \t ZAcceleration \t Magnitude Acceleration");
	}
}
