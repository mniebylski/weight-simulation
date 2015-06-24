package weightsimulation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TreeMap;

public class DataFile {
	// Variables
	String path = "";

	TreeMap<Date, Double> dataMap = new TreeMap<Date, Double>();

	// Format
	SimpleDateFormat formatter = new SimpleDateFormat("M/d/YYYY");

	// Constructor
	DataFile(String path) {
		this.path = path;

		// Load data into a Tree Map
		loadData();
	}

	// Accessors
	public int getLength() {
		return dataMap.size();
	}

	public double getPoint(String date) {
		double point = 0;
		try {
			point = dataMap.get(formatter.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return point;
	}

	public TreeMap<Date, Double> getMap() {
		return dataMap;
	}

	// Methods
	public void put(String date, Double weight) {
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true)))) {
			out.print("\n" + date + " " + weight);

			String dateS = date;

			Date dateF;
			try {
				dateF = formatter.parse(dateS);
				dataMap.put(dateF, weight);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void remove(String date) throws IOException, ParseException {
		// Make a date
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
		Date dateFmt = formatter.parse(date);
		
		// Remove from data map
		dataMap.remove(dateFmt);

		// Remove from file
		// Create temp text
		File file = new File(path);
		File fileTemp = new File("temp.txt");

		BufferedReader reader = new BufferedReader(new FileReader(file));
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileTemp));

		String currentLine;

		while ((currentLine = reader.readLine()) != null) {

			if (currentLine.contains(date))
				continue;

			writer.write(currentLine);
		}

		writer.close();
		boolean successful = fileTemp.renameTo(file);
		System.out.println(successful);

	}

	public void loadData() {
		// Create scanner
		File file = new File(path);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Cycle through text
		while (sc.hasNextLine()) {
			if (sc.nextLine() != "" || sc.nextLine() != null) {
				// Get Date String
				String dateStr = sc.next();

				// Splice Month
				int month = Integer.parseInt((dateStr.substring(0, dateStr.indexOf("/"))));

				// Splice Day
				dateStr = dateStr.substring(dateStr.indexOf("/") + 1, dateStr.length());
				int day = Integer.parseInt((dateStr.substring(0, dateStr.indexOf("/"))));

				// Splice Year
				dateStr = dateStr.substring(dateStr.indexOf("/") + 1, dateStr.length());
				int year = Integer.parseInt(dateStr);

				// Get Weight
				Double weight = sc.nextDouble();

				// Get Date
				String date = month + "/" + day + "/" + year;
				java.util.Date utilDate = null;

				try {
					SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
					utilDate = formatter.parse(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				// System.out.println(date+" "+weight );

				// Add To Tree Map
				dataMap.put(utilDate, weight);
			}
		}
	}

}
