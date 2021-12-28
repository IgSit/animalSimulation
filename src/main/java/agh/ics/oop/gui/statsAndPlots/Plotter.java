package agh.ics.oop.gui.statsAndPlots;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;

public class Plotter extends AbstractPlotter{
    private XYChart.Series<String, Number> animalSeries;
    private XYChart.Series<String, Number> grassSeries;

    public Plotter(int MAX_DATA) {
        super(MAX_DATA);
    }

    public void start() {
        super.start();
        lineChart.setTitle("Animal/grass stats");

        animalSeries = new XYChart.Series<>();
        animalSeries.setName("Number of animals");
        lineChart.getData().add(animalSeries);

        grassSeries = new XYChart.Series<>();
        grassSeries.setName("Number of grass");
        lineChart.getData().add(grassSeries);
    }

    public void updatePlot(Number day, Number numberOfAnimals, Number numberOfGrass) {
        Platform.runLater(() -> {
            animalSeries.getData().add(new XYChart.Data<>("" + day, numberOfAnimals));
            grassSeries.getData().add(new XYChart.Data<>("" + day, numberOfGrass));

            if (animalSeries.getData().size() > MAX_DATA) { // both of them have the same amount of data
                animalSeries.getData().remove(0);
                grassSeries.getData().remove(0);
            }
        });
    }
}
