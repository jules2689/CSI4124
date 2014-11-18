package simModel;

public class Trains {

	protected static enum StatusType {
		BOARDING, TRAVELLING, ARRIVED
	};

	protected int numCars; // an integer value between 1 and 9 which represents
							// the number of car

	protected Integer[] numCustomers; // id matches station
	protected Integer[] numLeavingCustomers; // id matches station

	StatusType status;//

	public Trains(int numCars, int numStations) {
		this.numCars = numCars; // default length of train
		numCustomers = new Integer[numStations];
		numLeavingCustomers = new Integer[numStations];
	}

	public int getCustomerLeaving(int id) {
		return numLeavingCustomers[id];
	}
	
	public void setCustomerLeaving(int id, int value){
		numLeavingCustomers[id] = value;
	}

	// Required methods to manipulate the group
	protected void insertGrp(int id, int customers) {
		numCustomers[id] += customers;
	}

	protected boolean removeGrp(int id, int customers) {
		numCustomers[id] -= customers;
		return (true);
	}

	protected int getN() {
		return numCustomers.length;
	} // Attribute n

	protected int getMaxNumberOfCustomers() {
		return numCars * Constants.MAX_CUSTOMERS_PER_CAR;
	}

	protected int getAvailableCapacity() {
		return getMaxNumberOfCustomers() - getN();
	}
}
