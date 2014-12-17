import absmodJ.ConfidenceInterval;

public class TableHelper {
	
 public static void printTable(ConfidenceInterval cfDiff21ForType1, ConfidenceInterval cfDiff31ForType1, ConfidenceInterval cfDiff41ForType1, ConfidenceInterval cfDiff21ForType2, ConfidenceInterval cfDiff31ForType2, ConfidenceInterval cfDiff41ForType2,  ConfidenceInterval cfDiff21ForType3, ConfidenceInterval cfDiff31ForType3, ConfidenceInterval cfDiff41ForType3, ConfidenceInterval cfDiff21ForType4, ConfidenceInterval cfDiff31ForType4, ConfidenceInterval cfDiff41ForType4)
 {
	String breakLine = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
    
    // Create the table
    System.out.println(breakLine);
    System.out.printf("                         "); // First column for run numbers
    System.out.printf(" cfDiff21ForType1 || cfDiff31ForType1 || cfDiff41ForType1 ||");
    System.out.printf(" cfDiff21ForType2 || cfDiff31ForType2 || cfDiff41ForType2 ||");
    System.out.printf(" cfDiff21ForType3 || cfDiff31ForType3 || cfDiff41ForType3 ||");
    System.out.printf(" cfDiff21ForType4 || cfDiff31ForType4 || cfDiff41ForType4\n");
    System.out.printf(breakLine);
    
    printTableRows(new ConfidenceInterval[]{cfDiff21ForType1, cfDiff31ForType1, cfDiff41ForType1, cfDiff21ForType2, cfDiff31ForType2, cfDiff41ForType2, cfDiff21ForType3, cfDiff31ForType3, cfDiff41ForType3, cfDiff21ForType4, cfDiff31ForType4, cfDiff41ForType4});
    
    System.out.printf("\n" + breakLine);
    
    double pointEstimates[] = {cfDiff21ForType1.getPointEstimate(), cfDiff31ForType1.getPointEstimate(),  cfDiff41ForType1.getPointEstimate(), 
 		   					   cfDiff21ForType2.getPointEstimate(), cfDiff31ForType2.getPointEstimate(),  cfDiff41ForType2.getPointEstimate(), 
 		   					   cfDiff21ForType3.getPointEstimate(), cfDiff31ForType3.getPointEstimate(),  cfDiff41ForType3.getPointEstimate(),
 		   					   cfDiff21ForType4.getPointEstimate(), cfDiff31ForType4.getPointEstimate(),  cfDiff41ForType4.getPointEstimate()};
    
    printTableRow("y(n)", pointEstimates);
    
    double variances[] = {cfDiff21ForType1.getVariance(), cfDiff31ForType1.getVariance(),  cfDiff41ForType1.getVariance(), 
				          cfDiff21ForType2.getVariance(), cfDiff31ForType2.getVariance(),  cfDiff41ForType2.getVariance(), 
				          cfDiff21ForType3.getVariance(), cfDiff31ForType3.getVariance(),  cfDiff41ForType3.getVariance(),
				          cfDiff21ForType4.getVariance(), cfDiff31ForType4.getVariance(),  cfDiff41ForType4.getVariance()};
    
    printTableRow("S(n)", variances);
    
    double zetas[] = {cfDiff21ForType1.getZeta(), cfDiff31ForType1.getZeta(),  cfDiff41ForType1.getZeta(), 
	        	 	  cfDiff21ForType2.getZeta(), cfDiff31ForType2.getZeta(),  cfDiff41ForType2.getZeta(), 
	        	 	  cfDiff21ForType3.getZeta(), cfDiff31ForType3.getZeta(),  cfDiff41ForType3.getZeta(),
	        	 	  cfDiff21ForType4.getZeta(), cfDiff31ForType4.getZeta(),  cfDiff41ForType4.getZeta()};
    
    printTableRow("ζ(n)", zetas);
    
    double cfmins[] = {cfDiff21ForType1.getCfMin(), cfDiff31ForType1.getCfMin(),  cfDiff41ForType1.getCfMin(), 
	 		 		   cfDiff21ForType2.getCfMin(), cfDiff31ForType2.getCfMin(),  cfDiff41ForType2.getCfMin(), 
	 		 		   cfDiff21ForType3.getCfMin(), cfDiff31ForType3.getCfMin(),  cfDiff41ForType3.getCfMin(),
	 		 		   cfDiff21ForType4.getCfMin(), cfDiff31ForType4.getCfMin(),  cfDiff41ForType4.getCfMin()};
    
    printTableRow("CI Min", cfmins);
    
    double cfmaxes[] = {cfDiff21ForType1.getCfMax(), cfDiff31ForType1.getCfMax(),  cfDiff41ForType1.getCfMax(), 
	 			  		cfDiff21ForType2.getCfMax(), cfDiff31ForType2.getCfMax(),  cfDiff41ForType2.getCfMax(), 
	 			  		cfDiff21ForType3.getCfMax(), cfDiff31ForType3.getCfMax(),  cfDiff41ForType3.getCfMax(),
	 			  		cfDiff21ForType4.getCfMax(), cfDiff31ForType4.getCfMax(),  cfDiff41ForType4.getCfMax()};
    
    printTableRow("CI Max", cfmaxes);
    
    double r[] = {cfDiff21ForType1.getZeta()/cfDiff21ForType1.getPointEstimate(), cfDiff31ForType1.getZeta()/cfDiff31ForType1.getPointEstimate(),  cfDiff41ForType1.getZeta()/cfDiff41ForType1.getPointEstimate(), 
 		   		  cfDiff21ForType2.getZeta()/cfDiff21ForType2.getPointEstimate(), cfDiff31ForType2.getZeta()/cfDiff31ForType2.getPointEstimate(),  cfDiff41ForType2.getZeta()/cfDiff41ForType2.getPointEstimate(), 
 		   		  cfDiff21ForType3.getZeta()/cfDiff21ForType3.getPointEstimate(), cfDiff31ForType3.getZeta()/cfDiff31ForType3.getPointEstimate(),  cfDiff41ForType3.getZeta()/cfDiff41ForType3.getPointEstimate(),
 		   		  cfDiff21ForType4.getZeta()/cfDiff21ForType4.getPointEstimate(), cfDiff31ForType4.getZeta()/cfDiff31ForType4.getPointEstimate(),  cfDiff41ForType4.getZeta()/cfDiff41ForType4.getPointEstimate(),};

    printTableRow("r", r);
    
    System.out.println("\n" + breakLine);

}

private static void printTableRows(ConfidenceInterval[] confidenceIntervals)
{
	int length = confidenceIntervals[0].getValues().length;
	for (int i = 0; i < length; i++) {
		System.out.printf("\n" + fixedLengthString(Integer.toString(i), 18) + " | ");
		for (int j = 0; j < confidenceIntervals.length; j++) {
			System.out.printf("      %8.3f      ", confidenceIntervals[j].getValues()[i]);
		}
	}
}

private static void printTableRow(String name, double[] array) {
	System.out.printf("\n" + fixedLengthString(name, 18) + " | ");
	for (int i = 0; i < array.length; i++) {
		double x = array[i]; 
		System.out.printf("      %8.3f      ", x);
	}
}

private static String fixedLengthString(String string, int length) {
 return String.format("%1$"+length+ "s", string);
}

}
