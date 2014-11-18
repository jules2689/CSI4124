// File: Experiment.java
// Description:

import simModel.*;
import cern.jet.random.engine.*;

// Main Method: Experiments
// 
class SMThemeParkExperi1 {
	public static void main(String[] args) {
		int i = 4; // total 4 cases
		double startTime = 0.0, endTime = 750.0;
		SMThemePark park; // Simulation object
		int initialNumTrains = 4;
		int initialNumCars = 16;

		int[] boardingOptions = new int[] { 0, 1, 0, 1 }; // boarding options in
															// 4 cases
		// fix_boarding_time options in 4 cases
		boolean[] fixBoardingTime = new boolean[] { true, true, false, false }; 
		int NUMRUNS = fixBoardingTime.length; // TODO needs modifying?

		Seeds[] sds = new Seeds[NUMRUNS];
		// Lets get a set of uncorrelated seeds
		RandomSeedGenerator rsg = new RandomSeedGenerator();
		for (i = 0; i < NUMRUNS; i++) {
			sds[i] = new Seeds(rsg);
		}

		for (i = 0; i < NUMRUNS; i++) {
			System.out.println("==========Case " + i + "==========");
			System.out.println("Number of Cars: " + initialNumCars + " Number of Trains: " + initialNumTrains);
			park = new SMThemePark(startTime, endTime, initialNumTrains, initialNumCars, boardingOptions[i], fixBoardingTime[i], sds[i], false);
			park.runSimulation();
			park.outputResults();
		}
	}
}
