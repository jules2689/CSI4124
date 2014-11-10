package simModel;

public class Train {

	static int MAX_CARS = 9;

	protected int maxCustomers;
	protected int numCars; // an integer value between 1 and 9 which represents
							// the number of car
	protected int numCustomers;
	protected int customerLeaving;// customer leaving in the next station
	protected int status;//

	protected final int trainId; // specified the num of train

	public Train(int id, int numCars) {
		this.numCars = numCars; // default length of train
		this.maxCustomers = Constants.MAX_CUSTOMERS_PER_CAR * this.numCars;
		this.trainId = id;
	}

	public void setCustomerLeaving(int customerLeaving) {
		this.customerLeaving = customerLeaving;
	}

	public int getCustomerLeaving() {
		return this.customerLeaving;
	}
}
