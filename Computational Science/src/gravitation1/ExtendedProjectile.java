package gravitation1;

import java.util.ArrayList;
import java.util.List;

public class ExtendedProjectile extends Projectile {

	List<PointProjectile> projectiles = new ArrayList<PointProjectile>();
	private Vector3 totalForce;
	private boolean moveSwitch;
	
	public ExtendedProjectile(String name, boolean moveSwitch) {
		super(name);
		this.moveSwitch = moveSwitch;
	}
	
	public void addProjectile(PointProjectile proj) {
		projectiles.add(proj);
	}
	/**
	 * 
	 * @return total mass of Extended Object
	 */
	public double totalMass()
	{
		double totalMass = 0;
		for (PointProjectile proj: projectiles)
		{
			totalMass += proj.getMass();
		}
		return totalMass;
	}
	@Override
	public String toString() {
		if (moveSwitch)
		{
		return projectiles.get(projectiles.size()/2).toString();
		}
		String response = new String();
		final int nTimes = 12;
		for (int i = 0; i < nTimes; ++i) {
			response += "0";
			if (i != nTimes - 1) {
				response += "\t";
			}
		}
		return response;
	}

	@Override
	protected void setAccel(GravitationEngine engine) {
		// TODO Auto-generated method stub
		totalForce = Vector3.nullVector();
		List<PointProjectile> allProjectiles = engine.getProjectiles();
		for(PointProjectile proj: projectiles)
		{
			for(PointProjectile p: allProjectiles)
			{
				Vector3 distance = Vector3.subtract(p.getPosition(), proj.getPosition());
				if (proj.getName() != p.getName())
				{
					Vector3 singleForce = Vector3.scale(Vector3.unitVector(distance), p.getMass()*proj.getMass()*engine.getGravitationalConstant()*1/(distance.magnitude()*distance.magnitude()));
					totalForce = Vector3.add(totalForce, singleForce);
				}
			}
		}
		Vector3 setAcceleration = Vector3.scale(totalForce, 1/totalMass());
		for(PointProjectile proj: projectiles)
		{
			proj.setNewAcceleration(setAcceleration);
		}
	}

	@Override
	protected void advanceVelocity(double timeIncrement) {
		// TODO Auto-generated method stub
		for(PointProjectile proj: projectiles)
		{
			proj.advanceVelocity(timeIncrement);
		}

	}

	@Override
	protected void advancePosition(double timeIncrement) {
		// TODO Auto-generated method stub
		for(PointProjectile proj: projectiles)
		{
			proj.advancePosition(timeIncrement);
		}
	}

	@Override
	public void addToList(List<PointProjectile> list) {
		for (PointProjectile proj : projectiles) {
			list.add(proj);
		}
	}

}
