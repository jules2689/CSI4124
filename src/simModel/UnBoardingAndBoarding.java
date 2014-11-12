package simModel;

import java.util.List;

import absmodJ.ConditionalActivity;

public class UnBoardingAndBoarding extends ConditionalActivity {
	SMThemePark park;
	List<Integer> idList;

	public UnBoardingAndBoarding(SMThemePark park) {
		this.park = park;
	}

	protected static boolean precondition(SMThemePark park) {
		Debugger.debug("===UnBoardingAndBoarding===precondition===START");
		boolean returnValue = false;

		List<Integer> idList = park.udp.stationReadyForUnboarding();
		if (null != idList && idList.size() > 0) {
			returnValue = true;
		}

		Debugger.debug("===UnBoardingAndBoarding===precondition===end:" + returnValue);
		return returnValue;
	}

	@Override
	protected double duration() {
		// DVP.uLoadUnloadTime(boardingOption)
		return park.dvp.uLoadUnloadTime(park.boardingOption);
	}

	@Override
	public void startingEvent() {
		Debugger.debug("===UnBoardingAndBoarding===startingEvent===START");
		Debugger.debug("===UnBoardingAndBoarding===printTrack:", 2);
		park.printAllTrack();

		this.idList = park.udp.stationReadyForUnboarding();// get all station identifier

		if (this.idList != null && this.idList.size() > 0) {
			int id;
			for (int k = 0; k < idList.size(); k++) {
				id = idList.get(k).intValue();// ID
				Track iTrack = park.tracks.trackGroup[id]; // RQ.Tracks[ID]
				Train headTrain = iTrack.trainGroup.get(0);// RQ.Tracks[ID].Trains[0]
				Station station = park.stations.stationGroup[id];
				// RQ.Tracks[ID].Trains[0] = BOARDING
				headTrain.status = Constants.TRAIN_STATUS_BOARDING;
				// unboarding:
				// RQ.Tracks[ID].Trains[0].numCustomers ��
				// RQ.Tracks[ID].Trains[0].numCustomers -
				// RQ.Tracks[ID].Trains[0].leavingCustomers
				headTrain.numCustomers = headTrain.numCustomers - headTrain.getCustomerLeaving();
				int capacityAvailableForTrain = headTrain.maxCustomers - headTrain.numCustomers;
				
				// boarding:
				if (station.uNumCustomers >= capacityAvailableForTrain) {
					// train is full, some customer cannot boarding
					station.uNumCustomers = station.uNumCustomers - capacityAvailableForTrain;
					headTrain.numCustomers = headTrain.maxCustomers;
				} else {
					// train is not full, all customers boarding in the train
					headTrain.numCustomers = headTrain.numCustomers + station.uNumCustomers;
					station.uNumCustomers = 0;
				}
				
				setOutputForStation(station);
				// update customer leaving at next station
				// RQ.Tracks[ID].Trains[0].leavingCustomers ��
				// RQ.Tracks[ID].Trains[0].numCustomers *
				// Constants.PERCENTAGE_OF_UNBOARDING[ID+1]
				int nextId = (id + 1) % 4;
				headTrain.customerLeaving = (int) (headTrain.numCustomers * Constants.PERCENTAGE_OF_UNBOARDING[nextId]);
				park.tracks.trackGroup[id].trainGroup.set(0, headTrain);
			}

			Debugger.debug("After boarding print:", 2);
			this.park.printAllTrack();
			Debugger.debug("===UnBoardingAndBoarding===startingEvent===end");
		}
	}

	@Override
	protected void terminatingEvent() {
		if (this.idList != null && this.idList.size() > 0) {
			int id;
			for (int k = 0; k < this.idList.size(); k++) {
				id = idList.get(k).intValue();// ID
				ExtraBoardingTime extraBoardingTime = new ExtraBoardingTime(this.park, id);
				// SP.Start(ExtraBoardingTime)
				park.spStart(extraBoardingTime);
			}
		}
	}
	
	protected void setOutputForStation(Station stn) {
		if (stn.uNumCustomers == 0) {
			park.output.incrType1BoardingEvent();
		} else if (stn.uNumCustomers >= 1 && stn.uNumCustomers < 25) {
			park.output.incrType2BoardingEvent();
		} else if (stn.uNumCustomers >= 25 && stn.uNumCustomers < 50) {
			park.output.incrType3BoardingEvent();
		} else {
			park.output.incrType4BoardingEvent();
		}
		park.output.incrTotalEvent();
		
		Debugger.debug("\n\nStats for " + stn.name, 4);
		Debugger.debug("Has... " + stn.uNumCustomers, 4);
		Debugger.debug("Total Events : " + park.output.getTotalEvent(),4);
		Debugger.debug("Total 1 : " + park.output.getType1BoardingEvent(),4);
		Debugger.debug("Total 2 : " + park.output.getType2BoardingEvent(),4);
		Debugger.debug("Total 3 : " + park.output.getType3BoardingEvent(),4);
		Debugger.debug("Total 4 : " + park.output.getType4BoardingEvent() + "\n\n",4);
	}

}
