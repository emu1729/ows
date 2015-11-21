package evolutionHomework;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public class PoisonousGuppy extends Herbivore {

	@Override
	protected double metabolicConsumption() {
		return 1;
	}

	@Override
	protected double maxEnergyStorage() {
		return 20;
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
		return 3;
	}

	@Override
	protected double initialEnergy() {
		return 10;
	}

	@Override
	protected double energyAsFood() {
		return -100;
	}

	@Override
	protected double dailyEnergyMax() {
		return 20;
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
		return "PGuppy";
	}
	
	@Override
	public double getSpotValue(){
		return 0;
	}

	@Override
	protected Turn userDefinedChooseMove() {
		List<Animal> others = getCell().getOtherAnimals(this);
		for (Animal ani : others) {
			if (checkMateability(ani) && getEnergyReserve() > 6) {
				return new Mate(ani);
			}
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
		return Color.CYAN;
	}

	@Override
	public void Draw(Graphics graph, int x, int y) {
		super.Draw(graph, x, y);
		if (isMale()) {
			float brightness = (float)(SPOT_BRIGHTNESS) / 2;
			if (brightness < 0) {
				brightness = 0;
			} else if (brightness > 1) { 
				brightness = 1; 
			}

			graph.setColor(Color.getHSBColor(295f / 360, 1f, brightness));

			double size = SPOT_SIZE / 2;
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

	static private final double SPOT_SIZE = 0.5; // Let 1 be the maximum size
	static private final double SPOT_BRIGHTNESS = 0.5; // Let 1 be the maximum brightness

	@Override
	protected void randomGenes(List<Gene> genes) {
		genes.add(new Gene(GeneType.SPOT_SIZE, SPOT_SIZE));
		genes.add(new Gene(GeneType.SPOT_BRIGHTNESS, SPOT_BRIGHTNESS));
	}


}
