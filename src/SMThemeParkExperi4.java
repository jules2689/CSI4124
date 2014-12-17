// File: Experiment.java
// Description:

import absmodJ.ConfidenceInterval;
import simModel.*;
import cern.jet.random.engine.*;

// Main Method: Experiments
// 
class SMThemeParkExperi4 {
	public static final double CONF_LEVEL = 0.99; //TODO
	
	public static void main(String[] args) {
		double startTime = 0.0, endTime = 750.0;
		SMThemePark park; // Simulation object
		int NUMRUNS = 50; // TODO needs modifying?
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
	    
	    double [] valuesCase1ForCost = new double[NUMRUNS];
	    double [] valuesCase2ForCost = new double[NUMRUNS];
	    double [] valuesCase3ForCost = new double[NUMRUNS];
	    double [] valuesCase4ForCost = new double[NUMRUNS];     
	     
	    double [] valuesDiff21ForType1 = new double[NUMRUNS];
	    double [] valuesDiff21ForType2 = new double[NUMRUNS];
	    double [] valuesDiff21ForType3 = new double[NUMRUNS];
	    double [] valuesDiff21ForType4 = new double[NUMRUNS];
	    double [] valuesDiff21ForCost = new double[NUMRUNS];
	    
	    double [] valuesDiff31ForType1 = new double[NUMRUNS];
	    double [] valuesDiff31ForType2 = new double[NUMRUNS];
	    double [] valuesDiff31ForType3 = new double[NUMRUNS];
	    double [] valuesDiff31ForType4 = new double[NUMRUNS];
	    double [] valuesDiff31ForCost = new double[NUMRUNS];
	    double [] valuesDiff41ForType1 = new double[NUMRUNS];
	    double [] valuesDiff41ForType2 = new double[NUMRUNS];
	    double [] valuesDiff41ForType3 = new double[NUMRUNS];
	    double [] valuesDiff41ForType4 = new double[NUMRUNS];
	    double [] valuesDiff41ForCost = new double[NUMRUNS];
	    
	    
	    //scen
	    double [] valuesCase1ForType1Scen = new double[NUMRUNS];
	    double [] valuesCase1ForType2Scen = new double[NUMRUNS];
	    double [] valuesCase1ForType3Scen = new double[NUMRUNS];
	    double [] valuesCase1ForType4Scen = new double[NUMRUNS];      
	    
	    double [] valuesCase2ForType1Scen = new double[NUMRUNS];
	    double [] valuesCase2ForType2Scen = new double[NUMRUNS];
	    double [] valuesCase2ForType3Scen = new double[NUMRUNS];
	    double [] valuesCase2ForType4Scen = new double[NUMRUNS];    

	    double [] valuesCase3ForType1Scen = new double[NUMRUNS];
	    double [] valuesCase3ForType2Scen = new double[NUMRUNS];
	    double [] valuesCase3ForType3Scen = new double[NUMRUNS];
	    double [] valuesCase3ForType4Scen = new double[NUMRUNS];       

	    double [] valuesCase4ForType1Scen = new double[NUMRUNS];
	    double [] valuesCase4ForType2Scen = new double[NUMRUNS];
	    double [] valuesCase4ForType3Scen = new double[NUMRUNS];
	    double [] valuesCase4ForType4Scen = new double[NUMRUNS];
	     
	    double [] valuesDiff21ForType1Scen = new double[NUMRUNS];
	    double [] valuesDiff21ForType2Scen = new double[NUMRUNS];
	    double [] valuesDiff21ForType3Scen = new double[NUMRUNS];
	    double [] valuesDiff21ForType4Scen = new double[NUMRUNS];
	    double [] valuesDiff31ForType1Scen = new double[NUMRUNS];
	    double [] valuesDiff31ForType2Scen = new double[NUMRUNS];
	    double [] valuesDiff31ForType3Scen = new double[NUMRUNS];
	    double [] valuesDiff31ForType4Scen = new double[NUMRUNS];
	    double [] valuesDiff41ForType1Scen = new double[NUMRUNS];
	    double [] valuesDiff41ForType2Scen = new double[NUMRUNS];
	    double [] valuesDiff41ForType3Scen = new double[NUMRUNS];
	    double [] valuesDiff41ForType4Scen = new double[NUMRUNS];
	    
		// Number of Seeds
		for (int i = 0; i < NUMRUNS; i++) {
			Seeds seed = sds[i];
			int runNumber = 1;
//			System.out
//					.println("\n\n======================================================");
//			System.out.println("=======================SEED #" + i
//					+ "========================");
//			System.out
//					.println("======================================================");

			// For Each Case Scenario Within Cars Within Trains
			
			//CASE 1
//			System.out.println("\n");
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
//						System.out.println("==========Case 1" 
//								+ ", Seed " + i + ", Run Number " + runNumber
//								+ "==========");
//						System.out.println("Number of Cars: " + numCars
//								+ " Number of Trains: " + numTrains);
//						park.outputResults();
						valuesCase1ForType1[i] = park.output.getPerctOfType1Scen();
						valuesCase1ForType2[i] = park.output.getPerctOfType2Scen();
						valuesCase1ForType3[i] = park.output.getPerctOfType3Scen();
						valuesCase1ForType4[i] = park.output.getPerctOfType4Scen();
						
						valuesCase1ForCost[i] = park.output.getCost();
						
						valuesCase1ForType1Scen[i] = park.output.getType1BoardingEvent();
						valuesCase1ForType2Scen[i] = park.output.getType2BoardingEvent();
						valuesCase1ForType3Scen[i] = park.output.getType3BoardingEvent();
						valuesCase1ForType4Scen[i] = park.output.getType4BoardingEvent();
						break traincarloop;
					}
				}
			}
		
			//case 2
//			System.out.println("\n");
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
//						System.out.println("==========Case 2" 
//								+ ", Seed " + i + ", Run Number " + runNumber
//								+ "==========");
//						System.out.println("Number of Cars: " + numCars
//								+ " Number of Trains: " + numTrains);
//						park.outputResults();
						valuesCase2ForType1[i] = park.output.getPerctOfType1Scen();
						valuesCase2ForType2[i] = park.output.getPerctOfType2Scen();
						valuesCase2ForType3[i] = park.output.getPerctOfType3Scen();
						valuesCase2ForType4[i] = park.output.getPerctOfType4Scen();
						
						valuesCase2ForCost[i] = park.output.getCost();
						
						valuesDiff21ForType1[i] = valuesCase2ForType1[i] - valuesCase1ForType1[i];// Difference with Case 1
						valuesDiff21ForType2[i] = valuesCase2ForType2[i] - valuesCase1ForType2[i];
						valuesDiff21ForType3[i] = valuesCase2ForType3[i] - valuesCase1ForType3[i];
						valuesDiff21ForType4[i] = valuesCase2ForType4[i] - valuesCase1ForType4[i];
						valuesDiff21ForCost[i] = valuesCase2ForCost[i] - valuesCase1ForCost[i];
						
						
						valuesCase2ForType1Scen[i] = park.output.getType1BoardingEvent();
						valuesCase2ForType2Scen[i] = park.output.getType2BoardingEvent();
						valuesCase2ForType3Scen[i] = park.output.getType3BoardingEvent();
						valuesCase2ForType4Scen[i] = park.output.getType4BoardingEvent();
						
						valuesDiff21ForType1Scen[i] = valuesCase2ForType1Scen[i] - valuesCase1ForType1Scen[i];// Difference with Case 1
						valuesDiff21ForType2Scen[i] = valuesCase2ForType2Scen[i] - valuesCase1ForType2Scen[i];
						valuesDiff21ForType3Scen[i] = valuesCase2ForType3Scen[i] - valuesCase1ForType3Scen[i];
						valuesDiff21ForType4Scen[i] = valuesCase2ForType4Scen[i] - valuesCase1ForType4Scen[i];
						
						
						break traincarloop;
					}
				}
			}
		
			
			//case 3
//			System.out.println("\n");
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
//						System.out.println("==========Case 3" 
//								+ ", Seed " + i + ", Run Number " + runNumber
//								+ "==========");
//						System.out.println("Number of Cars: " + numCars
//								+ " Number of Trains: " + numTrains);
//						park.outputResults();
						valuesCase3ForType1[i] = park.output.getPerctOfType1Scen();
						valuesCase3ForType2[i] = park.output.getPerctOfType2Scen();
						valuesCase3ForType3[i] = park.output.getPerctOfType3Scen();
						valuesCase3ForType4[i] = park.output.getPerctOfType4Scen();
						
						valuesCase3ForCost[i] = park.output.getCost();
						
						valuesDiff31ForType1[i] = valuesCase3ForType1[i] - valuesCase1ForType1[i];// Difference with Case 1
						valuesDiff31ForType2[i] = valuesCase3ForType2[i] - valuesCase1ForType2[i];
						valuesDiff31ForType3[i] = valuesCase3ForType3[i] - valuesCase1ForType3[i];
						valuesDiff31ForType4[i] = valuesCase3ForType4[i] - valuesCase1ForType4[i];
						valuesDiff31ForCost[i] = valuesCase3ForCost[i] - valuesCase1ForCost[i];
						
						valuesCase3ForType1Scen[i] = park.output.getType1BoardingEvent();
						valuesCase3ForType2Scen[i] = park.output.getType2BoardingEvent();
						valuesCase3ForType3Scen[i] = park.output.getType3BoardingEvent();
						valuesCase3ForType4Scen[i] = park.output.getType4BoardingEvent();
						
						valuesDiff31ForType1Scen[i] = valuesCase3ForType1Scen[i] - valuesCase1ForType1Scen[i];// Difference with Case 1
						valuesDiff31ForType2Scen[i] = valuesCase3ForType2Scen[i] - valuesCase1ForType2Scen[i];
						valuesDiff31ForType3Scen[i] = valuesCase3ForType3Scen[i] - valuesCase1ForType3Scen[i];
						valuesDiff31ForType4Scen[i] = valuesCase3ForType4Scen[i] - valuesCase1ForType4Scen[i];
						break traincarloop;
					}
				}
			}
		
			//case 4
//			System.out.println("\n");
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
//						System.out.println("==========Case 4" 
//								+ ", Seed " + i + ", Run Number " + runNumber
//								+ "==========");
//						System.out.println("Number of Cars: " + numCars
//								+ " Number of Trains: " + numTrains);
//						park.outputResults();
						valuesCase4ForType1[i] = park.output.getPerctOfType1Scen();
						valuesCase4ForType2[i] = park.output.getPerctOfType2Scen();
						valuesCase4ForType3[i] = park.output.getPerctOfType3Scen();
						valuesCase4ForType4[i] = park.output.getPerctOfType4Scen();
						
						valuesCase4ForCost[i] = park.output.getCost();
						
						valuesDiff41ForType1[i] = valuesCase4ForType1[i] - valuesCase1ForType1[i];// Difference with Case 1
						valuesDiff41ForType2[i] = valuesCase4ForType2[i] - valuesCase1ForType2[i];
						valuesDiff41ForType3[i] = valuesCase4ForType3[i] - valuesCase1ForType3[i];
						valuesDiff41ForType4[i] = valuesCase4ForType4[i] - valuesCase1ForType4[i];
						valuesDiff41ForCost[i] = valuesCase4ForCost[i] - valuesCase1ForCost[i];
						
						valuesCase4ForType1Scen[i] = park.output.getType1BoardingEvent();
						valuesCase4ForType2Scen[i] = park.output.getType2BoardingEvent();
						valuesCase4ForType3Scen[i] = park.output.getType3BoardingEvent();
						valuesCase4ForType4Scen[i] = park.output.getType4BoardingEvent();
					
						valuesDiff41ForType1Scen[i] = valuesCase4ForType1Scen[i] - valuesCase1ForType1Scen[i];// Difference with Case 1
						valuesDiff41ForType2Scen[i] = valuesCase4ForType2Scen[i] - valuesCase1ForType2Scen[i];
						valuesDiff41ForType3Scen[i] = valuesCase4ForType3Scen[i] - valuesCase1ForType3Scen[i];
						valuesDiff41ForType4Scen[i] = valuesCase4ForType4Scen[i] - valuesCase1ForType4Scen[i];
						break traincarloop;
					}
				}
			}
		}
		
		//
		   ConfidenceInterval cfDCase1ForType1 = new ConfidenceInterval(valuesCase1ForType1, CONF_LEVEL);
		   ConfidenceInterval cfDCase1ForType2 = new ConfidenceInterval(valuesCase1ForType2, CONF_LEVEL);
		   ConfidenceInterval cfDCase1ForType3 = new ConfidenceInterval(valuesCase1ForType3, CONF_LEVEL);
		   ConfidenceInterval cfDCase1ForType4 = new ConfidenceInterval(valuesCase1ForType4, CONF_LEVEL);
		   ConfidenceInterval cfDCase1ForCost = new ConfidenceInterval(valuesCase1ForCost, CONF_LEVEL);
		   
		   ConfidenceInterval cfDCase2ForType1 = new ConfidenceInterval(valuesCase2ForType1, CONF_LEVEL);
		   ConfidenceInterval cfDCase2ForType2 = new ConfidenceInterval(valuesCase2ForType2, CONF_LEVEL);
		   ConfidenceInterval cfDCase2ForType3 = new ConfidenceInterval(valuesCase2ForType3, CONF_LEVEL);
		   ConfidenceInterval cfDCase2ForType4 = new ConfidenceInterval(valuesCase2ForType4, CONF_LEVEL);
		   ConfidenceInterval cfDCase2ForCost = new ConfidenceInterval(valuesCase2ForCost, CONF_LEVEL);
		   
		   ConfidenceInterval cfDCase3ForType1 = new ConfidenceInterval(valuesCase3ForType1, CONF_LEVEL);
		   ConfidenceInterval cfDCase3ForType2 = new ConfidenceInterval(valuesCase3ForType2, CONF_LEVEL);
		   ConfidenceInterval cfDCase3ForType3 = new ConfidenceInterval(valuesCase3ForType3, CONF_LEVEL);
		   ConfidenceInterval cfDCase3ForType4 = new ConfidenceInterval(valuesCase3ForType4, CONF_LEVEL);
		   ConfidenceInterval cfDCase3ForCost = new ConfidenceInterval(valuesCase3ForCost, CONF_LEVEL);
		   
		   ConfidenceInterval cfDCase4ForType1 = new ConfidenceInterval(valuesCase4ForType1, CONF_LEVEL);
		   ConfidenceInterval cfDCase4ForType2 = new ConfidenceInterval(valuesCase4ForType2, CONF_LEVEL);
		   ConfidenceInterval cfDCase4ForType3 = new ConfidenceInterval(valuesCase4ForType3, CONF_LEVEL);
		   ConfidenceInterval cfDCase4ForType4 = new ConfidenceInterval(valuesCase4ForType4, CONF_LEVEL);
		   ConfidenceInterval cfDCase4ForCost = new ConfidenceInterval(valuesCase4ForCost, CONF_LEVEL);
		   
		   // Simulation values
		   System.out.printf("========Type1==============================\n");
	       for(int i = 0; i < valuesCase1ForType1.length; i++)
	           System.out.printf("%5d %8.3f %8.3f %8.3f %8.3f\n",i+1, 
	        		   valuesCase1ForType1[i], valuesCase2ForType1[i],valuesCase3ForType1[i],valuesCase4ForType1[i]);
	       // Confidence intervals
	       System.out.printf("--------------------------------------------\n");
	       System.out.printf("    PE %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType1.getPointEstimate(), cfDCase2ForType1.getPointEstimate(), 
	    		   									cfDCase3ForType1.getPointEstimate(), cfDCase4ForType1.getPointEstimate());
	       System.out.printf("  S(n) %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType1.getStdDev(), cfDCase2ForType1.getStdDev(),
	    		   									cfDCase3ForType1.getStdDev(), cfDCase4ForType1.getStdDev());
	       System.out.printf("  zeta %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType1.getZeta(), cfDCase2ForType1.getZeta(), 
													cfDCase3ForType1.getZeta(), cfDCase4ForType1.getZeta());
	       System.out.printf("CI Min %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType1.getCfMin(), cfDCase2ForType1.getCfMin(), 
	    		   									cfDCase3ForType1.getCfMin(), cfDCase4ForType1.getCfMin());
	       System.out.printf("CI Max %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType1.getCfMax(), cfDCase2ForType1.getCfMax(), 
	    		   									cfDCase3ForType1.getCfMax(), cfDCase4ForType1.getCfMax());
	       System.out.printf("--------------------------------------------\n");
	       
	       System.out.printf("========Type2==============================\n");
	       for(int i = 0; i < valuesCase1ForType2.length; i++)
	           System.out.printf("%5d %8.3f %8.3f %8.3f %8.3f\n",i+1, 
	        		   valuesCase1ForType2[i], valuesCase2ForType2[i],valuesCase3ForType2[i],valuesCase4ForType2[i]);
	       System.out.printf("--------------------------------------------\n");
	       System.out.printf("    PE %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType2.getPointEstimate(), cfDCase2ForType2.getPointEstimate(), 
	    		   									cfDCase3ForType2.getPointEstimate(), cfDCase4ForType2.getPointEstimate());
	       System.out.printf("  S(n) %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType2.getStdDev(), cfDCase2ForType2.getStdDev(),
	    		   									cfDCase3ForType2.getStdDev(), cfDCase4ForType2.getStdDev());
	       System.out.printf("  zeta %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType2.getZeta(), cfDCase2ForType2.getZeta(), 
													cfDCase3ForType2.getZeta(), cfDCase4ForType2.getZeta());
	       System.out.printf("CI Min %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType2.getCfMin(), cfDCase2ForType2.getCfMin(), 
	    		   									cfDCase3ForType2.getCfMin(), cfDCase4ForType2.getCfMin());
	       System.out.printf("CI Max %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType2.getCfMax(), cfDCase2ForType2.getCfMax(), 
	    		   									cfDCase3ForType2.getCfMax(), cfDCase4ForType2.getCfMax());
	       System.out.printf("--------------------------------------------\n");
	       
	       System.out.printf("========Type3==============================\n");
	       for(int i = 0; i < valuesCase1ForType3.length; i++)
	           System.out.printf("%5d %8.3f %8.3f %8.3f %8.3f\n",i+1, 
	        		   valuesCase1ForType3[i], valuesCase2ForType3[i],valuesCase3ForType3[i],valuesCase4ForType3[i]);
	       System.out.printf("--------------------------------------------\n");
	       System.out.printf("    PE %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType3.getPointEstimate(), cfDCase2ForType3.getPointEstimate(), 
	    		   									cfDCase3ForType3.getPointEstimate(), cfDCase4ForType3.getPointEstimate());
	       System.out.printf("  S(n) %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType3.getStdDev(), cfDCase2ForType3.getStdDev(),
	    		   									cfDCase3ForType3.getStdDev(), cfDCase4ForType3.getStdDev());
	       System.out.printf("  zeta %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType3.getZeta(), cfDCase2ForType3.getZeta(), 
													cfDCase3ForType3.getZeta(), cfDCase4ForType3.getZeta());
	       System.out.printf("CI Min %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType3.getCfMin(), cfDCase2ForType3.getCfMin(), 
	    		   									cfDCase3ForType3.getCfMin(), cfDCase4ForType3.getCfMin());
	       System.out.printf("CI Max %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType3.getCfMax(), cfDCase2ForType3.getCfMax(), 
	    		   									cfDCase3ForType3.getCfMax(), cfDCase4ForType3.getCfMax());
	       System.out.printf("--------------------------------------------\n");
	       
	       System.out.printf("========Type4==============================\n");
	       for(int i = 0; i < valuesCase1ForType4.length; i++)
	           System.out.printf("%5d %8.3f %8.3f %8.3f %8.3f\n",i+1, 
	        		   valuesCase1ForType4[i], valuesCase2ForType4[i],valuesCase3ForType4[i],valuesCase4ForType4[i]);
	       System.out.printf("--------------------------------------------\n");
	       System.out.printf("    PE %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType4.getPointEstimate(), cfDCase2ForType4.getPointEstimate(), 
	    		   									cfDCase3ForType4.getPointEstimate(), cfDCase4ForType4.getPointEstimate());
	       System.out.printf("  S(n) %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType4.getStdDev(), cfDCase2ForType4.getStdDev(),
	    		   									cfDCase3ForType4.getStdDev(), cfDCase4ForType4.getStdDev());
	       System.out.printf("  zeta %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType4.getZeta(), cfDCase2ForType4.getZeta(), 
													cfDCase3ForType4.getZeta(), cfDCase4ForType4.getZeta());
	       System.out.printf("CI Min %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType4.getCfMin(), cfDCase2ForType4.getCfMin(), 
	    		   									cfDCase3ForType4.getCfMin(), cfDCase4ForType4.getCfMin());
	       System.out.printf("CI Max %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForType4.getCfMax(), cfDCase2ForType4.getCfMax(), 
	    		   									cfDCase3ForType4.getCfMax(), cfDCase4ForType4.getCfMax());
	       System.out.printf("--------------------------------------------\n");
	       
	       
	       System.out.printf("========Total Cost=========================\n");
	       for(int i = 0; i < valuesCase1ForCost.length; i++)
	           System.out.printf("%5d %8.3f %8.3f %8.3f %8.3f\n",i+1, 
	        		   valuesCase1ForCost[i], valuesCase2ForCost[i],valuesCase3ForCost[i],valuesCase4ForCost[i]);
	       System.out.printf("--------------------------------------------\n");
	       System.out.printf("    PE %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForCost.getPointEstimate(), cfDCase2ForCost.getPointEstimate(), 
	    		   									cfDCase3ForCost.getPointEstimate(), cfDCase4ForCost.getPointEstimate());
	       System.out.printf("  S(n) %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForCost.getStdDev(), cfDCase2ForCost.getStdDev(),
	    		   									cfDCase3ForCost.getStdDev(), cfDCase4ForCost.getStdDev());
	       System.out.printf("  zeta %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForCost.getZeta(), cfDCase2ForCost.getZeta(), 
													cfDCase3ForCost.getZeta(), cfDCase4ForCost.getZeta());
	       System.out.printf("CI Min %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForCost.getCfMin(), cfDCase2ForCost.getCfMin(), 
	    		   									cfDCase3ForCost.getCfMin(), cfDCase4ForCost.getCfMin());
	       System.out.printf("CI Max %8.3f %8.3f %8.3f %8.3f\n", cfDCase1ForCost.getCfMax(), cfDCase2ForCost.getCfMax(), 
	    		   									cfDCase3ForCost.getCfMax(), cfDCase4ForCost.getCfMax());
	       System.out.printf("--------------------------------------------\n");
		   
		   
		   
		   
		   
		
		 // Get the conficence intervals
	       ConfidenceInterval cfDiff21ForType1 = new ConfidenceInterval(valuesDiff21ForType1, CONF_LEVEL);
	       ConfidenceInterval cfDiff21ForType2 = new ConfidenceInterval(valuesDiff21ForType2, CONF_LEVEL);
	       ConfidenceInterval cfDiff21ForType3 = new ConfidenceInterval(valuesDiff21ForType3, CONF_LEVEL);
	       ConfidenceInterval cfDiff21ForType4 = new ConfidenceInterval(valuesDiff21ForType4, CONF_LEVEL);
	       ConfidenceInterval cfDiff21ForCost = new ConfidenceInterval(valuesDiff21ForCost, CONF_LEVEL);
	       
	       ConfidenceInterval cfDiff31ForType1 = new ConfidenceInterval(valuesDiff31ForType1, CONF_LEVEL);
	       ConfidenceInterval cfDiff31ForType2 = new ConfidenceInterval(valuesDiff31ForType2, CONF_LEVEL);
	       ConfidenceInterval cfDiff31ForType3 = new ConfidenceInterval(valuesDiff31ForType3, CONF_LEVEL);
	       ConfidenceInterval cfDiff31ForType4 = new ConfidenceInterval(valuesDiff31ForType4, CONF_LEVEL);	 
	       ConfidenceInterval cfDiff31ForCost = new ConfidenceInterval(valuesDiff31ForCost, CONF_LEVEL);
	       
	       ConfidenceInterval cfDiff41ForType1 = new ConfidenceInterval(valuesDiff41ForType1, CONF_LEVEL);
	       ConfidenceInterval cfDiff41ForType2 = new ConfidenceInterval(valuesDiff41ForType2, CONF_LEVEL);
	       ConfidenceInterval cfDiff41ForType3 = new ConfidenceInterval(valuesDiff41ForType3, CONF_LEVEL);
	       ConfidenceInterval cfDiff41ForType4 = new ConfidenceInterval(valuesDiff41ForType4, CONF_LEVEL);
	       ConfidenceInterval cfDiff41ForCost = new ConfidenceInterval(valuesDiff41ForCost, CONF_LEVEL);
	       
	       
	       ConfidenceInterval cfDiff21ForType1Scen = new ConfidenceInterval(valuesDiff21ForType1Scen, CONF_LEVEL);
	       ConfidenceInterval cfDiff21ForType2Scen = new ConfidenceInterval(valuesDiff21ForType2Scen, CONF_LEVEL);
	       ConfidenceInterval cfDiff21ForType3Scen = new ConfidenceInterval(valuesDiff21ForType3Scen, CONF_LEVEL);
	       ConfidenceInterval cfDiff21ForType4Scen = new ConfidenceInterval(valuesDiff21ForType4Scen, CONF_LEVEL);
	       
	       ConfidenceInterval cfDiff31ForType1Scen = new ConfidenceInterval(valuesDiff31ForType1Scen, CONF_LEVEL);
	       ConfidenceInterval cfDiff31ForType2Scen = new ConfidenceInterval(valuesDiff31ForType2Scen, CONF_LEVEL);
	       ConfidenceInterval cfDiff31ForType3Scen = new ConfidenceInterval(valuesDiff31ForType3Scen, CONF_LEVEL);
	       ConfidenceInterval cfDiff31ForType4Scen = new ConfidenceInterval(valuesDiff31ForType4Scen, CONF_LEVEL);
	       
	       ConfidenceInterval cfDiff41ForType1Scen = new ConfidenceInterval(valuesDiff41ForType1Scen, CONF_LEVEL);
	       ConfidenceInterval cfDiff41ForType2Scen = new ConfidenceInterval(valuesDiff41ForType2Scen, CONF_LEVEL);
	       ConfidenceInterval cfDiff41ForType3Scen = new ConfidenceInterval(valuesDiff41ForType3Scen, CONF_LEVEL);
	       ConfidenceInterval cfDiff41ForType4Scen = new ConfidenceInterval(valuesDiff41ForType4Scen, CONF_LEVEL);
	       
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
//	       System.out.printf("-------------------------------------------------------------------------------------------\n");
	       
	     //COST
	       System.out.printf("cfDiff21ForCost  %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n",
	    		   cfDiff21ForCost.getPointEstimate(), cfDiff21ForCost.getVariance(), cfDiff21ForCost.getZeta(), 
	    		   cfDiff21ForCost.getCfMin(), cfDiff21ForCost.getCfMax(),
	    	         Math.abs(cfDiff21ForCost.getZeta()/cfDiff21ForCost.getPointEstimate()));
	       System.out.printf("cfDiff31ForCost  %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
	    		   cfDiff31ForCost.getPointEstimate(), cfDiff31ForCost.getVariance(), cfDiff31ForCost.getZeta(), 
	    		   cfDiff31ForCost.getCfMin(), cfDiff31ForCost.getCfMax(),
		             Math.abs(cfDiff31ForCost.getZeta()/cfDiff31ForCost.getPointEstimate()));
	       System.out.printf("cfDiff41ForCost  %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
	    		   cfDiff41ForCost.getPointEstimate(), cfDiff41ForCost.getVariance(), cfDiff41ForCost.getZeta(), 
	    		   cfDiff41ForCost.getCfMin(), cfDiff41ForCost.getCfMax(),
		             Math.abs(cfDiff41ForCost.getZeta()/cfDiff41ForCost.getPointEstimate()));
	       System.out.printf("-------------------------------------------------------------------------------------------\n");
	       
	       //TYPE1Scen
//	       System.out.printf("cfDiff21ForType1Scen %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n",
//	    		   cfDiff21ForType1Scen.getPointEstimate(), cfDiff21ForType1Scen.getVariance(), cfDiff21ForType1Scen.getZeta(), 
//	    		   cfDiff21ForType1Scen.getCfMin(), cfDiff21ForType1Scen.getCfMax(),
//	    	         Math.abs(cfDiff21ForType1Scen.getZeta()/cfDiff21ForType1Scen.getPointEstimate()));
//	       System.out.printf("cfDiff31ForType1Scen %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
//	    		   cfDiff31ForType1Scen.getPointEstimate(), cfDiff31ForType1Scen.getVariance(), cfDiff31ForType1Scen.getZeta(), 
//	    		   cfDiff31ForType1Scen.getCfMin(), cfDiff31ForType1Scen.getCfMax(),
//		             Math.abs(cfDiff31ForType1Scen.getZeta()/cfDiff31ForType1Scen.getPointEstimate()));
//	       System.out.printf("cfDiff41ForType1Scen %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
//	    		   cfDiff41ForType1Scen.getPointEstimate(), cfDiff41ForType1Scen.getVariance(), cfDiff41ForType1Scen.getZeta(), 
//	    		   cfDiff41ForType1Scen.getCfMin(), cfDiff41ForType1Scen.getCfMax(),
//		             Math.abs(cfDiff41ForType1Scen.getZeta()/cfDiff41ForType1Scen.getPointEstimate()));
//	       
//	       //TYPE2Scen
//	       System.out.printf("cfDiff21ForType2Scen %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n",
//	    		   cfDiff21ForType2Scen.getPointEstimate(), cfDiff21ForType2Scen.getVariance(), cfDiff21ForType2Scen.getZeta(), 
//	    		   cfDiff21ForType2Scen.getCfMin(), cfDiff21ForType2Scen.getCfMax(),
//	    	         Math.abs(cfDiff21ForType2Scen.getZeta()/cfDiff21ForType2Scen.getPointEstimate()));
//	       System.out.printf("cfDiff31ForType2Scen %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
//	    		   cfDiff31ForType2Scen.getPointEstimate(), cfDiff31ForType2Scen.getVariance(), cfDiff31ForType2Scen.getZeta(), 
//	    		   cfDiff31ForType2Scen.getCfMin(), cfDiff31ForType2Scen.getCfMax(),
//		             Math.abs(cfDiff31ForType2Scen.getZeta()/cfDiff31ForType2Scen.getPointEstimate()));
//	       System.out.printf("cfDiff41ForType2Scen %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
//	    		   cfDiff41ForType2Scen.getPointEstimate(), cfDiff41ForType2Scen.getVariance(), cfDiff41ForType2Scen.getZeta(), 
//	    		   cfDiff41ForType2Scen.getCfMin(), cfDiff41ForType2Scen.getCfMax(),
//		             Math.abs(cfDiff41ForType2Scen.getZeta()/cfDiff41ForType2Scen.getPointEstimate()));
//	       
//	       //TYPE3Scen
//	       System.out.printf("cfDiff21ForType3Scen %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n",
//	    		   cfDiff21ForType3Scen.getPointEstimate(), cfDiff21ForType3Scen.getVariance(), cfDiff21ForType3Scen.getZeta(), 
//	    		   cfDiff21ForType3Scen.getCfMin(), cfDiff21ForType3Scen.getCfMax(),
//	    	         Math.abs(cfDiff21ForType3Scen.getZeta()/cfDiff21ForType3Scen.getPointEstimate()));
//	       System.out.printf("cfDiff31ForType3Scen %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
//	    		   cfDiff31ForType3Scen.getPointEstimate(), cfDiff31ForType3Scen.getVariance(), cfDiff31ForType3Scen.getZeta(), 
//	    		   cfDiff31ForType3Scen.getCfMin(), cfDiff31ForType3Scen.getCfMax(),
//		             Math.abs(cfDiff31ForType3Scen.getZeta()/cfDiff31ForType3Scen.getPointEstimate()));
//	       System.out.printf("cfDiff41ForType3Scen %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
//	    		   cfDiff41ForType3Scen.getPointEstimate(), cfDiff41ForType3Scen.getVariance(), cfDiff41ForType3Scen.getZeta(), 
//	    		   cfDiff41ForType3Scen.getCfMin(), cfDiff41ForType3Scen.getCfMax(),
//		             Math.abs(cfDiff41ForType3Scen.getZeta()/cfDiff41ForType3Scen.getPointEstimate()));
//	       
//	       //TYPE4Scen
//	       System.out.printf("cfDiff21ForType4Scen %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n",
//	    		   cfDiff21ForType4Scen.getPointEstimate(), cfDiff21ForType4Scen.getVariance(), cfDiff21ForType4Scen.getZeta(), 
//	    		   cfDiff21ForType4Scen.getCfMin(), cfDiff21ForType4Scen.getCfMax(),
//	    	         Math.abs(cfDiff21ForType4Scen.getZeta()/cfDiff21ForType4Scen.getPointEstimate()));
//	       System.out.printf("cfDiff31ForType4Scen %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
//	    		   cfDiff31ForType4Scen.getPointEstimate(), cfDiff31ForType4Scen.getVariance(), cfDiff31ForType4Scen.getZeta(), 
//	    		   cfDiff31ForType4Scen.getCfMin(), cfDiff31ForType4Scen.getCfMax(),
//		             Math.abs(cfDiff31ForType4Scen.getZeta()/cfDiff31ForType4Scen.getPointEstimate()));
//	       System.out.printf("cfDiff41ForType4Scen %13.3f %18.3f %8.3f %8.3f %8.3f %14.3f\n", 
//	    		   cfDiff41ForType4Scen.getPointEstimate(), cfDiff41ForType4Scen.getVariance(), cfDiff41ForType4Scen.getZeta(), 
//	    		   cfDiff41ForType4Scen.getCfMin(), cfDiff41ForType4Scen.getCfMax(),
//		             Math.abs(cfDiff41ForType4Scen.getZeta()/cfDiff41ForType4Scen.getPointEstimate()));
	       System.out.printf("-------------------------------------------------------------------------------------------\n");

	}
}
