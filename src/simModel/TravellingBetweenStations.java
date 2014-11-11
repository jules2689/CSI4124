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
		//TODO: Investigate this method
		Debugger.debug("TravellingBetweenStations==startingEvent==start");//TODO
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
			Debugger.debug("remove 0 of track:" + this.id, 2);
		}

		Debugger.debug("AFTER travelling begin:");
		this.park.printAllTrack();
		Debugger.debug("TravellingBetweenStations==startingEvent==end");//TODO
	}

	@Override
	protected void terminatingEvent() {
		Debugger.debug("TravellingBetweenStations==terminatingEvent==start");//TODO
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
					Debugger.debug("==modify:trackGroup:"+ nextId, 2);
					Debugger.debug("==modify:train index:" + i + "ID:" + t.trainId, 2);
				}
			}
		}

		 Debugger.debug("after terminate travelling:", 2);
		 this.park.printAllTrack();
		 Debugger.debug("TravellingBetweenStations==terminatingEvent==end");//TODO
	}

}
