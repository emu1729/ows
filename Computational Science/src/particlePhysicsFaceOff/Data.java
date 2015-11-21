package particlePhysicsFaceOff;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Data implements Serializable {
	
	private static final long serialVersionUID = -368642764052829461L;
	
	private List<Event> events = new ArrayList<>();
	
	public List<Event> getEvents() {
		return events;
	}
	
	public void addEvent(Event event) {
		events.add(event);
	}
}
