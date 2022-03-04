package agh.ics.oop.gui;

import agh.ics.oop.gui.InputOutput.InputValues;
import agh.ics.oop.map.RectangularMap;
import javafx.application.*;
import javafx.geometry.Insets;
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
                labels[i].setStyle("-fx-text-fill: #bbbbbb; -fx-font-size: 15px");
                textFields[i] = new TextField(defaultValues[i]);
                textFields[i].setStyle("-fx-background-color: #797979; -fx-border-width: 2px; -fx-border-color: #484747");
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
            menu.setSpacing(20);

            Scene scene = new Scene(menu, 320, 300);
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

                borderedApp = new PartialApp(borderedMap, numberOfAnimals, "borderedData.csv");
                nonBorderedApp = new PartialApp(nonBorderedMap, numberOfAnimals, "nonBorderedData.csv");

                runSimulation(primaryStage);
            });

            /* CSS */

            menu.setStyle("-fx-background-color: #5b5b5b");
            menu.setPadding(new Insets(10, 10, 10 ,10));

            button.setStyle("-fx-background-color: #2ea12e; -fx-font-weight: 800; -fx-font-size: 14px; " +
                    "-fx-font-family: 'JetBrains Mono'");
            button.setPadding(new Insets(10, 10 ,10 ,10));
        }
        catch(IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            System.exit(1);
        }
    }

    private void runSimulation(Stage primaryStage) {
        HBox simulations = new HBox(borderedApp.runSimulation(), nonBorderedApp.runSimulation());
        simulations.setSpacing(50);
        simulations.setStyle("-fx-background-color: #545353");
        Scene newScene = new Scene(simulations, 1300, 700);
        primaryStage.setScene(newScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });
    }

}
