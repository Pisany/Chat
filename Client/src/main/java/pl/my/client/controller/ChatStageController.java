package pl.my.client.controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.util.ResourceBundle;

import static pl.my.client.controller.MainStageController.client;

public class ChatStageController implements Initializable {

    @FXML
    private TextArea historyChatStage;
    @FXML
    private TextArea userListChatStage;
    @FXML
    private TextField messageChatStage;
    @FXML
    private Button sendButtonChatStage;
    @FXML
    private AnchorPane anchorPaneChatStage;


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        historyChatStage.setEditable(false);
        userListChatStage.setEditable(false);
        historyChatStage.setWrapText(true);

        historyChatStage.textProperty().bind(client.propertyTextAreaProperty());
        userListChatStage.textProperty().bind(client.propertyUserListProperty());

        //confirm messages by ENTER KEY
        messageChatStage.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
                sendMessage();
        });
    }

    public void sendMessage() {
        client.setStr(messageChatStage.getText());
        client.sendMessage();
        messageChatStage.clear();
        messageChatStage.requestFocus();
    }


}




