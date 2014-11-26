// File: Experiment.java
// Description:

import java.util.Arrays;

import absmodJ.ConfidenceInterval;
import simModel.*;
import cern.jet.random.engine.*;

// Main Method: Experiments
// 
class SMThemeParkExperi3 {
	public static double [] cfLevels = { 0.90, 0.99, 0.94 }; //TODO
	
	public static void main(String[] args) {
		double startTime = 0.0, endTime = 750.0;
		SMThemePark park; // Simulation object
		int NUMRUNS = 65; // TODO needs modifying?
		int[] boardingOptions = new int[] { Constants.SINGLE_SIDED,
				Constants.DOUBLE_SIDED, Constants.SINGLE_SIDED,
				Constants.DOUBLE_SIDED }; // boarding options in
		// 4 cases
		boolean[] fixBoardingTime = new boolean[] { true, true, false, false }; // fix_boarding_time
																				// options
																				// in
																				// 4
																				// cases

		// Lets get a set of uncorrelated seeds
		Seeds[] sds = new Seeds[NUMRUNS];
		RandomSeedGenerator rsg = new RandomSeedGenerator();
		for (int i = 0; i < NUMRUNS; i++) {
			sds[i] = new Seeds(rsg);
		}
		// The other are Ck's and one more must be calculated to ensure the overall CF

		double [] confLevels = computeLastCFLevel(cfLevels);
		
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
			int runNumber = 1;
			System.out
					.println("\n\n======================================================");
			System.out.println("=======================SEED #" + i
					+ "========================");
			System.out
					.println("======================================================");

			// For Each Case Scenario Within Cars Within Trains
			
			//CASE 1
			System.out.println("\n");
			// For Each Train Scenario Within Seeds
			traincarloop: for (int numTrains = Constants.MIN_NUMBER_OF_TRAINS; numTrains <= Constants.MAX_NUMBER_OF_TRAINS; numTrains++) {
				// For Each Car Scenario Within Train
				for (int numCars = Constants.MIN_NUMBER_OF_CARS * numTrains; numCars <= Constants.MAX_NUMBER_OF_CARS
						* numTrains; numCars++) {
					park = new SMThemePark(startTime, endTime, numTrains,
							numCars, boardingOptions[0],
							fixBoardingTime[0], seed, false);
					park.runSimulation();
					runNumber++;
					if (park.projectGoalReached()) {
						System.out.println("==========Case 1" 
								+ ", Seed " + i + ", Run Number " + runNumber
								+ "==========");
						System.out.println("Number of Cars: " + numCars
								+ " Number of Trains: " + numTrains);
						park.outputResults();
						valuesCase1ForType1[i] = park.output.getPerctOfType1Scen();
						valuesCase1ForType2[i] = park.output.getPerctOfType2Scen();
						valuesCase1ForType3[i] = park.output.getPerctOfType3Scen();
						valuesCase1ForType4[i] = park.output.getPerctOfType4Scen();
						break traincarloop;
					}
				}
			}
		
			//case 2
			System.out.println("\n");
			// For Each Train Scenario Within Seeds
			traincarloop: for (int numTrains = Constants.MIN_NUMBER_OF_TRAINS; numTrains <= Constants.MAX_NUMBER_OF_TRAINS; numTrains++) {
				// For Each Car Scenario Within Train
				for (int numCars = Constants.MIN_NUMBER_OF_CARS * numTrains; numCars <= Constants.MAX_NUMBER_OF_CARS
						* numTrains; numCars++) {
					park = new SMThemePark(startTime, endTime, numTrains,
							numCars, boardingOptions[1],
							fixBoardingTime[1], seed, false);
					park.runSimulation();
					runNumber++;
					if (park.projectGoalReached()) {
						System.out.println("==========Case 2" 
								+ ", Seed " + i + ", Run Number " + runNumber
								+ "==========");
						System.out.println("Number of Cars: " + numCars
								+ " Number of Trains: " + numTrains);
						park.outputResults();
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
			System.out.println("\n");
			// For Each Train Scenario Within Seeds
			traincarloop: for (int numTrains = Constants.MIN_NUMBER_OF_TRAINS; numTrains <= Constants.MAX_NUMBER_OF_TRAINS; numTrains++) {
				// For Each Car Scenario Within Train
				for (int numCars = Constants.MIN_NUMBER_OF_CARS * numTrains; numCars <= Constants.MAX_NUMBER_OF_CARS
						* numTrains; numCars++) {
					park = new SMThemePark(startTime, endTime, numTrains,
							numCars, boardingOptions[2],
							fixBoardingTime[2], seed, false);
					park.runSimulation();
					runNumber++;
					if (park.projectGoalReached()) {
						System.out.println("==========Case 3" 
								+ ", Seed " + i + ", Run Number " + runNumber
								+ "==========");
						System.out.println("Number of Cars: " + numCars
								+ " Number of Trains: " + numTrains);
						park.outputResults();
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
			System.out.println("\n");
			// For Each Train Scenario Within Seeds
			traincarloop: for (int numTrains = Constants.MIN_NUMBER_OF_TRAINS; numTrains <= Constants.MAX_NUMBER_OF_TRAINS; numTrains++) {
				// For Each Car Scenario Within Train
				for (int numCars = Constants.MIN_NUMBER_OF_CARS * numTrains; numCars <= Constants.MAX_NUMBER_OF_CARS
						* numTrains; numCars++) {
					park = new SMThemePark(startTime, endTime, numTrains,
							numCars, boardingOptions[3],
							fixBoardingTime[3], seed, false);
					park.runSimulation();
					runNumber++;
					if (park.projectGoalReached()) {
						System.out.println("==========Case 4" 
								+ ", Seed " + i + ", Run Number " + runNumber
								+ "==========");
						System.out.println("Number of Cars: " + numCars
								+ " Number of Trains: " + numTrains);
						park.outputResults();
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
		
		 // Get the conficence intervals
	       ConfidenceInterval cfDiff21ForType1 = new ConfidenceInterval(valuesDiff21ForType1, confLevels[1]);
	       ConfidenceInterval cfDiff21ForType2 = new ConfidenceInterval(valuesDiff21ForType2, confLevels[1]);
	       ConfidenceInterval cfDiff21ForType3 = new ConfidenceInterval(valuesDiff21ForType3, confLevels[1]);
	       ConfidenceInterval cfDiff21ForType4 = new ConfidenceInterval(valuesDiff21ForType4, confLevels[1]);
	       
	       ConfidenceInterval cfDiff31ForType1 = new ConfidenceInterval(valuesDiff31ForType1, confLevels[2]);
	       ConfidenceInterval cfDiff31ForType2 = new ConfidenceInterval(valuesDiff31ForType2, confLevels[2]);
	       ConfidenceInterval cfDiff31ForType3 = new ConfidenceInterval(valuesDiff31ForType3, confLevels[2]);
	       ConfidenceInterval cfDiff31ForType4 = new ConfidenceInterval(valuesDiff31ForType4, confLevels[2]);	       
	       
	       ConfidenceInterval cfDiff41ForType1 = new ConfidenceInterval(valuesDiff41ForType1, confLevels[3]);
	       ConfidenceInterval cfDiff41ForType2 = new ConfidenceInterval(valuesDiff41ForType2, confLevels[3]);
	       ConfidenceInterval cfDiff41ForType3 = new ConfidenceInterval(valuesDiff41ForType3, confLevels[3]);
	       ConfidenceInterval cfDiff41ForType4 = new ConfidenceInterval(valuesDiff41ForType4, confLevels[3]);
	       
	       // Create the table
	       System.out.printf("-------------------------------------------------------------------------------------------\n");
	       System.out.printf("      Comparison    Point estimate(ybar(n))  s(n)     zeta   CI Min   CI Max |zeta/ybar(n)|\n");
	       System.out.printf("-------------------------------------------------------------------------------------------\n");
	      //TYPE1
	       System.out.printf("cfDiff21ForType1 %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n",
	    		   cfDiff21ForType1.getPointEstimate(), cfDiff21ForType1.getVariance(), cfDiff21ForType1.getZeta(), 
	    		   cfDiff21ForType1.getCfMin(), cfDiff21ForType1.getCfMax(),
	    	         Math.abs(cfDiff21ForType1.getZeta()/cfDiff21ForType1.getPointEstimate()));
	       System.out.printf("cfDiff31ForType1 %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
	    		   cfDiff31ForType1.getPointEstimate(), cfDiff31ForType1.getVariance(), cfDiff31ForType1.getZeta(), 
	    		   cfDiff31ForType1.getCfMin(), cfDiff31ForType1.getCfMax(),
		             Math.abs(cfDiff31ForType1.getZeta()/cfDiff31ForType1.getPointEstimate()));
	       System.out.printf("cfDiff41ForType1 %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
	    		   cfDiff41ForType1.getPointEstimate(), cfDiff41ForType1.getVariance(), cfDiff41ForType1.getZeta(), 
	    		   cfDiff41ForType1.getCfMin(), cfDiff41ForType1.getCfMax(),
		             Math.abs(cfDiff41ForType1.getZeta()/cfDiff41ForType1.getPointEstimate()));
	       
	       //TYPE2
	       System.out.printf("cfDiff21ForType2 %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n",
	    		   cfDiff21ForType2.getPointEstimate(), cfDiff21ForType2.getVariance(), cfDiff21ForType2.getZeta(), 
	    		   cfDiff21ForType2.getCfMin(), cfDiff21ForType2.getCfMax(),
	    	         Math.abs(cfDiff21ForType2.getZeta()/cfDiff21ForType2.getPointEstimate()));
	       System.out.printf("cfDiff31ForType2 %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
	    		   cfDiff31ForType2.getPointEstimate(), cfDiff31ForType2.getVariance(), cfDiff31ForType2.getZeta(), 
	    		   cfDiff31ForType2.getCfMin(), cfDiff31ForType2.getCfMax(),
		             Math.abs(cfDiff31ForType2.getZeta()/cfDiff31ForType2.getPointEstimate()));
	       System.out.printf("cfDiff41ForType2 %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
	    		   cfDiff41ForType2.getPointEstimate(), cfDiff41ForType2.getVariance(), cfDiff41ForType2.getZeta(), 
	    		   cfDiff41ForType2.getCfMin(), cfDiff41ForType2.getCfMax(),
		             Math.abs(cfDiff41ForType2.getZeta()/cfDiff41ForType2.getPointEstimate()));
	       
	       //TYPE3
	       System.out.printf("cfDiff21ForType3 %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n",
	    		   cfDiff21ForType3.getPointEstimate(), cfDiff21ForType3.getVariance(), cfDiff21ForType3.getZeta(), 
	    		   cfDiff21ForType3.getCfMin(), cfDiff21ForType3.getCfMax(),
	    	         Math.abs(cfDiff21ForType3.getZeta()/cfDiff21ForType3.getPointEstimate()));
	       System.out.printf("cfDiff31ForType3 %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
	    		   cfDiff31ForType3.getPointEstimate(), cfDiff31ForType3.getVariance(), cfDiff31ForType3.getZeta(), 
	    		   cfDiff31ForType3.getCfMin(), cfDiff31ForType3.getCfMax(),
		             Math.abs(cfDiff31ForType3.getZeta()/cfDiff31ForType3.getPointEstimate()));
	       System.out.printf("cfDiff41ForType3 %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
	    		   cfDiff41ForType3.getPointEstimate(), cfDiff41ForType3.getVariance(), cfDiff41ForType3.getZeta(), 
	    		   cfDiff41ForType3.getCfMin(), cfDiff41ForType3.getCfMax(),
		             Math.abs(cfDiff41ForType3.getZeta()/cfDiff41ForType3.getPointEstimate()));
	       
	       //TYPE4
	       System.out.printf("cfDiff21ForType4 %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n",
	    		   cfDiff21ForType4.getPointEstimate(), cfDiff21ForType4.getVariance(), cfDiff21ForType4.getZeta(), 
	    		   cfDiff21ForType4.getCfMin(), cfDiff21ForType4.getCfMax(),
	    	         Math.abs(cfDiff21ForType4.getZeta()/cfDiff21ForType4.getPointEstimate()));
	       System.out.printf("cfDiff31ForType4 %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
	    		   cfDiff31ForType4.getPointEstimate(), cfDiff31ForType4.getVariance(), cfDiff31ForType4.getZeta(), 
	    		   cfDiff31ForType4.getCfMin(), cfDiff31ForType4.getCfMax(),
		             Math.abs(cfDiff31ForType4.getZeta()/cfDiff31ForType4.getPointEstimate()));
	       System.out.printf("cfDiff41ForType4 %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
	    		   cfDiff41ForType4.getPointEstimate(), cfDiff41ForType4.getVariance(), cfDiff41ForType4.getZeta(), 
	    		   cfDiff41ForType4.getCfMin(), cfDiff41ForType4.getCfMax(),
		             Math.abs(cfDiff41ForType4.getZeta()/cfDiff41ForType4.getPointEstimate()));
	       System.out.printf("-------------------------------------------------------------------------------------------\n");

	}
	   /*
	    * The last level must satisfy the following equation
	    *     C = (1-K) + sum_k_1_K(Ck)     (sum_k_1_K is sum over terms for k = 1 to K)
	    *     where C = levels[0], 
	    *           Ck are the values of levels[i] for i = 1 to levels.length-1
	    *           K = levels.length  (note that we add one element, i.e. K = newLevels.length-1)
	    *     Can use alternative:  C = 1 - sum_k_1_K(alpha_k)  where alpha_k = 1 - Ck
	    *     Thus C = 1 - sum_k_1_K-1(alpha_k) - alpha_K, and thus
	    *          alpha_K = 1 - C - sum_k_1_K-1(alpha_k)   (note that alpha_k for k = 1 to K-1 are known)
	    *          and CK = 1 - alpha_K
	    */
	   public static double [] computeLastCFLevel(double [] levels)
	   {
		   double [] newLevels = new double[levels.length+1];
		   double alphaK = 1.0 - levels[0];   // Assign C - 1 to alpha K
		   newLevels[0] = levels[0];  // Save C in new array
		   // Sum 1 - Ck to alphaK for k = 1 to K - 1
		   // At the sam time save values in new array
		   for(int k = 1 ; k<levels.length; k++)
		   {
			   alphaK = alphaK - (1 - levels[k]);
			   newLevels[k] = levels[k];
		   }
		   newLevels[newLevels.length - 1] = 1.0 - alphaK;
		   // Double check the values
		   double sum = 0.0;
		   for(int k = 1 ; k < newLevels.length ; k++) sum = sum +(1 - newLevels[k]);
		   if(Math.abs(newLevels[0] - (1.0 - sum)) > 0.001) 
		   {
			   System.out.println("Last value invalid in Confidence Levels: "+Arrays.toString(newLevels));
			   newLevels = null;  // return null value to flag error.
		   }
		   return(newLevels);		   
	   }
}
