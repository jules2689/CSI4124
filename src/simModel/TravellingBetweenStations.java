package simModel;

import absmodJ.SequelActivity;

public class TravellingBetweenStations extends SequelActivity {

	SMThemePark model;
	int id;
	Trains train;

	public TravellingBetweenStations(SMThemePark model, int id) {
		this.model = model;
		this.id = id;
	}

	@Override
	protected double duration() {
		return model.dvp.travelTime((this.id + 1) % model.gStations.length);
	}

	@Override
	public void startingEvent() {
		train = model.rqTracks[id].trainList.get(0);
		// RQ.Tracks[ID].Trains[0] = TRAVELLING
		train.status = Trains.StatusType.TRAVELLING;
		// SP.insertQue(RQ.Tracks[ID+1], RQ.Tracks[ID].Trains[0])
		int nextId = (this.id + 1) % model.gStations.length; // 0-3
		// SP.RemoveQue(RQ.Tracks[ID].Trains[0])
		model.rqTracks[this.id].spRemoveQue();
		model.rqTracks[nextId].spInsertQue(train);
	}

	@Override
	protected void terminatingEvent() {
		// RCG.Trains[ID].status = ARRIVED
		train.status = Trains.StatusType.ARRIVED;
	}

}
