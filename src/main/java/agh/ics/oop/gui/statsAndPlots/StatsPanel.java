package agh.ics.oop.gui.statsAndPlots;

import agh.ics.oop.gui.mapVisualisation.MapVisualiser;
import agh.ics.oop.map.RectangularMap;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StatsPanel {
    private final RectangularMap map;
    private boolean trackByGenotype = false;
    private final Label dominantGenotypes;
    private final VBox panel;

    public StatsPanel(RectangularMap map, MapVisualiser mapVisualiser) {
        this.map = map;
        Button trackAnimalsButton = new Button("TRACK");
        trackAnimalsButton.setOnAction(eventClick -> {
            if (!trackByGenotype) {
                map.trackDominantGeneAnimals();
                trackAnimalsButton.setText("UNTRACK");

            }
            else {
                map.untrackDominantGeneAnimals();
                trackAnimalsButton.setText("TRACK");
            }
            trackByGenotype = !trackByGenotype;
            Platform.runLater(mapVisualiser::mapChanged);
        });
        dominantGenotypes = new Label(map.findDominantGenotypes().toString());
        Label title = new Label("Dominant genotypes: ");
        VBox elements = new VBox(trackAnimalsButton, title, dominantGenotypes);
        panel = new VBox(elements);

         /* CSS */

        panel.setPadding(new Insets(10, 10,10, 10));

        elements.setSpacing(10);
        elements.setAlignment(Pos.BASELINE_CENTER);

        trackAnimalsButton.setStyle("-fx-background-color: #b4861f; -fx-font-weight: 800; -fx-font-size: 14px; -fx-font-family: 'JetBrains Mono'");

        title.setStyle("-fx-text-fill: #a4a4a4; -fx-font-weight: 700; -fx-font-size: 18px; -fx-font-family: 'JetBrains Mono'");
        dominantGenotypes.setStyle("-fx-text-fill: #a4a4a4; -fx-font-weight: 500; -fx-font-size: 15px; -fx-font-family: 'JetBrains Mono'");
    }

    public void updateStats() {
        Platform.runLater(() -> dominantGenotypes.setText(map.findDominantGenotypes().toString()));
    }

    public VBox getPanel() {
        return panel;
    }
}
