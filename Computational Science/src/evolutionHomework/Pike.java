package evolutionHomework;

import geneticsHomework.Arena;

import java.awt.Color;
import java.util.List;
import java.util.*;

public class Pike extends Carnivore {
	
	protected boolean isPrey(Animal target){
		return target instanceof Herbivore;
	}
	
	@Override
	protected double getSpotValue(){
		return 0;
	}

	@Override
	protected double metabolicConsumption() {
		return 3;
	}

	@Override
	protected double maxEnergyStorage() {
		return 100;
	}

	@Override
	protected int ageOfSexualMaturity() {
		return 5;
	}

	@Override
	protected int gestationTime() {
		return 5;
	}

	@Override
	protected int litterSize() {
		return 1;
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
		return 30;
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
		return "Pike";
	}

	@Override
	protected Turn userDefinedChooseMove() {
		List<Animal> others = getCell().getOtherAnimals(this);
		List<Animal> allOthers = getArena().getAllAnimals(this);
		List<Animal> closeAnimals = new ArrayList<Animal>();
		for(Animal a: allOthers){
			if(this.distance(a) <= 4){
				closeAnimals.add(a);
				System.out.println(a);
			}
		}
		for (Animal ani : others) {
			if (checkMateability(ani) && getEnergyReserve() > 45) {
				return new Mate(ani);
			}
		}
		Animal bestFood = null;
		double bestValue = 0;
		for (Animal a : others) {
			double potentialValue = 0;
			if(getGenotype().getGene(GeneType.AVOIDANCE) < 0.5){
				if (a.getName().equals("Guppy") || a.getName().equals("PGuppy")) {
					if(a.getGenotype().getGene(GeneType.SPOT_SIZE) > 0.6 && a.getGenotype().getGene(GeneType.SPOT_SIZE) < 0.4){
						if(a.getGenotype().getGene(GeneType.SPOT_BRIGHTNESS) > 0.6 && a.getGenotype().getGene(GeneType.SPOT_BRIGHTNESS) < 0.4){
							potentialValue = a.getSpotValue();
							if(potentialValue >= bestValue){
								bestFood = a;
							}
						}
					}
				}
			}
			else{
				if (a.getName().equals("Guppy") || a.getName().equals("PGuppy")) {
					potentialValue = a.getSpotValue();
					if(potentialValue >= bestValue){
						bestFood = a;
					}
				}
			}
		}
		if(bestFood != null){
			return new CarnivoreEat(bestFood);
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

	@Override
	protected void randomGenes(List<Gene> genes) {
		// TODO Auto-generated method stub
		genes.add(new Gene(GeneType.AVOIDANCE, Arena.getRandom().nextDouble()));
	}

}

