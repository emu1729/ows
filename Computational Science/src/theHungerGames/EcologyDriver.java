package theHungerGames;

import theHungerGames.*;

public class EcologyDriver {

	/**
	 * Driver class for ecology simulation
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */

	private static void addAnimal(Arena arena, int number, Class<? extends Animal> type) throws InstantiationException, IllegalAccessException {
		for (int i = 0; i < number; ++i) {
			Animal newAnimal = Animal.makeRandomAnimal(type);
			arena.addRandomAnimal(newAnimal);
		}
	}
	
	private static final double TIME_INCREMENT = 1;
	private static final int TIMER_SPEED = 50;
	
	public static void main(String args[]) throws InstantiationException, IllegalAccessException {

		// These control the size of the grid
		final int xSize = 60;
		final int ySize = 60;
		final int cellSize = 10;

		final int nHerbivore = 200;
		final int nCarnivore = 100;
		
		Arena mymap = new Arena(xSize, ySize, cellSize);

		for (int ix = 0; ix < mymap.getXDim(); ++ix) {
			for (int iy = 0; iy < mymap.getYDim(); ++iy) {
				//if(iy < mymap.getYDim()/2){
					mymap.changeCell(ix, iy, new FoodCell(mymap, ix, iy));
				//}
			}
		}
		
		addAnimal(mymap, nHerbivore, HerbivoreEMILY.class);
		//addAnimal(mymap, nHerbivore, HerbivoreENEMY.class);
		addAnimal(mymap, nCarnivore, CarnivoreEMILY.class);

		Viewer.runViewer(mymap, TIME_INCREMENT, TIMER_SPEED);
	}

}
