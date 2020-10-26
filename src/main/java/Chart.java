import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Chart extends JFrame {

    public Chart(String title, String chartTitle, String xAxisLabel, String yAxisLabel, String seriesName, List<Point> points) throws HeadlessException {
        super(title);

        // Create dataset
        XYDataset dataset = createDataset(seriesName, points);

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                chartTitle,
                xAxisLabel,
                yAxisLabel,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        plot.setRenderer(renderer);

        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private XYDataset createDataset(String seriesName, List<Point> points) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries(seriesName);

        for (Point point : points) {
            series.add(point.getX(), Double.isFinite(point.getY()) ? point.getY() : null);
        }

        //Add series to dataset
        dataset.addSeries(series);

        return dataset;
    }
}
