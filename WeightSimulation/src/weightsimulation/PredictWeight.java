package weightsimulation;

import java.util.Date;
import java.util.TreeMap;

public class PredictWeight {
	String path;

	PredictWeight(Patient patient, String path) {
		this.path = path;
	}

	// Generate map of data points
	public TreeMap generatePoints(int days) {
		TreeMap<Date, Double> tm = new TreeMap<Date, Double>();

		// Generate First Time
		tm.put(new Date(System.currentTimeMillis() + 86400000), 146.5);

		// Generate Points For Each Day
		for (int i = 0; i < days; i++) {
			tm.put(new Date(System.currentTimeMillis() + (86400000 * (i + 2))), 146.5 - (Math.pow(1.1, i + 1)));
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
