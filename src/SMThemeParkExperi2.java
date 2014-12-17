// File: Experiment.java
// Description:

import absmodJ.ConfidenceInterval;
import simModel.*;
import cern.jet.random.engine.*;

// Main Method: Experiments
// 
class SMThemeParkExperi2 {
	public static final double CONF_LEVEL = 0.968; //TODO
	
	public static void main(String[] args) {
		double startTime = 0.0, endTime = 750.0;
		SMThemePark park; // Simulation object
		int NUMRUNS = 30;
		int[] boardingOptions = new int[] { Constants.SINGLE_SIDED, Constants.DOUBLE_SIDED, Constants.SINGLE_SIDED, Constants.DOUBLE_SIDED }; // boarding options in 4 cases
		boolean[] fixBoardingTime = new boolean[] { true, true, false, false }; // fix_boarding_time options in 4 cases

		// Lets get a set of uncorrelated seeds
		Seeds[] sds = new Seeds[NUMRUNS];
		RandomSeedGenerator rsg = new RandomSeedGenerator();
		for (int i = 0; i < NUMRUNS; i++) {
			sds[i] = new Seeds(rsg);
		}
		
		// The other are Ck's and one more must be calculated to ensure the overall CF
	    double [] valuesCase1ForType1 = new double[NUMRUNS];
	    double [] valuesCase1ForType2 = new double[NUMRUNS];
	    double [] valuesCase1ForType3 = new double[NUMRUNS];
	    double [] valuesCase1ForType4 = new double[NUMRUNS];      
	    
	    double [] valuesCase2ForType1 = new double[NUMRUNS];
	    double [] valuesCase2ForType2 = new double[NUMRUNS];
	    double [] valuesCase2ForType3 = new double[NUMRUNS];
	    double [] valuesCase2ForType4 = new double[NUMRUNS];    

	    double [] valuesCase3ForType1 = new double[NUMRUNS];
	    double [] valuesCase3ForType2 = new double[NUMRUNS];
	    double [] valuesCase3ForType3 = new double[NUMRUNS];
	    double [] valuesCase3ForType4 = new double[NUMRUNS];       

	    double [] valuesCase4ForType1 = new double[NUMRUNS];
	    double [] valuesCase4ForType2 = new double[NUMRUNS];
	    double [] valuesCase4ForType3 = new double[NUMRUNS];
	    double [] valuesCase4ForType4 = new double[NUMRUNS];
	     
	    double [] valuesDiff21ForType1 = new double[NUMRUNS];
	    double [] valuesDiff21ForType2 = new double[NUMRUNS];
	    double [] valuesDiff21ForType3 = new double[NUMRUNS];
	    double [] valuesDiff21ForType4 = new double[NUMRUNS];
	    double [] valuesDiff31ForType1 = new double[NUMRUNS];
	    double [] valuesDiff31ForType2 = new double[NUMRUNS];
	    double [] valuesDiff31ForType3 = new double[NUMRUNS];
	    double [] valuesDiff31ForType4 = new double[NUMRUNS];
	    double [] valuesDiff41ForType1 = new double[NUMRUNS];
	    double [] valuesDiff41ForType2 = new double[NUMRUNS];
	    double [] valuesDiff41ForType3 = new double[NUMRUNS];
	    double [] valuesDiff41ForType4 = new double[NUMRUNS];
	    
	    
		// Number of Seeds
		for (int i = 0; i < NUMRUNS; i++) {
			Seeds seed = sds[i];
			// For Each Case Scenario Within Cars Within Trains
			
			//case 1
			// For Each Train Scenario Within Seeds
			traincarloop: for (int numTrains = Constants.MIN_NUMBER_OF_TRAINS; numTrains <= Constants.MAX_NUMBER_OF_TRAINS; numTrains++) {
				// For Each Car Scenario Within Train
				for (int numCars = Constants.MIN_NUMBER_OF_CARS * numTrains; numCars <= Constants.MAX_NUMBER_OF_CARS * numTrains; numCars++) {
					park = new SMThemePark(startTime, endTime, numTrains, numCars, boardingOptions[0], fixBoardingTime[0], seed, false);
					park.runSimulation();
					if (park.projectGoalReached()) {
						valuesCase1ForType1[i] = park.output.getPerctOfType1Scen();
						valuesCase1ForType2[i] = park.output.getPerctOfType2Scen();
						valuesCase1ForType3[i] = park.output.getPerctOfType3Scen();
						valuesCase1ForType4[i] = park.output.getPerctOfType4Scen();
						break traincarloop;
					}
				}
			}
		
			//case 2
			// For Each Train Scenario Within Seeds
			traincarloop: for (int numTrains = Constants.MIN_NUMBER_OF_TRAINS; numTrains <= Constants.MAX_NUMBER_OF_TRAINS; numTrains++) {
				// For Each Car Scenario Within Train
				for (int numCars = Constants.MIN_NUMBER_OF_CARS * numTrains; numCars <= Constants.MAX_NUMBER_OF_CARS * numTrains; numCars++) {
					park = new SMThemePark(startTime, endTime, numTrains, numCars, boardingOptions[1], fixBoardingTime[1], seed, false);
					park.runSimulation();
					if (park.projectGoalReached()) {
						valuesCase2ForType1[i] = park.output.getPerctOfType1Scen();
						valuesCase2ForType2[i] = park.output.getPerctOfType2Scen();
						valuesCase2ForType3[i] = park.output.getPerctOfType3Scen();
						valuesCase2ForType4[i] = park.output.getPerctOfType4Scen();
						
						valuesDiff21ForType1[i] = valuesCase2ForType1[i] - valuesCase1ForType1[i];// Difference with Case 1
						valuesDiff21ForType2[i] = valuesCase2ForType2[i] - valuesCase1ForType2[i];
						valuesDiff21ForType3[i] = valuesCase2ForType3[i] - valuesCase1ForType3[i];
						valuesDiff21ForType4[i] = valuesCase2ForType4[i] - valuesCase1ForType4[i];
						break traincarloop;
					}
				}
			}
		
			
			//case 3
			// For Each Train Scenario Within Seeds
			traincarloop: for (int numTrains = Constants.MIN_NUMBER_OF_TRAINS; numTrains <= Constants.MAX_NUMBER_OF_TRAINS; numTrains++) {
				// For Each Car Scenario Within Train
				for (int numCars = Constants.MIN_NUMBER_OF_CARS * numTrains; numCars <= Constants.MAX_NUMBER_OF_CARS * numTrains; numCars++) {
					park = new SMThemePark(startTime, endTime, numTrains, numCars, boardingOptions[2], fixBoardingTime[2], seed, false);
					park.runSimulation();
					if (park.projectGoalReached()) {
						valuesCase3ForType1[i] = park.output.getPerctOfType1Scen();
						valuesCase3ForType2[i] = park.output.getPerctOfType2Scen();
						valuesCase3ForType3[i] = park.output.getPerctOfType3Scen();
						valuesCase3ForType4[i] = park.output.getPerctOfType4Scen();
						
						valuesDiff31ForType1[i] = valuesCase3ForType1[i] - valuesCase1ForType1[i];// Difference with Case 1
						valuesDiff31ForType2[i] = valuesCase3ForType2[i] - valuesCase1ForType2[i];
						valuesDiff31ForType3[i] = valuesCase3ForType3[i] - valuesCase1ForType3[i];
						valuesDiff31ForType4[i] = valuesCase3ForType4[i] - valuesCase1ForType4[i];
						break traincarloop;
					}
				}
			}
		
			//case 4
			// For Each Train Scenario Within Seeds
			traincarloop: for (int numTrains = Constants.MIN_NUMBER_OF_TRAINS; numTrains <= Constants.MAX_NUMBER_OF_TRAINS; numTrains++) {
				// For Each Car Scenario Within Train
				for (int numCars = Constants.MIN_NUMBER_OF_CARS * numTrains; numCars <= Constants.MAX_NUMBER_OF_CARS * numTrains; numCars++) {
					park = new SMThemePark(startTime, endTime, numTrains, numCars, boardingOptions[3], fixBoardingTime[3], seed, false);
					park.runSimulation();
					if (park.projectGoalReached()) {
						valuesCase4ForType1[i] = park.output.getPerctOfType1Scen();
						valuesCase4ForType2[i] = park.output.getPerctOfType2Scen();
						valuesCase4ForType3[i] = park.output.getPerctOfType3Scen();
						valuesCase4ForType4[i] = park.output.getPerctOfType4Scen();
						
						valuesDiff41ForType1[i] = valuesCase4ForType1[i] - valuesCase1ForType1[i];// Difference with Case 1
						valuesDiff41ForType2[i] = valuesCase4ForType2[i] - valuesCase1ForType2[i];
						valuesDiff41ForType3[i] = valuesCase4ForType3[i] - valuesCase1ForType3[i];
						valuesDiff41ForType4[i] = valuesCase4ForType4[i] - valuesCase1ForType4[i];
						break traincarloop;
					}
				}
			}
		}
		
		 // Get the confidence intervals
	     ConfidenceInterval cfDiff21ForType1 = new ConfidenceInterval(valuesDiff21ForType1, CONF_LEVEL);
	     ConfidenceInterval cfDiff21ForType2 = new ConfidenceInterval(valuesDiff21ForType2, CONF_LEVEL);
	     ConfidenceInterval cfDiff21ForType3 = new ConfidenceInterval(valuesDiff21ForType3, CONF_LEVEL);
	     ConfidenceInterval cfDiff21ForType4 = new ConfidenceInterval(valuesDiff21ForType4, CONF_LEVEL);
	       
	     ConfidenceInterval cfDiff31ForType1 = new ConfidenceInterval(valuesDiff31ForType1, CONF_LEVEL);
	     ConfidenceInterval cfDiff31ForType2 = new ConfidenceInterval(valuesDiff31ForType2, CONF_LEVEL);
	     ConfidenceInterval cfDiff31ForType3 = new ConfidenceInterval(valuesDiff31ForType3, CONF_LEVEL);
	     ConfidenceInterval cfDiff31ForType4 = new ConfidenceInterval(valuesDiff31ForType4, CONF_LEVEL);	       
	       
	     ConfidenceInterval cfDiff41ForType1 = new ConfidenceInterval(valuesDiff41ForType1, CONF_LEVEL);
	     ConfidenceInterval cfDiff41ForType2 = new ConfidenceInterval(valuesDiff41ForType2, CONF_LEVEL);
	     ConfidenceInterval cfDiff41ForType3 = new ConfidenceInterval(valuesDiff41ForType3, CONF_LEVEL);
	     ConfidenceInterval cfDiff41ForType4 = new ConfidenceInterval(valuesDiff41ForType4, CONF_LEVEL);
	       
	     TableHelper.printTable(cfDiff21ForType1, cfDiff31ForType1, cfDiff41ForType1, cfDiff21ForType2, cfDiff31ForType2, cfDiff41ForType2, cfDiff21ForType3, cfDiff31ForType3, cfDiff41ForType3, cfDiff21ForType4, cfDiff31ForType4, cfDiff41ForType4);
	}
}
