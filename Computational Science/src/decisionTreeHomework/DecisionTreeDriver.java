package decisionTreeHomework;

import java.io.Serializable;
import java.util.*;
import java.awt.*;
import java.util.List;

public class DecisionTreeDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//level_1();
		//level_2();
		//level_3();
		//challenge();
	}
	
	public static void level_1(){
		IOHelper<Data<Event>> myReader = new IOHelper<>();
		Data<Event> signalData = myReader.read("signalBig.dat");
		Data<Event> backgroundData = myReader.read("backgroundBig.dat");
		Data<Event> testData = myReader.read("decisionTreeData.dat");
		
		DecisionTree<Event> tree = new DecisionTree<Event>();
		for(Event e: signalData.getEvents()){
			e.setWeight(1);
		}
		for(Event e: backgroundData.getEvents()){
			e.setWeight(1);
		}
		tree.train(signalData, backgroundData);
		int count = 0;
		for(Event e: testData.getEvents()){
			double purity = tree.runEvent(e);
			if (purity >= 0.5){
				System.out.println(count + "\t" + 1);
			}
			else{
				System.out.println(count + "\t" + 0);
			}
			//System.out.println(count + "\t" + e.toString() + "\t" + purity);
			count++;
		}
	}
	
	public static void level_2(){
		IOHelper<Data<Event>> myReader = new IOHelper<>();
		Data<Event> signalData = myReader.read("signalBig.dat");
		Data<Event> backgroundData = myReader.read("backgroundBig.dat");
		Data<Event> testData = myReader.read("decisionTreeData.dat");
		
		DecisionTree<Event> tree = new DecisionTree<Event>();
		for(Event e: signalData.getEvents()){
			e.setWeight(1);
		}
		for(Event e: backgroundData.getEvents()){
			e.setWeight(1);
		}
		tree.train(signalData, backgroundData);
		int count = 0;
		for(Event e: testData.getEvents()){
			double purity = tree.runEvent(e);
			if (purity >= 0.5){
				System.out.println(count + "\t" + 1 + "\t" + purity);
			}
			else{
				System.out.println(count + "\t" + 0 + "\t" + purity);
			}
			//System.out.println(count + "\t" + e.toString() + "\t" + purity);
			count++;
		}
	}
	
	public static void level_3(){
		final int numOfTrees = 10;
		final double betaConstant = 0.5;
		List<DecisionTree<Event>> forest = new ArrayList<DecisionTree<Event>>();
		
		IOHelper<Data<Event>> myReader = new IOHelper<>();
		Data<Event> signalData = myReader.read("signalBig.dat");
		Data<Event> backgroundData = myReader.read("backgroundBig.dat");
		Data<Event> testData = myReader.read("decisionTreeData.dat");
		
		for(Event e: signalData.getEvents()){
			e.setWeight(1);
		}
		for(Event e: backgroundData.getEvents()){
			e.setWeight(1);
		}
		
		//creates a forest of 10 trees
		for (int i = 0; i < numOfTrees; i++){
			DecisionTree<Event> tree = new DecisionTree<Event>();
			tree.train(signalData, backgroundData);
			double wrongWeight = 0;
			double totalWeight = 0;
			//counts total weight and incorrect weight and sets correctlyCategorized boolean
			for(Event e: signalData.getEvents()){
				totalWeight += e.getWeight();
				if(tree.runEvent(e) >= 0.5){
					e.setCorrectlyCategorized(true);
				}
				else{
					wrongWeight += e.getWeight();
					e.setCorrectlyCategorized(false);
				}
			}
			for(Event e: backgroundData.getEvents()){
				totalWeight += e.getWeight();
				if(tree.runEvent(e) < 0.5){
					e.setCorrectlyCategorized(true);
				}
				else{
					wrongWeight += e.getWeight();
					e.setCorrectlyCategorized(false);
				}
			}
			//calculates error and score for each decision tree
			double error = wrongWeight/totalWeight;
			double score = betaConstant * Math.log((1-error)/error);
			tree.setScore(score);
			//calculates newTotalWeight
			
			for(Event e: signalData.getEvents()){
				if(e.getCorrectlyCategorized() == false){
					e.setWeight(e.getWeight() * Math.exp(score));
				}
				else{
					e.setWeight(1.0);
				}
			}
			for(Event e: backgroundData.getEvents()){
				if(e.getCorrectlyCategorized() == false){
					e.setWeight(e.getWeight() * Math.exp(score));
				}
				else{
					e.setWeight(1.0);
				}
			}
			forest.add(tree);
		}
		int count = 0;
		for(Event e: testData.getEvents()){
			//Averages purity over entire forest based upon tree score
			double division = 0;
			for (DecisionTree<Event> tree: forest){
				double purity = tree.runEvent(e);
				if (purity >= 0.5){
					division += tree.getScore();
				}
				else{
					division -= tree.getScore();
				}
			}
			if (division > 0){
				System.out.println(count + "\t" + 1 + "\t" + division);
			}
			else{
				System.out.println(count + "\t" + 0 + "\t" + division);
			}
			count++;
		}
	}
	
	public static void challenge(){
		IOHelper<Data<Event>> myReader = new IOHelper<>();
		Data<Event> signalData = myReader.read("signal.dat");
		Data<Event> backgroundData = myReader.read("background.dat");
		Data<Event> testData = myReader.read("decisionTreeData.dat");
		
		NeuralNode<Event> node = new NeuralNode<Event>(8);
		node.train(signalData, backgroundData);
		//int count = 1;
		//for (Event e: signalData.getEvents()){
			//System.out.println(count + "\t" + 1 + "\t" + node.runEvent(e));
			//count++;
		//}
		//count = 1;
		//for(Event e: backgroundData.getEvents()){
			//System.out.println(count + "\t" + 0 + "\t" + node.runEvent(e));
			//count++;
		//}
		int count = 0;
		for(Event e: testData.getEvents()){
			if(node.runEvent(e) > 0.5){
				System.out.println(count + "\t" + 1 + "\t" + node.runEvent(e));
			}
			else {
				System.out.println(count + "\t" + 0 + "\t" + node.runEvent(e));
			}
			count++;
		}
	}

}
