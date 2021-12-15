package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class App extends Application{
    private PartialApp borderedApp;
    private PartialApp nonBorderedApp;

    @Override
    public void start(Stage primaryStage) {
        try {
            String[] labelTexts = {"Map width: ", "Map height: ", "Jungle ratio: ", "Animal start energy: ",
                    "Plant profit energy: ", "Animal move energy: ", "Number of animals: "};
            String[] defaultValues = {"10", "10", "0.5", "25", "15", "3", "40"};
            int n = labelTexts.length;
            TextField[] textFields = new TextField[n];
            Label[] labels = new Label[n];

            for (int i =0; i < n; i++) {
                labels[i] = new Label(labelTexts[i]);
                textFields[i] = new TextField(defaultValues[i]);
            }
            VBox labelBox = new VBox(labels[InputValues.WIDTH.toIndex()], labels[InputValues.HEIGHT.toIndex()],
                    labels[InputValues.JUNGLE_RATIO.toIndex()], labels[InputValues.ANIMAL_ENERGY.toIndex()],
                    labels[InputValues.PLANT_ENERGY.toIndex()], labels[InputValues.MOVE_ENERGY.toIndex()],
                    labels[InputValues.ANIMAL_AMOUNT.toIndex()]);
            labelBox.setSpacing(9);

            VBox textBox = new VBox(textFields[InputValues.WIDTH.toIndex()], textFields[InputValues.HEIGHT.toIndex()],
                    textFields[InputValues.JUNGLE_RATIO.toIndex()], textFields[InputValues.ANIMAL_ENERGY.toIndex()],
                    textFields[InputValues.PLANT_ENERGY.toIndex()], textFields[InputValues.MOVE_ENERGY.toIndex()],
                    textFields[InputValues.ANIMAL_AMOUNT.toIndex()]);

            HBox inputBox = new HBox(labelBox, textBox);
            Button button = new Button("Apply and run");
            VBox menu = new VBox(inputBox, button);
            menu.setAlignment(Pos.CENTER);

            Scene scene = new Scene(menu);
            primaryStage.setScene(scene);
            primaryStage.show();

            button.setOnAction(event -> {
                int width = Integer.parseInt(textFields[InputValues.WIDTH.toIndex()].getText());
                int height = Integer.parseInt(textFields[InputValues.HEIGHT.toIndex()].getText());
                double jungleRation = Double.parseDouble(textFields[InputValues.JUNGLE_RATIO.toIndex()].getText());
                int animalEnergy = Integer.parseInt(textFields[InputValues.ANIMAL_ENERGY.toIndex()].getText());
                int plantEnergy = Integer.parseInt(textFields[InputValues.PLANT_ENERGY.toIndex()].getText());
                int moveEnergy = Integer.parseInt(textFields[InputValues.MOVE_ENERGY.toIndex()].getText());
                RectangularMap borderedMap = new RectangularMap(width, height, jungleRation, animalEnergy, plantEnergy, moveEnergy, true);
                RectangularMap nonBorderedMap = new RectangularMap(width, height, jungleRation, animalEnergy, plantEnergy, moveEnergy, false);
                int numberOfAnimals = Integer.parseInt(textFields[InputValues.ANIMAL_AMOUNT.toIndex()].getText());

                borderedApp = new PartialApp(borderedMap, numberOfAnimals);
                nonBorderedApp = new PartialApp(nonBorderedMap, numberOfAnimals);

                runSimulation(primaryStage);
            });
        }
        catch(IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            System.exit(1);
        }
    }

    private void runSimulation(Stage primaryStage) {
        Scene newScene = new Scene(new HBox(borderedApp.runSimulation(), nonBorderedApp.runSimulation()), 1300, 700);
        primaryStage.setScene(newScene);
        primaryStage.show();
    }

}
