package simModel;

public class UDPs {
	SMThemePark model; // for accessing the clock

	// Constructor
	public UDPs(SMThemePark model) {
		this.model = model;
	}

	// Translate User Defined Procedures into methods

	public boolean checkTrainArrived(int id) {
		Tracks track = model.rqTracks[id];
		// at least two trains are on the track
		if (track.tracks != null && track.getN() > 1) {
			Trains t1 = track.tracks.get(0);
			Trains t2 = track.tracks.get(1);
			if (t1.status == Trains.StatusType.ARRIVED
					&& t2.status == Trains.StatusType.ARRIVED) {
				return true;
			}
		}
		return false;
	}

	// List of stations are ready for unboarding
	protected int stationReadyForUnboarding() {
		for (int i = 0; i < model.rqTracks.length; i++) {
			Tracks track = model.rqTracks[i];
			if (track != null && track.getN() > 0) {
				Trains headTrain = track.tracks.get(0);
				if (headTrain.status == Trains.StatusType.ARRIVED) {
					return i;
				}
			}
		}
		return Constants.NO_STATION;
	}

	protected void setOutputForStation(Stations stn) {
		if (stn.numCustomers == 0) {
			model.output.incrType1BoardingEvent();
		} else if (stn.numCustomers >= 1 && stn.numCustomers < 25) {
			model.output.incrType2BoardingEvent();
		} else if (stn.numCustomers >= 25 && stn.numCustomers < 50) {
			model.output.incrType3BoardingEvent();
		} else {
			model.output.incrType4BoardingEvent();
		}
		model.output.incrTotalEvent();
	}

	protected void boardAtStation(int id) {
		Trains train = model.rqTracks[id].tracks.get(0);
		Stations station = model.gStations[id];

		int capacityAvailableForTrain = train.getAvailableCapacity();
		int numCustomersBoarding = 0;

		// boarding:
		if (station.numCustomers >= capacityAvailableForTrain) {
			// train is full, some customer cannot boarding
			numCustomersBoarding = capacityAvailableForTrain;
		} else {
			// train is not full, all customers boarding in the train
			numCustomersBoarding = station.numCustomers;
		}
		train.insertGrp(id, numCustomersBoarding);
		station.removeGrp(numCustomersBoarding);

		// update customer leaving at next station
		// RQ.Tracks[ID].Trains[0].leavingCustomers
		// RQ.Tracks[ID].Trains[0].numCustomers *
		// Constants.PERCENTAGE_OF_UNBOARDING[ID+1]

		for (int i = 0; i < model.gStations.length; i++) {
			int nextID = (id + 1) % model.gStations.length;
			int numLeaving = (int) (numCustomersBoarding * model.dvp
					.getPercentageOfCustomersLeaving(id, nextID));
			train.setCustomerLeaving(id, train.getCustomerLeaving(id)
					+ numLeaving);
		}
	}
}
