/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weightsimulation;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

public class Display {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				javax.swing.JFrame frame = new JFrame("Weight Simulation");

				frame.setSize(800, 600);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);

				XYDataset ds = createDataset();
				JFreeChart chart = ChartFactory.createTimeSeriesChart(
						"Weight Over Time", "Date (mm/dd/yy)", "Weight (lbs)",
						ds);

				
				XYPlot plot = (XYPlot) chart.getPlot();
				
				//Set Custom Range
				ValueAxis yAxis = plot.getRangeAxis();
				yAxis.setRange(100.0, 180.0);
				
				//Set Custom Domain
				ValueAxis xAxis = plot.getDomainAxis();
				xAxis.setRange(0,5);
				
				ChartPanel cp = new ChartPanel(chart);

				frame.getContentPane().add(cp);
			}
		});

	}

	// Generate Points For Graph
	private static XYDataset createDataset() {

		DefaultXYDataset ds = new DefaultXYDataset();

		// Dates Row 1, Weights Row 2
		double[][] data = { { 0, 1, 2, 3 }, { 160, 159, 160, 156 } };

		ds.addSeries("series1", data);

		return ds;
	}
}
