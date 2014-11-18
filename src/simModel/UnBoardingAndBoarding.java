package simModel;

import absmodJ.ConditionalActivity;

public class UnBoardingAndBoarding extends ConditionalActivity {
	SMThemePark model;
	static int id;

	public UnBoardingAndBoarding(SMThemePark park) {
		this.model = park;
	}

	protected static boolean precondition(SMThemePark model) {
		// return none or an id
		return model.udp.stationReadyForUnboarding() != Constants.NO_STATION;
	}

	@Override
	protected double duration() {
		return model.dvp.uLoadUnloadTime(model.boardingOption);
	}

	@Override
	public void startingEvent() {
		id = model.udp.stationReadyForUnboarding();
		Trains train = model.rqTracks[id].tracks.get(0);
		Stations station = model.gStations[id];
		// RQ.Tracks[ID].Trains[0] = BOARDING
		train.status = Trains.StatusType.BOARDING;
		// unboarding:
		// RQ.Tracks[ID].Trains[0].numCustomers
		// RQ.Tracks[ID].Trains[0].numCustomers -
		// RQ.Tracks[ID].Trains[0].leavingCustomers
		train.removeGrp(id, train.getCustomerLeaving(id));

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
			int numLeaving = (int) (numCustomersBoarding * Constants.PERCENTAGE_OF_UNBOARDING[i]);
			if (id == i) {
				train.setCustomerLeaving(id, 0);
			} else {
				train.setCustomerLeaving(id, train.getCustomerLeaving(id)
						+ numLeaving);
			}
		}
	}

	@Override
	protected void terminatingEvent() {
		ExtraBoardingTime extraBoardingTime = new ExtraBoardingTime(this.model,
				id);
		model.spStart(extraBoardingTime);
	}

	/*
	 * protected void setOutputForStation(Station stn) { if (stn.uNumCustomers
	 * == 0) { model.output.incrType1BoardingEvent(); } else if
	 * (stn.uNumCustomers >= 1 && stn.uNumCustomers < 25) {
	 * model.output.incrType2BoardingEvent(); } else if (stn.uNumCustomers >= 25
	 * && stn.uNumCustomers < 50) { model.output.incrType3BoardingEvent(); }
	 * else { model.output.incrType4BoardingEvent(); }
	 * model.output.incrTotalEvent();
	 * 
	 * Debugger.debug("\n\nStats for " + stn.name, 4); Debugger.debug("Has... "
	 * + stn.uNumCustomers, 4); Debugger.debug("Total Events : " +
	 * model.output.getTotalEvent(), 4); Debugger.debug("Total 1 : " +
	 * model.output.getType1BoardingEvent(), 4); Debugger.debug("Total 2 : " +
	 * model.output.getType2BoardingEvent(), 4); Debugger.debug("Total 3 : " +
	 * model.output.getType3BoardingEvent(), 4); Debugger.debug("Total 4 : " +
	 * model.output.getType4BoardingEvent() + "\n\n", 4); }
	 */

}
