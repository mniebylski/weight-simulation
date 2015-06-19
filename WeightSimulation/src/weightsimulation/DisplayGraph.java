//Graphing data

package weightsimulation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Stroke;
import java.awt.Window;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import java.util.*;
import java.io.*;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class DisplayGraph {

	// Predefine Data Sets
	static TimeSeriesCollection ds;
	static TimeSeries data;
	static TimeSeries future;

	DisplayGraph() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Create Frame
				javax.swing.JFrame frame = new JFrame("Weight Simulation");
				frame.setResizable(false);

				// Set Frame Properties
				frame.setSize(722, 719);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Load Data Set
				XYDataset ds = createDataset();

				// Create an XY Time Series Chart
				JFreeChart chart = ChartFactory.createTimeSeriesChart(
						"Weight Over Time", "Date (mm/dd/yy)", "Weight (lbs)",
						ds, false, false, false);

				// Create Horizontal Line (Target Weight)
				ValueMarker marker = new ValueMarker(140);
				marker.setPaint(new Color(0, 219, 29));

				float[] dashed = new float[] { 10.0f, 10.0f };
				BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_SQUARE,
						BasicStroke.JOIN_BEVEL, 10.0f, dashed, 10.0f);

				marker.setStroke(stroke);

				// marker.setLabel("Target Weight: "+140); // see JavaDoc for
				// labels, colors,

				// Create XYPlot Object
				XYPlot plot = (XYPlot) chart.getPlot();
				plot.addRangeMarker(marker);

				// Background
				plot.setBackgroundPaint(new GradientPaint(0, 0, new Color(81,
						114, 153), 200, 200, new Color(125, 185, 232), false));
				plot.setBackgroundImageAlpha(0.5f);

				// Set Custom Range
				ValueAxis yAxis = plot.getRangeAxis();
				yAxis.setRange(135.0, 155.0);

				// Set Domain
				DateAxis xAxis = (DateAxis) plot.getDomainAxis();

				// Domain Customization
				((DateAxis) xAxis).setDateFormatOverride(new SimpleDateFormat(
						"MM/dd/yyyy"));
				xAxis.setAutoTickUnitSelection(true);
				xAxis.setVerticalTickLabels(true);

				// Make Domain Smallest Past Val to Largest Future
				xAxis.setRange(ds.getXValue(0, 0), ds.getXValue(1, 6));

				// Make Graph Curved
				XYSplineRenderer rend = new XYSplineRenderer();
				XYSplineRenderer rend1 = new XYSplineRenderer();

				plot.setRenderer(rend);
				rend.setPrecision(7);
				rend1.setPrecision(7);

				// Color Graph
				plot.getRenderer(0).setSeriesStroke(0, new BasicStroke(2.0f));
				plot.getRenderer(0).setSeriesPaint(0, Color.RED);

				plot.getRenderer(0).setSeriesStroke(
						1,
						new BasicStroke(2, BasicStroke.CAP_SQUARE,
								BasicStroke.JOIN_BEVEL, 2.0f, dashed, 2.0f));
				plot.getRenderer(0).setSeriesPaint(1, Color.RED);
				frame.getContentPane().setLayout(null);

				// Create A Chart Panel
				ChartPanel chartPane = new ChartPanel(chart);
				chartPane.setBounds(10, 0, 700, 593);

				// Disable Resizing
				chartPane.setDomainZoomable(false);
				chartPane.setRangeZoomable(false);

				// Add Chart to Frame
				frame.getContentPane().add(chartPane);
				chartPane.setLayout(null);

				JPanel panel = new JPanel();
				panel.setBounds(0, 0, 10, 10);
				frame.getContentPane().add(panel);

				// Buttons
				JButton btnNewButton = new JButton("Back");
				btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
				btnNewButton.setBounds(16, 623, 88, 72);
				frame.getContentPane().add(btnNewButton);
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Show hub
						PatientHub hub = new PatientHub();
						hub.setVisible(true);
						// Hide Current
						JButton button = (JButton) e.getSource();
						Window window = SwingUtilities
								.windowForComponent(button);
						window.setVisible(false);
					}
				});

				// Title For Range Selection
				JLabel lblDateRange = new JLabel("Date Range");
				lblDateRange.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
				lblDateRange.setBounds(312, 593, 132, 43);
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
				btnNewButton_1.setBounds(128, 635, 88, 51);
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
				btnWeeks.setBounds(231, 635, 88, 51);
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
				btnMonths.setBounds(331, 635, 88, 51);
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
				btnYear.setBounds(431, 635, 88, 51);
				frame.getContentPane().add(btnYear);

				// Set A Custom Range
				JButton btnCustom = new JButton("Custom");
				btnCustom.setBounds(531, 635, 88, 51);
				frame.getContentPane().add(btnCustom);
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

		// Create data sets
		ds = new TimeSeriesCollection();
		data = new TimeSeries("Data Weigh-ins");
		future = new TimeSeries("Future Predictions");

		// Create scanner
		File file = new File("data.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Cycle through text
		while (sc.hasNextLine()) {
			if (sc.nextLine() != "") {
				// Get Date String
				String dateStr = sc.next();

				// Splice Month
				int month = Integer.parseInt((dateStr.substring(0,
						dateStr.indexOf("/"))));

				// Splice Day
				dateStr = dateStr.substring(dateStr.indexOf("/") + 1,
						dateStr.length());
				int day = Integer.parseInt((dateStr.substring(0,
						dateStr.indexOf("/"))));

				// Splice Year
				dateStr = dateStr.substring(dateStr.indexOf("/") + 1,
						dateStr.length());
				int year = Integer.parseInt(dateStr);

				// Get Weight
				Double weight = sc.nextDouble();
				Day myDay = new Day(day, month, year);

				// Add Data Point
				data.add(myDay, weight);
			}
		}
		// Get Future Points
		future.add(new Day(17, 6, 2015), 145);
		future.add(new Day(18, 6, 2015), 143.8);
		future.add(new Day(19, 6, 2015), 142.7);
		future.add(new Day(20, 6, 2015), 142.2);
		future.add(new Day(21, 6, 2015), 141);
		future.add(new Day(22, 6, 2015), 140);
		future.add(new Day(23, 6, 2015), 139.6);

		ds.addSeries(data);
		ds.addSeries(future);

		/*
		 * Epoch Date Conversion and Change
		 * 
		 * Date date = new Date((Long) ds.getX(0, 0)); Date datePast = new
		 * Date((Long) ds.getX(0, 0) - 86400000); DateFormat format = new
		 * SimpleDateFormat("MM/dd/YYYY");
		 * format.setTimeZone(TimeZone.getTimeZone("Etc/UTC")); String formatted
		 * = format.format(date); String formattedPast =
		 * format.format(datePast); System.out.println("Now it's " + formatted +
		 * " and yesterday it was " + formattedPast);
		 */

		return ds;
	}
}