package agh.ics.oop.gui.mapVisualisation;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class ImageFactory {
    private static final HashMap<String, Image> dict = new HashMap<>();

    public ImageFactory() {}

    public Image getImage(String url) throws FileNotFoundException {
        if (dict.containsKey(url))
            return dict.get(url);
        Image image = new Image(new FileInputStream(url));
        dict.put(url, image);
        return image;
    }
}