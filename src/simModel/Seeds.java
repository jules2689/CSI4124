package simModel;

import cern.jet.random.engine.RandomSeedGenerator;

public class Seeds {
	int arrFP; // customer arrival for FP
	int arrSH; // customer arrival for SH
	int arrGI; // customer arrival for GI
	int arrRC; // customer arrival for RC

	int seed2; // comment 2
	int seed3; // comment 3
	int seed4; // comment 4

	public Seeds(RandomSeedGenerator rsg) {
		arrFP = rsg.nextSeed();
		arrSH = rsg.nextSeed();
		arrGI = rsg.nextSeed();
		arrRC = rsg.nextSeed();
	}
}
