package simModel;

import absmodJ.ScheduledAction;

public class ArriveAtStationRC extends ScheduledAction {

	SMThemePark park;

	public ArriveAtStationRC(SMThemePark park) {
		this.park = park;
	}

	@Override
	protected double timeSequence() {
		return park.rvp.uCustomersGI();
	}

	@Override
	protected void actionEvent() {
		Station station = park.stations.stationGroup[Constants.RC];
		station.uNumCustomers++;
	}

}
