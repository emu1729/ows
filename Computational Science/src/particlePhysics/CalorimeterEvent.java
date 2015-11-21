package particlePhysics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import chemicalReactionsHomework.Particle;

public class CalorimeterEvent implements Serializable {

	private static final long serialVersionUID = -6102468898822038223L;
	private List<Tower> towers = new ArrayList<>();
	
	//variables for level 3 and Challenge
	private List<Tower> seedTowers = new ArrayList<Tower>();
	private List<Integer> widths = new ArrayList<Integer>();
	private final double closeArea = 2;
	
	//variables for level 1 and 2
	private double finalWidth;
	private double finalError;
	private Tower highestEnergy;
	private final double widthIncrement = 0.001; //implement at end
	private final double leastDistance = 0.1; //implement at end
	
	
	public void addTower(Tower tower) {
		for (Tower tow : towers) {
			if (tow.getEta() == tower.getEta() && tow.getPhi() == tower.getPhi()) {
				tow.setEnergy(tow.getEnergy() + tower.getEnergy());
				return;
			}
		}
		towers.add(tower);
	}
	
	public List<Tower> getTowers() {
		return towers;
	}
	
	/**
	 * Finds maximumTower
	 */
	public void getMaxTower(){
		double maxEnergy = 0;
		Tower maxTower = new Tower(0, 0, 0);
		for (Tower t: towers){
			if (t.getEnergy() > maxEnergy){
				maxTower = t;
				maxEnergy = t.getEnergy();
			}
		}
		highestEnergy = maxTower;
	}
	
	/**
	 * 
	 * @param b potential width
	 * @return error value for a potential width
	 */
	public double getError(double b, List<Tower> towerList, Tower t){
		double error = 0;
		for (Tower s: towerList){
			//f(x) = b/(pi(x-m)^2 + b^2))
			double predictedValue = b / (Math.PI * (Math.pow(s.getDistance(t), 2) + b*b));
			error += Math.pow((s.getEnergy() - predictedValue), 2);
		}
		return error;
	}
	
	/**
	 * gets single width for one cluster maximum
	 */
	public void getWidth(){
		double maxError = 10e20;
		double width = 0;
		for (double i = 0; i < 1.1; i += widthIncrement){
			double error = getError(i, towers, highestEnergy);
			if (error < maxError){
				maxError = error;
				width = i;
			}
		}
		finalWidth = width;
		finalError = maxError;
	}
	
	/**
	 * return the maxima of the event
	 */
	public void getMaxima(){
		List<Tower> seedTower = new ArrayList<Tower>();
		for (Tower t: towers){
			boolean localMaximum = true;
			List<Tower> closeTowers = new ArrayList<Tower>();
			for (Tower s: towers){
				if (t.getDistance(s) < closeArea){
					closeTowers.add(s);
				}
			}
			for (Tower c: closeTowers){
				if (t.getEnergy() < c.getEnergy()){
					localMaximum = false;
				}
			}
			if (localMaximum){
				seedTower.add(t);
			}
			seedTowers = seedTower;
		}
	}
	
	/**
	 * 
	 * @param t tower
	 * @return closest towers in event to tower t
	 */
	public List<Tower> closeTowers(Tower t){
		List<Tower> closestTowers = new ArrayList<Tower>();
		for (Tower c: towers){
			double distance = t.getDistance(c);
			boolean closestTower = true;
			for(Tower s: seedTowers){
				if(s.getDistance(c) < distance){
					closestTower = false;
				}
			}
			if (closestTower){
				closestTowers.add(c);
			}
		}
		return closestTowers;
	}
	
	/**
	 * gets width for general cluster
	 */
	public double getWidthGeneral(Tower t){
		List<Tower> closestTowers = closeTowers(t);
		double maxError = 1000;
		double width = 0;
		for (double i = 0; i < 5; i += widthIncrement){
			double error = getError(i, closestTowers, t);
			if (error < maxError){
				maxError = error;
				width = i;
			}
		}
		return width;
	}
	
	/**
	 * Maps Towers to widths
	 * @return seedTower to width HashMap
	 */
	public Map<Tower, Double> getWidthMap() {
		Map<Tower, Double> widths = new HashMap<Tower, Double>();
		for(Tower t: seedTowers)
		{
			double width = getWidthGeneral(t);
			widths.put(t, width);
		}
		return widths;
	}
	
	/**
	 * Prints Width Map
	 */
	public List<String> printWidthMap(){
		List<String> mapPrint = new ArrayList<String>();
		for(Tower t: seedTowers){
			if (t.getPhi() <= 2*Math.PI){
				mapPrint.add(new String(t.getEta() + "\t" + t.getPhi() + "\t" + t.getEnergy() + "\t" + getWidthMap().get(t)));
			}
		}
		return mapPrint;
	}
	
	/**
	 * Prints Width Map to Console
	 */
	public void printWidthMapToConsole(){
		for(Tower t: seedTowers){
			if (0 <= t.getPhi() && t.getPhi() <= 2*Math.PI){
				System.out.println(t.getEta() + "\t" + t.getPhi() + "\t" + t.getEnergy() + "\t" + getWidthMap().get(t));
			}
		}
	}
	
	/**
	 * Extends event to account for boundaries
	 */
	public void extendsTowers(){
		List<Tower> toBeAdded = new ArrayList<Tower>();
		for (Tower t: towers){
			if (t.getPhi() <= Math.PI)
				toBeAdded.add(new Tower(t.getEta(), t.getPhi() + 2 * Math.PI, t.getEnergy()));
			if (t.getPhi() <= 2 * Math.PI && t.getPhi() >= Math.PI){
				toBeAdded.add(new Tower(t.getEta(), t.getPhi() - 2*Math.PI, t.getEnergy()));
			}
		}
		for (Tower t: toBeAdded){
			addTower(t);
		}
	}
	
	public String toString(){
		return highestEnergy.getEta() + "\t" + highestEnergy.getPhi() + "\t" + highestEnergy.getEnergy() + "\t" + finalWidth + "\t" + finalError;
	}
}
