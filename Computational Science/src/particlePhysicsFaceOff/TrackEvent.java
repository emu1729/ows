package particlePhysicsFaceOff;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrackEvent implements Serializable {
	private static final long serialVersionUID = 3700914643771128918L;
	
	private List<Vector3> hits = new ArrayList<>();
	
	public List<Vector3> getHits() {
		return hits;
	}
	
	public void addPoint(Vector3 point) {
		hits.add(point);
	}
}
