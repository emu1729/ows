package evacSim.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Timer;

import evacSim.util.RNG;

// TODO make this into a singleton that can be reset, and store all the other class's static variables in here
/**
 * Main controller class for the simulation.
 * 
 * @author Daniel Keyes
 * @author Joseph Mattingly
 *
 */
public class SimulationController {

	private UpdateHandler myHandler = null;
	private Grid currentState;
	private Timer timer;
	private boolean useRealtime;
	private int timestep;

	private Statistics stats;

	private static final int DEFAULT_SEED = 1234;

	// global RNG instance
	// TODO: make this a singleton member variable or something that uses good programming practice
	public static final RNG random = new RNG(DEFAULT_SEED);
	public static int simTime = 0;

	/**
	 * Instantiate simulation variables to run the simulation.
	 * 
	 * @param startingGrid A working model of the simulated universe
	 * @param timestep Timer delay between simulation steps
	 * @param crosswalkPeriod How long, by Timer delay the crosswalks remain open or closed
	 * @param useRealtime Normalize to real-time visualization
	 */
	public SimulationController(Grid startingGrid, int timestep, int crosswalkPeriod, boolean useRealtime) {
		currentState = startingGrid;
		stats = new Statistics();

		CrosswalkController.getInstance().setPeriod(crosswalkPeriod);

		this.useRealtime = useRealtime;
		this.timestep = timestep;
		timer = new Timer(timestep, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateSimulation();
			}
		});
		
		// tacky: reset all the static variables in this class and elsewhere so we can re-run the simulation. see the TODO earlier.
		simTime = 0;
		Door.numPeopleExited = 0;
		CrosswalkController.getInstance().reset(); // yay, singleton design pattern
		Person.numPeopleSafe = 0;
		Person.peopleSafeOverTime = new LinkedList<Integer>();
	}

	/**
	 * This is really only useful for testing purposes.
	 * 
	 * @param smallGrid A small grid
	 * @param timestep A time step
	 */
	public SimulationController(Grid smallGrid, int timestep) {
		this(smallGrid, timestep, 75, true);
	}

	/**
	 * Manually set an update handler.
	 * 
	 * @param handler An object that implements the update handler
	 */
	public void setHandler(UpdateHandler handler) {
		myHandler = handler;
	}

	/**
	 * Creates a simple simulation containing a small grid, a door, and a few obstacles, initialized with some default parameters.
	 * 
	 * @return A simple simulation that is good only for simple visualization purposes
	 */
	public static SimulationController createSimpleSimulation() {
		Grid smallGrid = new Grid(100, 100);

		// A 2x3 block of obstacles in the corner, with a door in the center
		for (int i = 0; i < 5; i++)
		{
			for (int c = 0; i < 100; c++)
			{
			smallGrid.setCell(i, c, new Obstacle());
			}
		}
		for (int i = 95; i < 101; i++)
		{
			for (int c = 0; i < 100; c++)
			{
			smallGrid.setCell(i, c, new Obstacle());
			}
		}
		smallGrid.setCell(3, 1, new Door());

		smallGrid.initializeEmptyCells();

		int timestep = 100;
		return new SimulationController(smallGrid, timestep);
	}

	/**
	 * This method creates the simulation we're actually interested in doing. It creates a world based around the Crum-Forster building
	 * and simulates a disaster immediately south of the building.
	 * 
	 * @return
	 */
	public static SimulationController createEvacSimulation(boolean runInRealtime, boolean randomizeDoors) {
		Grid largeGrid = createDefaultBuildingPlacement();
		
		List<int[]> doorCoords = new LinkedList<int[]>();
		if (randomizeDoors) {
			// used a simple fixed door configuration
			// row = 252, 47 <= col <= 262, top
			// col = 47, 253 <= row <= 299, east (without the corner)
			// col = 262, 253 <= row <= 299, west (without the corner)
			List<int[]> availableCoords = new LinkedList<int[]>();
			for (int row = 252, col = 47; col <= 262; col++)
				availableCoords.add(new int[] { row, col });
			for (int col = 47, row = 253; row <= 299; row++)
				availableCoords.add(new int[] { row, col });
			for (int col = 262, row = 253; row <= 299; row++)
				availableCoords.add(new int[] { row, col });

			// first place the quadrouple door. then the double door. then single doors.
			// after placing each door, remove that coordinate and the two surrounding coords from the list
			// quad door
			RNG random = SimulationController.random;
			int quadIndex = random.nextI(0, availableCoords.size() - 1 - 3);
			// replace 4 coords, and then remove those 4 and a surrounding coord from the block
			for (int i = 0; i < 4; i++) {
				doorCoords.add(availableCoords.remove(quadIndex));
			}
			if (availableCoords.size() > quadIndex)
				availableCoords.remove(quadIndex); // the following block
			if (availableCoords.size() > quadIndex - 1)
				availableCoords.remove(quadIndex - 1); // the previous block
			// also if we just orphaned a block at the end, remove it
			if (availableCoords.size() == quadIndex + 1)
				availableCoords.remove(quadIndex);
			if (quadIndex == 2)
				availableCoords.remove(0);
			// double door
			int doubIndex = random.nextI(0, availableCoords.size() - 1 - 1);
			// replace 2 coords, and then remove those 2 and a surrounding coord from the block
			for (int i = 0; i < 2; i++) {
				doorCoords.add(availableCoords.remove(doubIndex));
			}
			if (availableCoords.size() > doubIndex)
				availableCoords.remove(doubIndex); // the following block
			if (availableCoords.size() > doubIndex - 1)
				availableCoords.remove(doubIndex - 1); // the previous block
			// 4 single doors
			for (int i = 0; i < 4; i++) {
				int singIndex = random.nextI(0, availableCoords.size() - 1 - 0);
				// replace a coord, and then remove that coord and surrounding coord from the block
				doorCoords.add(availableCoords.remove(singIndex));
				if (availableCoords.size() > singIndex)
					availableCoords.remove(singIndex); // the following block
				if (availableCoords.size() > singIndex - 1)
					availableCoords.remove(singIndex - 1); // the previous block
			}
		} else {
			// used a simple fixed door configuration
			//change this pls
			for (int i = 0; i < 50; i++)
			{
			int t = 5 * i;
			doorCoords.add(new int[] { 250, 22 + t});
			}
		}
		for (int[] coord : doorCoords) {
			largeGrid.setCell(coord[0], coord[1], new Door());
		}
		largeGrid.initializeEmptyCells();

		int crosswalkInterval = 75;
		int timestep = 10;

		SimulationController result = new SimulationController(largeGrid, timestep, crosswalkInterval, runInRealtime);
		result.stats.addStatistic("Door positions", doorCoords);

		return result;
	}
	
	

	/**
	 * This method re-runs an old simulation based off a given grid, using the current random seed.
	 * 
	 * @return
	 */
	public static SimulationController createEvacSimulation(boolean runInRealtime, Grid oldGrid) {
		List<int[]> doorCoords = new LinkedList<int[]>();
		for(Cell c : oldGrid){
			if(c instanceof Door){
				doorCoords.add(new int[]{c.getRow(), c.getCol()});
			}
		}

		int crosswalkInterval = 75;
		int timestep = 10;

		SimulationController result = new SimulationController(oldGrid, timestep, crosswalkInterval, runInRealtime);
		result.stats.addStatistic("Door positions", doorCoords);

		return result;
	}
	
	public static Grid createDefaultBuildingPlacement(){
		Grid largeGrid = new Grid(300, 308);

		// Paving 5th St NW
		/*for (int c = 0; c <= 268; c++)
			for (int r = 0; r <= 36; r++)
				largeGrid.setCell(r, c, new Road());*/
		// Paving Armstead Pl
		/*for (int c = 41; c <= 268; c++)
			for (int r = 227; r <= 246; r++)
				largeGrid.setCell(r, c, new Road());*/
		// Paving Spring St
		for (int c = 0; c <= 40; c++)
			for (int r = 0; r <= 299; r++)
				largeGrid.setCell(r, c, new Road());
		// Paving Peachtree St NW
		for (int c = 269; c <= 307; c++)
			for (int r = 0; r <= 299; r++)
				largeGrid.setCell(r, c, new Road());
		for (int c = 0; c <= 50; c++)
			for (int r = 100; r <= 200; r++)
				largeGrid.setCell(r, c, new Road());
		for (int c = 52; c <= 90; c++)
			for (int r = 100; r <= 200; r++)
				largeGrid.setCell(r, c, new Road());
		for (int c = 92; c <= 130; c++)
			for (int r = 100; r <= 200; r++)
				largeGrid.setCell(r, c, new Road());
		for (int c = 132; c <= 170; c++)
			for (int r = 100; r <= 200; r++)
				largeGrid.setCell(r, c, new Road());
		for (int c = 172; c <= 210; c++)
			for (int r = 100; r <= 200; r++)
				largeGrid.setCell(r, c, new Road());
		for (int c = 212; c <= 250; c++)
			for (int r = 100; r <= 200; r++)
				largeGrid.setCell(r, c, new Road());
		for (int c = 252; c <= 307; c++)
			for (int r = 100; r <= 200; r++)
				largeGrid.setCell(r, c, new Road());
		for (int c = 0; c <= 307; c++)
			for (int r = 252; r <= 289; r++)
				largeGrid.setCell(r, c, new Road());
		// Paving 5th St NE
		/*for (int c = 293; c <= 307; c++)
			for (int r = 125; r <= 144; r++)
				largeGrid.setCell(r, c, new Road());*/
		// Build the Management Building
		//for (int c = 47; c <= 262; c++)
		//	for (int r = 43; r <= 220; r++)
		//		if (!(r <= 146 && c >= 192)) // Management courtyard
		//			largeGrid.setCell(r, c, new Obstacle());
		// Build the GT Hotel
		/*for (int c = 0; c <= 10; c++)
			for (int r = 43; r <= 299; r++)
				largeGrid.setCell(r, c, new Obstacle());*/
		// Build the new building on top of the Crum and Forster Building
		/*for (int c = 47; c <= 262; c++)
			for (int r = 252; r <= 299; r++)
				largeGrid.setCell(r, c, new Obstacle());*/
		// Various sundry buildings
		/*for (int c = 299; c <= 307; c++)
			for (int r = 151; r <= 299; r++)
				largeGrid.setCell(r, c, new Obstacle());*/
		/*for (int c = 299; c <= 307; c++)
			for (int r = 0; r <= 118; r++)
				largeGrid.setCell(r, c, new Obstacle());
		for (int c = 47; c <= 262; c++)
			for (int r = 0; r <= 10; r++)
				largeGrid.setCell(r, c, new Obstacle());
		for (int c = 0; c <= 10; c++)
			for (int r = 0; r <= 10; r++)
				largeGrid.setCell(r, c, new Obstacle());*/
		// Painting two crosswalks
		// 5th and Spring
		/*for (int c = 42; c < 46; c++)
			for (int r = 17; r <= 36; r++)
				largeGrid.setCell(r, c, new CrosswalkV());
		for (int c = 12; c < 16; c++)
			for (int r = 17; r <= 36; r++)
				largeGrid.setCell(r, c, new CrosswalkV());
		for (int c = 17; c < 41; c++)
			for (int r = 38; r <= 42; r++)
				largeGrid.setCell(r, c, new CrosswalkH());
		for (int c = 17; c < 41; c++)
			for (int r = 11; r <= 15; r++)
				largeGrid.setCell(r, c, new CrosswalkH());*/
		
		// 5th (NW) and Peachtree
		/*for (int c = 294; c <= 298; c++)
			for (int r = 125; r <= 144; r++)
				largeGrid.setCell(r, c, new CrosswalkV());
		for (int c = 269; c <= 292; c++)
			for (int r = 119; r <= 123; r++)
				largeGrid.setCell(r, c, new CrosswalkH());
		for (int c = 269; c <= 292; c++)
			for (int r = 146; r <= 150; r++)
				largeGrid.setCell(r, c, new CrosswalkH());*/
		
		// 5th (NE) and Peachtree
		/*for (int c = 263; c <= 267; c++)
			for (int r = 17; r <= 36; r++)
				largeGrid.setCell(r, c, new CrosswalkV());
		for (int c = 269; c <= 292; c++)
			for (int r = 11; r <= 15; r++)
				largeGrid.setCell(r, c, new CrosswalkH());
		for (int c = 269; c <= 292; c++)
			for (int r = 38; r <= 42; r++)
				largeGrid.setCell(r, c, new CrosswalkH());*/
		
		// No crosswalk modeling at Armstead
		/*for (int c = 45; c < 50; c++)
			for (int r = 227; r <= 246; r++)
				largeGrid.setCell(r, c, new EmptyCell());
		for (int c = 260; c <= 264; c++)
			for (int r = 227; r <= 246; r++)
				largeGrid.setCell(r, c, new EmptyCell());*/
				
		return largeGrid;
	}

	/**
	 * Start the simulation.
	 */
	public void start() {
		stats.addStatistic("People safe", Person.peopleSafeOverTime);
		// stats.addStatistic("Mean distance");

		currentState.initialize();

		if (useRealtime) {
			timer.start();
		} else {
			while (!Person.isEveryoneSafe()) {
				updateSimulation();
			}
		}
	}

	/**
	 * Step through the simulation, updating all cells as appropriate.
	 */
	public void updateSimulation() {
		CrosswalkController.getInstance().step();
		simTime += timestep;
		System.out.println(simTime);

		for (Cell c : currentState) {
			c.calcUpdate();
		}
		for (Cell c : currentState) {
			c.update();
		}

		if (myHandler != null)
			myHandler.onUpdate();

		// update some statistics
		Person.peopleSafeOverTime.add(Person.numPeopleSafe);
		// Person.medianPersonDistance = ??
	}

	/**
	 * Get all the cells in the implementation of the world.
	 * 
	 * @return The world as it is at the given simulation step.
	 */
	public Grid getGrid() {
		return currentState;
	}

	/**
	 * Get results from the statistics aggregator.
	 * 
	 * @return The statistics aggregator.
	 */
	public Statistics getStatistics() {
		return stats;
	}
}