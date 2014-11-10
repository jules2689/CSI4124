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
		// System.out.println("===UnBoardingAndBoarding===precondition===START");//TODO
		// DELETE
		boolean returnValue = false;

		List<Integer> idList = park.udp.stationReadyForUnboarding();
		if (null != idList && idList.size() > 0) {
			returnValue = true;
		}

		// System.out.println("===UnBoardingAndBoarding===precondition===end:" +
		// returnValue);//TODO DELETE
		return returnValue;
	}

	@Override
	protected double duration() {
		// DVP.uLoadUnloadTime(boardingOption)
		return park.dvp.uLoadUnloadTime(park.boardingOption);
	}

	@Override
	public void startingEvent() {
		// System.out.println("===UnBoardingAndBoarding===startingEvent===START");//TODO
		// DELETE

		// TODO delete
		// System.out.println("===UnBoardingAndBoarding===printTrack:");
		// park.printAllTrack();

		this.idList = park.udp.stationReadyForUnboarding();// get all station
															// identifier

		// TODO delete start
		// if(null != this.idList && this.idList.size()>0){
		// System.out.println("idList is not null");
		// for(int k = 0; k < idList.size(); k++){
		// System.out.println(this.idList.get(k).toString());
		// }
		// }
		// TODO delete end

		if (this.idList != null && this.idList.size() > 0) {
			int id;
			for (int k = 0; k < idList.size(); k++) {
				id = ((Integer) idList.get(k)).intValue();// ID
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
				if (park.stations.stationGroup[id].uNumCustomers > headTrain.maxCustomers
						- headTrain.numCustomers) {
					// train is full, some customer cannot boarding
					park.stations.stationGroup[id].uNumCustomers = park.stations.stationGroup[id].uNumCustomers
							- (headTrain.maxCustomers - headTrain.numCustomers);
					headTrain.numCustomers = headTrain.maxCustomers;
				} else {
					// train is not full, all customers boarding in the train
					headTrain.numCustomers = headTrain.numCustomers
							+ park.stations.stationGroup[id].uNumCustomers;
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

			// System.out.println("After boarding print:");
			// this.park.printAllTrack();//TODO DELETE

			// System.out.println("===UnBoardingAndBoarding===startingEvent===end");//TODO
			// DELETE
		}

		// for(int i = 0; i < park.tracks.trackGroup.length; i++){
		// Track iTrack = park.tracks.trackGroup[i]; //RQ.Tracks[ID]
		// if(iTrack.trainGroup.size() > 0){
		// Train headTrain = iTrack.trainGroup.get(0);//RQ.Tracks[ID].Trains[0]
		// if(headTrain.status == Constants.TRAIN_STATUS_ARRIVED){
		// //RQ.Tracks[ID].Trains[0] = BOARDING
		// headTrain.status = Constants.TRAIN_STATUS_BOARDING;
		// //unboarding:
		// //RQ.Tracks[ID].Trains[0].numCustomers ��
		// //RQ.Tracks[ID].Trains[0].numCustomers -
		// RQ.Tracks[ID].Trains[0].leavingCustomers
		// headTrain.numCustomers = headTrain.numCustomers -
		// headTrain.getCustomerLeaving();
		//
		// //boarding:
		// if(park.stations.stationGroup[i].uNumCustomers >
		// headTrain.maxCustomers - headTrain.numCustomers){
		// //train is full, some customer cannot boarding
		// park.stations.stationGroup[i].uNumCustomers =
		// park.stations.stationGroup[i].uNumCustomers
		// - ( headTrain.maxCustomers - headTrain.numCustomers ) ;
		// headTrain.numCustomers = headTrain.maxCustomers;
		// }else{
		// //train is not full, all customers boarding in the train
		// headTrain.numCustomers = headTrain.numCustomers +
		// park.stations.stationGroup[i].uNumCustomers;
		// park.stations.stationGroup[i].uNumCustomers = 0;
		// }
		// //update customer leaving at next station
		// //RQ.Tracks[ID].Trains[0].leavingCustomers ��
		// //RQ.Tracks[ID].Trains[0].numCustomers *
		// Constants.PERCENTAGE_OF_UNBOARDING[ID+1]
		// headTrain.customerLeaving = (int) (headTrain.numCustomers
		// * Constants.PERCENTAGE_OF_UNBOARDING[i + 1]);
		// }
		// }
		// }
	}

	@Override
	protected void terminatingEvent() {
		if (this.idList != null && this.idList.size() > 0) {
			int id;
			for (int k = 0; k < this.idList.size(); k++) {
				id = ((Integer) idList.get(k)).intValue();// ID
				ExtraBoardingTime extraBoardingTime = new ExtraBoardingTime(
						this.park, id);
				// SP.Start(ExtraBoardingTime)
				park.spStart(extraBoardingTime);
			}
		}
	}

}
