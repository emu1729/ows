package theHungerGames;


import java.awt.Color;
import java.util.List;

public class HerbivoreENEMY extends Herbivore {

	static private Color color = new Color(254, 146, 190);
	
	@Override
	protected Color getColor() {
		return color;
	}

	@Override
	public String getName() {
		return "EnemyHerbivore";
	}

	@Override
	protected Turn userDefinedChooseMove() {
		List<Animal> others = getCell().getOtherAnimals(this);
		List<Animal> allOthers = getArena().getAllAnimals(this);
		if(!(getCell() instanceof FoodCell)){
			double maxDistance = 1e4;
			Cell bestCell = null;
			for (int ix = 0; ix < getArena().getXDim(); ++ix) {
				for (int iy = 0; iy < getArena().getYDim(); ++iy) {
					if(getArena().getCell(ix, iy) instanceof FoodCell){
						double deltaX = ix - this.getCell().getX();
						double deltaY = iy - this.getCell().getY();
						double distance = deltaX * deltaX + deltaY * deltaY;
						if(maxDistance > distance){
							bestCell = getArena().getCell(ix, iy);
							maxDistance = distance;
						}
					}
				}
			}
			if(bestCell != null){
				return new MoveToward(getCell(), bestCell, true);
			}
		}
		for (Animal ani: allOthers){
			double deltaX = ani.getCell().getX() - this.getCell().getX();
			double deltaY = ani.getCell().getY() - this.getCell().getY();
			double distance = deltaX * deltaX + deltaY * deltaY;
			if(ani instanceof Carnivore){
				if (distance < 10){
					return new MoveToward(getCell(), ani.getCell(), false);
				}
			}
			else if (!isPregnant() && checkMateability(ani)){
				if(getEnergyReserve() > maxEnergyStorage()*3/4){
					if(distance > 20){
						return new MoveToward(getCell(), ani.getCell(), true);
					}
					return new Mate(ani);
				}
			}
		}
		if (getCell().howMuchFood() > 2){
			return new HerbivoreEat();
		}
		return new Move(Direction.randomDirection());
	}

	@Override
	protected double getInitialGene(GeneType type) {
		
		double ranNum = getRandom().nextGaussian() * .1 + .5;
		
		switch(type) {
		case SIZE1:
			return getRandom().nextGaussian() * .1 + .5;

		case SIZE2:
			return getRandom().nextGaussian() * .3 + .5;

		case SPEED1:
			return getRandom().nextGaussian() * .3 + .7;

		case SPEED2:
			return getRandom().nextGaussian() * .3 + .1;

		case MARKINGS1:
			return getRandom().nextGaussian() * .5 + .5;

		case MARKINGS2:
			return getRandom().nextGaussian() * .5 + .5;

		case FERTILITY:
			return getRandom().nextGaussian() * .5 + .5;

		default:
			throw new RuntimeException("Never reach here");

		}
	}

	@Override
	protected double getInitialSD(GeneType type) {
		switch(type) {
		case SIZE1:
			return .5;

		case SIZE2:
			return .2;

		case SPEED1:
			return .7;

		case SPEED2:
			return .1;

		case MARKINGS1:
			return .2;

		case MARKINGS2:
			return .1;

		case FERTILITY:
			return 1;

		default:
			throw new RuntimeException("Never reach here");

		}
	}

}

