package kinematics;

public class RunWorldKinematics {
	
	private static final double timeIncrement = 0.1;
	private static final double totalTime = 100;
	private static final double mass = 5;
	private static Vector position;
	private static Vector velocity;
	private static Vector acceleration;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		level1();
		level2();
		level3();
		challenge();
	}
	
	public static void level1()
	{
		OldWorld world = new OldWorld();
		position = new Vector(0, 0, 0);
		velocity = new Vector(2, 0, 0);
		acceleration = new Vector(0, 0, 0);
		world.addProjectile(new Projectile(position, velocity, acceleration, mass));
		world.getHeader();
		world.getValues(timeIncrement, totalTime, mass);
	}
	
	public static void level2()
	{
		OldWorld world = new OldWorld();
		position = new Vector(0, 0, 0);
		velocity = new Vector (0, 0, 0);
		acceleration = new Vector(-9.8, 0, 0);
		world.addProjectile(new Projectile(position, velocity, acceleration, mass));
		world.getHeader();
		world.getValues(timeIncrement, totalTime, mass);
	}
	
	public static void level3()
	{
		OldWorld world = new OldWorld();
		position = new Vector(0, 0, 0);
		velocity = new Vector(0, 0, 0);
		acceleration = new Vector (-9.8, 0, 0);
		world.addProjectile(new Projectile(position, velocity, acceleration, mass));
		world.getHeader();
		world.setAirResistance();
		world.getValues(timeIncrement, totalTime, mass);
	}
	
	public static void challenge()
	{
		OldWorld world = new OldWorld();
		position = new Vector(0, 0, 0);
		velocity = new Vector(0, 0, 0);
		acceleration = new Vector (-9.8, 0, 0);
		world.addProjectile(new Projectile(position, velocity, acceleration, mass));
		world.getHeader();
		world.setAirResistance();
		world.setSpringForce();
		world.getValues(timeIncrement, totalTime, mass);
	}
}
