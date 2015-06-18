package weightsimulation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Stroke;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
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

public class DisplayGraph {

	DisplayGraph() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Create Frame
				javax.swing.JFrame frame = new JFrame("Weight Simulation");

				// Set Frame Properties
				frame.setSize(900, 800);
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
				xAxis.setAutoTickUnitSelection(false);
				xAxis.setVerticalTickLabels(true);
				// Make Domain Smallest Past Val to Largest Future
				xAxis.setRange(ds.getXValue(0, 0), ds.getXValue(1, 6));

				// Make Graph Curved
				XYSplineRenderer rend = new XYSplineRenderer();
				XYSplineRenderer rend1 = new XYSplineRenderer();

				plot.setRenderer(rend);
				rend.setPrecision(5);
				rend1.setPrecision(5);

				// Color Graph
				plot.getRenderer(0).setSeriesStroke(0, new BasicStroke(2.0f));
				plot.getRenderer(0).setSeriesPaint(0, Color.RED);

				plot.getRenderer(0).setSeriesStroke(
						1,
						new BasicStroke(2, BasicStroke.CAP_SQUARE,
								BasicStroke.JOIN_BEVEL, 2.0f, dashed, 2.0f));
				plot.getRenderer(0).setSeriesPaint(1, Color.RED);

				// Create A Chart Panel
				ChartPanel chartPane = new ChartPanel(chart);

				// Disable Resizing
				chartPane.setDomainZoomable(false);
				chartPane.setRangeZoomable(false);

				// Add Chart to Frame
				frame.getContentPane().add(chartPane);
				frame.setVisible(true);
			}
		});
	}

	// Generate Points For Graph
	private static XYDataset createDataset() {

		// Create data sets
		TimeSeriesCollection ds = new TimeSeriesCollection();
		TimeSeries data = new TimeSeries("Data Weigh-ins");
		TimeSeries future = new TimeSeries("Future Predictions");

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
				String dateStr = sc.next();
				System.out.println("Next line: " + dateStr);
				int month = Integer.parseInt((dateStr.substring(0,
						dateStr.indexOf("/"))));
				dateStr = dateStr.substring(dateStr.indexOf("/") + 1,
						dateStr.length());
				int day = Integer.parseInt((dateStr.substring(0,
						dateStr.indexOf("/"))));
				dateStr = dateStr.substring(dateStr.indexOf("/") + 1,
						dateStr.length());
				int year = Integer.parseInt(dateStr);
				Double weight = sc.nextDouble();
				Day myDay = new Day(day, month, year);
				data.add(myDay, weight);
				System.out.println(myDay);
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

		Date date = new Date((Long) ds.getX(0, 0));
		DateFormat format = new SimpleDateFormat("dd");
		format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
		String formatted = format.format(date);

		return ds;
	}
}