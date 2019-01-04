package pl.my.client.model;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.my.client.controller.MainStageController;
import pl.my.client.model.utils.FxmlUtils;

public class Main extends Application {

    static final int PORT = 5000;
    static final String IP = "127.0.0.1";

    static final int PING_DEALY = 10000; //ms

    private static final String MAIN_STAGE_FXML = "/fxml/MainStage.fxml";
    public static final String CHAT_STAGE_FXML = "/fxml/ChatStage.fxml";
    public static final String LOGIN_STAGE_FXML = "/fxml/LoginStage.fxml";



    //start programu
    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = FxmlUtils.fxmlLoader(MAIN_STAGE_FXML);
        assert root != null: "Root - FxmlUtils is NULL";
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        MainStageController.client.closeConnection();
    }

}
