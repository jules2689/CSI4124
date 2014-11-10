package simModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
	// Objects can be created here or in the Initialise Action
	Tracks tracks = new Tracks(); // RQ.Tracks
	Stations stations = new Stations(); // G.Stations
	Trains trains = new Trains(); // RCG.Trains

	/* Input Variables */
	// Define any Independent Input Varaibles here

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
	public SMThemePark(double t0time, double tftime, int nTrains, int nCars, int boardingOption, boolean fixBoardingTime, Seeds sd) {
		// System.out.println("==SMThemePark==construct==start");//TODO
		// Initialise parameters here
		this.numberOfTrains = nTrains;
		this.numberOfCars = nCars;
		this.boardingOption = boardingOption;
		this.fixBoardingTime = fixBoardingTime;

		// Create RVP object with given seed
		rvp = new RVPs(this, sd);

		// Initial four trains and put them on the track
		// for(int i = 0; i < this.numberOfTrains; i++){
		// Train icTrain = new Train(i);
		// icTrain.status = Constants.TRAIN_STATUS_ARRIVED;
		// this.trains.insertGrp(icTrain);
		// //put all trains on the track
		// this.tracks.trackGroup[Constants.FP].insertQue(icTrain);
		// }
		// Initial four station
		for (int i = 0; i < this.stations.stationGroup.length; i++) {
			this.stations.stationGroup[i] = new Station();
		}
		// Initial tracks
		for (int i = 0; i < this.tracks.trackGroup.length; i++) {
			this.tracks.trackGroup[i] = new Track();
		}

		closingTime = tftime; // record the closing time
		// Initialise the simulation model
		initAOSimulModel(t0time, tftime);

		Initialise init = new Initialise(this);
		scheduleAction(init); // Should always be first one scheduled.
		// Schedule other scheduled actions and acitvities here

		// Schedule the first arrivals
		ArriveAtStationFP arrivalFP = new ArriveAtStationFP(this);
		scheduleAction(arrivalFP); // customer arrive at FP

		ArriveAtStationGI arrivalGI = new ArriveAtStationGI(this);
		scheduleAction(arrivalGI); // customer arrive at FP

		ArriveAtStationRC arrivalRC = new ArriveAtStationRC(this);
		scheduleAction(arrivalRC); // customer arrive at FP

		ArriveAtStationSH arrivalSH = new ArriveAtStationSH(this);
		scheduleAction(arrivalSH); // customer arrive at FP
		// System.out.println("==SMThemePark==construct==end");//TODO
	}

	/************ Implementation of Data Modules ***********/
	/*
	 * Testing preconditions
	 */
	@Override
	protected void testPreconditions(Behaviour behObj) {
		reschedule(behObj);

		// Check preconditions of Conditional Activities
		if (UnBoardingAndBoarding.precondition(this) == true) {
			UnBoardingAndBoarding act = new UnBoardingAndBoarding(this); // Generate
																			// instance
			act.startingEvent();
			scheduleActivity(act);
		}

		// Check preconditions of Interruptions in Extended Activities
		int num = esbl.size();
		SBNotice nt;
		Behaviour obj;
		int interruptionNum; // interruption number
		for (int i = 0; i < num; i++) {
			nt = esbl.get(i);
			obj = (esbl.get(i)).behaviourInstance;
			if (ExtSequelActivity.class.isInstance(obj)) {
				ExtSequelActivity extSeqObj = (ExtSequelActivity) nt.behaviourInstance;
				interruptionNum = extSeqObj.interruptionPreCond();
				if (interruptionNum != 0) {
					// System.out.println("=testPreconditions==start interruption=start");//TODO
					// delete
					extSeqObj.interruptionSCS(interruptionNum);
					unscheduleBehaviour(nt);
					i--;
					num--;
					// System.out.println("=testPreconditions==start interruption=end");//TODO
					// delete
				}
			}
		}
	}

	@Override
	protected boolean implicitStopCondition() // termination explicit
	{
		// System.out.println("===SMThemePark===implicitStopCondition===start===");//TODO
		// delete
		boolean retVal = false;
		Station stn;
		boolean custFlag = false;
		for (int i = 0; i < this.stations.stationGroup.length; i++) {
			stn = this.stations.stationGroup[i];
			if (stn.uNumCustomers > 0) {
				custFlag = true;
				break;
			}
		}
		if (getClock() >= closingTime && custFlag) {
			retVal = true;
		}

		// System.out.println("ClosingTime = " + closingTime + "currentTime = "
		// + getClock() + "RG.Counter.n = " + rgCounter.size());

		// System.out.println("implicit stop condition returns " + retVal);
		// System.out.println("===SMThemePark===implicitStopCondition===end===retVal:"+retVal);//TODO
		// delete
		return (retVal);

	}

	@Override
	protected void eventOccured() {
		// this.showSBL();
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

	// check if satisfied
	public boolean checkContinue() {
		boolean continueflag = this.numberOfTrains < 9;
		
		if (continueflag) {
			System.out.println("************With " + this.numberOfTrains + " Trains and " + this.numberOfCars + " cars in total ***************");
			System.out.println("******PerctOfType4Scen: " + formatDoubleWithTwoPrecision(this.output.getPerctOfType4Scen()));
			System.out.println("******PerctOfType3Scen: " + formatDoubleWithTwoPrecision(this.output.getPerctOfType3Scen()));
			System.out.println("******PerctOfType2Scen: " + formatDoubleWithTwoPrecision(this.output.getPerctOfType2Scen()));
			System.out.println("******PerctOfType1Scen: " + formatDoubleWithTwoPrecision(this.output.getPerctOfType1Scen()));
			
			double total = this.output.getPerctOfType4Scen() + this.output.getPerctOfType3Scen() + this.output.getPerctOfType2Scen() + this.output.getPerctOfType1Scen();
			System.out.println("***TOTAL: " + total);
	
			if (this.output.getPerctOfType4Scen() == 0 && this.output.getPerctOfType3Scen() <= 5 && this.output.getPerctOfType2Scen() <= 10) {
				System.out.println("****Reach the goal with  " + this.numberOfTrains + " trains and " + this.numberOfCars + " cars in total.");
				continueflag = false;
			}
	
			if (this.numberOfCars == 72) {
				System.out.println("Could not reach the goal....");
				continueflag = false;
			}
			
			System.out.println("");
		}

		// System.out.println("check:" + continueflag);
		return continueflag;
	}
	
	//TODO Added Helper Method for Docs?
	private String formatDoubleWithTwoPrecision(double decimal) {
		return new DecimalFormat("#0.00").format(decimal);
	}

	// reset with increament of train
	public void resetWithIncre() {
		// System.out.println("==resetWithIncre start:"); //TODO delete
		this.numberOfCars++;

		if (this.numberOfCars > this.numberOfTrains * 9) { // if reach maximum
															// cars, increase
															// train and restart
															// with min cars
			this.numberOfTrains++;
			this.numberOfCars = this.numberOfTrains * 4;
		}

		// reset uNumCustomers in stations
		for (int i = 0; i < this.stations.stationGroup.length; i++) {
			this.stations.stationGroup[i].uNumCustomers = 0;
		}

		// remove all train from track
		for (int i = 0; i < this.tracks.trackGroup.length; i++) {
			this.tracks.trackGroup[i].trainGroup = new ArrayList<Train>();
		}

		initAOSimulModel(0.0, this.closingTime);
		Initialise init = new Initialise(this);
		// this.printAllTrack(); //TODO delete
		scheduleAction(init); // Should always be first one scheduled.
		// Schedule other scheduled actions and acitvities here

		// Schedule the first arrivals
		ArriveAtStationFP arrivalFP = new ArriveAtStationFP(this);
		scheduleAction(arrivalFP); // customer arrive at FP

		ArriveAtStationGI arrivalGI = new ArriveAtStationGI(this);
		scheduleAction(arrivalGI); // customer arrive at FP

		ArriveAtStationRC arrivalRC = new ArriveAtStationRC(this);
		scheduleAction(arrivalRC); // customer arrive at FP

		ArriveAtStationSH arrivalSH = new ArriveAtStationSH(this);
		scheduleAction(arrivalSH); // customer arrive at FP
		System.out.println("==resetWithIncre end==with numberOfCars:" // TODO
																		// delete
				+ this.numberOfCars
				+ " and numberOfTrains:"
				+ this.numberOfTrains); // TODO delete
		this.runSimulation();
	}

	// TODO method for debug DELETE
	public void printAllTrack() {
		for (int i = 0; i < this.tracks.trackGroup.length; i++) {
			Track track = this.tracks.trackGroup[i];
			Station stn = this.stations.stationGroup[i];
			System.out.println("########Track " + i + ":");
			System.out.println("###Station cust:" + stn.uNumCustomers);
			if (track.trainGroup.size() > 0) {
				Train train;
				for (int j = 0; j < track.trainGroup.size(); j++) {
					train = track.trainGroup.get(j);
					System.out.println("###train index:" + j
							+ " ###train number:" + train.trainId
							+ "###numCars:" + train.numCars + " ###train cust:"
							+ train.numCustomers + " ###train status:"
							+ train.status);
				}
			}
		}
	}
	
	// TODO method for debug DELETE
	public double[] currentStats(){
		double []results = new double[4];
		results[0] = this.output.getPerctOfType1Scen();
		results[1] = this.output.getPerctOfType2Scen();
		results[2] = this.output.getPerctOfType3Scen();
		results[3] = this.output.getPerctOfType4Scen();
		
		return results;
	}

}
