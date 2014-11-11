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
				// RQ.Tracks[ID].Trains[0] = BOARDING
				headTrain.status = Constants.TRAIN_STATUS_BOARDING;
				// unboarding:
				// RQ.Tracks[ID].Trains[0].numCustomers ��
				// RQ.Tracks[ID].Trains[0].numCustomers -
				// RQ.Tracks[ID].Trains[0].leavingCustomers
				headTrain.numCustomers = headTrain.numCustomers
						- headTrain.getCustomerLeaving();

				// boarding:
				if (park.stations.stationGroup[id].uNumCustomers > headTrain.maxCustomers - headTrain.numCustomers) {
					// train is full, some customer cannot boarding
					park.stations.stationGroup[id].uNumCustomers = park.stations.stationGroup[id].uNumCustomers - (headTrain.maxCustomers - headTrain.numCustomers);
					headTrain.numCustomers = headTrain.maxCustomers;
				} else {
					// train is not full, all customers boarding in the train
					headTrain.numCustomers = headTrain.numCustomers + park.stations.stationGroup[id].uNumCustomers;
					park.stations.stationGroup[id].uNumCustomers = 0;
				}
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
				ExtraBoardingTime extraBoardingTime = new ExtraBoardingTime(
						this.park, id);
				// SP.Start(ExtraBoardingTime)
				park.spStart(extraBoardingTime);
			}
		}
	}

}
