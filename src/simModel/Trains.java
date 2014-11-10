package simModel;

import java.util.HashSet;

public class Trains {
	// Attributes
	protected int numTrains; // Number of trains at the port - this attribute is
								// a parameter

	// For implementing the group, use a List object. //TODO
	// Since all trains are running on the RG.Track,
	// also, for convenience of adding trains, using List instead of HashSet
	// protected List<Train> trainList = new ArrayList<Train>(); // maintains
	// list of Train objects,
	protected HashSet<Train> trainGroup = new HashSet<Train>();

	// Required methods to manipulate the group
	protected void insertGrp(Train iTrain) {
		trainGroup.add(iTrain);
	}

	protected boolean removeGrp(Train iTrain) {
		return (trainGroup.remove(iTrain));
	}

	protected int getN() {
		return trainGroup.size();
	} // Attribute n
}
