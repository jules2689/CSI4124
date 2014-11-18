package simModel;

import absmodJ.AOSimulationModel;
import absmodJ.Behaviour;

// The Simulation model Class
import absmodJ.ExtSequelActivity;
import absmodJ.SBNotice;
import absmodJ.SequelActivity;

public class SMThemePark extends AOSimulationModel {
	// Constants available from Constants class
	/* Parameter */
	// Define the parameters
	protected int numberOfTrains; // number of trains, from 4-8
	protected int numberOfCars; // total number of cars
	protected int boardingOption; // 0:single-sided; 1:double_sided
	protected boolean fixBoardingTime; // true:fix boarding time

	/*-------------Entity Data Structures-------------------*/
	/* Group and Queue Entities */
	// Define the reference variables to the various
	// entities with scope Set and Unary
	// Objects can be created here or in the Initialize Action
	Tracks[] rqTracks = new Tracks[4]; // RQ.Tracks
	Stations[] gStations = new Stations[4]; // G.Stations
	Trains[] rcgTrains; // RCG.Trains

	/* Input Variables */
	// Define any Independent Input Variables here

	// References to RVP and DVP objects
	protected RVPs rvp; // Reference to rvp object - object created in
						// constructor
	protected DVPs dvp = new DVPs(this); // Reference to dvp object
	protected UDPs udp = new UDPs(this);

	// Output object
	public Output output = new Output(this);

	// Output values - define the public methods that return values
	// required for experimentation.

	// Other values
	// closing time of the park
	protected double closingTime;

	// Constructor
	public SMThemePark(double t0time, double tftime, int nTrains, int nCars,
			int boardingOption, boolean fixBoardingTime, Seeds sd,
			boolean traceFlag) {

		// Turn trancing on if traceFlag is true
		this.traceFlag = traceFlag;

		// Initialize parameters here
		this.numberOfTrains = nTrains;
		this.numberOfCars = nCars;
		this.boardingOption = boardingOption;
		this.fixBoardingTime = fixBoardingTime;

		// Create RVP object with given seed
		rvp = new RVPs(this, sd);

		// Initial four station
		String[] names = new String[] { Constants.FP_S, Constants.SH_S,
				Constants.GI_S, Constants.RC_S };
		for (int i = 0; i < this.gStations.length; i++) {
			this.gStations[i] = new Stations(names[i]);
		}
		// Initial tracks
		for (int i = 0; i < this.rqTracks.length; i++) {
			this.rqTracks[i] = new Tracks();
		}

		closingTime = tftime; // record the closing time
		// Initialize the simulation model
		initAOSimulModel(t0time, tftime);

		Initialise init = new Initialise(this);
		scheduleAction(init); // Should always be first one scheduled.
		// Schedule other scheduled actions and activities here

		// Schedule the first arrivals
		ArriveAtStationFP arrivalFP = new ArriveAtStationFP(this);
		scheduleAction(arrivalFP); // customer arrive at FP

		ArriveAtStationGI arrivalGI = new ArriveAtStationGI(this);
		scheduleAction(arrivalGI); // customer arrive at GI

		ArriveAtStationRC arrivalRC = new ArriveAtStationRC(this);
		scheduleAction(arrivalRC); // customer arrive at RC

		ArriveAtStationSH arrivalSH = new ArriveAtStationSH(this);
		scheduleAction(arrivalSH); // customer arrive at SH
	}

	public SMThemePark() {
	}

	/************ Implementation of Data Modules ***********/
	/*
	 * Testing preconditions
	 */
	@Override
	protected void testPreconditions(Behaviour behObj) {

		// Check preconditions of Conditional Activities
		reschedule(behObj);

		// test for unboarding and boarding
		if (UnBoardingAndBoarding.precondition(this) == true) {
			UnBoardingAndBoarding act = new UnBoardingAndBoarding(this); // Generate
																			// instance
			act.startingEvent();
			scheduleActivity(act);
		}

		// Check preconditions of Interruptions in Extended Activities
		int num; // number of entries in list
		int interruptionNum; // interruption number
		SBNotice nt;
		Behaviour obj;
		num = esbl.size();

		for (int i = 0; i < num; i++) {
			nt = esbl.get(i);
			obj = (esbl.get(i)).behaviourInstance;

			if (ExtSequelActivity.class.isInstance(obj)) {
				ExtSequelActivity extSeqObj = (ExtSequelActivity) nt.behaviourInstance;
				interruptionNum = extSeqObj.interruptionPreCond();
				if (interruptionNum != 0) {
					extSeqObj.interruptionSCS(interruptionNum);
					unscheduleBehaviour(nt);
					i--;
					num--;
				}
			}
		}
	}

	// Flag for controlling tracing
	boolean traceFlag = false;

	protected void eventOccured() {
		if (traceFlag) {
			// show the SBL and verification here
			this.showSBL();
		}
		//
		// System.out.println("***Total events:"+this.output.getTotalEvent());

		// if(0 != this.output.getTotalEvent()){
		// System.out.println("***type1BoardingEvent:"+this.output.getType1BoardingEvent()
		// +
		// " Percentage:" + this.output.getPerctOfType1Scen() + "%");
		// System.out.println("***type2BoardingEvent:"+this.output.getType2BoardingEvent()
		// +
		// " Percentage:" + this.output.getPerctOfType2Scen() + "%");
		// System.out.println("***type3BoardingEvent:"+this.output.getType3BoardingEvent()
		// +
		// " Percentage:" + this.output.getPerctOfType3Scen() + "%");
		// System.out.println("***type4BoardingEvent:"+this.output.getType4BoardingEvent()
		// +
		// " Percentage:" + this.output.getPerctOfType4Scen() + "%");
		// }
		// Can add other debug code to monitor the status of the system
		// See examples for suggestions on setup logging

		// Setup an updateTrjSequences() method in the Output class
		// and call here if you have Trajectory Sets
		// updateTrjSequences()
	}

	// Standard Procedure to start Sequel Activities with no parameters
	protected void spStart(SequelActivity seqAct) {
		seqAct.startingEvent();
		scheduleActivity(seqAct);
	}

	@Override
	protected double getClock() {
		return super.getClock();
	}

	/*
	 * // check if satisfied public boolean checkContinue() { boolean
	 * continueflag = this.numberOfTrains < 9;
	 * 
	 * if (continueflag) { System.out.println("************With " +
	 * this.numberOfTrains + " Trains and " + this.numberOfCars +
	 * " cars in total ***************");
	 * System.out.println("******PerctOfType4Scen: " +
	 * formatDoubleWithTwoPrecision(this.output .getPerctOfType4Scen()));
	 * System.out.println("******PerctOfType3Scen: " +
	 * formatDoubleWithTwoPrecision(this.output .getPerctOfType3Scen()));
	 * System.out.println("******PerctOfType2Scen: " +
	 * formatDoubleWithTwoPrecision(this.output .getPerctOfType2Scen()));
	 * System.out.println("******PerctOfType1Scen: " +
	 * formatDoubleWithTwoPrecision(this.output .getPerctOfType1Scen()));
	 * 
	 * double total = this.output.getPerctOfType4Scen() +
	 * this.output.getPerctOfType3Scen() + this.output.getPerctOfType2Scen() +
	 * this.output.getPerctOfType1Scen(); System.out.println("***TOTAL: " +
	 * total);
	 * 
	 * if (checkGoalReached()) { System.out.println("****Reach the goal with  "
	 * + this.numberOfTrains + " trains and " + this.numberOfCars +
	 * " cars in total, at a cost of $" + this.cost()); }
	 * 
	 * if (this.numberOfCars == 72) { continueflag = false; }
	 * 
	 * System.out.println(""); }
	 * 
	 * Debugger.debug("check: " + continueflag); return continueflag; }
	 * 
	 * public boolean checkGoalReached() { return
	 * this.output.getPerctOfType4Scen() == 0 &&
	 * this.output.getPerctOfType3Scen() <= 5 &&
	 * this.output.getPerctOfType2Scen() <= 10; }
	 * 
	 * // TODO Added Helper Method for Docs? private String
	 * formatDoubleWithTwoPrecision(double decimal) { return new
	 * DecimalFormat("#0.00").format(decimal); }
	 * 
	 * // reset with increament of train public void resetWithIncre() {
	 * Debugger.debug("==resetWithIncre start:"); this.numberOfCars++;
	 * 
	 * if (this.numberOfCars > this.numberOfTrains * 9) { // if reach maximum //
	 * cars, increase // train and restart // with min cars
	 * this.numberOfTrains++; this.numberOfCars = this.numberOfTrains * 4;
	 * Debugger.debug(
	 * "\n\n================================================\n================= NEW TRAIN "
	 * + this.numberOfTrains +
	 * " ==================\n================================================\n\n"
	 * , 4); }
	 * 
	 * // reset uNumCustomers in stations for (int i = 0; i <
	 * this.stations.stationGroup.length; i++) {
	 * this.stations.stationGroup[i].uNumCustomers = 0; }
	 * 
	 * // remove all train from track for (int i = 0; i <
	 * this.tracks.trackGroup.length; i++) {
	 * this.tracks.trackGroup[i].trainGroup = new ArrayList<Train>(); }
	 * 
	 * initAOSimulModel(0.0, this.closingTime); Initialise init = new
	 * Initialise(this); this.printAllTrack(); scheduleAction(init); // Should
	 * always be first one scheduled. // Schedule other scheduled actions and
	 * activities here
	 * 
	 * // Schedule the first arrivals ArriveAtStationFP arrivalFP = new
	 * ArriveAtStationFP(this); scheduleAction(arrivalFP); // customer arrive at
	 * FP
	 * 
	 * ArriveAtStationGI arrivalGI = new ArriveAtStationGI(this);
	 * scheduleAction(arrivalGI); // customer arrive at FP
	 * 
	 * ArriveAtStationRC arrivalRC = new ArriveAtStationRC(this);
	 * scheduleAction(arrivalRC); // customer arrive at FP
	 * 
	 * ArriveAtStationSH arrivalSH = new ArriveAtStationSH(this);
	 * scheduleAction(arrivalSH); // customer arrive at FP
	 * 
	 * Debugger.debug("==resetWithIncre end==with numberOfCars:" +
	 * this.numberOfCars + " and numberOfTrains:" + this.numberOfTrains, 2);
	 * this.runSimulation(); }
	 * 
	 * @Override public String toString() { String string = this.numberOfTrains
	 * + " trains\n" + this.numberOfCars + " cars\n";
	 * 
	 * if (this.boardingOption == 0) { string +=
	 * "boarding option: single-sided\n"; } else { string +=
	 * "boarding option: double-sided\n"; }
	 * 
	 * if (fixBoardingTime) { string += "Fixed boarding time\n"; } else { string
	 * += "Unfixed boarding time\n"; }
	 * 
	 * string += "Cost of scenario : $" + this.cost() + "\n";
	 * 
	 * return string; }
	 * 
	 * // TODO : Add to doc public int cost() { int cost = this.numberOfTrains *
	 * Constants.COST_OF_TRAIN; if (this.boardingOption == 1) { cost +=
	 * this.numberOfCars * (Constants.COST_OF_CAR + 20); } else { cost +=
	 * this.numberOfCars * Constants.COST_OF_CAR; } return cost; }
	 * 
	 * public SMThemePark shallowClone() { SMThemePark park = new SMThemePark();
	 * park.fixBoardingTime = this.fixBoardingTime; park.boardingOption =
	 * this.boardingOption; park.numberOfTrains = this.numberOfTrains;
	 * park.numberOfCars = this.numberOfCars; return park; }
	 * 
	 * // TODO method for debug DELETE public void printAllTrack() { for (int i
	 * = 0; i < this.tracks.trackGroup.length; i++) { Track track =
	 * this.tracks.trackGroup[i]; Station stn = this.stations.stationGroup[i];
	 * Debugger.debug("########Track " + i + ":", 2);
	 * Debugger.debug("###Station cust:" + stn.uNumCustomers, 2); if
	 * (track.trainGroup.size() > 0) { Train train; for (int j = 0; j <
	 * track.trainGroup.size(); j++) { train = track.trainGroup.get(j);
	 * Debugger.debug("###train index:" + j + " ###train number:" +
	 * train.trainId + "###numCars:" + train.numCars + " ###train cust:" +
	 * train.numCustomers + " ###train status:" + train.status, 2); } } } }
	 * 
	 * // TODO method for debug DELETE public double[] currentStats() { double[]
	 * results = new double[4]; results[0] = this.output.getPerctOfType1Scen();
	 * results[1] = this.output.getPerctOfType2Scen(); results[2] =
	 * this.output.getPerctOfType3Scen(); results[3] =
	 * this.output.getPerctOfType4Scen();
	 * 
	 * return results; }
	 */

	public void outputResults() {
		System.out.println("Percentage of Type 1 Events: "
				+ output.getPerctOfType1Scen());
		System.out.println("Percentage of Type 2 Events: "
				+ output.getPerctOfType2Scen());
		System.out.println("Percentage of Type 3 Events: "
				+ output.getPerctOfType3Scen());
		System.out.println("Percentage of Type 4 Events: "
				+ output.getPerctOfType4Scen());

		int cost = 0;
		cost += this.numberOfTrains * Constants.COST_OF_TRAIN;
		cost += this.numberOfCars * Constants.COST_OF_CAR;

		if (this.boardingOption == 0) {
			cost += this.numberOfCars * Constants.COST_OF_BOARDING_OPTION_0;
		} else if (this.boardingOption == 1) {
			cost += this.numberOfCars * Constants.COST_OF_BOARDING_OPTION_1;
		}
		System.out.println("Cost: " + cost);
	}
}
