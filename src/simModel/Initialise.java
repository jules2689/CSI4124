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
		
		//Initialize an array for the purpose of this method
		model.rcgTrains = new Trains[model.numberOfTrains];
		
		int numRemainderCars = model.numberOfCars%model.numberOfTrains;
		
		for (int i = 0; i < model.numberOfTrains; i++) {
			int numCars = (int)(model.numberOfCars/model.numberOfTrains);
			if (numRemainderCars > 0) {
				numCars++;
				numRemainderCars--;
			}
			Trains train = new Trains(numCars,model.gStations.length);
			train.status = Trains.StatusType.ARRIVED;
			model.rcgTrains[i] = train;
			
			int stationID = i%model.rqTracks.length; //0 - 3
			model.rqTracks[stationID].spInsertQue(train);
		}
		
		// initialize of output
		/*model.output.setTotalEvent(0);
		model.output.setType1BoardingEvent(0);
		model.output.setType2BoardingEvent(0);
		model.output.setType3BoardingEvent(0);
		model.output.setType4BoardingEvent(0);*/
	}

}
