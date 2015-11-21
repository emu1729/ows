package heatHomework;

public class HeatElement {

	private Vector3 location;
	private Coordinate coord;
	private double temperature;
	private double temperatureDerivative;
	private HeatGrid map;
	private double newTemp = 0;
	private double thermalDiffusivity;
	private boolean boundarySwitch;
	
	public HeatElement(HeatGrid heatGrid, Vector3 location, Coordinate coord, double temperature, double thermalDiffusivity, boolean boundarySwitch) {
		this.location = location;
		this.coord = coord;
		this.temperature = temperature;
		this.map = heatGrid;
		this.thermalDiffusivity = thermalDiffusivity;
		this.boundarySwitch = boundarySwitch;
		temperatureDerivative = 0;
	}
	
	public double getThermalDiffusivity()
	{
		return thermalDiffusivity;
	}
	
	public void setThermalDiffusivity(double thermalDiffusivity)
	{
		this.thermalDiffusivity = thermalDiffusivity;
	}
	
	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	/**
	 * This assigns a temperature to an element but doesn't actually make the change until update() is called
	 * @param temperature The new temperature to set
	 */
	public void setNewTemp(double temperature) {
		newTemp = temperature;
	}
	
	public Coordinate getCoord() {
		return coord;
	}
	
	public Vector3 getPosition() {
		return location;
	}
	
	public void calculate(double deltaTime) {
		// TODO Write this function to implement the heat equation
		double newTemperature = 0;
		double leftChangeInTempX = 0;
		double rightChangeInTempX = 0;
		if (coord.getX() > 0)
		{
			leftChangeInTempX = (temperature - map.getCell(coord.getX()-1, coord.getY(), coord.getZ()).getTemperature())/map.getDx();
		}
		else
		{
			leftChangeInTempX = (temperature - map.getAmbientTemp())/map.getDx();
			if (boundarySwitch == false)
			{
				leftChangeInTempX = 0;
			}
		}
		if (coord.getX() < map.getXSize()-1)
		{
			rightChangeInTempX = (map.getCell(coord.getX()+1, coord.getY(), coord.getZ()).getTemperature() - temperature)/map.getDx();
		}
		else
		{
			rightChangeInTempX = (map.getAmbientTemp() - temperature)/map.getDx();
			if (boundarySwitch == false)
			{
				rightChangeInTempX = 0;
			}
		}
		double changeInTempX = deltaTime * thermalDiffusivity * (rightChangeInTempX - leftChangeInTempX)/map.getDx();
		double leftChangeInTempY = 0;
		double rightChangeInTempY = 0;
		double changeInTempY = 0;
		if(map.getDy() > 0)
		{
			if (coord.getY() > 0)
			{
				leftChangeInTempY = (temperature - map.getCell(coord.getX(), coord.getY()-1, coord.getZ()).getTemperature())/map.getDy();
			}
			else
			{
				leftChangeInTempY = (temperature - map.getAmbientTemp())/map.getDy();
				if (boundarySwitch == false)
				{
					leftChangeInTempY = 0;
				}
			}
			if (coord.getY() < map.getYSize() - 1)
			{
				rightChangeInTempY = (map.getCell(coord.getX(), coord.getY()+1, coord.getZ()).getTemperature() - temperature)/map.getDy();
			}
			else
			{
				rightChangeInTempY = (map.getAmbientTemp() - temperature)/map.getDy();
				if (boundarySwitch == false)
				{
					rightChangeInTempY = 0;
				}
			}
			changeInTempY = deltaTime * thermalDiffusivity * (rightChangeInTempY - leftChangeInTempY)/map.getDy();
		}
		else
		{
			changeInTempY = 0;
		}
		temperatureDerivative = temperatureDerivative + (changeInTempX + changeInTempY);
		newTemperature = temperature + temperatureDerivative*deltaTime;
		setNewTemp(newTemperature);
	}
	/**
	 * This updates each element based on the previous calculation.
	 */
	public void update() {
		if (newTemp < 0) {
			setTemperature(0);
		} else {
			setTemperature(newTemp);
		}
	}
}
