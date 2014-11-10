package simModel;

import absmodJ.SequelActivity;

public class TravellingBetweenStations extends SequelActivity {

	SMThemePark park;
	int id;
	Train iTrain;

	public TravellingBetweenStations(SMThemePark park, int id) {
		this.park = park;
		this.id = id;
	}

	@Override
	protected double duration() {
		return park.dvp.travelTime(this.id + 1);
	}

	@Override
	public void startingEvent() {
		// System.out.println("TravellingBetweenStations==startingEvent==start");//TODO
		Track iTrack = park.tracks.trackGroup[this.id]; // RQ.Tracks[ID]
		iTrain = iTrack.trainGroup.get(0);// RQ.Tracks[ID].Trains[0]
		if (iTrain != null && iTrain.status == Constants.TRAIN_STATUS_BOARDING) {
			// RQ.Tracks[ID].Trains[0] = TRAVELLING
			iTrain.status = Constants.TRAIN_STATUS_TRAVELLING;
			// SP.insertQue(RQ.Tracks[ID+1], RQ.Tracks[ID].Trains[0])
			int nextId = (this.id + 1) % 4;
			park.tracks.trackGroup[nextId].trainGroup.add(iTrain);
			// SP.RemoveQue(RQ.Tracks[ID].Trains[0])
			park.tracks.trackGroup[this.id].trainGroup.remove(0);
			// System.out.println("remove 0 of track:" + this.id);
		}

		// TODO delete
		// System.out.println("AFTER travelling begin:");
		// this.park.printAllTrack();

		// System.out.println("TravellingBetweenStations==startingEvent==end");//TODO
	}

	@Override
	protected void terminatingEvent() {
		// System.out.println("TravellingBetweenStations==terminatingEvent==start");//TODO
		// RCG.Trains[ID].status = ARRIVED
		int nextId = (this.id + 1) % 4;
		if (iTrain != null && park.tracks.trackGroup[nextId].trainGroup.size() > 0) {
			Train t;
			for (int i = 0; i < park.tracks.trackGroup[nextId].trainGroup.size(); i++) {
				t = park.tracks.trackGroup[nextId].trainGroup.get(i);
				// locate RCG.Trains[ID];
				if (t.trainId == iTrain.trainId) {
					t.status = Constants.TRAIN_STATUS_ARRIVED;
					park.tracks.trackGroup[nextId].trainGroup.set(i, t);
					// TODO delete start
					// System.out.println("==modify:trackGroup:"+ nextId);
					// System.out.println("==modify:train index:" + i + "ID:" +
					// t.trainId);
					// TODO delete end
				}
			}
		}
		// TODO delete
		// System.out.println("after terminate travelling:");
		// this.park.printAllTrack();

		// System.out.println("TravellingBetweenStations==terminatingEvent==end");//TODO
	}

}
