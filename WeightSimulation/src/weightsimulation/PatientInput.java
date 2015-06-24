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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		lblWeightLossTracker.setBounds(158, 6, 131, 43);
		contentPane.add(lblWeightLossTracker);

		// Weight field and label
		JLabel lblWeight = new JLabel("Weight (lbs): ");
		lblWeight.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblWeight.setBounds(89, 50, 128, 43);
		contentPane.add(lblWeight);

		JLabel lblDay = new JLabel("Day:");
		lblDay.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblDay.setBounds(89, 105, 128, 43);
		contentPane.add(lblDay);

		JLabel lblMonth = new JLabel("Month:");
		lblMonth.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblMonth.setBounds(89, 160, 128, 43);
		contentPane.add(lblMonth);

		JLabel lblYear = new JLabel("Year:");
		lblYear.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblYear.setBounds(89, 215, 128, 43);
		contentPane.add(lblYear);

		txtEnterWeight = new JTextField();
		txtEnterWeight.setBounds(224, 53, 134, 43);
		contentPane.add(txtEnterWeight);
		txtEnterWeight.setColumns(10);

		txtEnterDay = new JTextField();
		txtEnterDay.setColumns(10);
		txtEnterDay.setBounds(224, 108, 134, 43);
		contentPane.add(txtEnterDay);

		txtEnterMonth = new JTextField();
		txtEnterMonth.setColumns(10);
		txtEnterMonth.setBounds(224, 163, 134, 43);
		contentPane.add(txtEnterMonth);

		txtEnterYear = new JTextField();
		txtEnterYear.setColumns(10);
		txtEnterYear.setBounds(224, 218, 134, 43);
		contentPane.add(txtEnterYear);

		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((txtEnterWeight.getText().length() >= 2 && txtEnterWeight.getText().length() < 7) && (txtEnterDay.getText().length() <= 2 && txtEnterDay.getText().length() >= 1) && (txtEnterMonth.getText().length() <= 2 && txtEnterMonth.getText().length() >= 1) && (txtEnterYear.getText().length() == 4)) {
					// Save Data

					// Covert metric to imperial
					double weight = Double.parseDouble(txtEnterWeight.getText());

					if (metric) {
						weight *= 2.2046;
					}

					String dateS = (txtEnterMonth.getText() + "/" + txtEnterDay.getText() + "/" + txtEnterYear.getText());

					JOptionPane.showMessageDialog(contentPane, "Data Successfully Entered", "Success", JOptionPane.DEFAULT_OPTION);

					DataFile data = new DataFile("data.txt");
					data.put(dateS, weight);

					// Clear Forms
					txtEnterWeight.setText("");
					txtEnterDay.setText("");
					txtEnterMonth.setText("");
					txtEnterYear.setText("");

				} else {
					// Throw Error
					JOptionPane.showMessageDialog(contentPane, "Please fill out all fields correctly.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(224, 286, 134, 47);
		contentPane.add(btnNewButton);

		// Enable Metric Mode
		JCheckBox chckbxMetricUnits = new JCheckBox("Metric Units");
		chckbxMetricUnits.setBounds(84, 296, 128, 23);
		contentPane.add(chckbxMetricUnits);

		// Back Button
		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnNewButton_1.setBounds(363, 286, 81, 47);
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
		setBounds(100, 100, 450, 362);
		createPanel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
