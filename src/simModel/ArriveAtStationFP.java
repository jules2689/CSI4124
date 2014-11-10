package simModel;

import absmodJ.ScheduledAction;

public class ArriveAtStationFP extends ScheduledAction {

	SMThemePark park;

	public ArriveAtStationFP(SMThemePark park) {
		this.park = park;
	}

	@Override
	protected double timeSequence() {
		return park.rvp.uCustomersFP();
	}

	@Override
	protected void actionEvent() {
		Station station = park.stations.stationGroup[Constants.FP];
		station.uNumCustomers++;
	}

}
