package boltzmannHomework;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author Peter Dong
 * This class visualizes the Particles in a ParticleContainer in a two-dimensional projection 
 */
public class ParticleVisualizer extends JPanel implements ActionListener {

	/**
	 * I add this because otherwise Eclipse gets mad at me.  It is not used.
	 */
	private static final long serialVersionUID = 1L;

	private double timeIncrement;
	private ParticleContainer grid;

	/**
	 * @param grid The ParticleContainer that is being visualized
	 * @param timeIncrement The amount of time between each update, in real physics time
	 */
	public ParticleVisualizer(ParticleContainer grid, double timeIncrement) {
		this.grid = grid;
		this.timeIncrement = timeIncrement;

		setBackground(Color.black);
		
		final int width = 800;
		final int height = 600;
		this.setSize(width, height);
		
		int incrementInMils = (int)(timeIncrement * 1000);
		Timer time = new Timer(incrementInMils, this);
		time.start();
	}
	
	/**
	 * The radius, in pixels, of each dot
	 */
	private static int DOT_SIZE = 5;

	@Override
	public void paint(Graphics arg0) {
		super.paint(arg0);
		
		for (Projectile element : grid.getParticles()) {

			int xCoord = (int)Math.round(element.getPosition().getX() / grid.getXSize() * (int)getSize().getWidth());
			int yCoord = (int)getSize().getHeight() - (int)Math.round(element.getPosition().getY() / grid.getYSize() * (int)getSize().getHeight());
			
			arg0.setColor(element.getColor());
			arg0.fillOval(xCoord, yCoord, DOT_SIZE, DOT_SIZE);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
		grid.advanceParticles(timeIncrement);
	}

}
