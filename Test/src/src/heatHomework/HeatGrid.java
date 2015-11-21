package heatHomework;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

/**
 * @author Peter Dong
 * This represents an object with a varying heat field
 */
public class HeatGrid {
	
	ArrayList<ArrayList<ArrayList<HeatElement>>> grid = new ArrayList<ArrayList<ArrayList<HeatElement>>>();
	double thermalDiffusivity;
	double ambientTemp;
	boolean boundarySwitch;
	
	/**
	 * @param thermalDiffusivity The thermal diffusivity constant
	 * @param initialTemp The original temperature of each element. This can be freely modified with getCell().setTemperature().
	 * @param min The minimum x, y, and z values of the object in physical space.
	 * @param max The maximum x, y, and z values of the object in physical space.
	 * @param size The number of elements in the object in the x, y, and z direction. Make sure each component is at least 1.
	 */
	public HeatGrid(double thermalDiffusivity, double initialTemp, Vector3 min, Vector3 max, Coordinate size, double ambientTemp, boolean boundarySwitch) {
		this.thermalDiffusivity = thermalDiffusivity;
		this.ambientTemp = ambientTemp;
		this.boundarySwitch = boundarySwitch;

		double xIncrement = getIncrement(min.getX(), max.getX(), size.getX());
		double yIncrement = getIncrement(min.getY(), max.getY(), size.getY());
		double zIncrement = getIncrement(min.getZ(), max.getZ(), size.getZ());
		
		for (int ix = 0; ix < size.getX(); ++ix) {
			grid.add(new ArrayList<ArrayList<HeatElement>>());
			for (int iy = 0; iy < size.getY(); ++iy) {
				grid.get(ix).add(new ArrayList<HeatElement>());
				for (int iz = 0; iz < size.getZ(); ++iz) {
					double xCoord = getCoord(min.getX(), xIncrement, ix);
					double yCoord = getCoord(min.getY(), yIncrement, iy);
					double zCoord = getCoord(min.getZ(), zIncrement, iz);
					
					HeatElement el = new HeatElement(this, new Vector3(xCoord, yCoord, zCoord), 
							new Coordinate(ix, iy, iz), initialTemp, thermalDiffusivity, boundarySwitch);
					grid.get(ix).get(iy).add(el);
				}
			}
		}
	}
	
	public void addObject(Coordinate topLeftCorner, Coordinate bottomRightCorner, double newThermalDiffusivity, double newTemperature)
	{
		for (int ix = topLeftCorner.getX(); ix <= bottomRightCorner.getX(); ++ix) {
			for (int iy = bottomRightCorner.getY(); iy <= topLeftCorner.getY(); ++iy) {
				for (int iz = topLeftCorner.getZ(); iz <= bottomRightCorner.getZ(); ++iz) {
					HeatElement el = getCell(ix, iy, iz);
					el.setThermalDiffusivity(newThermalDiffusivity);
					el.setTemperature(newTemperature);
				}
			}
		}
	}
	public double getAmbientTemp()
	{
		return ambientTemp;
	}
	/**
	 * @return The number of cells in the x direction
	 */
	public int getXSize() {
		return grid.size();
	}
	
	/**
	 * @return The number of cells in the y direction
	 */
	public int getYSize() {
		return grid.get(0).size();
	}
	
	/**
	 * @return The number of cells in the z direction
	 */
	public int getZSize() {
		return grid.get(0).get(0).size();
	}
	
	/**
	 * @return The physical distance between any two cells in the x direction
	 */
	public double getDx() {
		if (getXSize() <= 1) {
			return 0;
		} else {
			return getCell(1, 0, 0).getPosition().getX() - getCell(0, 0, 0).getPosition().getX();
		}
	}
	
	/**
	 * @return The physical distance between any two cells in the y direction
	 */
	public double getDy() {
		if (getYSize() <= 1) {
			return 0;
		} else {
			return getCell(0, 1, 0).getPosition().getY() - getCell(0, 0, 0).getPosition().getY();
		}	
	}

	/**
	 * @return The physical distance between any two cells in the z direction
	 */
	public double getDz() {
		if (getZSize() <= 1) {
			return 0;
		} else {
			return getCell(0, 0, 1).getPosition().getZ() - getCell(0, 0, 0).getPosition().getZ();
		}	
	}
	
	/**
	 * @return The minimum physical value of x, y, and z
	 */
	public Vector3 getMin() {
		return grid.get(0).get(0).get(0).getPosition();
	}
	
	/**
	 * @return The maximum physical value of x, y and z
	 */
	public Vector3 getMax() {
		return grid.get(getXSize() - 1).get(getYSize() - 1).get(getZSize() - 1).getPosition();		
	}
	
	public double getDiffusivity() {
		return thermalDiffusivity;
	}
	
	/**
	 * @return A list of the elements inside the grid, mainly for use by HeatVisualizer
	 */
	public List<HeatElement> getElements() {
		List<HeatElement> answer = new ArrayList<HeatElement>();
		
		for (ArrayList<ArrayList<HeatElement>> xlist : grid) {
			for (ArrayList<HeatElement> ylist : xlist) {
				for (HeatElement element : ylist) {
					answer.add(element);
				}
			}
		}
		
		return answer;
	}
	
	private double getIncrement(double min, double max, int size) {
		return size == 0 ? 0 : Math.abs(max - min) / size;
	}
	
	private double getCoord(double min, double increment, int index) {
		return min + index * increment;
	}
	
	/**
	 * @param xCoord The x coordinate of the cell
	 * @param yCoord The y coordinate of the cell
	 * @param zCoord The z coordinate of the cell
	 * @return The element at the requested coordinates
	 */
	public HeatElement getCell(int xCoord, int yCoord, int zCoord) {
		return grid.get(xCoord).get(yCoord).get(zCoord);
	}
	
	/**
	 * @param deltaTime The time increment of the simulation
	 * This loops over all elements and calls update() on each
	 */
	public void update(double deltaTime) {
		for (ArrayList<ArrayList<HeatElement>> xlist : grid) {
			for (ArrayList<HeatElement> ylist : xlist) {
				for (HeatElement element : ylist) {
					element.calculate(deltaTime);
				}
			}
		}
		
		for (ArrayList<ArrayList<HeatElement>> xlist : grid) {
			for (ArrayList<HeatElement> ylist : xlist) {
				for (HeatElement element : ylist) {
					element.update();
				}
			}
		}
	}
	
	/**
	 * @param timeIncrement The time increment of the simulation
	 * @param timeScale The scaling to the visualizer, used to adjust the speed of the display
	 * @param maxTemp The maximum temperature achieved, so the visualizer can calibrate its scale properly
	 */
	public void run(double timeIncrement, double timeScale, double maxTemp, String filename) {
		HeatVisualizer viz = new HeatVisualizer(this, timeIncrement, timeScale, maxTemp, filename);
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(viz);
		frame.setSize(viz.getWidth(), viz.getHeight());

		frame.setVisible(true);			
	}
}
