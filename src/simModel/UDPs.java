package simModel;

public class UDPs {
	SMThemePark model; // for accessing the clock

	// Constructor
	public UDPs(SMThemePark model) {
		this.model = model;
	}

	// Translate User Defined Procedures into methods
	
	public boolean checkTrainArrived(int id) {
		Tracks track = model.rqTracks[id];
		// at least two trains are on the track
		if (track.tracks != null && track.getN() > 1) {
			Trains t1 = track.tracks.get(0);
			Trains t2 = track.tracks.get(1);
			if (t1.status == Trains.StatusType.ARRIVED && t2.status == Trains.StatusType.ARRIVED) {
				return true;
			}
		}
		return false;
	}

	// List of stations are ready for unboarding
	protected int stationReadyForUnboarding() {
		for (int i = 0; i < model.rqTracks.length; i++) {
			Tracks track = model.rqTracks[i];
			if (track != null && track.getN() > 0) {
				Trains headTrain = track.tracks.get(0);
				if (headTrain.status == Trains.StatusType.ARRIVED) {
					return i;
				}
			}
		}
		return Constants.NO_STATION;
	}
}
