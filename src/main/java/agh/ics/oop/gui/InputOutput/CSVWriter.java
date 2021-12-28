package agh.ics.oop.gui.InputOutput;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// link: https://stackoverflow.com/questions/30073980/java-writing-strings-to-a-csv-file

public class CSVWriter {
    private final String fileName;

    public CSVWriter(String fileName) {
        this.fileName = fileName;
        try (PrintWriter writer = new PrintWriter( new FileWriter(this.fileName, false))) {

            String row = "NumberOfAnimals, NumberOfGrass, AvgAnimalEnergy, AvgAnimalLifeTime, AvgAmountOfChildren,\n";
            writer.write(row);
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void addStats(String row) {
        try (PrintWriter writer = new PrintWriter( new FileWriter(fileName, true))) {
            writer.write(row);
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}