package particlePhysicsFaceOff;

import java.io.Serializable;

public class Event implements Serializable {

	private static final long serialVersionUID = 8819352213230183217L;
	
	private int eventNumber;
	private TrackEvent tracks;
	private CalorimeterEvent clusters;
	
	public Event(int number, TrackEvent tracks, CalorimeterEvent clusters) {
		this.eventNumber = number;
		this.tracks = tracks;
		this.clusters = clusters;
	}
	
	public TrackEvent getTracks() {
		return tracks;
	}
	
	public CalorimeterEvent getClusters() {
		return clusters;
	}
	
	public int getEventNumber() {
		return eventNumber;
	}

}
