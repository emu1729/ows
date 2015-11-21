package chemicalReactionsHomework;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParticleDictionary {

	private class ParticleInfo {
		double mass;
		Color color;
		double meanFreeTime;
	}
	
	static private Map<String, ParticleInfo> map = new HashMap<String, ParticleInfo>();
	
	public void addParticle(String name, double mass, Color color, double meanFreeTime) {
		ParticleInfo info = new ParticleInfo();
		info.mass = mass;
		info.color = color;
		info.meanFreeTime = meanFreeTime;
		
		map.put(name, info);
	}
	
	public Set<String> getList() {
		return map.keySet();
	}
	
	public double getMass(String name) {
		return map.get(name).mass;
	}
	
	public Color getColor(String name) {
		return map.get(name).color;
	}
	
	public Particle makeParticle(Vector3 position, Vector3 velocity, String name) {
		ParticleInfo info = map.get(name);
		
		return new RandomWalkParticle(position, velocity, info.mass, info.color, name, info.meanFreeTime);
	}
	
}
