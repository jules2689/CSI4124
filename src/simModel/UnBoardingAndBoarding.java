package simModel;

import absmodJ.ConditionalActivity;

public class UnBoardingAndBoarding extends ConditionalActivity {
	SMThemePark model;
	int id;

	public UnBoardingAndBoarding(SMThemePark park) {
		this.model = park;
	}

	protected static boolean precondition(SMThemePark model) {
		// return none or an id
		return model.udp.stationReadyForUnboarding() != Constants.NO_STATION;
	}

	@Override
	protected double duration() {
		return model.dvp.uLoadUnloadTime();
	}

	@Override
	public void startingEvent() {
		id = model.udp.stationReadyForUnboarding();
		Trains train = model.rqTracks[id].trainList.get(0);
		// RQ.Tracks[ID].Trains[0] = BOARDING
		train.status = Trains.StatusType.BOARDING;
		// unboarding:
		// RQ.Tracks[ID].Trains[0].numCustomers
		// RQ.Tracks[ID].Trains[0].numCustomers -
		// RQ.Tracks[ID].Trains[0].leavingCustomers
		train.numCustomers[id] -= train.numLeavingCustomers[id];
		//as discussed in class, boarding is to take place at the end of extra boarding time
	}

	@Override
	protected void terminatingEvent() {
		ExtraBoardingTime extraBoardingTime = new ExtraBoardingTime(this.model,
				id);
		model.spStart(extraBoardingTime);
	}

}
