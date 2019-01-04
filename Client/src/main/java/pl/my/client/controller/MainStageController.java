package pl.my.client.controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import pl.my.client.model.Client;
import pl.my.client.model.utils.FxmlUtils;

import java.net.URL;
import java.util.ResourceBundle;


import static pl.my.client.model.Main.CHAT_STAGE_FXML;
import static pl.my.client.model.Main.LOGIN_STAGE_FXML;

public class MainStageController implements Initializable {

    @FXML
    public ToggleGroup toggleMainStage;
    @FXML
    public Label labelMainStage;
    @FXML
    public Label nickLabelMainStage;
    @FXML
    public TextField nameFieldMainStage;
    @FXML
    public Button acceptButtonMainStage;
    @FXML
    public AnchorPane anchorPaneMainStage;
    @FXML
    public ToggleButton localHostButtonMainStage;
    @FXML
    public Label informationLabelMainStage;
    @FXML
    public Label loginFieldMainStage;


    public static Client client;

//TODO change label width with "already exist"

    private StringProperty propertyTextField = new SimpleStringProperty(this, "nameProperty");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = new Client();
        client.startKlient();

        initBindings();


        nameFieldMainStage.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!client.getPropertyUserList().isEmpty()) {
                if (getPropertyTextField().length() < 3) {
                    acceptButtonMainStage.setDisable(true);
                    informationLabelMainStage.setTextFill(Color.rgb(255, 255, 0));
                    informationLabelMainStage.setText("too short");

                } else if (client.getPropertyUserList().contains(getPropertyTextField() + "\n")) {
                    acceptButtonMainStage.setDisable(true);
                    informationLabelMainStage.setTextFill(Color.rgb(248, 0, 0));
                    informationLabelMainStage.setText("already exist");

                } else {
                    informationLabelMainStage.setTextFill(Color.rgb(0, 187, 45));
                    informationLabelMainStage.setText("OK!");
                    acceptButtonMainStage.setDisable(false);

                }
            }
        });
    }

    private void initBindings() {

        acceptButtonMainStage.setDisable(true);
        nameFieldMainStage.textProperty().bindBidirectional(propertyTextFieldProperty());
        nickLabelMainStage.textProperty().bind(propertyTextFieldProperty());
        nameFieldMainStage.setDisable(true);

        toggleMainStage.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                nameFieldMainStage.setDisable(false);
        });

        //accept by press ENTER
        nameFieldMainStage.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
                acceptName();
        });
    }


    public void acceptName() {

        System.out.println("Nick :" + nameFieldMainStage.getText());
        System.out.println("Users: \n" + client.getPropertyUserList());

        client.setName(nameFieldMainStage.getText());
        client.sendCard();
        client.startPinger();

        AnchorPane anchorPaneChatStage = (AnchorPane) FxmlUtils.fxmlLoader(CHAT_STAGE_FXML);
        anchorPaneMainStage.getChildren().setAll(anchorPaneChatStage);

        //Window size and possition.
        assert anchorPaneChatStage != null: "anchorPaneChatStage - CHAT_STAGE is NULL ";
        anchorPaneChatStage.getScene().getWindow().setX(anchorPaneChatStage.getScene().getWindow().getX() - 100);
        anchorPaneChatStage.getScene().getWindow().setY(anchorPaneChatStage.getScene().getWindow().getY() - 100);
        anchorPaneChatStage.getScene().getWindow().setHeight(640);
        anchorPaneChatStage.getScene().getWindow().setWidth(820);

        nameFieldMainStage.clear();
    }





    public void loginPanel() {
        AnchorPane anchorPaneChatStage = (AnchorPane) FxmlUtils.fxmlLoader(LOGIN_STAGE_FXML);
        anchorPaneMainStage.getChildren().setAll(anchorPaneChatStage);
    }

    public void changeFont() {
        loginFieldMainStage.setTextFill(Color.rgb(0, 0, 220));
        loginFieldMainStage.setStyle("-fx-font-weight: bold; -fx-underline: true");
    }

    public void basicFont() {
        loginFieldMainStage.setTextFill(Color.rgb(0, 0, 0));
        loginFieldMainStage.setStyle(null);
    }

    private String getPropertyTextField() {
        return propertyTextField.get();
    }

    private StringProperty propertyTextFieldProperty() {
        return propertyTextField;
    }
}
