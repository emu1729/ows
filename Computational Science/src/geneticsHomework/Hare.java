package geneticsHomework;

import java.awt.Color;
import java.util.List;

public class Hare extends Herbivore {

	@Override
	protected double metabolicConsumption() {
		return 1;
	}

	@Override
	protected double maxEnergyStorage() {
		double finalEnergy = 5;
		if(getGenotype().getGene(GeneType.GENE_A)){
			finalEnergy = finalEnergy + 3; }
		else { finalEnergy = finalEnergy + 2; }
		if(getGenotype().getGene(GeneType.GENE_B)){
			finalEnergy = finalEnergy + 3; }
		else { finalEnergy = finalEnergy + 2; }
		if(getGenotype().getGene(GeneType.GENE_C)){
			finalEnergy = finalEnergy + 2; }
		else { finalEnergy = finalEnergy + 3; }
		if(getGenotype().getGene(GeneType.GENE_D)){
			finalEnergy = finalEnergy + 2; }
		else { finalEnergy = finalEnergy + 3; }
		if(getGenotype().getGene(GeneType.GENE_E)){
			finalEnergy = finalEnergy + 2; }
		else { finalEnergy = finalEnergy + 3; }
		return finalEnergy;
	}

	@Override
	protected int ageOfSexualMaturity() {
		return 2;
	}

	@Override
	protected int gestationTime() {
		return 4;
	}

	@Override
	protected int litterSize() {
		return 2;
	}

	@Override
	protected double initialEnergy() {
		return 5;
	}

	@Override
	protected double energyAsFood() {
		return 1;
	}

	@Override
	protected double dailyEnergyMax() {
		double finalEnergy = 0;
		if(getGenotype().getGene(GeneType.GENE_A)){
			finalEnergy = finalEnergy + 1; }
		else { finalEnergy = finalEnergy + 2; }
		if(getGenotype().getGene(GeneType.GENE_B)){
			finalEnergy = finalEnergy + 2; }
		else { finalEnergy = finalEnergy + 1; }
		if(getGenotype().getGene(GeneType.GENE_C)){
			finalEnergy = finalEnergy + 1; }
		else { finalEnergy = finalEnergy + 2; }
		if(getGenotype().getGene(GeneType.GENE_D)){
			finalEnergy = finalEnergy + 2; }
		else { finalEnergy = finalEnergy + 1; }
		if(getGenotype().getGene(GeneType.GENE_E)){
			finalEnergy = finalEnergy + 1; }
		else { finalEnergy = finalEnergy + 2; }
		return finalEnergy;
	}

	@Override
	public double energyToMate() {
		double finalEnergy = 1;
		if(getGenotype().getGene(GeneType.GENE_C)){
			finalEnergy = finalEnergy * 0.5; }
		else { finalEnergy = finalEnergy * 2; }
		if(getGenotype().getGene(GeneType.GENE_D)){
			finalEnergy = finalEnergy * 2; }
		else { finalEnergy = finalEnergy * 1; }
		if(getGenotype().getGene(GeneType.GENE_E)){
			finalEnergy = finalEnergy * 0.5; }
		else { finalEnergy = finalEnergy * 1; }
		return finalEnergy;
	}

	@Override
	public double energyToMove() {
		double finalEnergy = 1;
		if(getGenotype().getGene(GeneType.GENE_A)){
			finalEnergy = finalEnergy * 0.5; }
		else { finalEnergy = finalEnergy * 2; }
		if(getGenotype().getGene(GeneType.GENE_B)){
			finalEnergy = finalEnergy * 2; }
		else { finalEnergy = finalEnergy * 1; }
		return finalEnergy;
	}

	@Override
	public double energyToEat() {
		double finalEnergy = 0;
		if(getGenotype().getGene(GeneType.GENE_A)){
			finalEnergy = finalEnergy + 1; }
		else { finalEnergy = finalEnergy + 1.5; }
		return finalEnergy;
	}

	@Override
	public String getName() {
		String finalString = "Genotype ";
		if(getGenotype().getGene(GeneType.GENE_A)){
			finalString = finalString + "A"; }
		else { finalString = finalString + "a";}
		if(getGenotype().getGene(GeneType.GENE_B)){
			finalString = finalString + "B"; }
		else { finalString = finalString + "b";}
		if(getGenotype().getGene(GeneType.GENE_C)){
			finalString = finalString + "C"; }
		else { finalString = finalString + "c";}
		if(getGenotype().getGene(GeneType.GENE_D)){
			finalString = finalString + "D"; }
		else { finalString = finalString + "d";}
		if(getGenotype().getGene(GeneType.GENE_E)){
			finalString = finalString + "E"; }
		else { finalString = finalString + "e";}
		return finalString;
	}

	@Override
	protected Turn userDefinedChooseMove() {
		List<Animal> others = getCell().getOtherAnimals(this);
		for (Animal ani : others) {
			if (checkMateability(ani)) {
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
		return 1;
	}

	@Override
	protected Color getColor(){
		return Color.BLACK;
	}

	@Override
	protected void randomGenes(List<Gene> genes) {
		genes.add(new Gene(GeneType.MALE, Arena.getRandom().nextBoolean()));
		genes.add(new Gene(GeneType.GENE_A, Arena.getRandom().nextBoolean()));
		genes.add(new Gene(GeneType.GENE_B, Arena.getRandom().nextBoolean()));
		genes.add(new Gene(GeneType.GENE_C, Arena.getRandom().nextBoolean()));
		genes.add(new Gene(GeneType.GENE_D, Arena.getRandom().nextBoolean()));
		genes.add(new Gene(GeneType.GENE_E, Arena.getRandom().nextBoolean()));
	}


}
