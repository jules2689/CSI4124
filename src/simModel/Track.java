package simModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Track {

	// protected int trackSegID;
	// protected int travelTime;

	protected List<Train> trainGroup = new ArrayList<Train>();
	protected int n;

	// Required methods to manipulate the queue
	protected void insertQue(Train icTrain) {
		trainGroup.add(icTrain);
	}

	protected boolean removeQue() {
		if (trainGroup.size() > 0) {
			trainGroup.remove(0);
			return true;
		} else {
			return false;
		}
	}

	protected int getN() {
		this.n = trainGroup.size();
		return n;
	}

}
