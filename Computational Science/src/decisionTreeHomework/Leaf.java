package decisionTreeHomework;

public class Leaf<Type extends Event> {

	private Leaf<Type> output1 = null;
	private Leaf<Type> output2 = null;
	private double split;
	private int variable;
	
	private int nBackground;
	private int nSignal;
	private double purity;
	
	public Leaf() {
		this(0, 0);
	}
	
	public Leaf(int variable, double split) {
		this.variable = variable;
		this.split = split;
		purity = 0;
	}
	
	public boolean isFinal() {
		return output1 == null || output2 == null;
	}
	
	public double getPurity() {
		return (double)nSignal / (nSignal + nBackground);
	}
	
	public void setPurity(Data<Type> signal, Data<Type> background){
		nSignal = signal.getEvents().size();
		nBackground = background.getEvents().size();
		purity = (double)nSignal / (nSignal + nBackground);
	}
	
	public double getSetPurity(){
		return purity;
	}
	
	public Leaf<Type> getLeftBranch() {
		return output1;
	}
	
	public Leaf<Type> getRightBranch() {
		return output2;
	}
	
	public Leaf<Type> runEvent(Type event) {
		if (isFinal()) {
			return null;
		}
		
		if (event.getVars()[variable] <= split) {
			return output1;
		} else {
			return output2;
		}
	}
	
	public void train(Data<Type> signal, Data<Type> background) {
		nSignal = signal.getEvents().size();
		nBackground = background.getEvents().size();
		
		boolean branch = chooseVariable(signal, background);
	
		if (branch) {
			output1 = new Leaf<Type>();
			output2 = new Leaf<Type>();
			
			Data<Type> signalLeft = new Data<>();
			Data<Type> signalRight = new Data<>();
			Data<Type> backgroundLeft = new Data<>();
			Data<Type> backgroundRight = new Data<>();
			
			for (Type event : signal.getEvents()) {
				if (runEvent(event) == output1) {
					signalLeft.addEvent(event);
				} else {
					signalRight.addEvent(event);
				}
			}
			
			for (Type event : background.getEvents()) {
				if (runEvent(event) == output1) {
					backgroundLeft.addEvent(event);
				} else {
					backgroundRight.addEvent(event);
				}
			}
			
			output1.setPurity(signalLeft, backgroundLeft);
			output2.setPurity(signalRight, backgroundRight);
			output1.train(signalLeft, backgroundLeft);
			output2.train(signalRight, backgroundRight);
		}		
	}
	
	private boolean chooseVariable(Data<Type> signal, Data<Type> background) {
		// TODO set the values of variable and split here		
		// Return true if you were able to find a useful variable, and false if you were not and want to stop calculation here
		int bestVariable = 0;
		double bestSplit = 0;
		double bestGini = 0.5;
		double numIterations = 500;
		//run through all of the different variables
		for(int i = 0; i < 8; i++){
			//determine the maximum range of a variable
			double maxRange = 0;
			for (Event e: signal.getEvents()){
				if(e.getVars()[i] > maxRange){
					maxRange = e.getVars()[i];
				}
			}
			for (Event e: background.getEvents()){
				if(e.getVars()[i] > maxRange){
					maxRange = e.getVars()[i];
				}
			}
			System.out.println(i + "\t" + maxRange);
			for(int j = 0; j < numIterations; j++){
				double potentialSplit = maxRange*j/numIterations;
				//testing potentialSplit and potentialVariable
				double output1Signal = 0;
				double output2Signal = 0;
				double output1Background = 0;
				double output2Background = 0;
				for (Type e: signal.getEvents()){
					//System.out.println(e.getWeight());
					if(e.getVars()[i] <= potentialSplit){
						output1Signal = e.getWeight() + output1Signal;
					}
					else{
						output2Signal = e.getWeight() + output2Signal;
					}
				}
				for (Type e: background.getEvents()){
					if(e.getVars()[i] <= potentialSplit){
						output1Background = e.getWeight() + output1Background;
					}
					else{
						output2Background = e.getWeight() + output2Background;
					}
				}
				double potentialGini = 0;
				double totalOutput1 = output1Signal + output1Background;
				double totalOutput2 = output2Signal + output2Background;
				double total = totalOutput1 + totalOutput2;
				if (output1Signal > 50 && output1Background > 50 && output2Signal > 50 && output2Background > 50){
					//if((output1Signal+output1Background) > 50 && (output2Signal+output2Background) > 50){
						double output1Purity = output1Signal/(output1Signal + output1Background);
						double output2Purity = output2Signal/(output2Signal + output2Background);
						//Gini for each leaf = purity^2 + (1-purity)^2
						double output1Gini = 1 - Math.pow(output1Purity, 2) - Math.pow((1-output1Purity), 2);
						double output2Gini = 1 - Math.pow(output2Purity, 2) - Math.pow((1-output2Purity), 2);
						//Calculating weighted Gini
						potentialGini = output1Gini*totalOutput1/total + output2Gini*totalOutput2/total;
						//System.out.println(i + "\t" + potentialSplit + "\t" + output1Signal + "\t" + output1Background + "\t" + output2Signal + "\t" + output2Background + "\t" + potentialGini);
					//}
				
					if (potentialGini < bestGini){
							bestVariable = i;
							bestSplit = potentialSplit;
							bestGini = potentialGini;
							//System.out.println(bestGini);
					}
				}
			}
		}
		if (bestGini < 0.4){
			variable = bestVariable;
			split = bestSplit;
			System.out.println(variable + "\t" + split + "\t" + bestGini);
			return true;
		}
		else{
			return false;
		}
	}

}
