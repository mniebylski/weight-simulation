package weightsimulation;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.SpringLayout;

public class PatientHub extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientHub frame = new PatientHub();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PatientHub() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 355, 267);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblWeightLossHub = new JLabel("Weight Loss Hub");
		lblWeightLossHub.setBounds(60, 20, 240, 33);
		lblWeightLossHub.setFont(new Font("Lucida Grande", Font.BOLD, 28));
		contentPane.add(lblWeightLossHub);

		// Open Input Menu
		JButton btnNewButton = new JButton("Input Weight");
		btnNewButton.setBounds(47, 77, 117, 56);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Show input
				PatientInput pIn = new PatientInput();
				pIn.setVisible(true);
				//Hide Current
				JButton button = (JButton)e.getSource();
			    Window window = SwingUtilities.windowForComponent(button);
			    window.setVisible(false);
			}
		});
		contentPane.add(btnNewButton);

		// Open Goal Menu
		JButton btnGoals = new JButton("Goals");
		btnGoals.setBounds(194, 77, 117, 56);
		btnGoals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Show goals
				
			}
		});
		contentPane.add(btnGoals);

		// Open Progress Menu
		JButton btnProgressGraph = new JButton("Progress Graph");
		btnProgressGraph.setBounds(47, 153, 117, 56);
		btnProgressGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Show progress
				DisplayGraph disp = new DisplayGraph();
				
				//Hide Current
				JButton button = (JButton)e.getSource();
			    Window window = SwingUtilities.windowForComponent(button);
			    window.setVisible(false);
			}
		});
		contentPane.add(btnProgressGraph);

		// Open Strategy Menu
		JButton btnNewButton_1 = new JButton("Strategies");
		btnNewButton_1.setBounds(194, 153, 117, 56);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Show strats
				
			}
		});
		contentPane.add(btnNewButton_1);
	}
}
