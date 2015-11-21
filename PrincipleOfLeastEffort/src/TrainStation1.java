
public class TrainStation1 {

	public double x;
	public double y;
	public int theta;
	public double velocity;
	
	public TrainStation1() {
		// TODO Auto-generated method stub
		
		x = 0;
		y = 0;
		theta = 90;
		Canvas canvas = Canvas.getCanvas();
		peopleOnStation p = new peopleOnStation(0, 0, 90);
		Circle c = new Circle(10, 50, 50);
		c.makeVisible();
		for (int i = 0; i < 10; i++)
		{
		velocity = p.getVelocity(x, y, theta);
		p.move();
		x = p.getXPosition();
		y = p.getYPosition();
		System.out.println(x + "," + y);
		theta = 90;
		optimizeVelocity t = new optimizeVelocity(x, y, velocity, theta);
		theta = t.getAngle();
		}
		
	}
	
	public static void main (String []args)
	{
		TrainStation1 ts = new TrainStation1();
	}
}
