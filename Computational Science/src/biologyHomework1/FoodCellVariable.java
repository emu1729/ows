package biologyHomework1;

import java.awt.Color;
import java.util.ListIterator;
import java.util.Random;

/**
 * A child class of Cell which includes food that grows back over time
 * 
 * @author Peter Dong
 */
public class FoodCellVariable extends Cell {
	
	private static Random random = new Random();

	private double foodAmount;
	static private final double GROWTH_PER_TURN = 1;
	static private final double FOOD_MAX = 10;
	
	private double time;
	
	/**
	 * @param map - the Arena that the Cell belongs to
	 * @param x - the x coordinate of the Cell
	 * @param y - the y coordinate of the Cell
	 * @param food - the amount of food the Cell begins with
	 */
	public FoodCellVariable(Arena map, int x, int y) {
		super(map, x, y);
	}
	
	
	@Override
	public void doTurn() {
		boolean allAnimalsDone = false;
		
		while (!allAnimalsDone) {
			ListIterator<Animal> it = getList().listIterator();
			allAnimalsDone = true;
			
			while (it.hasNext()) {
				Animal ian = it.next();
				
				if (!ian.performedAction()) {
					allAnimalsDone = false;
					ian.doTurn();
					break;
				}
			}
		}
		
		time = getMap().getTime();
		double remainder = time / 365 - Math.floor(time / 365);
		System.out.println(remainder);
		if (remainder < 0.25){
			foodAmount = random.nextInt(200);
		}
		else if (0.25 <= remainder && remainder < 0.5){
			foodAmount = random.nextInt(300);
		}
		else if (0.5 <= remainder && remainder < 0.75){
			foodAmount = random.nextInt(200);
		}
		else{
			foodAmount = random.nextInt(100);
		}
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
}

