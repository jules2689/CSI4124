import absmodJ.ConfidenceInterval;

public class TableHelper {

	public static void printTable(ConfidenceInterval cfDiff21ForCosts,
			ConfidenceInterval cfDiff31ForCosts,
			ConfidenceInterval cfDiff41ForCosts,
			ConfidenceInterval cfDiff21ForTrains,
			ConfidenceInterval cfDiff31ForTrains,
			ConfidenceInterval cfDiff41ForTrains,
			ConfidenceInterval cfDiff21ForCars,
			ConfidenceInterval cfDiff31ForCars,
			ConfidenceInterval cfDiff41ForCars) {
		String breakLine = "--------------------------------------------------------------------------------------------------------------------------------------------------------";

		// Create the table
		ConfidenceInterval[] intervals = new ConfidenceInterval[] {
				cfDiff21ForCosts, cfDiff31ForCosts, cfDiff41ForCosts};//,
				//cfDiff21ForTrains, cfDiff31ForTrains, cfDiff41ForTrains,
				//cfDiff21ForCars, cfDiff31ForCars, cfDiff41ForCars };		
		
		System.out.println(breakLine);
		System.out.printf("                         "); // First column for run
														// numbers
		System.out
				.printf("       y(n)         ||       S(n)         ||       zeta       ||");
		System.out
				.printf("       CI Min       ||       CI Max       ||       r\n");
		System.out.printf(breakLine);

		
		
		String [] names = {"cfDiff21ForCosts","cfDiff31ForCosts","cfDiff41ForCosts"};//,"cfDiff21ForTrains", "cfDiff31ForTrains","cfDiff41ForTrains","cfDiff21ForCars","cfDiff31ForCars","cfDiff41ForCars"};
		for (int i =0; i < intervals.length; i++){
			String name = names[i];
			double [] values = {
					intervals[i].getPointEstimate(),
					intervals[i].getStdDev(),
					intervals[i].getZeta(),
					intervals[i].getCfMin(),
					intervals[i].getCfMax(),
					intervals[i].getZeta() / intervals[i].getPointEstimate()
			};
			printTableRow(name, values);
		}

		System.out.println("\n" + breakLine);

	}

	private static void printTableRows(ConfidenceInterval[] confidenceIntervals) {
		int length = confidenceIntervals[0].getValues().length;
		for (int i = 0; i < length; i++) {
			System.out.printf("\n" + fixedLengthString(Integer.toString(i), 18)
					+ " | ");
			for (int j = 0; j < confidenceIntervals.length; j++) {
				System.out.printf("      %9.3f      ",
						confidenceIntervals[j].getValues()[i]);
			}
		}
	}

	private static void printTableRow(String name, double[] array) {
		System.out.printf("\n" + fixedLengthString(name, 18) + " | ");
		for (int i = 0; i < array.length; i++) {
			double x = array[i];
			System.out.printf("      %9.3f      ", x);
		}
	}

	private static String fixedLengthString(String string, int length) {
		return String.format("%1$" + length + "s", string);
	}

}
