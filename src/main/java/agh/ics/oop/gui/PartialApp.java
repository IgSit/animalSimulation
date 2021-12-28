package agh.ics.oop.gui;

import agh.ics.oop.gui.InputOutput.CSVWriter;
import agh.ics.oop.gui.mapVisualisation.MapVisualiser;
import agh.ics.oop.gui.statsAndPlots.Plotter;
import agh.ics.oop.gui.statsAndPlots.StatsPanel;
import agh.ics.oop.gui.statsAndPlots.StatsPlotter;
import agh.ics.oop.interfaces.IEngineMoveObserver;
import agh.ics.oop.map.RectangularMap;
import agh.ics.oop.engine.SimulationEngine;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
        sendDataToFile();

        Thread engineThread = new Thread(engine);
        Button startButton = new Button("Start");
        startButton.setOnAction( eventClick -> {
            if (engineThread.getState() == Thread.State.NEW)
                engineThread.start();
            else if (engineThread.isAlive())
                engineThread.resume();
        });

        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(eventClick ->
                engineThread.suspend()
        );

        VBox plots = new VBox(plotter.getLineChart(), statsPlotter.getLineChart());
        VBox mapWithButtons = new VBox(new HBox(startButton, pauseButton), mapVisualiser.getGridPane());
        VBox mapWithStatsPanel = new VBox(mapWithButtons, statsPanel.getPanel());

        return new HBox(mapWithStatsPanel, plots);
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
