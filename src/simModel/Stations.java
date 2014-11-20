package simModel;

public class Stations {
	protected int numCustomers;
	public String name;

	public Stations(String name) {
		this.name = name;
	}

	// Required methods to manipulate the group
	protected void insertGrp(int customers) {
		numCustomers += customers;
	}

	protected boolean removeGrp(int customers) {
		numCustomers -= customers;
		return (true);
	}

}
