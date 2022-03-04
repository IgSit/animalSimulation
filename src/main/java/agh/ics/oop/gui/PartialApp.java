package agh.ics.oop.gui;

import agh.ics.oop.gui.InputOutput.CSVWriter;
import agh.ics.oop.gui.mapVisualisation.MapVisualiser;
import agh.ics.oop.gui.statsAndPlots.*;
import agh.ics.oop.interfaces.IEngineMoveObserver;
import agh.ics.oop.map.RectangularMap;
import agh.ics.oop.engine.SimulationEngine;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class PartialApp implements IEngineMoveObserver {
    private final SimulationEngine engine;
    private final RectangularMap map;
    private final MapVisualiser mapVisualiser;
    private final Plotter plotter;
    private final StatsPlotter statsPlotter;
    private final StatsPanel statsPanel;
    private final CSVWriter csvWriter;

    public PartialApp(RectangularMap map, int numberOfAnimals, String filename) {
        this.map = map;
        engine = new SimulationEngine(map, numberOfAnimals, this);
        engine.addObserver(this);
        mapVisualiser = new MapVisualiser(map);
        plotter = new Plotter(25);
        statsPlotter = new StatsPlotter(25);
        statsPanel = new StatsPanel(map, mapVisualiser);
        csvWriter = new CSVWriter(filename);
    }

    @Override
    public void mapChanged() {
        Platform.runLater(mapVisualiser::mapChanged);
    }

    public HBox runSimulation() {
        mapVisualiser.drawMap(map);
        plotter.start();
        statsPlotter.start();
        newDay(0);

        Thread engineThread = new Thread(engine);
        Button startButton = new Button("START");

        Button pauseButton = new Button("PAUSE");
        pauseButton.setOnAction(eventClick -> {
                    engineThread.suspend();
                    startButton.setStyle("-fx-background-color: #2ea12e; -fx-font-weight: 800; -fx-font-size: 14px; " +
                            "-fx-font-family: 'JetBrains Mono'; -fx-opacity: 1");
                    pauseButton.setStyle("-fx-background-color: #269bb0; -fx-font-weight: 800; -fx-font-size: 14px; " +
                            "-fx-font-family: 'JetBrains Mono'; -fx-opacity: 0.5");
                }
        );

        startButton.setOnAction( eventClick -> {
            if (engineThread.getState() == Thread.State.NEW)
                engineThread.start();
            else if (engineThread.isAlive())
                engineThread.resume();
            startButton.setStyle("-fx-background-color: #2ea12e; -fx-font-weight: 800; -fx-font-size: 14px; " +
                    "-fx-font-family: 'JetBrains Mono'; -fx-opacity: 0.5");
            pauseButton.setStyle("-fx-background-color: #269bb0; -fx-font-weight: 800; -fx-font-size: 14px; " +
                    "-fx-font-family: 'JetBrains Mono'; -fx-opacity: 1");
        });

        VBox plots = new VBox(plotter.getLineChart(), statsPlotter.getLineChart());
        HBox controls = new HBox(startButton, pauseButton);
        GridPane grid = mapVisualiser.getGridPane();
        VBox mapWithButtons = new VBox(controls, grid);
        VBox mapWithStatsPanel = new VBox(mapWithButtons, statsPanel.getPanel());

        HBox partialApp = new HBox(mapWithStatsPanel, plots);

        /* CSS styling */

        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10, 10, 10, 10));
        controls.setSpacing(20);
        startButton.setStyle("-fx-background-color: #2ea12e; -fx-font-weight: 800; -fx-font-size: 14px; " +
                             "-fx-font-family: 'JetBrains Mono'");
        pauseButton.setStyle("-fx-background-color: #269bb0; -fx-font-weight: 800; -fx-font-size: 14px; " +
                "-fx-font-family: 'JetBrains Mono'; -fx-opacity: 0.5");

        grid.setStyle("-fx-background-color: #919191");

        partialApp.setStyle("-fx-background-color: #545353");

        plots.setSpacing(20);
        plots.setPadding(new Insets(10, 10, 10, 10));

        return partialApp;
    }

    public void newDay(int day) {
        RectangularMap map = engine.getMap();
        plotter.updatePlot(day, map.getNumberOfAnimals(), map.getNumberOfGrass());
        statsPlotter.updatePlot(day, map.getAverageAnimalEnergy(), map.getAverageLifeTime(),
                map.getAverageAmountOfChildren());
        statsPanel.updateStats();
        sendDataToFile();
    }

    private void sendDataToFile() {
        String row = "";
        row += map.getNumberOfAnimals() + ", ";
        row += map.getNumberOfGrass() + ", ";
        row += map.getAverageAnimalEnergy() + ", ";
        row += map.getAverageLifeTime() + ", ";
        row += map.getAverageAmountOfChildren() + ",\n";
        csvWriter.addStats(row);
    }
}
