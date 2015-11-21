/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particlePhysicsFaceOff;

import java.util.*;

/**
 * Creates Particles 
 * @author Emily Mu
 */
public class FreindsGenerator extends EventGenerator{
	
	 private double magnitudeMean = 0;
	 private double energyMean = 0;
	 private double magnitudeStdDev = 1;
	 private double energyStdDev = 1;
	 
	 private double thetaSignalMean = 1.0;
	 private double thetaSignalStdDev = 0.2;
	 private double thetaBackgroundMean = 2.0;
	 private double thetaBackgroundStdDev = 0.2;
	 
	@Override
    protected List<Particle> makeParticles(boolean signalEvent) {
        List<Particle> particles = new ArrayList<Particle>();
        int size = getRandom().nextInt(7) + 3;
        for(int i = 0; i < size; i++) {
        	double magnitudeMomentum = 0;
    		while(magnitudeMomentum <= 0){
    			magnitudeMomentum = getRandom().nextGaussian()*magnitudeStdDev + magnitudeMean; //change parameters
    		}
            double energy = 0;
    		while(energy < magnitudeMomentum){
    			energy = getRandom().nextGaussian()*energyStdDev + energyMean; //change parameters
    		}
    		double phi = getRandom().nextDouble() * 2 * Math.PI;
    		double theta = 0;
    		Vector3 momentum;
    		if(i == 0){
    			theta = getRandom().nextDouble() * Math.PI;
    			momentum = Vector3.sphericalVector(magnitudeMomentum, theta, phi);
    		}
    		else{
    			Vector3 rotation = Vector3.subtract(new Vector3(0, 0, 1), Vector3.unitVector(particles.get(0).getMomentum()));
    			if(signalEvent){
    				while(theta <= 0 || theta >= Math.PI){
    					theta = getRandom().nextGaussian()*thetaSignalStdDev + thetaSignalMean;
    				}
    			}
    			else{
    				while(theta <= 0 || theta >= Math.PI){
    					theta = getRandom().nextGaussian()*thetaBackgroundStdDev + thetaBackgroundMean;
    				}
    			}
    			
    			momentum = Vector3.subtract(Vector3.sphericalVector(1, phi, theta), rotation);
    			
    			//momentum = Vector3.sphericalVector(magnitudeMomentum, phi, theta);
    			
    			momentum = Vector3.scale(momentum, magnitudeMomentum);
    		}
            particles.add(new Particle(energy, momentum));
        }
        return particles;
    }

    @Override
    public boolean discriminateEvent(List<Particle> event) {
    	Vector3 axis = event.get(0).getMomentum();
    	double angleSum = 0;
    	for(Particle p: event){
    		//angleSum += Vector3.getTheta(p.getMomentum());
    		angleSum += Vector3.getAngle(p.getMomentum(), axis);
    		System.out.println(Vector3.getAngle(p.getMomentum(), axis));
    	}
    	double mean = angleSum/(event.size()-1);
    	System.out.println("mean" + "\t" + mean);
    	if(Math.abs(mean - thetaSignalMean) <= Math.abs(mean - thetaBackgroundMean)){
    		return true;
    	}
    	else{
    		return false;
    	}
    }

    @Override
    protected double signalFraction() {
        return 0.1823; //To change body of generated methods, choose Tools | Templates.
    }
    
}
