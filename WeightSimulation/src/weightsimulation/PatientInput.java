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
import java.awt.event.*;

public class PatientInput extends JFrame implements ActionListener {
	private JPanel contentPane;
	private JTextField weightField;
	private JTextField dateField;
	private DisplayGraph graph;
	private int currentWeight;
	private int currentDate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DisplayGraph gr = new DisplayGraph();
					PatientInput frame = new PatientInput(gr);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void createPanel(){
		contentPane = new JPanel();
		Border b = new EmptyBorder(5, 5, 5, 5);
		contentPane.setBorder(b);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	    JLabel lblWeightLossTracker = new JLabel("Weight Loss Tracker");
		lblWeightLossTracker.setBounds(148, 6, 133, 16);
		contentPane.add(lblWeightLossTracker);
		
		//Weight field and label
		JLabel lblWeight = new JLabel("Weight: ");
		lblWeight.setBounds(20, 30, 100, 60);
		contentPane.add(lblWeight);
	    weightField = new JTextField(10);
	    contentPane.add(weightField);
	    weightField.setBounds(120, 30, 100, 60);
	    weightField.addActionListener(this);
	    weightField.setActionCommand("weight");
		
	    //Date field and label
		JLabel lblDate = new JLabel("Date: ");
		lblDate.setBounds(20, 130, 100, 60);
		contentPane.add(lblDate);
	    dateField = new JTextField(10);
	    contentPane.add(dateField);
	    dateField.setBounds(120, 130, 100, 60);
	    dateField.addActionListener(this);
	    dateField.setActionCommand("date");

	    //Graph Button
	    JButton graphButton = new JButton("Graph");
	    contentPane.add(graphButton);
	    graphButton.setBounds(120, 200, 50, 20);
	    graphButton.addActionListener(this);
	}
	
	//Constructor
	public PatientInput(DisplayGraph g) {
		graph = g;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		createPanel();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("weight")){
			String weightstr = weightField.getText();
			currentWeight = Integer.parseInt(weightstr);
		} else if (e.getActionCommand().equals("date")){
			String datestr = dateField.getText();
			currentDate = Integer.parseInt(datestr);
		} else if (e.getActionCommand().equals("Graph")){
			System.out.println(currentWeight + " /// " + currentDate);
		}
	}
}
