package kinematics;
/**
 * Engine class of Vector Kinematics
 */
import java.util.ArrayList;

public class OldWorld {

	private ArrayList<Projectile> projectiles;
	private boolean airResistance;
	private boolean springForce;
	private double C = -0.3;
	private Vector g = new Vector(-9.8, 0, 0);
	private double k = 2.0;
	
	public OldWorld()
	{
		projectiles = new ArrayList<Projectile>();
		airResistance = false;
		springForce = false;
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
		System.out.println(p.toString());
	}
	
	public void getValues(double timeIncrement, double totalTime, double mass)
	{ 
		for (Projectile p: projectiles)
		{
			System.out.println(0.0 +"\t" + p.toString());
		
			for (int i = 1; i <= totalTime/timeIncrement; i++)
			{
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
				System.out.println(i*timeIncrement + "\t" + p.toString());
				p.setAcceleration(g);
			}
		}
	}
	
	public void getHeader()
	{
		System.out.println("Time \t XPosition \t YPosition \t ZPosition \t XVelocity \t YVelocity \t ZVelocity \t XAcceleration \t YAcceleration \t ZAcceleration");
	}
}

