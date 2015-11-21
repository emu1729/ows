package biologyHomework1;

import java.awt.Color;
import java.util.List;

public class Lynx extends Carnivore {
	
	protected boolean isPrey(Animal target){
		return target instanceof Hare;
	}

	@Override
	protected double metabolicConsumption() {
		return 1;
	}

	@Override
	protected double maxEnergyStorage() {
		return 300;
	}

	@Override
	protected int ageOfSexualMaturity() {
		return 20;
	}

	@Override
	protected int gestationTime() {
		return 20;
	}

	@Override
	protected int litterSize() {
		return 5;
	}

	@Override
	protected double initialEnergy() {
		return 50;
	}

	@Override
	protected double energyAsFood() {
		return 5;
	}

	@Override
	protected double dailyEnergyMax() {
		return 200;
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
		return "Lynx";
	}

	@Override
	protected Turn userDefinedChooseMove() {
		List<Animal> others = getCell().getOtherAnimals(this);
		for (Animal ani : others) {
			if (checkMateability(ani) && getEnergyReserve() > 50 && getArena().getSeason() == 1) {
				return new Mate(ani);
			}
		}
		for(Animal a: others){
			if(a.getName().equals("Hare")){
				return new CarnivoreEat(a);
			}
		}
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

	@Override
	protected int getNTurns() {
		return 1;
	}

	@Override
	protected Color getColor() {
		return Color.BLUE;
	}

}

