package statistics;

public class Leaf {

	private int expectedSignal;
	private int expectedBackground;
	private int observedEvents;
	
	public Leaf(int expectedSignal, int expectedBackground, int observedEvents){
		this.expectedSignal = expectedSignal;
		this.expectedBackground = expectedBackground;
		this.observedEvents = observedEvents;
	}
	
	public void setExpectedSignal(int expectedSignal){
		this.expectedSignal = expectedSignal;
	}
	
	public void setExpectedBackground(int expectedBackground){
		this.expectedBackground = expectedBackground;
	}
	
	public void setObservedEvents(int observedEvents){
		this.observedEvents = observedEvents;
	}
	
	public int getExpectedSignal(){
		return expectedSignal;
	}
	
	public int getExpectedBackground(){
		return expectedBackground;
	}
	
	public int getObservedEvents(){
		return observedEvents;
	}
}
