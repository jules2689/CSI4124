package simModel;

import absmodJ.ScheduledAction;

public class ArriveAtStationSH extends ScheduledAction {

	SMThemePark park;

	public ArriveAtStationSH(SMThemePark park) {
		this.park = park;
	}

	@Override
	protected double timeSequence() {
		return park.rvp.uCustomersSH();
	}

	@Override
	protected void actionEvent() {
		Station station = park.stations.stationGroup[Constants.SH];
		station.uNumCustomers++;
	}

}
