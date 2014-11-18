// File: Experiment.java
// Description:

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

		// Loop for NUMRUN simulation runs for each case
		// Case i
		SMThemePark optimalPark = null;

		// each case once or twice
		// most of this is for next week

		for (i = 0; i < NUMRUNS; i++) {
			System.out.println("==========Case " + i + "==========");
			park = new SMThemePark(startTime, endTime, initialNumTrains,
					initialNumCars, boardingOptions[i], fixBoardingTime[i],
					sds[i], false);
			park.runSimulation();
		}
	}
}
