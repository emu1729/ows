package kinematics;
import java.io.*;

public class RunWorldProjectiles {

	private static final double timeIncrement = 0.001;
	private static final double totalTime = 25;
	private static final double mass = 5;
	private static final double initialVelocity = 10.0;
	private static final double pendulumLength = 4.0;
	private static final double C = -0.3;
	private static final Vector g = new Vector(0, 0, -9.8);
	private static final double k = 2.0;
	private static final double floor = 0.0;
	private static final Vector initialPendulumPosition = new Vector(2, 0, 0);
	private static Vector position;
	private static Vector velocity;
	private static Vector acceleration;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
				//level1(45);
				//level2(45);
				//level3();
				//challenge();
			}
			
			public static double level1(double angleDegrees)
			{
				World world = new World(C, g, k, floor);
				position = new Vector(0, 0, 0);
				velocity = new Vector(initialVelocity*Math.cos(angleDegrees*Math.PI/180), 0, initialVelocity*Math.sin(angleDegrees*Math.PI/180));
				acceleration = g;
				world.addProjectile(new Projectile(position, velocity, acceleration, mass));
				world.getHeader();
				return (world.getValues(timeIncrement));
			}
			
			public static double level2(double angleDegrees)
			{
				World world = new World(C, g, k, floor);
				position = new Vector(0, 0, 0);
				velocity = new Vector(initialVelocity*Math.cos(angleDegrees*Math.PI/180), 0, initialVelocity*Math.sin(angleDegrees*Math.PI/180));
				acceleration = g;
				world.addProjectile(new Projectile(position, velocity, acceleration, mass));
				world.getHeader();
				world.setAirResistance();
				return(world.getValues(timeIncrement));
			}
			
			public static void level3()
			{
				double maximumAngle1 = 0;
				double maximumRange1 = 0;
				double maximumAngle2 = 0;
				double maximumRange2 = 0;
				PrintStream originalStream = System.out;

				PrintStream dummyStream    = new PrintStream(new OutputStream(){
				    public void write(int b) {
				        //NO-OP
				    }
				});

				System.setOut(dummyStream);
				for (int i = 0; i < 90; i += 1)
				{
					if (level1(i) > maximumRange1)
					{
						maximumAngle1 = i;
						maximumRange1 = level1(i);
					}
					
					if (level2(i) > maximumRange2)
					{
						maximumAngle2 = i;
						maximumRange2 = level2(i);
					}
					
				}
				System.setOut(originalStream);
				System.out.println(maximumAngle1 + "\t" + maximumRange1 + "\t" + maximumAngle2 + "\t" + maximumRange2);
			}
			
			public static void challenge()
			{
				World world = new World(C, g, k, floor);
				position = initialPendulumPosition;
				velocity = new Vector(0, 0, 0);
				acceleration = new Vector (0, 0, 0);
				world.addProjectile(new Projectile(position, velocity, acceleration, mass));
				world.getHeader();
				world.setAirResistance();
				world.getPendulumValues(timeIncrement, totalTime, pendulumLength);
			}

			public static void doubleChallenge()
			{
				
			}
	}


