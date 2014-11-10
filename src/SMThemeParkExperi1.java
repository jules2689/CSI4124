// File: Experiment.java
// Description:

import simModel.SMThemePark;
import simModel.Seeds;
import cern.jet.random.engine.RandomSeedGenerator;

// Main Method: Experiments
// 
class SMThemeParkExperi1 {
	public static void main(String[] args) {
		int i, NUMRUNS = 4; // total 4 cases //TODO modify NUMBER = 4
		double startTime = 0.0, endTime = 750.0;
		Seeds[] sds = new Seeds[NUMRUNS];
		int[] boardingOptions = new int[] { 0, 1, 0, 1 }; // boarding options in
															// 4 cases
		boolean[] fixBoardingTime = new boolean[] { true, true, false, false }; // fix_boarding_time
																				// options
																				// in
																				// 4
																				// cases
		SMThemePark park; // Simulation object
		int initialNumTrains = 4;
		int initialNumCars = 16;

		// Lets get a set of uncorrelated seeds
		RandomSeedGenerator rsg = new RandomSeedGenerator();
		for (i = 0; i < NUMRUNS; i++)
			sds[i] = new Seeds(rsg);

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
			// and developping code for analysis

		}
	}
}
