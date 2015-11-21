package particlePhysicsFaceOff;

import java.util.List;
import particlePhysicsFaceOff.EventGenerator.Particle;

public class ParticleDriver {

	public static void main(String[] args)
	{
		int signalCount = 0;
		for (int i=0; i<1000; i++)
		{
			EventGenerator generator = new FreindsGenerator();
			List<Particle> event = generator.makeParticles(false);
			if(!generator.discriminateEvent(event))
			{
				signalCount++;
			}
		}
		System.out.println((double)signalCount/1000.);
	}
}
