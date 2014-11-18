package simModel;

import java.util.ArrayList;

public class Tracks {

	// protected int trackSegID;
	// protected int travelTime;

	protected ArrayList<Trains> tracks = new ArrayList<Trains>();

	// Required methods to manipulate the queue
	protected void spInsertQue(Trains train) {
		tracks.add(train);
	}

	protected Trains spRemoveQue() {
		if (tracks.size() > 0) {
			return tracks.remove(0);
		} else {
			return null;
		}
	}

	protected int getN() {
		return tracks.size();
	}

}
