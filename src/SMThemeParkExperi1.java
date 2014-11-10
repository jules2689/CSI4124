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
		
		int[] boardingOptions = new int[] { 0, 1, 0, 1 }; // boarding options in 4 cases
		boolean[] fixBoardingTime = new boolean[] { true, true, false, false }; // fix_boarding_time options in 4 cases
		int NUMRUNS = fixBoardingTime.length; //TODO needs modifying?

		Seeds[] sds = new Seeds[NUMRUNS];
		// Lets get a set of uncorrelated seeds
		RandomSeedGenerator rsg = new RandomSeedGenerator();
		for (i = 0; i < NUMRUNS; i++) {
			sds[i] = new Seeds(rsg);
		}

		// Loop for NUMRUN simulation runs for each case
		// Case i
		for (i = 0; i < NUMRUNS; i++) {
			System.out.println("==========Case " + i + "==========");
			park = new SMThemePark(startTime, endTime, initialNumTrains,
									initialNumCars, boardingOptions[i], fixBoardingTime[i],
									sds[i]);
			park.runSimulation();
			// System.out.println("==main:check:====");
			while (park.checkContinue()) {
				park.resetWithIncre();
				// initialNumCars ++ ;
				// if(initialNumCars % initialNumTrains == 0){
				// initialNumTrains ++ ;
				// }
				// System.out.println("**********initialNumCars:" +
				// initialNumCars +
				// "    initialNumTrains:" + initialNumTrains);//TODO delete
				// park = new
				// SMThemePark(startTime,endTime,initialNumTrains,initialNumCars,
				// boardingOptions[i],fixBoardingTime[i],sds[i]);
				// park.runSimulation();
			}
			// See examples for hints on collecting output
			// and developing code for analysis

		}
	}
}