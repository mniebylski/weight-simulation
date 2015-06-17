package weightsimulation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Stroke;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class DisplayGraph {

	public static void main(String[] args) {

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

				// Make Graph Curved
				final XYSplineRenderer rend = new XYSplineRenderer();
				rend.setPrecision(5); // Precision: the number of line segments
										// between 2 points
				plot.setRenderer(rend);
				
				plot.getRenderer().setSeriesStroke(
						1,
						new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
								BasicStroke.JOIN_ROUND, 1.0f, new float[] {
										6.0f, 6.0f }, 0.0f));

				// Create A Chart Panel
				ChartPanel cp = new ChartPanel(chart);

				// Add Chart to Frame
				frame.getContentPane().add(cp);
				frame.setVisible(true);
			}
		});

	}

	// Generate Points For Graph
	private static XYDataset createDataset() {

		TimeSeriesCollection ds = new TimeSeriesCollection();
		TimeSeries data = new TimeSeries("Past Weigh-ins");

		data.add(new Day(15, 6, 2015), 150);
		data.add(new Day(16, 6, 2015), 149);
		data.add(new Day(17, 6, 2015), 145);
		data.add(new Day(18, 6, 2015), 144);

		ds.addSeries(data);

		return ds;
	}
}
