package simModel;

class Output {

	SMThemePark park;

	// Constructor
	Output(SMThemePark model) {
		this.park = model;
		// Setup trajectory set
		// trjRGBerthsN = new OutputSequence("gBerthsN");

		// First point in output sequence - R.BerthGroup is empty at t=0
		// lastGBerthsN = 0;
		// trjRGBerthsN.put(0.0,0.0);
		// phiICTankerTotalWait = new OutputSequence("TankerWait");
	}

	// Use OutputSequence class to define Trajectory and Sample Sequences
	// Trajectory Sequences

	// Sample Sequences

	// DSOVs available in the OutputSequence objects
	// If seperate methods required to process Trajectory or Sample
	// Sequences - add them here

	// SSOVs
	private int totalEvent = 0;// TODO modify document
	private int type1BoardingEvent = 0;
	private int type2BoardingEvent = 0;
	private int type3BoardingEvent = 0;
	private int type4BoardingEvent = 0;

	// DSOVs
	private double perctOfType1Scen;
	private double perctOfType2Scen;
	private double perctOfType3Scen;
	private double perctOfType4Scen;

	public int getTotalEvent() {
		return totalEvent;
	}

	public int getType1BoardingEvent() {
		return type1BoardingEvent;
	}

	public int getType2BoardingEvent() {
		return type2BoardingEvent;
	}

	public int getType3BoardingEvent() {
		return type3BoardingEvent;
	}

	public int getType4BoardingEvent() {
		return type4BoardingEvent;
	}

	public double getPerctOfType1Scen() {
		this.perctOfType1Scen = 100 * this.type1BoardingEvent / (double)this.totalEvent;
		return this.perctOfType1Scen;
	}

	public double getPerctOfType2Scen() {
		this.perctOfType2Scen = 100 * this.type2BoardingEvent / (double)this.totalEvent;
		return this.perctOfType2Scen;
	}

	public double getPerctOfType3Scen() {
		this.perctOfType3Scen = 100 * this.type3BoardingEvent / (double)this.totalEvent;
		return this.perctOfType3Scen;
	}

	public double getPerctOfType4Scen() {
		this.perctOfType4Scen = 100 * this.type4BoardingEvent / (double)this.totalEvent;
		return this.perctOfType4Scen;
	}

	public void setTotalEvent(int totalEvent) {
		this.totalEvent = totalEvent;
	}

	public void setType1BoardingEvent(int type1BoardingEvent) {
		this.type1BoardingEvent = type1BoardingEvent;
	}

	public void setType2BoardingEvent(int type2BoardingEvent) {
		this.type2BoardingEvent = type2BoardingEvent;
	}

	public void setType3BoardingEvent(int type3BoardingEvent) {
		this.type3BoardingEvent = type3BoardingEvent;
	}

	public void setType4BoardingEvent(int type4BoardingEvent) {
		this.type4BoardingEvent = type4BoardingEvent;
	}
	
	public void incrTotalEvent() {
		totalEvent++;
	}
	
	public void incrType1BoardingEvent() {
		this.type1BoardingEvent++;
	}

	public void incrType2BoardingEvent() {
		this.type2BoardingEvent++;
	}

	public void incrType3BoardingEvent(){
		this.type3BoardingEvent++;
	}

	public void incrType4BoardingEvent() {
		this.type4BoardingEvent++;
	}

	// public void reset(){
	// this.setTotalEvent(0);
	// this.setType1BoardingEvent(0);
	// this.setType2BoardingEvent(0);
	// this.setType3BoardingEvent(0);
	// this.setType4BoardingEvent(0);
	// }
}
