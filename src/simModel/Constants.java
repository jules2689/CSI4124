package simModel;

public class Constants {
	/* Constants */
	// Define constants as static
	// Example: protected final static double realConstant = 8.0;
	protected final static int FP = 0; // Frog Pond
	protected final static int SH = 1; // Skunk Hollow
	protected final static int GI = 2; // Gator Island
	protected final static int RC = 3; // Raccoon Corner

	protected final static int MAX_CUSTOMERS_PER_CAR = 25; // the maximum number of customers per car

	// TODO modify document
	protected final static int TRAIN_STATUS_BOARDING = 0;
	protected final static int TRAIN_STATUS_TRAVELLING = 1;
	protected final static int TRAIN_STATUS_ARRIVED = 2;

	// TODO add to document
	protected final static double[] PERCENTAGE_OF_UNBOARDING = new double[] {
			0.30, 0.2425, 0.2625, 0.195 };
}
