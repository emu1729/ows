package biologyHomework1;

import java.awt.Color;
import java.util.List;
import java.util.Random;

public class Hare extends Herbivore {
	
	private static Random random = new Random();

	@Override
	protected double metabolicConsumption() {
		return 2;
	}

	@Override
	protected double maxEnergyStorage() {
		if(getAge() < 30){
			return 50;
		}
		else {
			return 100;
		}
	}

	@Override
	protected int ageOfSexualMaturity() {
		return 30;
	}

	@Override
	protected int gestationTime() {
		return 20;
	}

	@Override
	protected int litterSize() {
		return 4;
	}

	@Override
	protected double initialEnergy() {
		return 20;
	}

	@Override
	protected double energyAsFood() {
		if(getAge() < 30){
			return 25;
		}
		else {
			return 50;
		}
	}

	@Override
	protected double dailyEnergyMax() {
		if(getAge() < 30){
			return 10;
		}
		else {
			return 20;
		}
	}

	@Override
	public double energyToMate() {
		return 1;
	}

	@Override
	public double energyToMove() {
		return 1;
	}

	@Override
	public double energyToEat() {
		return 1;
	}

	@Override
	public String getName() {
		return "Hare";
	}

	@Override
	protected Turn userDefinedChooseMove() {
		List<Animal> others = getCell().getOtherAnimals(this);
		for (Animal ani : others) {
			if (checkMateability(ani) && getArena().getSeason() == 1) {
				return new Mate(ani);
			}
		}
		
		if (getCell().howMuchFood() > 1) {
			return new HerbivoreEat();
		} else {
			int ran = getRandom().nextInt(4);
			if (ran == 0) {
				return new Move(Direction.DOWN);
			} else if (ran == 1) {
				return new Move(Direction.UP);
			} else if (ran == 2) {
				return new Move(Direction.LEFT);
			} else {
				return new Move(Direction.RIGHT);
			}
		}
	}

	@Override
	protected int getNTurns() {
		if(getEnergyReserve() < 30){
			return 1;
		}
		else{
			return 2;
		}
	}

	@Override
	protected Color getColor() {
			return Color.RED;
	}

}
