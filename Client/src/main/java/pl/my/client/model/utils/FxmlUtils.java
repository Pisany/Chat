package pl.my.client.model.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;


public class FxmlUtils {


    public static Pane fxmlLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));


        try {
            return loader.load();
        } catch (IOException e) {
//            DialogsUtils.errorDialog(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}