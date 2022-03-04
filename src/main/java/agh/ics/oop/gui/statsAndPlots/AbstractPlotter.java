package agh.ics.oop.gui.statsAndPlots;

import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

public abstract class AbstractPlotter {
    protected final int MAX_DATA;
    protected LineChart<String, Number> lineChart;

    public AbstractPlotter(int MAX_DATA) {
        this.MAX_DATA = MAX_DATA;
    }

    public LineChart<String, Number> getLineChart() {
        return lineChart;
    }

    public void start() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day");
        xAxis.setAnimated(false);
        yAxis.setLabel("Number of");
        yAxis.setAnimated(false);

        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setAnimated(false);
        lineChart.setStyle("-fx-background-color: #777777");
        lineChart.setPadding(new Insets(10, 10, 10, 10));
    }
}
