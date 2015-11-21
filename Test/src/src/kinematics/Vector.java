package kinematics;
/**
 * Creates vectors of three dimensions
 * @author Emily Mu
 * @date 1.17.15
 *
 */
public class Vector {

	private double x;
	private double y;
	private double z;
	
	public Vector (double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getX()
	{
		return x;
	}
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public double getZ()
	{
		return z;
	}
	
	public void setZ(double z)
	{
		this.z = z;
	}
/**
 * @return the magnitude of the vector
 */
	public double getMagnitude()
	{
		return(Math.sqrt(x*x+y*y+z*z));
	}
/**
 * 
 * @param first vector
 * @param second vector
 * @return the vector sum of the two vectors
 */
	public static Vector addVector(Vector first, Vector second)
	{
		return(new Vector((first.getX() + second.getX()), (first.getY() + second.getY()), (first.getZ() + second.getZ())));
	}
/**
 * 
 * @param first vector
 * @param second vector
 * @return the vector difference of the two vectors
 */
	public static Vector subtractVector(Vector first, Vector second)
	{
		return(new Vector((first.getX() - second.getX()), (first.getY() - second.getY()), (first.getZ() - second.getZ())));
	}
/**
 * 	
 * @param vector1
 * @param scale
 * @return scales the vector by a number
 */
	public static Vector scaleVector(Vector vector1, double scale)
	{
		return(new Vector(scale*vector1.getX(), scale*vector1.getY(), scale*vector1.getZ()));
	}
/**
 * 	
 * @param first vector
 * @param second vector
 * @return gives the dot product of two vectors
 */
	public static double dotProduct(Vector first, Vector second)
	{
		return(first.getX()*second.getX()+first.getY()*second.getY()+first.getZ()*second.getZ());
	}
/**
 * 	
 * @param first vector
 * @param second vector
 * @return gives the crossProduct of two vectors
 */
	public static Vector crossProduct(Vector first, Vector second)
	{
		return(new Vector((first.getY()*second.getZ()-first.getZ()*second.getY()), (first.getZ()*second.getX()-first.getX()*second.getZ()), (first.getX()*second.getY() - first.getY()*second.getX())));
	}
/**
 * 
 * @param first vector
 * @param second vector
 * @return gives the angle between two vectors
 */
	public static double getAngle(Vector first, Vector second)
	{
		return(Math.acos((dotProduct(first, second))/(first.getMagnitude()*second.getMagnitude())));
	}
	
	public String toString()
	{
		return (x + "\t" + y + "\t" + z + "\t" + getMagnitude());
	}
/**
 * 
 * @param vector1
 * @return gives the unit vector of vector1
 */
	public static Vector unitVector(Vector vector1)
	{
		return(Vector.scaleVector(vector1, 1/vector1.getMagnitude()));
	}
}
