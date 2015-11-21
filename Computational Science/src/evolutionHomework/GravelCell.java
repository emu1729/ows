package evolutionHomework;

import java.awt.Color;
import java.util.ListIterator;
import java.util.Random;

/**
 * A child class of Cell which includes food that grows back over time
 * 
 * @author Peter Dong
 */
public class GravelCell extends Cell {

	private double foodAmount;
	static private final double GROWTH_PER_TURN = 1;
	static private final double FOOD_MAX = 10;
	private double gravelSize;
	
	
	/**
	 * @param map - the Arena that the Cell belongs to
	 * @param x - the x coordinate of the Cell
	 * @param y - the y coordinate of the Cell
	 * @param food - the amount of food the Cell begins with
	 */
	public GravelCell(Arena map, int x, int y, double gravelSize) {
		super(map, x, y);
		this.gravelSize = gravelSize;
	}
	
	
	@Override
	public double howMuchFood() {
		return foodAmount;
	}

	@Override
	public void beginningOfTurn() {
		foodAmount += GROWTH_PER_TURN;
		if (foodAmount > FOOD_MAX) {
			foodAmount = FOOD_MAX;
		}
		super.beginningOfTurn();
	}

	@Override
	protected Color getColor() {
		double brightness = 1 - ((double)foodAmount / FOOD_MAX * 165 / 240);
		return Color.getHSBColor(75.0f / 240, 175.0f / 240, (float)(brightness));
	}

	@Override
	public double eatFood(double amount) {
		if (amount <= foodAmount) {
			foodAmount -= amount;
			return amount;
		} else {
			double temp = foodAmount;
			foodAmount = 0;
			return temp;
		}
	}
	
	public double getGravelSize(){
		return gravelSize;
	}
}


