package agh.ics.oop.gui;

import agh.ics.oop.RectangularMap;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StatsPanel {
    private final RectangularMap map;
    private boolean trackByGenotype = false;
    private final Label dominantGenotypes;
    private final VBox panel;

    public StatsPanel(RectangularMap map, MapVisualiser mapVisualiser) {
        this.map = map;
        Button trackAnimalsButton = new Button("Track");
        trackAnimalsButton.setOnAction(eventClick -> {
            if (!trackByGenotype) {
                map.trackDominantGeneAnimals();
                trackAnimalsButton.setText("Untrack");

            }
            else {
                map.untrackDominantGeneAnimals();
                trackAnimalsButton.setText("Track");
            }
            trackByGenotype = !trackByGenotype;
            Platform.runLater(mapVisualiser::mapChanged);
        });
        dominantGenotypes = new Label(map.findDominantGenotypes().toString());
        panel = new VBox(new HBox(new Label("Dominant genotypes: "), dominantGenotypes, trackAnimalsButton));
    }

    public void updateStats() {
        Platform.runLater(() -> dominantGenotypes.setText(map.findDominantGenotypes().toString()));
    }

    public VBox getPanel() {
        return panel;
    }
}
