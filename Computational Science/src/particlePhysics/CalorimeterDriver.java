package particlePhysics;

import java.util.*;

import java.io.*;
import java.awt.*;
import java.util.List;

public class CalorimeterDriver {
	
	public static void main(String[] args) throws FileNotFoundException{
		//level_1();
		//level_2();
		level_3();
		//challenge();
	}
	
	public static void level_1() throws FileNotFoundException{
		PrintStream file = new PrintStream(new FileOutputStream("calorimetry.xls"));
		ClusterVisualizer viz = ClusterVisualizer.createVisualizer();
		IOHelper<CalorimeterData> myReader = new IOHelper<>();
		CalorimeterData data = myReader.read("calorimeterData.dat");
		data.getEvents();
		for(CalorimeterEvent e: data.getEvents()){
			e.getMaxTower();
			System.out.println(e.toString());
		}
	}
	
	public static void level_2() throws FileNotFoundException{
		PrintStream file = new PrintStream(new FileOutputStream("calorimetry.xls"));
		ClusterVisualizer viz = ClusterVisualizer.createVisualizer();
		IOHelper<CalorimeterData> myReader = new IOHelper<>();
		CalorimeterData data = myReader.read("calorimeterData.dat");
		data.getEvents();
		for(CalorimeterEvent e: data.getEvents()){
			e.getMaxTower();
			e.getWidth();
			System.out.println(e.toString());
		}
	}
	
	public static void level_3() throws FileNotFoundException{
		//PrintStream file = new PrintStream(new FileOutputStream("calorimetry.xls"));
		//List<Tower> seedTowers = new ArrayList<Tower>();
		//ClusterVisualizer viz = ClusterVisualizer.createVisualizer();
		IOHelper<CalorimeterData> myReader = new IOHelper<>();
		//CalorimeterData data = myReader.read("calorimeterDataLevel3.dat");
		CalorimeterData data = myReader.read("forFriendsBackgroundSample.dat");
		data.getEvents();
		int count = 1;
		for(CalorimeterEvent e: data.getEvents()){
			System.out.println(count);
			e.getMaxima();
			e.printWidthMapToConsole();
			//for (String s: e.printWidthMap()){
				//file.println(s);
			//}
			count++;
			}
		}
	
	public static void challenge() throws FileNotFoundException{
		PrintStream file = new PrintStream(new FileOutputStream("calorimetry.xls"));
		List<Tower> seedTowers = new ArrayList<Tower>();
		ClusterVisualizer viz = ClusterVisualizer.createVisualizer();
		IOHelper<CalorimeterData> myReader = new IOHelper<>();
		CalorimeterData data = myReader.read("calorimeterDataChallenge.dat");
		data.getEvents();
		int count = 1;
		for(CalorimeterEvent e: data.getEvents()){
			System.out.println(count);
			e.extendsTowers();
			e.getMaxima();
			e.printWidthMapToConsole();
			//for (String s: e.printWidthMap()){
				//file.println(s);
			//}
			count++;
			}
	}
	}
	

