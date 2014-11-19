package simModel;

import java.util.ArrayList;

public class Tracks {

	// protected int trackSegID;
	// protected int travelTime;

	protected ArrayList<Trains> trainList = new ArrayList<Trains>();

	// Required methods to manipulate the queue
	protected void spInsertQue(Trains train) {
		trainList.add(train);
	}

	protected Trains spRemoveQue() {
		if (trainList.size() > 0) {
			return trainList.remove(0);
		} else {
			return null;
		}
	}

	protected int getN() {
		return trainList.size();
	}

}
