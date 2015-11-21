package decisionTreeHomework;

public class NeuralNode<Type extends Event> {
	
	private double[] weights;
	private int nSignal;
	private int nBackground;
	
	public NeuralNode(int numOfInputs){
		weights = new double[numOfInputs];
	}
	
	public double sigmoidFunction(double weights[], Type e){
		double sum = 0.5;
		for (int i = 0; i < 8; i++){
			sum += weights[i] * e.getVars()[i];
		}
		double sigmoid = 1./(1 + Math.exp(-sum));
		return sigmoid;
	}
	
	public void train(Data<Type> signal, Data<Type> background){
		nSignal = signal.getEvents().size();
		nBackground = background.getEvents().size();
		int numTries = 10000;
		double minError = 1000;
		double[] bestWeights = new double[8];
		double[] potentialWeights = new double[8];
		for(int a = 0; a < numTries; a++){
			for (int i = 0; i < 8; i++){
				potentialWeights[i] = Math.random() * 2 - 1;
			}
			double errorPotential = 0;
			for(Type event: signal.getEvents()){
				double result = sigmoidFunction(potentialWeights, event);
				errorPotential += 1-result;
			}
			for(Type event: background.getEvents()){
				double result = sigmoidFunction(potentialWeights, event);
				errorPotential += result;
			}
			errorPotential = errorPotential/(nSignal+nBackground);
			if (errorPotential < minError){
				bestWeights = potentialWeights;
				minError = errorPotential;
				System.out.println("Hi");
				System.out.println(minError);
			}
		}
		weights = bestWeights;
		for(int i = 0; i< 8; i++){
			System.out.println(bestWeights[i]);
		}
	}
	
	public double runEvent(Type event){
		return sigmoidFunction(weights, event);
	}
	
}
