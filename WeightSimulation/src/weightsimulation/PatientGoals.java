package weightsimulation;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.JXTable;

import javax.swing.JSpinner;

import org.jdesktop.swingx.JXDatePicker;

public class PatientGoals extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientGoals frame = new PatientGoals();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Variables
	// Selection Mode: 0=Add,1=Edit
	int selectionMode = 0;

	// Format
	DateFormat format = new SimpleDateFormat("M/d/YYYY");

	/**
	 * Create the frame.
	 */
	public PatientGoals() {

		// Create Frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Header
		JLabel lblMyGoals = new JLabel("My Goals");
		lblMyGoals.setFont(new Font("Lucida Grande", Font.PLAIN, 26));
		lblMyGoals.setHorizontalAlignment(SwingConstants.CENTER);
		lblMyGoals.setBounds(154, 6, 120, 32);
		contentPane.add(lblMyGoals);

		// Back Button
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnBack.addActionListener(new ActionListener() {
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
		btnBack.setBounds(24, 299, 146, 50);
		contentPane.add(btnBack);

		// Create columns names
		String columnNames[] = { "Weight", "Date", "Lbs Left", "Days Left" };

		// Create some data
		String dataValues[][] = { { "12", "234", "67", "12" }, { "-123", "43", "853", "12" }, { "93", "89.2", "109", "12" }, { "279", "9033", "3092", "12" } };

		// Create scroll pane for table
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 50, 401, 168);
		contentPane.add(scrollPane);

		// Table Model
		DefaultTableModel model = new DefaultTableModel(dataValues, columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// Create Table
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);

		// Table Selection

		ListSelectionModel cellSelectionModel = table.getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			}
		});

		// Remove Button
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Check for selected row
				if (table.getSelectedRow() != -1) {
					// Remove Goal
					int delRow = (int) table.getSelectedRow();
					System.out.println(delRow);
					((DefaultTableModel) table.getModel()).removeRow(delRow);
				} else {
					// Error Message
					JOptionPane.showMessageDialog(contentPane, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);

				}
			}
		});
		btnRemove.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnRemove.setBounds(333, 237, 92, 50);
		contentPane.add(btnRemove);

		// Edit Button
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Check Mode
				if (selectionMode != 1) {
					// Set to append mode
					selectionMode = 1;
					// Display notification
					// Success Dialog
					JOptionPane.showMessageDialog(contentPane, "'Edit' Selected. Select a row, edit data below, and then press enter.", "Edit Selected", JOptionPane.DEFAULT_OPTION);

				} else {

				}

			}
		});

		btnEdit.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnEdit.setBounds(100, 230, 70, 63);
		contentPane.add(btnEdit);

		// New Button
		JButton btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Check Mode
				if (selectionMode != 0) {
					// Set to append mode
					selectionMode = 0;
					// Display notification
					// Success Dialog
					JOptionPane.showMessageDialog(contentPane, "'New' Selected. Enter data below and then press enter.", "New Selected", JOptionPane.DEFAULT_OPTION);

				} else {

				}

			}
		});
		btnNew.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnNew.setBounds(24, 230, 70, 63);
		contentPane.add(btnNew);

		// Target Weight Picker
		JLabel lblTargetWeight = new JLabel("Target Weight");
		lblTargetWeight.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblTargetWeight.setBounds(192, 227, 114, 22);
		contentPane.add(lblTargetWeight);

		JSpinner spinner = new JSpinner();
		spinner.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		spinner.setBounds(182, 247, 146, 40);
		contentPane.add(spinner);

		// Target Date Selector
		JLabel lblTargetDate = new JLabel("Target Date");
		lblTargetDate.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblTargetDate.setBounds(192, 288, 92, 22);
		contentPane.add(lblTargetDate);

		JXDatePicker datePicker = new JXDatePicker();
		datePicker.setBounds(182, 309, 146, 40);
		contentPane.add(datePicker);

		// Enter Info
		JButton btnNewButton = new JButton("Enter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Check date and weight
				if ((int) spinner.getValue() > 1 && (long) datePicker.getDate().getTime() > System.currentTimeMillis()) {
					if (selectionMode == 0) {
						// Add Goal
						model.addRow(new Object[] { spinner.getValue(), format.format(datePicker.getDate()), "4", "4" });
					} else if (selectionMode == 1) {
						// Edit Goal

					} else {
						// Error Message
						JOptionPane.showMessageDialog(contentPane, "Please either choose 'Add' or 'Edit'.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					// Patient left out data
					// Error Message
					JOptionPane.showMessageDialog(contentPane, "Please check your input data.", "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnNewButton.setBounds(333, 299, 92, 50);
		contentPane.add(btnNewButton);

		// Disable Reordering
		table.getTableHeader().setReorderingAllowed(false);

	}
}
