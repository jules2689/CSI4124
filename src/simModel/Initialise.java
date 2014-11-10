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
		int numRemainderCars = model.numberOfCars % model.numberOfTrains;
		Train icTrain;
		for (int i = 0; i < model.numberOfTrains; i++) {
			icTrain = new Train(i, model.numberOfCars / model.numberOfTrains);
			if (numRemainderCars > 0) {
				icTrain.numCars++;
				icTrain.maxCustomers = icTrain.maxCustomers
						+ Constants.MAX_CUSTOMERS_PER_CAR;
				numRemainderCars--;
			}
			icTrain.status = Constants.TRAIN_STATUS_ARRIVED;
			model.trains.insertGrp(icTrain);

			// put all trains on the track,
			// segment of track(from FP to SH)
			model.tracks.trackGroup[Constants.FP].trainGroup.add(icTrain);
			// TODO delete
			// model.printAllTrack();//TODO delete

			// initialize of output
			model.output.setTotalEvent(0);
			model.output.setType1BoardingEvent(0);
			model.output.setType2BoardingEvent(0);
			model.output.setType3BoardingEvent(0);
			model.output.setType4BoardingEvent(0);
		}
	}

}
