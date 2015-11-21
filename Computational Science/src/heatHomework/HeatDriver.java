package heatHomework;

public class HeatDriver {

	// Fill all these in with real numbers
	private double THERMAL_DIFFUSIVITY;
	private double initialTemp;
	private Vector3 min;
	private Vector3 max;
	private Coordinate size;
	private double timeIncrement;
	private double tempSpike;
	private double ambientTemp;
	
	private Coordinate spikeLocation;
	private double timeScale;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//level1();
		//level2();
		//level3();
		challenge();
	}
	
	public static void level1() {
		double THERMAL_DIFFUSIVITY = 0.1;
		double initialTemp = 10;
		Vector3 min = new Vector3(0, 0, 0);
		Vector3 max = new Vector3(20, 1, 1);
		Coordinate size = new Coordinate(20, 1, 1);
		double timeIncrement = 0.1;
		double tempSpike = 100;
		double ambientTemp = 0;
		Coordinate spikeLocation = new Coordinate(10, 0, 0);
		double timeScale = 1;
		HeatGrid grid = new HeatGrid(THERMAL_DIFFUSIVITY, initialTemp, min, max, size, ambientTemp, true);
		grid.getCell(spikeLocation.getX(), spikeLocation.getY(), spikeLocation.getZ()).setTemperature(tempSpike);
		grid.run(timeIncrement, timeScale, tempSpike, "test1.jpg");
	}
	
	public static void level2() {
		double THERMAL_DIFFUSIVITY = 0.1;
		double initialTemp = 100;
		Vector3 min = new Vector3(0, 0, 0);
		Vector3 max = new Vector3(10, 10, 1);
		Coordinate size = new Coordinate(20, 20, 1);
		double timeIncrement = 0.1;
		double tempSpike = 500;
		double ambientTemp = 0;
		Coordinate spikeLocation = new Coordinate(10, 10, 0);
		double timeScale = 1;
		HeatGrid grid = new HeatGrid(THERMAL_DIFFUSIVITY, initialTemp, min, max, size, ambientTemp, true);
		grid.getCell(spikeLocation.getX(), spikeLocation.getY(), spikeLocation.getZ()).setTemperature(tempSpike);
		grid.run(timeIncrement, timeScale, tempSpike, "test2.jpg");
	}
	
	public static void level3() {
		double THERMAL_DIFFUSIVITY = 0.1;
		double initialTemp = 100;
		Vector3 min = new Vector3(0, 0, 0);
		Vector3 max = new Vector3(10, 10, 1);
		Coordinate size = new Coordinate(10, 10, 1);
		double timeIncrement = 0.1;
		double tempSpike = 500;
		double ambientTemp = 2;
		Coordinate spikeLocation = new Coordinate(5, 5, 0);
		double timeScale = 1;
		HeatGrid grid = new HeatGrid(THERMAL_DIFFUSIVITY, initialTemp, min, max, size, ambientTemp, false);
		grid.getCell(spikeLocation.getX(), spikeLocation.getY(), spikeLocation.getZ()).setTemperature(tempSpike);
		grid.run(timeIncrement, timeScale, tempSpike, "test3.jpg");
	}
	
	public static void challenge() {
		double THERMAL_DIFFUSIVITY = 0.01;
		double initialTemp = 100;
		Vector3 min = new Vector3(0, 0, 0);
		Vector3 max = new Vector3(100, 100, 1);
		Coordinate size = new Coordinate(100, 100, 1);
		double timeIncrement = 0.001;
		double tempSpike = 500;
		double ambientTemp = 2;
		double timeScale = 10000;
		
		Coordinate object1TopLeftLocation = new Coordinate(0, 50, 0);
		Coordinate object1BottomRightLocation = new Coordinate(50, 0, 0);
		double object1ThermalDiffusivity = 0.3;
		double object1Temperature = 100;
		
		Coordinate object2TopRightLocation = new Coordinate(50, 50, 0);
		Coordinate object2BottomLeftLocation = new Coordinate(99, 0, 0);
		double object2ThermalDiffusivity = 0.1;
		double object2Temperature = 300;
		
		Coordinate object3TopRightLocation = new Coordinate(0, 99, 0);
		Coordinate object3BottomLeftLocation = new Coordinate(50, 50, 0);
		double object3ThermalDiffusivity = 0.4;
		double object3Temperature = 300;
		
		Coordinate object4TopRightLocation = new Coordinate(50, 99, 0);
		Coordinate object4BottomLeftLocation = new Coordinate(99, 50, 0);
		double object4ThermalDiffusivity = 0.5;
		double object4Temperature = 500;
		
		HeatGrid grid = new HeatGrid(THERMAL_DIFFUSIVITY, initialTemp, min, max, size, ambientTemp, false);
		grid.addObject(object1TopLeftLocation, object1BottomRightLocation, object1ThermalDiffusivity, object1Temperature);
		grid.addObject(object2TopRightLocation, object2BottomLeftLocation, object2ThermalDiffusivity, object2Temperature);
		grid.addObject(object3TopRightLocation, object3BottomLeftLocation, object3ThermalDiffusivity, object3Temperature);
		grid.addObject(object4TopRightLocation, object4BottomLeftLocation, object4ThermalDiffusivity, object4Temperature);
		grid.run(timeIncrement, timeScale, tempSpike, "challenge.jpg");
	}

}
