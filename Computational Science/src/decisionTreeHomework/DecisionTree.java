package decisionTreeHomework;

public class DecisionTree<Type extends Event> {
	
	private Leaf<Type> headnode = new Leaf<>();
	private double score;
	
	public void train(Data<Type> signal, Data<Type> background) {
		headnode.train(signal, background);
	}
	
	public double runEvent(Type event) {
		Leaf<Type> leaf = headnode;
		
		while (!leaf.isFinal()) {
			leaf = leaf.runEvent(event);
		}
		
		return leaf.getSetPurity();
	}
	
	//sets the score of the tree
	public void setScore(double newScore){
		score = newScore;
	}
	
	//gets the score of the tree
	public double getScore(){
		return score;
	}
}
