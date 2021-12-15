package agh.ics.oop.gui;

import agh.ics.oop.IEngineMoveObserver;
import agh.ics.oop.RectangularMap;
import agh.ics.oop.SimulationEngine;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PartialApp implements IEngineMoveObserver {
    private final SimulationEngine engine;
    private final RectangularMap map;
    private MapVisualiser mapVisualiser;
    private Plotter plotter;
    private StatsPlotter statsPlotter;
    private StatsPanel statsPanel;

    public PartialApp(RectangularMap map, int numberOfAnimals) {
        this.map = map;
        engine = new SimulationEngine(map, numberOfAnimals, this);
        engine.addObserver(this);
    }

    @Override
    public void mapChanged() {
        Platform.runLater(() -> mapVisualiser.mapChanged());
    }

    public HBox runSimulation() {
        mapVisualiser = new MapVisualiser(map);
        mapVisualiser.drawMap(map);
        plotter = new Plotter(25);
        plotter.start();
        statsPlotter = new StatsPlotter(25);
        statsPlotter.start();
        statsPanel = new StatsPanel(map, mapVisualiser);

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
    }
}
