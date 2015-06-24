//Graphing data

package weightsimulation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Window;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXDatePicker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import java.util.*;

import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JSpinner;

public class DisplayGraph {

	// Predefine Data Sets
	static TimeSeriesCollection ds;
	static TimeSeries data;
	static TimeSeries future;

	static// Patient
	Patient p1 = new Patient();

	DisplayGraph() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				// Create Frame
				javax.swing.JFrame frame = new JFrame("Weight Simulation");
				frame.setResizable(false);

				// Set Frame Properties
				frame.setSize(778, 693);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Load Data Set
				XYDataset ds = createDataset();

				// Create an XY Time Series Chart
				JFreeChart chart = ChartFactory.createTimeSeriesChart("Weight Over Time", "Date (M/d/yy)", "Weight (lbs)", ds, false, false, false);

				// Make Goal Data Object
				DataFile goalData = new DataFile("goals.txt");
				TreeMap<Date, Double> goalMap = goalData.getMap();
				
				// Create XYPlot Object
				XYPlot plot = (XYPlot) chart.getPlot();
				
				// Create Dashed Strokes
				float[] dashed = new float[] { 10.0f, 10.0f };
				BasicStroke stroke = new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 10.0f, dashed, 10.0f);

				// Get Smallest Goal
				double lowestGoal = -1;
				
				// Create Goal Lines
				for (Entry<Date, Double> entry : goalMap.entrySet()) {
					
					// Get Map Data
					Date key = entry.getKey();
					Double value = entry.getValue();
					
					// Set Lowest Goal
					if(lowestGoal==-1){
						lowestGoal = value;
					}else if(lowestGoal>value){
						lowestGoal = value;
					}
					
					
					// Create Horizontal Line (Target Weight)
					ValueMarker marker = new ValueMarker(value);
					marker.setPaint(new Color(0, 219, 29));

					marker.setStroke(stroke);
					plot.addRangeMarker(marker);
					
					// Create Vertical Line (Target Date)
					// Format Date Data
					DateFormat fmtD = new SimpleDateFormat("d");
					DateFormat fmtM = new SimpleDateFormat("M");
					DateFormat fmtY = new SimpleDateFormat("YYYY");

					// Convert String To Int
					String day = fmtD.format(key);
					String month = fmtM.format(key);
					String year = fmtY.format(key);

					// Draw Line
					RegularTimePeriod graphTimePeriod = new Day( Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year));
					ValueMarker vm = new ValueMarker(graphTimePeriod.getMiddleMillisecond());  
					vm.setPaint(new Color(0, 219, 29));

					vm.setStroke(stroke);
			        plot.addDomainMarker(vm);
			        
			        // Add Labels
			        vm.setLabel("Target Weight: "+value);
				}

				// Background
				plot.setBackgroundPaint(new GradientPaint(0, 0, new Color(81, 114, 153), 200, 200, new Color(125, 185, 232), false));
				plot.setBackgroundImageAlpha(0.5f);
				
				// Set Custom Range
				ValueAxis yAxis = plot.getRangeAxis();
				if (((TimeSeriesCollection) ds).getRangeBounds(true).getLowerBound() < lowestGoal || lowestGoal == -1) {
					// Make Range From Smallest Weight To Largest Weight
					yAxis.setRange(((TimeSeriesCollection) ds).getRangeBounds(true).getLowerBound() - .5, ((TimeSeriesCollection) ds).getRangeBounds(true).getUpperBound() + .5);
				} else {
					System.out.println("Using lowestGoal: "+lowestGoal);
					// Make Range From Lowest Goal To Largest Weight
					yAxis.setRange(lowestGoal - .5, ((TimeSeriesCollection) ds).getRangeBounds(true).getUpperBound() + .5);
				}

				// Set Domain
				DateAxis xAxis = (DateAxis) plot.getDomainAxis();

				// Domain Customization
				((DateAxis) xAxis).setDateFormatOverride(new SimpleDateFormat("M/d/yyyy"));
				xAxis.setAutoTickUnitSelection(true);
				xAxis.setVerticalTickLabels(true);

				// Make Domain Smallest Past Value to Largest Future Value
				xAxis.setRange(findDomain(7)[0], findDomain(7)[1]);

				// Make Graph Curved
				XYSplineRenderer rend = new XYSplineRenderer();
				XYSplineRenderer rend1 = new XYSplineRenderer();

				plot.setRenderer(rend);
				rend.setPrecision(7);
				rend1.setPrecision(7);

				// Color Graph Past
				plot.getRenderer(0).setSeriesStroke(0, new BasicStroke(2.0f));
				plot.getRenderer(0).setSeriesPaint(0, Color.RED);

				// Color Graph Prediction
				plot.getRenderer(0).setSeriesStroke(1, new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 2.0f, dashed, 2.0f));
				plot.getRenderer(0).setSeriesPaint(1, Color.YELLOW);
				frame.getContentPane().setLayout(null);

				// Create A Chart Panel
				ChartPanel chartPane = new ChartPanel(chart);
				chartPane.setBounds(6, 0, 766, 496);

				// Disable Resizing
				chartPane.setDomainZoomable(false);
				chartPane.setRangeZoomable(false);

				// Add Chart to Frame
				frame.getContentPane().add(chartPane);
				chartPane.setLayout(null);

				// Buttons
				JButton btnNewButton = new JButton("Back");
				btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
				btnNewButton.setBounds(620, 598, 152, 52);
				frame.getContentPane().add(btnNewButton);
				btnNewButton.addActionListener(new ActionListener() {
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

				// Title For Range Selection
				JLabel lblDateRange = new JLabel("Date Range");
				lblDateRange.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
				lblDateRange.setBounds(6, 508, 109, 57);
				frame.getContentPane().add(lblDateRange);

				// Set Range to 7 Days
				JButton btnNewButton_1 = new JButton("7 Days");
				btnNewButton_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Set New Axis
						xAxis.setRange(findDomain(7)[0], findDomain(7)[1]);

						// Refresh Panel
						frame.invalidate();
						frame.validate();
						frame.repaint();
					}
				});
				btnNewButton_1.setBounds(127, 509, 88, 56);
				frame.getContentPane().add(btnNewButton_1);

				// Set Range to 3 Weeks
				JButton btnWeeks = new JButton("3 Weeks");
				btnWeeks.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Set New Axis
						xAxis.setRange(findDomain(21)[0], findDomain(21)[1]);

						// Refresh Panel
						frame.invalidate();
						frame.validate();
						frame.repaint();
					}
				});
				btnWeeks.setBounds(230, 509, 88, 56);
				frame.getContentPane().add(btnWeeks);

				// Set Range to 6 Months
				JButton btnMonths = new JButton("6 Months");
				btnMonths.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Set New Axis
						xAxis.setRange(findDomain(182)[0], findDomain(182)[1]);

						// Refresh Panel
						frame.invalidate();
						frame.validate();
						frame.repaint();
					}
				});
				btnMonths.setBounds(330, 509, 88, 56);
				frame.getContentPane().add(btnMonths);

				// Set Range to 1 Year
				JButton btnYear = new JButton("1 Year");
				btnYear.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Set New Axis
						xAxis.setRange(findDomain(365)[0], findDomain(365)[1]);

						// Refresh Panel
						frame.invalidate();
						frame.validate();
						frame.repaint();
					}
				});
				btnYear.setBounds(430, 509, 88, 56);
				frame.getContentPane().add(btnYear);

				// Create Two Custom Date Pickers
				JXDatePicker fromDate = new JXDatePicker();
				fromDate.getEditor().setText("From");
				fromDate.setBounds(620, 508, 151, 28);
				frame.getContentPane().add(fromDate);

				JXDatePicker toDate = new JXDatePicker();
				toDate.getEditor().setText("To");
				toDate.setBounds(620, 537, 151, 28);
				frame.getContentPane().add(toDate);

				// Set A Custom Range
				JButton btnCustom = new JButton("Custom");
				btnCustom.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Make Sure Dates Are In Correct Order
						if ((long) fromDate.getDate().getTime() < (long) toDate.getDate().getTime()) {
							// Set New Axis
							xAxis.setRange(fromDate.getDate(), toDate.getDate());

							// Refresh Panel
							frame.invalidate();
							frame.validate();
							frame.repaint();
						} else {
							// Error Message
							JOptionPane.showMessageDialog(frame, "Please check your dates.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				btnCustom.setBounds(530, 509, 88, 56);

				frame.getContentPane().add(btnCustom);

				// Title For Weight Range
				JLabel lblWeightRange = new JLabel("Weight Range");
				lblWeightRange.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
				lblWeightRange.setBounds(6, 593, 142, 57);
				frame.getContentPane().add(lblWeightRange);

				// Spinners For Custom Weight Range
				JSpinner min = new JSpinner();
				min.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
				min.setBounds(137, 601, 135, 50);
				frame.getContentPane().add(min);

				JSpinner max = new JSpinner();
				max.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
				max.setBounds(284, 601, 135, 50);
				frame.getContentPane().add(max);

				// Labels For Spinners
				JLabel lblMax = new JLabel("Maximum");
				lblMax.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
				lblMax.setBounds(298, 577, 88, 28);
				frame.getContentPane().add(lblMax);

				JLabel lblMinimum = new JLabel("Minimum");
				lblMinimum.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
				lblMinimum.setBounds(158, 581, 82, 20);
				frame.getContentPane().add(lblMinimum);

				// Update Range
				JButton btnApply = new JButton("Set Range");
				btnApply.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Check Validity Of Spinners
						if (((int) min.getValue() < (int) max.getValue()) && ((int) min.getValue() >= 0)) {
							// Update Range
							yAxis.setRange((int) min.getValue(), (int) max.getValue());
							// Refresh
							frame.invalidate();
							frame.validate();
							frame.repaint();
						} else {
							// Show Error If Max and Min Are Switched Or Too
							// Small/ Out Of Order
							JOptionPane.showMessageDialog(frame, "Please check your values.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				btnApply.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
				btnApply.setBounds(456, 598, 152, 52);
				frame.getContentPane().add(btnApply);

				// Show Frame
				frame.setVisible(true);
			}
		});
	}

	// Make Custom Date Range
	private static Date[] findDomain(int days) {
		Date[] domain = new Date[2];
		long today = System.currentTimeMillis() / 1000;
		Date start = new Date((today - (days * 86400)) * 1000);
		Date end = new Date((today + (days * 86400)) * 1000);

		domain[0] = start;
		domain[1] = end;

		return domain;

	}

	// Generate Points For Graph
	private static XYDataset createDataset() {

		// Variables For Last Point
		int monthL = 0;
		int dayL = 0;
		int yearL = 0;
		double weightL = 0;

		// Create data sets
		ds = new TimeSeriesCollection();
		data = new TimeSeries("Data Weigh-ins");
		future = new TimeSeries("Future Predictions");

		// Date Reader
		DataFile fileR = new DataFile("data.txt");

		// Tree Map
		TreeMap<Date, Double> dataMap = fileR.getMap();

		// Iterate Through Map
		for (Map.Entry<Date, Double> entry : dataMap.entrySet()) {
			// Get Map Data
			Date key = entry.getKey();
			Double value = entry.getValue();

			// Format Date Data
			DateFormat fmtD = new SimpleDateFormat("d");
			DateFormat fmtM = new SimpleDateFormat("M");
			DateFormat fmtY = new SimpleDateFormat("YYYY");

			// Convert String To Int
			String day = fmtD.format(key);
			String month = fmtM.format(key);
			String year = fmtY.format(key);

			// Add Data Point
			data.add(new Hour(0, Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year)), value);

			// Set Last Point
			monthL = Integer.parseInt(month);
			dayL = Integer.parseInt(day);
			yearL = Integer.parseInt(year);
			weightL = value;
		}

		// Create Predictor
		PredictWeight prediction = new PredictWeight(p1, "data.txt");

		// Get Map Of Data
		TreeMap<Date, Double> futureSet = prediction.generatePoints(7);

		// Add First Point
		future.add(new Hour(2, dayL, monthL, yearL), weightL);

		// Cycle Through And Add Data
		for (Entry<Date, Double> entry : futureSet.entrySet()) {
			// Get Map Data
			Date key = entry.getKey();
			Double value = entry.getValue();

			// Format Date Data
			DateFormat fmtD = new SimpleDateFormat("d");
			DateFormat fmtM = new SimpleDateFormat("M");
			DateFormat fmtY = new SimpleDateFormat("YYYY");

			// Convert String to Int
			String day = fmtD.format(key);
			String month = fmtM.format(key);
			String year = fmtY.format(key);

			// Add New Data Point
			future.add(new Hour(0, Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year)), value);

		}

		// Combine Data Sets
		ds.addSeries(data);
		ds.addSeries(future);

		/*
		 * Epoch Date Conversion and Change
		 * 
		 * Date date = new Date((Long) ds.getX(0, 0)); Date datePast = new
		 * Date((Long) ds.getX(0, 0) - 86400000); DateFormat format = new
		 * SimpleDateFormat("M/d/YYYY");
		 * format.setTimeZone(TimeZone.getTimeZone("Etc/UTC")); String formatted
		 * = format.format(date); String formattedPast =
		 * format.format(datePast); System.out.println("Now it's " + formatted +
		 * " and yesterday it was " + formattedPast);
		 */

		return ds;
	}
}