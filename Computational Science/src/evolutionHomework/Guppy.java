package evolutionHomework;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public class Guppy extends Herbivore {

	@Override
	protected double metabolicConsumption() {
		return 1;
	}

	@Override
	protected double maxEnergyStorage() {
		return 15;
	}

	@Override
	protected int ageOfSexualMaturity() {
		return 2;
	}

	@Override
	protected int gestationTime() {
		return 3;
	}

	@Override
	protected int litterSize() {
		return 6;
	}

	@Override
	protected double initialEnergy() {
		return 5;
	}

	@Override
	protected double energyAsFood() {
		return 10;
	}

	@Override
	protected double dailyEnergyMax() {
		return 10;
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
		return "Guppy";
	}
	
	protected double getSpotValue() {
		double potentialValue = getGenotype().getGene(GeneType.SPOT_BRIGHTNESS) + getGenotype().getGene(GeneType.SPOT_SIZE);
		return potentialValue;
	}

	@Override
	protected Turn userDefinedChooseMove() {
		List<Animal> others = getCell().getOtherAnimals(this);
		Animal bestMate = null;
		double bestValue = 0;
		for (Animal ani : others) {
			double potentialValue = 0;
			if (checkMateability(ani)) {
				potentialValue = getGenotype().getGene(GeneType.SPOT_BRIGHTNESS) + getGenotype().getGene(GeneType.SPOT_SIZE);
				if(potentialValue >= bestValue){
					bestMate = ani;
				}
			}
		}
		if(bestMate != null){
			return new Mate(bestMate);
		}
		if (getCell().howMuchFood() > 1) {
			return new HerbivoreEat();
		} else {
			return new Move(Direction.randomDirection());
		}
	}

	@Override
	protected int getNTurns() {
		return 1;
	}

	@Override
	protected Color getColor() {
		return Color.RED;
	}

	@Override
	public void Draw(Graphics graph, int x, int y) {
		super.Draw(graph, x, y);
		if (isMale()) {
			float brightness = (float)getGenotype().getGene(GeneType.SPOT_BRIGHTNESS) / 2;
			if (brightness < 0) {
				brightness = 0;
			} else if (brightness > 1) { 
				brightness = 1; 
			}

			graph.setColor(Color.getHSBColor(295f / 360, 1f, brightness));

			double size = getGenotype().getGene(GeneType.SPOT_SIZE) / 2;
			if (size < 0) {
				size = 0;
			} else if (size > 1) {
				size = 1;
			}
			int sizeX = (int)Math.round(size * getXSize());
			int sizeY = (int)Math.round(size * getYSize());
			graph.fillOval(x + sizeX, y + sizeY, getXSize() - (2 * sizeX - 1), getYSize() - (2 * sizeY - 1));
		}
	}

	static private final double INITIAL_SPOT_SIZE = .5; // Let 1 be the maximum size
	static private final double INITIAL_SPOT_BRIGHTNESS = .5; // Let 1 be the maximum brightness
	static private final double SPOT_SIZE_SD = .1;
	static private final double SPOT_BRIGHTNESS_SD = .1;

	@Override
	protected void randomGenes(List<Gene> genes) {
		genes.add(new Gene(GeneType.SPOT_SIZE, INITIAL_SPOT_SIZE, SPOT_SIZE_SD));
		genes.add(new Gene(GeneType.SPOT_BRIGHTNESS, INITIAL_SPOT_BRIGHTNESS, SPOT_BRIGHTNESS_SD));
	}


}
