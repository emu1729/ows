package particlePhysics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClusterVisualizer extends JPanel {

	/**
	 * I add this because otherwise Eclipse gets mad at me.  It is not used.
	 */
	private static final long serialVersionUID = 1L;

	private double maxEnergy = 0;
	private List<Tower> points = new ArrayList<>();

	static private final double ETAMAX = 3.14;
	static private final int INCREMENTS = 20;
	static private final double X_SIZE = 2 * Math.PI / INCREMENTS;
	static private final double Y_SIZE = 2 * ETAMAX / INCREMENTS;

	/**
	 * @param grid The ParticleContainer that is being visualized
	 * @param timeIncrement The amount of time between each update, in real physics time
	 */
	public ClusterVisualizer() {
		setBackground(Color.black);

		final int width = 800;
		final int height = 600;
		this.setSize(width, height);
	}

	@Override
	public void paint(Graphics arg0) {
		super.paint(arg0);

		for (Tower point : points) {
			drawTower(arg0, point);
		}
	}

	/**
	 * @param energy The energy of the tower
	 * @return The color of the element based on the color scale
	 */
	private Color calcColor(double energy) {
		double temp = energy / maxEnergy;
		final double darkBlue = 0.65;
		double hue = (1 - temp) * darkBlue;
		final double saturation = 0.9;
		final double brightness = 0.9;

	//	System.out.println(temperature);
		return Color.getHSBColor((float)hue, (float)saturation, (float)brightness);
	}
	
	private Point transform(double x, double y) {
		// transform: xprime = Mx/2PI
		int xCoord = (int)Math.round(x * getSize().getWidth() / (2 * Math.PI));
		// transform: yprime = M*(-y/2S+1/2)
		int yCoord = (int)Math.round((.5 - y / (ETAMAX * 2)) * (int)getSize().getHeight());
		return new Point(xCoord, yCoord);
	}

	private void drawTower(Graphics arg0, Tower point) {
		Point newPoint = transform(point.getPhi() - X_SIZE / 2, point.getEta() - Y_SIZE / 2);
		Point maxPoint = transform(point.getPhi() + X_SIZE / 2, point.getEta() + Y_SIZE / 2);
		
		int width = maxPoint.x - newPoint.x;
		int height = newPoint.y - maxPoint.y;
				
		arg0.setColor(calcColor(point.getEnergy()));
		arg0.fillRect(newPoint.x, newPoint.y, width, height);
	}

	public void addTower(Tower point) {
		points.add(point);
		if (point.getEnergy() > maxEnergy) {
			maxEnergy = point.getEnergy();
		}
		repaint();
	}

	public void clear() {
		points.clear();
	}

	static public ClusterVisualizer createVisualizer() {
		ClusterVisualizer viz = new ClusterVisualizer();

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(viz);
		frame.setSize(viz.getWidth(), viz.getHeight());

		frame.setVisible(true);	

		return viz;
	}
}
