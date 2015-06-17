package weightsimulation;

import java.util.HashMap;

public class Patient {

	// Demographics
	private int myAge;
	private double myHeight; // in centimeters
	private double myBMI;
	private int myGender; // 0 for male, 1 for female

	// Study ID
	private int myPatientID;

	// HashMap from date to weight reading
	private HashMap<Integer, Double> myWeighings;

	// Constructor
	public Patient(int age, double height, int gender, int id) {
		myAge = age;
		myHeight = height;
		myGender = gender;
		myPatientID = id;
		myWeighings = new HashMap<Integer, Double>();
	}

	// default constructor
	public Patient() {
		myAge = 30;
		myHeight = 175;
		myGender = 0;
		myPatientID = 12345678;
		myWeighings = new HashMap<Integer, Double>();
	}

	// Accessors
	public int getAge() {
		return myAge;
	}

	public double getHeight() {
		return myHeight;
	}

	public double getBMI() {
		return myBMI;
	}

	public int getGender() {
		return myGender;
	}

	public int getID() {
		return myPatientID;
	}

	public HashMap<Integer, Double> getWeighings() {
		return myWeighings;
	}

	// Modifiers
	public void setAge(int newAge) {
		myAge = newAge;
	}

	public void setHeight(double newHeight) {
		myHeight = newHeight;
	}

	public void addWeighing(int date, double weight) {
		myWeighings.put(date, weight);
		myBMI = weight / ((myHeight / 100) * (myHeight / 100));
	}

	public void clearWeighings() {
		myWeighings.clear();
	}

	// Methods
	public String toString() {
		return myPatientID + myWeighings.toString();
	}
}
