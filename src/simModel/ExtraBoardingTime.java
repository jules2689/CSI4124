package simModel;

import absmodJ.ExtSequelActivity;

public class ExtraBoardingTime extends ExtSequelActivity {
	SMThemePark park;
	int id;

	public ExtraBoardingTime(SMThemePark park, int id) {
		this.park = park;
		this.id = id;
	}

	@Override
	public int interruptionPreCond() {
		// System.out.println("==ExtraBoardingTime==interruptionPreCond==start");//TODO
		// delete
		int flagValue = 0;
		// fixedBoardingTime = TRUE OR
		// UP.checkTrainArrived(G.Stations[ID]) = TRUE OR
		// RQ.Tracks[ID].Trains[0].NumCustomers =
		// RQ.Tracks[ID].Trains[0].maxCustomers
		if (park.fixBoardingTime) {
			flagValue = 1;
		} else if (park.udp.checkTrainArrived(id)) {
			flagValue = 2;
		} else if (park.tracks.trackGroup[id].trainGroup.size() > 0) {
			Train iTrain = park.tracks.trackGroup[id].trainGroup.get(0);
			if (iTrain.numCustomers == iTrain.maxCustomers) {
				flagValue = 3;
			}
		}
		// System.out.println("==ExtraBoardingTime==interruptionPreCond==end:" +
		// flagValue);//TODO delete
		return flagValue;
	}

	@Override
	public void interruptionSCS(int arg0) {
		// System.out.println("==ExtraBoardingTime==interruptionSCS==start");//TODO
		// delete
		// calculate the situation event
		Station stn = park.stations.stationGroup[id];
		setOutputForStation(stn);

		// SP.Start(TravellingBetweenStations)
		TravellingBetweenStations travelAct = new TravellingBetweenStations(
				this.park, this.id);
		park.spStart(travelAct);

		// System.out.println("==ExtraBoardingTime==interruptionSCS==end");//TODO
		// delete
	}

	@Override
	protected double duration() {
		return 1; // 1 minute;
	}

	@Override
	public void startingEvent() {
		// System.out.println("==ExtraBoardingTime==startingEvent==do nothing==");//TODO
		// delete
	}

	@Override
	protected void terminatingEvent() {
		// System.out.println("==ExtraBoardingTime==terminatingEvent==start");//TODO
		// delete
		// calculate the situation event
		Station stn = park.stations.stationGroup[id];
		setOutputForStation(stn);

		// SP.Start(TravellingBetweenStations)
		TravellingBetweenStations travelAct = new TravellingBetweenStations(
				this.park, this.id);
		park.spStart(travelAct);
		// System.out.println("==ExtraBoardingTime==terminatingEvent==end");//TODO
		// delete
	}

	protected void setOutputForStation(Station stn) {
		if (stn.uNumCustomers == 0) {
			park.output.setType1BoardingEvent(park.output.getType1BoardingEvent() + 1);
		} else if (stn.uNumCustomers >= 1 && stn.uNumCustomers < 25) {
			park.output.setType2BoardingEvent(park.output.getType2BoardingEvent() + 1);
		} else if (stn.uNumCustomers >= 25 && stn.uNumCustomers < 50) {
			park.output.setType3BoardingEvent(park.output.getType3BoardingEvent() + 1);
		} else {
			park.output.setType4BoardingEvent(park.output.getType4BoardingEvent() + 1);
		}

		park.output.setTotalEvent(park.output.getTotalEvent() + 1);
	}

}
