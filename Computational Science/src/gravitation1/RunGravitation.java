package gravitation1;

//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.PrintStream;

public class RunGravitation {

	private static final double timeIncrement = 25000;
	private static final int nIncrements = 200;
	
	private static final double EARTH_MASS = 5.972 * Math.pow(10, 24);
	private static final double MOON_MASS = 7.3476739 * Math.pow(10, 22);
	
	private static final double DISTANCE_EARTH_MOON = 385000000;
	
	private static final double MOON_VELOCITY = 1023; //m per second
	private static final double MOON_RADIUS = 1737500;
	private static final double MOON_RADIUS_Increment = MOON_RADIUS/6;
	
	private static final double EARTH_RADIUS = 6371000;
	private static final double EARTH_RADIUS_I = DISTANCE_EARTH_MOON;
	
	private static final double smallIncrement = EARTH_RADIUS/6;
	private static final double smallIncrement_I = DISTANCE_EARTH_MOON/10;
	private static final double massRatio = Math.PI/6;
	
	public static double timeInterval = 25000;

    public static int earthPartitions = 10;
    public static int moonPartitions = 5;
    public static double shellRatio = 0.8;
	

	public static void main(String[] args){ //throws FileNotFoundException{
		// TODO Auto-generated method stub
		//PrintStream file = new PrintStream(new FileOutputStream("gravitation.xls");
		//level1();
		//level2();
		level2PartII();
		//level3();
		//level3PartII();
		//level3PartIII();
		//challenge();
		//challenge1();
	}
	
	public static void level1()
	{
		GravitationEngine universe = new GravitationEngine(System.out);
		universe.addProjectile(new PointProjectile(new Vector3(), new Vector3(), EARTH_MASS, "Earth"));
		universe.addProjectile(new PointProjectile(new Vector3(DISTANCE_EARTH_MOON, 0, 0), new Vector3(0, MOON_VELOCITY, 0), MOON_MASS, "Moon"));
		System.out.println(universe.header());
		universe.run(timeIncrement, nIncrements, true);
	}
	
	public static void level2()
	{
		
		GravitationEngine universe = new GravitationEngine(System.out);
		ExtendedProjectile earth = new ExtendedProjectile("Earth", false);
		for(int i = 0; i <= EARTH_RADIUS; i+=smallIncrement)
		{
			for(int j = 0; j <= EARTH_RADIUS; j+=smallIncrement)
			{
				for(int k = 0; k <= EARTH_RADIUS; k+=smallIncrement)
				{
					double xPosition = i - (EARTH_RADIUS)/2;;
					double yPosition = j - (EARTH_RADIUS)/2;
					double zPosition = k - (EARTH_RADIUS)/2;
					double mass = EARTH_MASS/(Math.pow(EARTH_RADIUS/smallIncrement+1, 3));
					earth.addProjectile(new PointProjectile(new Vector3(xPosition, yPosition, zPosition), new Vector3(), mass, ""));
				}
			}
		}
		universe.addProjectile(earth);
		universe.addProjectile(new PointProjectile(new Vector3(DISTANCE_EARTH_MOON, 0, 0), new Vector3(0, MOON_VELOCITY, 0), MOON_MASS, "Moon"));
		System.out.println(universe.header());
		universe.run(timeIncrement, nIncrements, true);

	}
	public static void level2PartII()
	{
		
		GravitationEngine universe = new GravitationEngine(System.out);
		ExtendedProjectile earth = new ExtendedProjectile("Earth", false);
		for(int i = 0; i <= EARTH_RADIUS_I; i+=smallIncrement_I)
		{
			for(int j = 0; j <= EARTH_RADIUS_I; j+=smallIncrement_I)
			{
				for(int k = 0; k <= EARTH_RADIUS_I; k+=smallIncrement_I)
				{
					double xPosition = i - (EARTH_RADIUS_I)/2;
					double yPosition = j - (EARTH_RADIUS_I)/2;
					double zPosition = k - (EARTH_RADIUS_I)/2;
					double mass = EARTH_MASS/(Math.pow(EARTH_RADIUS_I/smallIncrement_I+1, 3));
					earth.addProjectile(new PointProjectile(new Vector3(xPosition, yPosition, zPosition), new Vector3(), mass, ""));
				}
			}
		}
		universe.addProjectile(earth);
		universe.addProjectile(new PointProjectile(new Vector3(DISTANCE_EARTH_MOON, 0, 0), new Vector3(0, MOON_VELOCITY, 0), MOON_MASS, "Moon"));
		System.out.println(universe.header());
		universe.run(timeIncrement, nIncrements, true);

	}
	
	public static void level3()
	{
		GravitationEngine universe = new GravitationEngine(System.out);
		ExtendedProjectile earth = new ExtendedProjectile("Earth", false);
		for(int i = 0; i <= 2*EARTH_RADIUS; i+=smallIncrement)
		{
			for(int j = 0; j <= 2*EARTH_RADIUS; j+=smallIncrement)
			{
				for(int k = 0; k <= 2*EARTH_RADIUS; k+=smallIncrement)
				{
					double xPosition = i - EARTH_RADIUS;
					double yPosition = j - EARTH_RADIUS;
					double zPosition = k - EARTH_RADIUS;
					double mass = EARTH_MASS/(Math.pow(2*EARTH_RADIUS/smallIncrement, 3)*massRatio);
					if (new Vector3(xPosition, yPosition, zPosition).magnitude() <= EARTH_RADIUS)
					{
						earth.addProjectile(new PointProjectile(new Vector3(xPosition, yPosition, zPosition), new Vector3(), mass, ""));
					}
				}
			}
		}
		universe.addProjectile(earth);
		universe.addProjectile(new PointProjectile(new Vector3(DISTANCE_EARTH_MOON, 0, 0), new Vector3(0, MOON_VELOCITY, 0), MOON_MASS, "Moon"));
		System.out.println(universe.header());
		universe.run(timeIncrement, nIncrements, true);
	}
	
	public static void level3PartII()
	{
		GravitationEngine universe = new GravitationEngine(System.out);
		ExtendedProjectile earth = new ExtendedProjectile("Earth", false);
		for(int i = 0; i <= 2*EARTH_RADIUS_I; i+=smallIncrement_I)
		{
			for(int j = 0; j <= 2*EARTH_RADIUS_I; j+=smallIncrement_I)
			{
				for(int k = 0; k <= 2*EARTH_RADIUS_I; k+=smallIncrement_I)
				{
					double xPosition = i - EARTH_RADIUS_I;
					double yPosition = j - EARTH_RADIUS_I;
					double zPosition = k - EARTH_RADIUS_I;
					double mass = EARTH_MASS/(Math.pow(2*EARTH_RADIUS_I/smallIncrement_I, 3)*massRatio);
					if (new Vector3(xPosition, yPosition, zPosition).magnitude() <= EARTH_RADIUS_I)
					{
						earth.addProjectile(new PointProjectile(new Vector3(xPosition, yPosition, zPosition), new Vector3(), mass, ""));
					}
				}
			}
		}
		universe.addProjectile(earth);
		universe.addProjectile(new PointProjectile(new Vector3(DISTANCE_EARTH_MOON, 0, 0), new Vector3(0, MOON_VELOCITY, 0), MOON_MASS, "Moon"));
		System.out.println(universe.header());
		universe.run(timeIncrement, nIncrements, true);
	}
	
	public static void level3PartIII()
	{
		GravitationEngine universe = new GravitationEngine(System.out);
		ExtendedProjectile earth = new ExtendedProjectile("Earth", false);
		for(int i = 0; i <= 2*EARTH_RADIUS; i+=smallIncrement)
		{
			for(int j = 0; j <= 2*EARTH_RADIUS; j+=smallIncrement)
			{
				for(int k = 0; k <= 2*EARTH_RADIUS; k+=smallIncrement)
				{
					double xPosition = i - EARTH_RADIUS;
					double yPosition = j - EARTH_RADIUS;
					double zPosition = k - EARTH_RADIUS;
					double mass = 2*EARTH_MASS/(Math.pow(2*EARTH_RADIUS/smallIncrement, 3)*massRatio);
					Vector3 displacement = new Vector3(xPosition, yPosition, zPosition);
					if (displacement.magnitude() <= EARTH_RADIUS && displacement.magnitude() >= EARTH_RADIUS*0.8)
					{
						earth.addProjectile(new PointProjectile(displacement, new Vector3(), mass, ""));
					}
				}
			}
		}
		universe.addProjectile(earth);
		universe.addProjectile(new PointProjectile(new Vector3(0, 0, 0), new Vector3(500, 200, 1000), MOON_MASS, "Moon"));
		System.out.println(universe.header());
		universe.run(timeIncrement, nIncrements, true);
	}
	
	public static void challenge()
	{
		GravitationEngine universe = new GravitationEngine(System.out);
		ExtendedProjectile earth = new ExtendedProjectile("Earth", true);
		for(int i = 0; i <= EARTH_RADIUS; i+=smallIncrement)
		{
			for(int j = 0; j <= EARTH_RADIUS; j+=smallIncrement)
			{
				for(int k = 0; k <= EARTH_RADIUS; k+=smallIncrement)
				{
					double xPosition = i - (EARTH_RADIUS)/2+1;
					double yPosition = j - (EARTH_RADIUS)/2+1;
					double zPosition = k - (EARTH_RADIUS)/2+1;
					double earthMass = EARTH_MASS/(Math.pow((EARTH_RADIUS/smallIncrement+1), 3));
					earth.addProjectile(new PointProjectile(new Vector3(xPosition, yPosition, zPosition), new Vector3(), earthMass, "earth"));
				}
			}
		}
		universe.addProjectile(earth);
		ExtendedProjectile moon = new ExtendedProjectile("Moon", true);
		for(int i = 0; i <= MOON_RADIUS; i+=MOON_RADIUS_Increment)
		{
			for(int j = 0; j <= MOON_RADIUS; j+=MOON_RADIUS_Increment)
			{
				for(int k = 0; k <= MOON_RADIUS; k+=MOON_RADIUS_Increment)
				{
					double xPosition = i - (MOON_RADIUS)/2 + 1 + DISTANCE_EARTH_MOON;
					double yPosition = j - (MOON_RADIUS)/2+1;
					double zPosition = k - (MOON_RADIUS)/2+1;
					double moonMass = MOON_MASS/(Math.pow((MOON_RADIUS/MOON_RADIUS_Increment+1), 3));
				    moon.addProjectile(new PointProjectile(new Vector3(xPosition, yPosition, zPosition), new Vector3(0, MOON_VELOCITY, 0), moonMass, "moon"));
				}
			}
		}
		universe.addProjectile(moon);
		System.out.println(universe.header());
		universe.run(timeIncrement, nIncrements, true);
	}
	
	public static void challenge1() {
        GravitationEngine ge = new GravitationEngine(System.out);

        ExtendedProjectile earth = new ExtendedProjectile("Earth", true);
        for(int i = 0; i < earthPartitions; ++i) {
            for(int j = 0; j < earthPartitions; ++j) {
                for(int k = 0; k < earthPartitions; ++k) {
                    double x = EARTH_RADIUS * ((double)(2 * i + 1) / earthPartitions - 1);
                    double y = EARTH_RADIUS * ((double)(2 * j + 1) / earthPartitions - 1);
                    double z = EARTH_RADIUS * ((double)(2 * k + 1) / earthPartitions - 1);
                    earth.addProjectile(new PointProjectile(new Vector3(x,y,z), new Vector3(0,0,0),EARTH_MASS / Math.pow(earthPartitions,3),"Earth"));
                }
            }
        }
        ge.addProjectile(earth);

        ExtendedProjectile moon = new ExtendedProjectile("Moon", true);
        for(int i = 0; i < moonPartitions; ++i) {
            for(int j = 0; j < moonPartitions; ++j) {
                for(int k = 0; k < moonPartitions; ++k) {
                    double x = MOON_RADIUS * ((double)(2 * i + 1) / moonPartitions - 1) + DISTANCE_EARTH_MOON;
                    double y = MOON_RADIUS * ((double)(2 * j + 1) / moonPartitions - 1);
                    double z = MOON_RADIUS * ((double)(2 * k + 1) / moonPartitions - 1);
                    moon.addProjectile(new PointProjectile(new Vector3(x,y,z), new Vector3(0,MOON_VELOCITY,0),MOON_MASS / Math.pow(moonPartitions,3),"Moon"));
                }
            }
        }
        ge.addProjectile(moon);
        System.out.print(ge.header() + "\n");
        ge.run(timeInterval,nIncrements);
    }
}
