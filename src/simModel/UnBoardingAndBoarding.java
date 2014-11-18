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
		// RQ.Tracks[ID].Trains[0] = BOARDING
		train.status = Trains.StatusType.BOARDING;
		// unboarding:
		// RQ.Tracks[ID].Trains[0].numCustomers
		// RQ.Tracks[ID].Trains[0].numCustomers -
		// RQ.Tracks[ID].Trains[0].leavingCustomers
		train.removeGrp(id, train.getCustomerLeaving(id));
		//boarding done in extra boarding time
	}

	@Override
	protected void terminatingEvent() {
		ExtraBoardingTime extraBoardingTime = new ExtraBoardingTime(this.model,
				id);
		model.spStart(extraBoardingTime);
	}

}
