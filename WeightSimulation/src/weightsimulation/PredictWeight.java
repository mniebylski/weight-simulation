package weightsimulation;

import java.util.Date;
import java.util.TreeMap;

public class PredictWeight {
	String path;

	PredictWeight(String fileLocation) {
		path = fileLocation;
	}

	// Generate map of data points
	public TreeMap generatePoints(int days) {
		TreeMap<Date, Double> tm = new TreeMap<Date, Double>();

		// Generate First Time
		tm.put(new Date(System.currentTimeMillis()), 140.0);

		// Generate Points For Each Day
		for (int i = 0; i < days; i++) {

		}

		// Return Map
		return tm;
	}

	// Calculate weight after a certain time
	private double calculateWeight() {
		double weight = 0;

		return weight;
	}

}
