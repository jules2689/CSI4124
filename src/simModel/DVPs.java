package simModel;

class DVPs {
	SMThemePark model; // for accessing the clock

	// Constructor
	public DVPs(SMThemePark model) {
		this.model = model;
	}

	// ����boardingOption����ʱ��
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

	//TODO?
	// Translate deterministic value procedures into methods
	/*
	 * ------------------------------------------------- Example protected
	 * double getEmpNum() // for getting next value of EmpNum(t) { double
	 * nextTime; if(model.clock == 0.0) nextTime = 90.0; else if(model.clock ==
	 * 90.0) nextTime = 210.0; else if(model.clock == 210.0) nextTime = 420.0;
	 * else if(model.clock == 420.0) nextTime = 540.0; else nextTime = -1.0; //
	 * stop scheduling return(nextTime); }
	 * ------------------------------------------------------------
	 */
}
