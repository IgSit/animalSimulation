package agh.ics.oop.gui.mapVisualisation;

import agh.ics.oop.mapObjects.MapObject;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;

public class GuiElementBox {
    private static final ImageFactory factory = new ImageFactory();
    private final int delimiter;

    public GuiElementBox(int delimiter) {
        this.delimiter = delimiter;  // smaller of map width and height to make at least not buggy visualisation
    }

    public VBox updateBox(MapObject object, boolean inJungle, int startEnergy) {
        try {
            Image image = factory.getImage(object.getImageURL());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200.0 / delimiter);
            imageView.setFitHeight(200.0 / delimiter);
            VBox vBox = new VBox(imageView);
            vBox.setAlignment(Pos.CENTER);
            if (inJungle)
                vBox.setBackground(new Background(new BackgroundFill(Color.rgb(8, 77, 8), CornerRadii.EMPTY, Insets.EMPTY)));
            else
                vBox.setBackground(new Background(new BackgroundFill(Color.rgb(135, 173, 33), CornerRadii.EMPTY, Insets.EMPTY)));
            if (object.hasEnergy()) {
                if (object.getEnergy() < 0.4 * startEnergy)
                    vBox.setStyle("-fx-border-width: 3; -fx-border-color: #b90101");
                else if (object.getEnergy() < 0.7 * startEnergy)
                    vBox.setStyle("-fx-border-width: 3; -fx-border-color: #e5b403");
                else
                    vBox.setStyle("-fx-border-width: 3; -fx-border-color: #54e50a");
            }
            if (object.isTrackedByGenotype())
                vBox.setBackground(new Background(new BackgroundFill(Color.rgb(21, 126, 182), CornerRadii.EMPTY, Insets.EMPTY)));
            return vBox;
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
            System.exit(1);
            return null;
        }
    }
}