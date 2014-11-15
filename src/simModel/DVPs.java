package simModel;

class DVPs {
	SMThemePark model; // for accessing the clock

	// Constructor
	public DVPs(SMThemePark model) {
		this.model = model;
	}

	// boardingOption
	protected double uLoadUnloadTime(int boardingOption) {
		if (boardingOption == 1) {
			return 1.25; // double sided
		} else {
			return 2.0; // single sided
		}
	}

	protected double travelTime(int nextId) {
		switch (nextId) {
		case Constants.SH:
			return 5.0;
		case Constants.GI:
			return 8.0;
		case Constants.RC:
			return 7.0;
		default:
			return 6.0;
		}
	}

	protected double getEmpNum() // for getting next value of EmpNum(t)
	{
		double nextTime;
		if (model.getClock() <= 660.0) {
			nextTime = model.getClock() + 60.0;
		} else {
			nextTime = -1;
		}

		return nextTime;
	}
}
