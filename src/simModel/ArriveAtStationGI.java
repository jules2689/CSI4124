package simModel;

import absmodJ.ScheduledAction;

public class ArriveAtStationGI extends ScheduledAction {

	SMThemePark park;

	public ArriveAtStationGI(SMThemePark park) {
		this.park = park;
	}

	@Override
	protected double timeSequence() {
		return park.rvp.uCustomersGI();
	}

	@Override
	protected void actionEvent() {
		Station station = park.stations.stationGroup[Constants.GI];
		station.uNumCustomers++;
	}

}
