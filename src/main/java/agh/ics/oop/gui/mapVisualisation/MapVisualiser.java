package agh.ics.oop.gui.mapVisualisation;

import agh.ics.oop.map.AbstractWorldMap;
import agh.ics.oop.map.RectangularMap;
import agh.ics.oop.map.Vector2d;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MapVisualiser {
    private final GridPane gridPane = new GridPane();
    private final RectangularMap map;

    public MapVisualiser(RectangularMap map) {
        this.map = map;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    private VBox drawCell(int x, int y, Vector2d lowerLeft, Vector2d upperRight) {
        int mapX = lowerLeft.x() + x - 1;
        int mapY = upperRight.y() - y + 1;
        if (x == 0 && y == 0)
            return new VBox(new Label("y/x"));
        else if (x == 0)
            return new VBox(new Label(Integer.toString(mapY)));
        else if (y == 0)
            return new VBox(new Label(Integer.toString(mapX)));
        else {
            Vector2d position = new Vector2d(mapX, mapY);
            if (map.isOccupied(position)) {
                GuiElementBox elementBox = new GuiElementBox();
                return elementBox.updateBox(map.objectAt(position), map.inJungle(position), map.getStartEnergy());
            }
            VBox emptyBox = new VBox();
            if (map.inJungle(new Vector2d(mapX, mapY)))
                emptyBox.setBackground(new Background(new BackgroundFill(Color.rgb(8, 77, 8), CornerRadii.EMPTY, Insets.EMPTY)));
            else
                emptyBox.setBackground(new Background(new BackgroundFill(Color.rgb(135, 173, 33), CornerRadii.EMPTY, Insets.EMPTY)));
            return emptyBox;
        }
    }

    public void drawMap(AbstractWorldMap map) {
        Vector2d lowerLeft = map.getLowerLeft();
        Vector2d upperRight = map.getUpperRight();
        int xRange = upperRight.x() - lowerLeft.x() + 1;
        int yRange = upperRight.y() - lowerLeft.y() + 1;

        for (int x = 0; x <= xRange; x++) {
            for (int y = 0; y <= yRange; y++) {
                VBox cell = drawCell(x, y, lowerLeft, upperRight);
                cell.setAlignment(Pos.CENTER);
                gridPane.add(cell, x, y);
                GridPane.setValignment(cell, VPos.CENTER);
                GridPane.setHalignment(cell, HPos.CENTER);
            }
        }
        for (int i = 0; i <= yRange; ++i) {
            gridPane.getRowConstraints().add(new RowConstraints(25));
        }

        for (int i = 0; i <= xRange; ++i) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(25));
        }
        gridPane.setGridLinesVisible(true);
    }

    public void mapChanged() {
        Platform.runLater(() -> {
            gridPane.setGridLinesVisible(false);
            gridPane.getChildren().clear();
            gridPane.getRowConstraints().clear();
            gridPane.getColumnConstraints().clear();
            drawMap(map);
        });
    }
}
