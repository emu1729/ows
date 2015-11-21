import java.util.ArrayList;
public class optimizeVelocity {

	private ArrayList<peopleOnStation> closePeople;
	double x = 0;
	double y = 0;
	double previousVelocity = 0;
	int optimalTheta = 0; //theta closest to stairs
	int bestTheta = 0; //theta closest to optimal
	int previousTheta = 0; 
	double xStairs = 50;
	double yStairs = 50;
	public optimizeVelocity(double x, double y, double previousVelocity, int previousTheta)
	{
		closePeople = new ArrayList<peopleOnStation>();
		this.x = x;
		this.y = y;
		this.previousVelocity = previousVelocity;
		this.previousTheta = previousTheta;
		double optimalTheta1 = Math.atan((xStairs-x)/(yStairs-y));
		optimalTheta = (int) (optimalTheta1 * 180/Math.PI);
	}
	
	public void addPerson(peopleOnStation p)
	{
		double distance = Math.sqrt((x-p.getXPosition())*(x-p.getXPosition()) + (y-p.getYPosition())*(y-p.getYPosition()));
		if (distance < 5)
		{
			closePeople.add(p);
		}
	}
	public int getAngle()
	{
		boolean b = true;
		int i = 0;
		double theta = 0;
		outerloop:
		for(peopleOnStation p: closePeople)
			{
				for (int c = 0; c<101; c++)
				{
					double d = 1.33 * c/100;
					theta = (i + optimalTheta) * Math.PI/180;
					double newXPosition = d * Math.cos(theta) + y;
					double newYPosition = d * Math.cos(theta) + y;
					double length = Math.sqrt((newXPosition-p.getXPosition())*(newXPosition-p.getXPosition()) + (newYPosition-p.getYPosition())*(newYPosition-p.getYPosition()));
					if (length < 1.33)
					{
						b = false;
						if (i == 0)
						{
							i = 1;
						}
						else if (i > 0)
						{
							i = -i;
						}
						else
						{
							i = Math.abs(i) + 1;
						}
						break outerloop;
					}
				}
			}
			
		if (b)
		{
			bestTheta = (int) (theta * 180/Math.PI);
		}
		return bestTheta;
	}
}

