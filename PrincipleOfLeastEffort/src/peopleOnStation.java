import java.util.ArrayList;
public class peopleOnStation {

	double xStairs = 50;
	double yStairs = 50;
	double xposition = 0;
	double yposition = 0;
	double length = 0;
	double velocity = 0.00;
	double optimalTheta1 = 0.00;
	double betweenTheta = 0.00;
	double theta1 = 0;
	public Circle c1;
	
	public peopleOnStation(double x, double y, int theta) {
		// TODO Auto-generated method stub
			
			c1 = new Circle(10, (int) xposition, (int) yposition);
			c1.makeVisible();
	   }
	
	public double getVelocity(double x, double y, int theta)
	{
		xposition = x;
		yposition = y;
		double optimalTheta1 = Math.atan((xStairs-x)/(yStairs-y));
		int optimalTheta = (int) (optimalTheta1 * 180 / Math.PI);
		int betweenTheta1 = optimalTheta - theta;
		betweenTheta = betweenTheta1 * Math.PI/180;
		theta1 = theta * Math.PI/180;
		for (int i = 0; i < 2500; i++)
		{
			double effort = 100.00;
			double second = 0.275;
			double v = i/100;
			double newEffort = second*(2.23 + 1.26 * Math.abs(v*v)) + 2*Math.abs(length - v*second*betweenTheta)*1.33;
			if (newEffort < effort)
			{
				effort = newEffort;
				velocity = v;
				
			}
		}
		return velocity;
	}
		
		public double getLength()
		{
			double length = Math.sqrt((xStairs-xposition)*(xStairs-xposition) + (yStairs-yposition)*(yStairs-yposition));
			return length;
		}
		
		public void move()
		{
			xposition = xposition + Math.cos(theta1) * velocity * 0.275;
			yposition = yposition + Math.sin(theta1) * velocity * 0.275;
		}
		
		public double getXPosition()
		{
			return xposition;
		}
		
		public double getYPosition()
		{
			return yposition;
		}
		
}
