package agh.ics.oop.gui;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;

public class StatsPlotter extends AbstractPlotter {
    private XYChart.Series<String, Number> avgEnergySeries;
    private XYChart.Series<String, Number> avgLifeTimeSeries;
    private XYChart.Series<String, Number> avgNumberOfChildrenSeries;

    public StatsPlotter(int MAX_DATA) {
        super(MAX_DATA);
    }

    public void start() {
        super.start();
        lineChart.setTitle("Statistics of animals");

        avgEnergySeries = new XYChart.Series<>();
        avgEnergySeries.setName("Average energy");
        lineChart.getData().add(avgEnergySeries);

        avgLifeTimeSeries = new XYChart.Series<>();
        avgLifeTimeSeries.setName("Average time of life");
        lineChart.getData().add(avgLifeTimeSeries);

        avgNumberOfChildrenSeries = new XYChart.Series<>();
        avgNumberOfChildrenSeries.setName("Average amount of children");
        lineChart.getData().add(avgNumberOfChildrenSeries);
    }

    public void updatePlot(Number day, Number avgEnergy, Number avgLifeTime, Number avgChildrenNumber) {
        Platform.runLater(() -> {

            avgEnergySeries.getData().add(new XYChart.Data<>("" + day, avgEnergy));
            avgLifeTimeSeries.getData().add(new XYChart.Data<>("" + day, avgLifeTime));
            avgNumberOfChildrenSeries.getData().add(new XYChart.Data<>("" + day, avgChildrenNumber));

            if (avgEnergySeries.getData().size() > MAX_DATA) { // all of them have the same amount of data
                avgEnergySeries.getData().remove(0);
                avgLifeTimeSeries.getData().remove(0);
                avgNumberOfChildrenSeries.getData().remove(0);
            }
        });
    }
}
