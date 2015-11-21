package particlePhysics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CalorimeterData implements Serializable {

	private static final long serialVersionUID = 6483760533940886409L;
	List<CalorimeterEvent> events = new ArrayList<>();
	
	public void addEvent(CalorimeterEvent event) {
		events.add(event);
	}
	
	public List<CalorimeterEvent> getEvents() {
		return events;
	}
}
