package kinematics;

public class EulerMethod {

	private static final double mass = 5.0;
	private static final double C = 0.3;
	private static final double k = 2.0;
	private static final double g = -9.8;
	private double x;
	private double x0;
	private double v;
	private double v0;
	private double a;
	private double a0;
	private double force;
	
	public double calculateNewPosition(double velocity1, double time1)
	{
		x0 = velocity1 * time1;
		x += x0;
		return x;
	}
	
	public double calculateNewVelocity(double acceleration1, double time1)
	{
		v0 = acceleration1 * time1;
		v += v0;
		return v;
	}
	
	public double calculateNewAcceleration(double velocityCurrent)
	{
		force = C * velocityCurrent * velocityCurrent;
		a0 = force/mass;
		a = g + a0;
		if (a < 0)
		{
			return a;
		}
		else
		{
			return 0.0;
		}
	}
	
	public double calculateNewSpringAcceleration(double velocityCurrent, double displacementCurrent)
	{
		if (velocityCurrent < 0)
		{
			force = C * velocityCurrent * velocityCurrent - k * displacementCurrent;
		}
		else
		{
			force = -C * velocityCurrent * velocityCurrent - k * displacementCurrent;
		}
		
		a0 = force/mass;
		a = g + a0;
		return a;
	}
}
