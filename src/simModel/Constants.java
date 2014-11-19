package simModel;

public class Constants {
	/* Constants */
	// Define constants as static
	// Example: protected final static double realConstant = 8.0;
	protected final static int FP = 0; // Frog Pond
	protected final static int SH = 1; // Skunk Hollow
	protected final static int GI = 2; // Gator Island
	protected final static int RC = 3; // Raccoon Corner
	protected final static int NO_STATION = -1; //no station
	
	//TODO: Docs
	protected final static String FP_S = "Frog Pond"; // Frog Pond
	protected final static String SH_S = "Skunk Hollow"; // Skunk Hollow
	protected final static String GI_S = "Gator Island"; // Gator Island
	protected final static String RC_S = "Raccoon Corner"; // Raccoon Corner

	//TODO: Docs
	protected final static int MAX_CUSTOMERS_PER_CAR = 25; // the maximum number of customers per car
	public final static int MIN_NUMBER_OF_TRAINS = 4; // the minimum number of trains in the system
	public final static int MAX_NUMBER_OF_TRAINS = 8; // the maximum number of trains in the system
	public final static int MIN_NUMBER_OF_CARS = 4; // the minimum number of cars in the system; experiment starts with min 4
	public final static int MAX_NUMBER_OF_CARS = 9; // the maximum number of cars in the system

	//TODO add to document
	protected final static int COST_OF_TRAIN = 300;
	protected final static int COST_OF_CAR = 500;
	protected final static int COST_OF_BOARDING_OPTION_0 = 0;
	protected final static int COST_OF_BOARDING_OPTION_1 = 20;
}
