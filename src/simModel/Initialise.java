package simModel;

import absmodJ.ScheduledAction;

class Initialise extends ScheduledAction {
	SMThemePark model;

	// Constructor
	public Initialise(SMThemePark model) {
		this.model = model;
	}

	double[] ts = { 0.0, -1.0 }; // -1.0 ends scheduling
	int tsix = 0; // set index to first entry.

	@Override
	public double timeSequence() {
		return ts[tsix++]; // only invoked at t=0
	}

	@Override
	public void actionEvent() {
		// System Initialisation
		// Add initilisation instructions
		// initial 4 trains to trainGroup
		
		//Initialize an array for the purpose of this method
		Train[] trains = new Train[model.numberOfTrains];
		
		//Fill the array with trains
		Train icTrain;
		for (int i = 0; i < model.numberOfTrains; i++) {
			icTrain = new Train(i, 0);
			icTrain.status = Constants.TRAIN_STATUS_ARRIVED;
			trains[i] = icTrain;
		}
		
		//Add cars to the trains
		int trainId = 0;
		for (int i = model.numberOfCars; i > 0; i--) {
			icTrain = trains[trainId];
			icTrain.addCar();
			trainId = (trainId + 1) % trains.length;
		}
		
		int constants[] = new int[]{Constants.FP, Constants.GI, Constants.SH, Constants.RC};
		int constantPos = 0;
		
		//Add the trains to the group and the tracks
		for (int i = 0; i < model.numberOfTrains; i++) {
			icTrain = trains[i];
			model.trains.insertGrp(icTrain);
			int stationId = constants[constantPos];
			model.tracks.trackGroup[stationId].trainGroup.add(icTrain); // put all trains on the track
			constantPos = (constantPos + 1) % constants.length;
			Debugger.debug(icTrain.toString(), 3);
		}
		
		// initialize of output
		model.output.setTotalEvent(0);
		model.output.setType1BoardingEvent(0);
		model.output.setType2BoardingEvent(0);
		model.output.setType3BoardingEvent(0);
		model.output.setType4BoardingEvent(0);
	}

}
