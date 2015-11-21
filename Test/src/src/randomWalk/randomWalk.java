package randomWalk;

import java.util.*;

public class randomWalk {
	private static Random random = new Random();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//level1();
		//level2();
		//level3(10);
		challenge();
	}
	public static void level1()
	{
		int steps = 100;
		for (int i = 1; i < 101; i++)
		{
			double position = 0;
			for (int j = 0; j < steps; j++)
			{
				if(random.nextBoolean())
				{
					position++;
				}
				else
				{
					position--;
				}
			}
			System.out.println(i + "\t" + position + "\t" + Math.abs(position));
		}
	}
	public static void level2()
	{
		level3(2);
	}
	
	public static void level3(int dimension)
	{
		for (int steps = 1; steps < 1001; steps++)
		{
			double mean = 0;
			double standardDeviation = 0;
			List<Double> displacements = new ArrayList<Double>();
			int iterations = 100;
			//runs the test iterations times
			for (int i = 0; i < iterations; i++)
			{
				int [] position = new int[dimension];
				double displacement = 0;
				//takes steps in d directions
				for (int j = 0; j < steps; j++)
				{
					int d = random.nextInt(dimension);
					if (random.nextBoolean()==false)
					{
						position[d] = position[d] + 1;
					}
					else
					{
						position[d] = position[d] - 1;
					}
				}
				for (int k = 0; k < position.length; k++)
				{
					displacement += Math.pow(position[k],2);
				}
				displacement = Math.sqrt(displacement);
				mean += displacement;
				displacements.add(displacement);  
			}
			mean = mean/iterations;
			for(double d: displacements)
			{
				standardDeviation += Math.pow((d - mean), 2);
			}
			standardDeviation = Math.sqrt(standardDeviation/iterations);
			System.out.println(steps + "\t" + mean + "\t" + standardDeviation);
		}
	}
	
	public static void challenge()
	{
		for (int iterations = 1; iterations < 100; iterations++)
		{
			double mean = 0;
			//runs the test iterations times
			for (int i = 0; i < iterations; i++)
			{
				double xPosition = 0;
				double yPosition = 0;
				double zPosition = 0;
				double displacement = 0;
				//takes steps in d directions
				for (int j = 0; j < 100; j++)
				{
					double phi = Math.random() * Math.PI;
					double theta = 2 * Math.random() * Math.PI;
					xPosition += Math.sin(phi) * Math.cos(theta);
					yPosition += Math.sin(phi) * Math.sin(theta);
					zPosition += Math.cos(phi);
				}
				displacement = xPosition * xPosition + yPosition * yPosition + zPosition * zPosition;
				displacement = Math.sqrt(displacement);
				mean += displacement;  
			}
			mean = mean/iterations;
			System.out.println(iterations + "\t" + mean);
		}
	}
		
	}


