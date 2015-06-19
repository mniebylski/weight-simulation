package weightsimulation;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PatientStrategies extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientStrategies frame = new PatientStrategies();
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
	public PatientStrategies() {

		// Create Frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Header
		JLabel lblMyStrategies = new JLabel("My Strategies");
		lblMyStrategies.setFont(new Font("Lucida Grande", Font.PLAIN, 26));
		lblMyStrategies.setBounds(135, 6, 168, 38);
		contentPane.add(lblMyStrategies);

		// Back Button
		JButton button = new JButton("Back");
		button.addActionListener(new ActionListener() {
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
		button.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		button.setBounds(6, 230, 92, 42);
		contentPane.add(button);
	}
}
