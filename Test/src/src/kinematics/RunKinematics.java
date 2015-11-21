/**
 * 
 */
package kinematics;

/**
 * @author Emily Mu
 * @date 1.13.15
 */
public class RunKinematics {

	/**
	 * @param constantVelocity gives constant velocity
	 * @param timeInterval gives interval of time
	 * @param totalTime gives total length of time
	 * @param constantAcceleration is gravity acceleration
	 * @param position is position variable
	 * @param velocity is velocity variable
	 * @param acceleration is acceleration variable
	 */
	private static final double constantVelocity = 2.0;
	private static final double timeInterval = 0.1;
	private static final double totalTime = 100;
	private static final double constantAcceleration = -9.8;
	private static double position;
	private static double velocity;
	private static double acceleration;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		level1();
		level2();
		level3();
		challenge();
	}
/**
 * Calculates position from constant velocity of 2.0 m/s
 */
	public static void level1()
	{	
		position = 0.0;
		System.out.println("Time(s)" + "\t Position(m)" + "\t Velocity(m/s)");
		EulerMethod euler1 = new EulerMethod();
		for (int i = 0; i <= totalTime/timeInterval; i ++)
		{
			System.out.println((timeInterval*i) + "\t" + position + "\t" + constantVelocity);
			position = euler1.calculateNewPosition(constantVelocity, timeInterval);
		}
	}
/**
 * Calculates position and velocity of a free falling object
 */
	public static void level2()
	{
		position = 0.0;
		velocity = 0.0;
		System.out.println("Time(s)" + "\t Position(m)" + "\t Velocity(m/s)" + "\t Acceleration(m/s^2)");
		EulerMethod euler2 = new EulerMethod();
		for (int i = 0; i <= totalTime/timeInterval; i ++)
		{
			System.out.println((timeInterval*i) + "\t" + position + "\t" + velocity + "\t" + constantAcceleration);
			velocity = euler2.calculateNewVelocity(constantAcceleration, timeInterval);
			position = euler2.calculateNewPosition(velocity, timeInterval);
		}
	}
/**
 * Calculates position and velocity of a free falling object with air resistance
 */
	public static void level3()
	{
		position = 0.0;
		velocity = 0.0;
		acceleration = -9.8;
		System.out.println("Time(s)" + "\t Position(m)" + "\t Velocity(m/s)" + "\t Acceleration(m/s^2)");
		EulerMethod euler3 = new EulerMethod();
		for (int i = 0; i <= totalTime/timeInterval; i++)
		{
			System.out.println((timeInterval*i) + "\t" + position + "\t" + velocity + "\t" + acceleration);
			acceleration = euler3.calculateNewAcceleration(velocity);
			velocity = euler3.calculateNewVelocity(acceleration, timeInterval);
			position = euler3.calculateNewPosition(velocity, timeInterval);
		}
	}
/**
 * Calculates position and velocity of object hanging from a spring
 */
	public static void challenge()
	{
		position = 0.0;
		velocity = 0.0;
		acceleration = -9.8;
		System.out.println("Time(s)" + "\t Position(m)" + "\t Velocity(m/s)" + "\t Acceleration(m/s^2)");
		EulerMethod euler4 = new EulerMethod();
		for (int i = 0; i <= totalTime/timeInterval; i++)
		{
			System.out.println((timeInterval*i) + "\t" + position + "\t" + velocity + "\t" + acceleration);
			acceleration = euler4.calculateNewSpringAcceleration(velocity, position);
			velocity = euler4.calculateNewVelocity(acceleration, timeInterval);
			position = euler4.calculateNewPosition(velocity, timeInterval);
		}
	}
}
