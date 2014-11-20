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
		if (track.trainList != null && track.getN() > 1) {
			Trains t2 = track.trainList.get(1);
			if (t2.status == Trains.StatusType.ARRIVED) {
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
				Trains headTrain = track.trainList.get(0);
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
		Trains train = model.rqTracks[id].trainList.get(0);
		Stations station = model.gStations[id];

		int capacityAvailableForTrain = getAvailableCapacity(train);
		int numCustomersBoarding = 0;

		// boarding:

		if (station.numCustomers >= capacityAvailableForTrain) {

			// train is full, some customer cannot boarding
			numCustomersBoarding = capacityAvailableForTrain;
		} else {
			// train is not full, all customers boarding in the train
			numCustomersBoarding = station.numCustomers;
		}
		train.numCustomers += numCustomersBoarding;
		station.removeGrp(numCustomersBoarding);

		// update customer leaving at next station
		// RQ.Tracks[ID].Trains[0].leavingCustomers
		// RQ.Tracks[ID].Trains[0].numCustomers *
		// Constants.PERCENTAGE_OF_UNBOARDING[ID+1]

		for (int i = 0; i < model.gStations.length; i++) {
			int numLeaving = (int) (numCustomersBoarding * model.dvp
					.getPercentageOfCustomersLeaving(id, i));
			if (i == id) {
				train.numLeavingCustomers[i] = 0;
			} else {
				train.numLeavingCustomers[i] += numLeaving;
			}
		}
	}
	
	public int getCost(){
		int cost = 0;
		cost += model.numberOfTrains * Constants.COST_OF_TRAIN;
		cost += model.numberOfCars * Constants.COST_OF_CAR;

		if (model.boardingOption == Constants.COST_OF_SINGLE_SIDED) {
			cost += model.numberOfCars * Constants.COST_OF_SINGLE_SIDED;
		} else if (model.boardingOption == Constants.DOUBLE_SIDED) {
			cost += model.numberOfCars * Constants.COST_OF_DOUBLE_SIDED;
		}
		return cost;
	}
	
	protected int getMaxNumberOfCustomers(Trains train) {
		return train.numCars * Constants.MAX_CUSTOMERS_PER_CAR;
	}

	protected int getAvailableCapacity(Trains train) {
		return getMaxNumberOfCustomers(train) - train.numCustomers;
	}
}
