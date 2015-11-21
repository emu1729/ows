package evolutionHomework;

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
	private static final int TIMER_SPEED = 100;
	
	public static void main(String args[]) throws InstantiationException, IllegalAccessException {

		// These control the size of the grid
		final int xSize = 40;
		final int ySize = 10;
		final int cellSize = 40;

		final int nPoisonousHerbivore = 300;
		final int nCarnivore = 60;
		final int nHerbivore = 1000;
		
		Arena mymap = new Arena(xSize, ySize, cellSize);

		for (int ix = 0; ix < mymap.getXDim(); ++ix) {
			for (int iy = 0; iy < mymap.getYDim(); ++iy) {
				/**
					if(ix < mymap.getXDim()/2){
						mymap.changeCell(ix, iy, new GravelCell(mymap, ix, iy, 0.2));
					}
					
//				if (ix == iy || ix == mymap.getYDim() - iy) {
//					mymap.changeCell(ix, iy,  new WallCell(mymap, ix, iy));
//				} else {
					mymap.changeCell(ix, iy, new GravelCell(mymap, ix, iy, 0.8));
//				}	
 				
				 **/
				mymap.changeCell(ix, iy, new FoodCell(mymap, ix, iy));
			}
		}
		
		addAnimal(mymap, nHerbivore, Guppy.class);
		addAnimal(mymap, nPoisonousHerbivore, PoisonousGuppy.class);
		addAnimal(mymap, nCarnivore, Pike.class);

		Viewer.runViewer(mymap, TIME_INCREMENT, TIMER_SPEED);
	}

}
