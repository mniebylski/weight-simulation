package weightsimulation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import org.jfree.data.time.Hour;

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

	public TreeMap getMap() {
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

	public void remove(Date date) {
		dataMap.remove(date);
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
				
				//System.out.println(date+" "+weight );

				// Add To Tree Map
				dataMap.put(utilDate, weight);
			}
		}
	}

}
