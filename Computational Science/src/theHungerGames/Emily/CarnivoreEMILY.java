package theHungerGames;

import java.awt.Color;
import java.util.List;

import theHungerGames.*;

public class CarnivoreEMILY extends Carnivore {

	static private Color color = new Color(239, 229, 98);
	
	@Override
	protected Color getColor() {
		return color;
	}

	@Override
	public String getName() {
		return "Dr.Dong";
	}

	@Override
	protected Turn userDefinedChooseMove() {
		List<Animal> others = getCell().getOtherAnimals(this);
		List<Animal> allOthers = getArena().getAllAnimals(this);
		if(getArena().getNDays() <= 100){
			for (Animal ani : allOthers) {
				double deltaX = ani.getCell().getX() - this.getCell().getX();
				double deltaY = ani.getCell().getY() - this.getCell().getY();
				double distance = deltaX * deltaX + deltaY * deltaY;
				if (checkMateability(ani)) {
					if(distance == 0){
						return new Mate(ani);
					}
					else if(getEnergyReserve() > maxEnergyStorage()/2){
						return new MoveToward(getCell(), ani.getCell(), true);
					}
				} else if (isPrey(ani) && getEnergyReserve() < maxEnergyStorage()/2) {
					return new CarnivoreEat(ani);
				}	
				else if (getEnergyReserve() < maxEnergyStorage()/2){
					if(isPrey(ani)){
						return new MoveToward(getCell(), ani.getCell(), true);
					}
					else{
						return new Move(Direction.randomDirection());
					}
				}
			}
		}
		/**
		else{
			for (Animal ani : allOthers) {
				double deltaX = ani.getCell().getX() - this.getCell().getX();
				double deltaY = ani.getCell().getY() - this.getCell().getY();
				double distance = deltaX * deltaX + deltaY * deltaY;
				if (checkMateability(ani)) {
					if(distance == 0){
						return new Mate(ani);
					}
					else if(getEnergyReserve() > maxEnergyStorage()*3/4){
						return new MoveToward(getCell(), ani.getCell(), true);
					}
				} else if (isPrey(ani) && !(ani instanceof HerbivoreEMILY)) {
					if(distance == 0){
						return new CarnivoreEat(ani);
					}
					else {
						return new MoveToward(getCell(), ani.getCell(), true);
					}
				}	
				else if (getEnergyReserve() < maxEnergyStorage()*7/8){
					if(isPrey(ani)){
						return new MoveToward(getCell(), ani.getCell(), true);
					}
					else{
						return new Move(Direction.randomDirection());
					}
				}
			}
		}
		**/
		
		else {
			for(Animal ani: allOthers){
				double deltaX = ani.getCell().getX() - this.getCell().getX();
				double deltaY = ani.getCell().getY() - this.getCell().getY();
				double distance = deltaX * deltaX + deltaY * deltaY;
				if(getEnergyReserve() > maxEnergyStorage()*3/4){
					if (checkMateability(ani) && distance == 0){
						System.out.println("Hi");
						return new Mate(ani);
					}
					else if(getEnergyReserve() > maxEnergyStorage()*7/8){
						return new MoveToward(getCell(), ani.getCell(), true);
					}
				}
				else if (isPrey(ani) && !(ani instanceof HerbivoreEMILY)){
					if(distance == 0){
						System.out.println("OH");
						return new CarnivoreEat(ani);
					}
					else {
						return new MoveToward(getCell(), ani.getCell(), true);
					}
				}
				else if(isPrey(ani) && getEnergyReserve() < maxEnergyStorage()/8){
					if(distance == 0){
						return new CarnivoreEat(ani);
					}
					else {
						return new MoveToward(getCell(), ani.getCell(), true);
					}
				}
			}
		}
		

		return new Move(Direction.randomDirection());
	}

	@Override
	protected double getInitialGene(GeneType type) {
		double ranNum = getRandom().nextGaussian() * .1 + .5;
		
		switch(type) {
		case SIZE1:
			return getRandom().nextGaussian() * .1 + .3;

		case SIZE2:
			return getRandom().nextGaussian() * .1 + .5;

		case SPEED1:
			return getRandom().nextGaussian() * .1 + .5;

		case SPEED2:
			return getRandom().nextGaussian() * .1 + .5;

		case MARKINGS1:
			return getRandom().nextGaussian() * .5 + .5;

		case MARKINGS2:
			return getRandom().nextGaussian() * .5 + .5;

		case FERTILITY:
			return getRandom().nextGaussian() * .1 + .5;

		default:
			throw new RuntimeException("Never reach here");

		}
	}

	@Override
	protected double getInitialSD(GeneType type) {
		switch(type) {
		case SIZE1:
			return .3;

		case SIZE2:
			return .5;

		case SPEED1:
			return .5;

		case SPEED2:
			return .5;

		case MARKINGS1:
			return .5;

		case MARKINGS2:
			return .5;

		case FERTILITY:
			return 1;

		default:
			throw new RuntimeException("Never reach here");

		}
	}

}
