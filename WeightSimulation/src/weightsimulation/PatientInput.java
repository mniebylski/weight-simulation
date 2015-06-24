/* Patient Input Class for WeightSimulation Project
 * 
 * By: Max and Joyce
 *
 */

package weightsimulation;

//Import Statements
import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdesktop.swingx.JXDatePicker;

public class PatientInput extends JFrame implements ActionListener {
	private JPanel contentPane;
	private DisplayGraph graph;
	private int currentWeight;
	private int currentDate;
	private JTextField txtEnterWeight;
	private JTextField txtEnterDay;
	private JTextField txtEnterMonth;
	private JTextField txtEnterYear;

	boolean metric = false;

	/**
	 * Launch the application.
	 */

	public void createPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		Border b = new EmptyBorder(5, 5, 5, 5);
		contentPane.setBorder(b);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblWeightLossTracker = new JLabel("Data Input");
		lblWeightLossTracker.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		lblWeightLossTracker.setBounds(104, 6, 131, 43);
		contentPane.add(lblWeightLossTracker);

		// Weight field and label
		JLabel lblWeight = new JLabel("Weight (lbs): ");
		lblWeight.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblWeight.setBounds(26, 47, 128, 43);
		contentPane.add(lblWeight);

		txtEnterWeight = new JTextField();
		txtEnterWeight.setBounds(166, 50, 147, 43);
		contentPane.add(txtEnterWeight);
		txtEnterWeight.setColumns(10);

		// Date Picker
		JXDatePicker datePicker = new JXDatePicker();
		datePicker.setBounds(165, 102, 148, 43);
		contentPane.add(datePicker);

		// Submit Weight
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((txtEnterWeight.getText().length() >= 2 && txtEnterWeight.getText().length() < 7) && (datePicker.getDate() != null)) {
					// Save Data

					// Covert metric to imperial
					double weight = Double.parseDouble(txtEnterWeight.getText());

					if (metric) {
						weight *= 2.2046;
					}

					// Date Chosen
					Date dateC = datePicker.getDate();

					// Create Vertical Line (Target Date)
					// Format Date Data
					DateFormat fmtD = new SimpleDateFormat("d");
					DateFormat fmtM = new SimpleDateFormat("M");
					DateFormat fmtY = new SimpleDateFormat("YYYY");

					// Convert String To Int
					String day = fmtD.format(dateC);
					String month = fmtM.format(dateC);
					String year = fmtY.format(dateC);

					JOptionPane.showMessageDialog(contentPane, "Data Successfully Entered", "Success", JOptionPane.DEFAULT_OPTION);

					DataFile data = new DataFile("data.txt");
					data.put(month + "/" + day + "/" + year, weight);

					// Clear Form
					datePicker.setDate(null);

				} else {
					// Throw Error
					JOptionPane.showMessageDialog(contentPane, "Please fill out all fields correctly.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(179, 190, 134, 53);
		contentPane.add(btnNewButton);

		// Enable Metric Mode
		JCheckBox chckbxMetricUnits = new JCheckBox("Metric Units");
		chckbxMetricUnits.setBounds(44, 155, 108, 23);
		contentPane.add(chckbxMetricUnits);

		// Back Button
		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnNewButton_1.setBounds(26, 190, 134, 53);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Show hub
				PatientHub hub = new PatientHub();
				hub.setVisible(true);
				// Hide Current
				JButton button = (JButton) e.getSource();
				Window window = SwingUtilities.windowForComponent(button);
				window.setVisible(false);
			}
		});
		contentPane.add(btnNewButton_1);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblDate.setBounds(93, 102, 50, 43);
		contentPane.add(lblDate);

		chckbxMetricUnits.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (chckbxMetricUnits.isSelected()) {
					lblWeight.setText("Weight (kg): ");
					metric = true;
				} else {
					;
					lblWeight.setText("Weight (lbs): ");
					metric = false;
				}
			}
		});

	}

	public PatientInput() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 336, 284);
		createPanel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
