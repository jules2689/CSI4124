package simModel;

public class Trains {

	protected static enum StatusType {
		BOARDING, TRAVELLING, ARRIVED
	};

	protected int numCars; // an integer value between 1 and 9 which represents
							// the number of car

	protected int[] numCustomers; // id matches station
	protected int[] numLeavingCustomers; // id matches station

	StatusType status;//

	public Trains(int numCars, int numStations) {
		this.numCars = numCars; // default length of train
		numCustomers = new int[numStations];
		numLeavingCustomers = new int[numStations];
	}

	protected int getN() {
		int sum = 0;
		for (int i = 0; i< this.numCustomers.length; i++) {
			sum += this.numCustomers[i];
		}
		return sum;
	} // Attribute n

	
}
