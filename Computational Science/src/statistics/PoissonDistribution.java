package statistics;

import java.util.Random;
import java.util.ArrayList;

public class PoissonDistribution {
	private static Random random = new Random();
	
	private boolean backgroundUncertainty;
	private double backgroundUncertaintyPercentage;
	
	public PoissonDistribution(){
		backgroundUncertainty = false;
	}
	
	/**
	 * 
	 * @param backgroundUncertaintyPercentage
	 */
	public void setBackgroundUncertainty(double backgroundUncertaintyPercentage){
		backgroundUncertainty = true;
		this.backgroundUncertaintyPercentage = backgroundUncertaintyPercentage;
	}
	
	/**
	 * Returns random number based upon Poisson distribution
	 * @param lambda
	 * @return
	 */
	public int getRandomNumber(double lambda){
		int result = 0;
		double scaler = Math.exp(-lambda);
		double adder = scaler;
		double ran = random.nextDouble();
		
		while (ran > adder) {
			++result;
			scaler *= lambda / result;
			adder += scaler;
		}
		
		return result;
	}
	
	/**
	 * Returns bestLamba for expected and observed number of events
	 * @param expectedNumber
	 * @param observedNumber
	 * @param range
	 * @param numOfRandom
	 * @param increment
	 * @return
	 */
	public double getBestLambda(int expectedNumber, int observedNumber, int range, double numOfRandom, double increment){
		double bestLambda = 0;
		double bestNumberCorrect = 0;
		for(int i = 0; i < range; i++){
			double lambda = expectedNumber + increment * i;
			int numberCorrect = 0;
			for(int j = 0; j < numOfRandom; j++){
				int randomNumber = getRandomNumber(lambda);
				if (randomNumber == observedNumber){
					numberCorrect++;
				}
			}
			//System.out.println(lambda + "\t" + numberCorrect);
			if (numberCorrect > bestNumberCorrect){
				bestLambda = lambda;
				bestNumberCorrect = numberCorrect;
			}
		}
		System.out.println(bestLambda + "\t" + bestNumberCorrect);
		return bestLambda;
	}
	
	/**
	 * 
	 * @param decisionTree of leaves
	 * @param sigmaTries
	 * @param increment
	 * @param triesPerLeaf
	 * @return best sigma for specific decision tree
	 */
	public double getBestSigma(ArrayList<Leaf> decisionTree, int sigmaTries, double increment, double triesPerLeaf){
		double bestSigma = 0;
		double bestNumberCorrect = 0;
		for(int i = 0; i < sigmaTries; i++){
			double potentialSigma = i * increment;
			double potentialNumberCorrect = 0;
			for(Leaf leaf: decisionTree){
				//potentialLambda = background + sigma * signal
				double potentialExpected = leaf.getExpectedBackground() + potentialSigma * leaf.getExpectedSignal();
				for (int j = 0; j < triesPerLeaf; j++){
					int randomNumber = getRandomNumber(potentialExpected);
					if (randomNumber == leaf.getObservedEvents()){
						potentialNumberCorrect++;
					}
				}
			}
			if (potentialNumberCorrect > bestNumberCorrect){
				bestSigma = potentialSigma;
				bestNumberCorrect = potentialNumberCorrect;
				//System.out.println(bestNumberCorrect + "\t" + bestSigma);
			}
		}
		return bestSigma;
	}
	
	/**
	 * return pseudoTree
	 * @param trueTree
	 * @param trueSigma
	 * @return pseudoTree
	 */
	public ArrayList<Leaf> getPotentialTree(ArrayList<Leaf> trueTree, double trueSigma){
		ArrayList<Leaf> potentialTree = new ArrayList<Leaf>();
		double uncertainty = 1;
		if(backgroundUncertainty){
			uncertainty = -1;
			while(uncertainty < 0){
				uncertainty = random.nextGaussian()*backgroundUncertaintyPercentage + 1;
			}
		}
		for(Leaf leaf: trueTree){
			double trueLambda = leaf.getExpectedBackground() + leaf.getExpectedSignal()*trueSigma;
			int newObservedEvents = getRandomNumber(trueLambda);
			int randomBackground = (int) Math.round(uncertainty * leaf.getExpectedBackground());
			potentialTree.add(new Leaf(leaf.getExpectedSignal(), randomBackground, newObservedEvents));
		}
		return potentialTree;
	}
	
	/**
	 * @param trueTree
	 * @param potentialTree
	 * @return merit
	 */
	public double getMerit(ArrayList<Leaf> trueTree, ArrayList<Leaf> potentialTree){
		double merit = 0;
		int size = trueTree.size();
		for(int i = 0; i < size; i++){
			double sum = 0;
			sum += Math.abs(trueTree.get(i).getExpectedBackground() - potentialTree.get(i).getExpectedBackground());
			sum += Math.abs(trueTree.get(i).getObservedEvents() - potentialTree.get(i).getObservedEvents());
			merit += sum/trueTree.get(i).getExpectedSignal();
		}
		return merit;
	}
	
}
