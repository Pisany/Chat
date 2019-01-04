package pl.my.client.controller;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


import static pl.my.client.controller.MainStageController.client;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginStageController implements Initializable {

    public static final String ICONS_ACCEPT_PNG = "/icons/Accept.png";
    public static final String ICONS_CANCEL_PNG = "/icons/Cancel.png";
    public static final String ICONS_LENGTH_PNG = "/icons/length.png";
    @FXML
    public ToggleButton registerButtonLoginStage;
    @FXML
    public ToggleButton loginButtonLoginStage;
    @FXML
    public TextField nameFieldLoginStage;
    @FXML
    public PasswordField firstPasswordFieldLoginStage;
    @FXML
    public PasswordField secondPasswordFieldLoginStage;
    @FXML
    public TextField emailFieldLoginStage;
    @FXML
    public Button acceptButtonLoginStage;
    @FXML
    public ToggleGroup toggleGroupLoginStage;
    @FXML
    public Label secondPasswordLabelLoginStage;
    @FXML
    public Label emailLabelLoginStage;
    @FXML
    public ImageView imageUsernameLoginStage;
    @FXML
    public ImageView imageFirstPasswordLoginStage;
    @FXML
    public ImageView imageSecondPasswordLoginStage;
    @FXML
    public ImageView imageEmailLoginStage;
    @FXML
    public Label miniLabelUsernameLoginStage;
    @FXML
    public Label miniLabelFirstPasswordLoginStage;
    @FXML
    public Label miniLabelSecondPasswordLoginStage;
    @FXML
    public Label miniLabelEmailLoginStage;

//TODO Fix TAB switching

    private StringProperty propertyUsername = new SimpleStringProperty(this, "username", "");
    private StringProperty propertyFirstPassword = new SimpleStringProperty(this, "firstPassword", "");
    private StringProperty propertySecondPassword = new SimpleStringProperty(this, "secondPassword", "");


    private Boolean usernameFlag = false;
    private Boolean firstPasswordFlag = false;
    private Boolean secondPasswordFlag = false;
    private Boolean emailFlag = false;

    private Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9]+[._a-zA-Z0-9]*@[a-zA-Z0-9]+\\.[a-z]{2,}[.a-z]*$");
    private Pattern passwordPattern = Pattern.compile("");
//    private Pattern passwordPattern = Pattern.compile("");

    private Matcher emailMatcher = null;
    private Matcher passwordMatcher = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        login();

        acceptButtonLoginStage.setDisable(true);

        nameFieldLoginStage.textProperty().bindBidirectional(propertyUsername);
        firstPasswordFieldLoginStage.textProperty().bindBidirectional(propertyFirstPassword);
        secondPasswordFieldLoginStage.textProperty().bindBidirectional(propertySecondPassword);

    }


    public void login() {
        setComponentsVisible("Login", false);
        clearImage();
        setImageVisible(false);
        cleatFields();


        checkUsernameLogin();
        checkPasswordLogin();
        setAcceptButtonLoginStage();

    }

    public void register() {
        setComponentsVisible("Signup", true);
        setImageVisible(false);
        cleatFields();


        checkUsernameRegister();
        checkFirstPasswordRegister();
        checkSecondPasswordRegister();
        checkEmailRegister();

        setAcceptButtonLoginStage();

    }

    private void setComponentsVisible(String buttonName, Boolean flag) {
        acceptButtonLoginStage.setText(buttonName);

        secondPasswordFieldLoginStage.setVisible(flag);
        secondPasswordFieldLoginStage.setDisable(!flag);
        secondPasswordLabelLoginStage.setVisible(flag);
        secondPasswordLabelLoginStage.setDisable(!flag);

        emailFieldLoginStage.setVisible(flag);
        emailFieldLoginStage.setDisable(!flag);
        emailLabelLoginStage.setVisible(flag);
        emailLabelLoginStage.setDisable(!flag);

    }

    private void clearImage() {
        imageUsernameLoginStage.setImage(null);
        imageFirstPasswordLoginStage.setImage(null);
        imageSecondPasswordLoginStage.setImage(null);
        imageEmailLoginStage.setImage(null);
    }

    private void cleatFields() {
        nameFieldLoginStage.clear();
        firstPasswordFieldLoginStage.clear();
        secondPasswordFieldLoginStage.clear();
        emailFieldLoginStage.clear();
    }

    private void setImageVisible(Boolean flag) {
        imageUsernameLoginStage.setVisible(flag);
        imageFirstPasswordLoginStage.setVisible(flag);
        imageSecondPasswordLoginStage.setVisible(flag);
        imageEmailLoginStage.setVisible(flag);

        imageUsernameLoginStage.setDisable(!flag);
        imageFirstPasswordLoginStage.setDisable(!flag);
        imageSecondPasswordLoginStage.setDisable(!flag);
        imageEmailLoginStage.setDisable(!flag);
    }

    private void setAdditionalLabelVisible(Boolean flag) {
        miniLabelUsernameLoginStage.setVisible(flag);
        miniLabelFirstPasswordLoginStage.setVisible(flag);
        miniLabelSecondPasswordLoginStage.setVisible(flag);
        miniLabelEmailLoginStage.setVisible(flag);
    }


    private void checkUsernameRegister() {

        ChangeListener<String> changeListener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                imageUsernameLoginStage.setVisible(true);

                if (registerButtonLoginStage.isSelected()) {
                    if (getPropertyUsername().length() >= 3 && getPropertyUsername().length() < 13) {
                        client.checkUserName(getPropertyUsername());

                        do {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        } while (!client.propertyCheckingFlagProperty().get());

                        if (client.getUsernameFlag()) {
                            imageUsernameLoginStage.setImage(new Image(ICONS_CANCEL_PNG));
                            usernameFlag = false;
                        } else {
                            imageUsernameLoginStage.setImage(new Image(ICONS_ACCEPT_PNG));
                            usernameFlag = true;
                        }
                    } else {
                        imageUsernameLoginStage.setImage(new Image(ICONS_LENGTH_PNG));
                        usernameFlag = false;
                    }


                    setAcceptButtonLoginStage();
                } else {
                    imageUsernameLoginStage.setImage(null);
                    nameFieldLoginStage.textProperty().removeListener(this);
                }
            }
        };


        nameFieldLoginStage.textProperty().addListener(changeListener);


    }

    private void checkFirstPasswordRegister() {

        firstPasswordFieldLoginStage.textProperty().addListener((observable, oldValue, newValue) -> {
            imageFirstPasswordLoginStage.setVisible(true);
            if (registerButtonLoginStage.isSelected()) {
                if (getPropertyFirstPassword().length() >= 6 && getPropertyFirstPassword().length() <= 18) {
                    imageFirstPasswordLoginStage.setImage(new Image(ICONS_ACCEPT_PNG));
                    firstPasswordFlag = true;

                } else {
                    imageFirstPasswordLoginStage.setImage(new Image(ICONS_CANCEL_PNG));
                    firstPasswordFlag = false;
                }
                setAcceptButtonLoginStage();
            } else {
                imageFirstPasswordLoginStage.setImage(null);
            }

        });

    }

    private void checkSecondPasswordRegister() {

        secondPasswordFieldLoginStage.textProperty().addListener((observable, oldValue, newValue) -> {
            imageSecondPasswordLoginStage.setVisible(true);
            if (registerButtonLoginStage.isSelected()) {
                if (getPropertyFirstPassword().equals(getPropertySecondPassword())) {
                    imageSecondPasswordLoginStage.setImage(new Image(ICONS_ACCEPT_PNG));
                    secondPasswordFlag = true;
                } else {
                    imageSecondPasswordLoginStage.setImage(new Image(ICONS_CANCEL_PNG));
                    secondPasswordFlag = false;

                }
                setAcceptButtonLoginStage();
            } else {
                imageSecondPasswordLoginStage.setImage(null);
            }
        });
    }

    private void checkEmailRegister() {

        emailFieldLoginStage.textProperty().addListener((observable, oldValue, newValue) -> {
            imageEmailLoginStage.setVisible(true);
            if (registerButtonLoginStage.isSelected()) {

                emailMatcher = emailPattern.matcher(emailFieldLoginStage.getText());

                if (emailMatcher.matches()) {
                    emailFlag = true;
                    imageEmailLoginStage.setImage(new Image(ICONS_ACCEPT_PNG));
                } else {
                    emailFlag = false;
                    imageEmailLoginStage.setImage(new Image(ICONS_CANCEL_PNG));
                }
                setAcceptButtonLoginStage();
            } else {
                imageSecondPasswordLoginStage.setImage(null);
            }
        });

    }

    private void checkUsernameLogin() {
        nameFieldLoginStage.textProperty().addListener((observable, oldValue, newValue) -> {
            usernameFlag = getPropertyUsername().length() >= 3 && getPropertyUsername().length() < 13;
        });
    }

    private void checkPasswordLogin() {
        firstPasswordFieldLoginStage.textProperty().addListener((observable, oldValue, newValue) -> {
            firstPasswordFlag = getPropertyFirstPassword().length() >= 6 && getPropertyFirstPassword().length() <= 18;
        });
    }

    private void setAcceptButtonLoginStage() {

        if (registerButtonLoginStage.isSelected()) {
            if (usernameFlag && firstPasswordFlag && secondPasswordFlag && emailFlag) {
                acceptButtonLoginStage.setDisable(false);
            } else
                acceptButtonLoginStage.setDisable(true);
        } else {
            if (usernameFlag && firstPasswordFlag) {
                acceptButtonLoginStage.setDisable(false);
            } else
                acceptButtonLoginStage.setDisable(true);

        }

    }

    public void acceptButtonLoginStage() {
        //send everything, everywhere to everybody

        if (registerButtonLoginStage.isSelected()) {
            client.sendNewUser(nameFieldLoginStage.getText(), firstPasswordFieldLoginStage.getText(), emailFieldLoginStage.getText());
        } else
            client.sendLoginCard(nameFieldLoginStage.getText(), firstPasswordFieldLoginStage.getText());

        login();
        loginButtonLoginStage.setSelected(true);

    }


    public void imageEnter(MouseEvent mouseEvent) {
        setAdditionalLabelVisible(false);
        String foo = mouseEvent.getSource().toString();
        if (foo.contains("id=imageUsernameLoginStage") && imageUsernameLoginStage.isVisible()) {
            String foo2 = imageUsernameLoginStage.getImage().getUrl();
            if (foo2.contains(ICONS_ACCEPT_PNG)) {
                miniLabelUsernameLoginStage.setVisible(true);
                miniLabelUsernameLoginStage.setText("Login is not used :D");

            } else if (foo2.contains(ICONS_CANCEL_PNG)) {
                miniLabelUsernameLoginStage.setVisible(true);
                miniLabelUsernameLoginStage.setText("Login is used :<");

            } else if (foo2.contains(ICONS_LENGTH_PNG)) {
                miniLabelUsernameLoginStage.setVisible(true);
                miniLabelUsernameLoginStage.setText("Login is too short");
            }

        } else if (foo.contains("id=imageFirstPasswordLoginStage") && imageFirstPasswordLoginStage.isVisible()) {
            String foo2 = imageFirstPasswordLoginStage.getImage().getUrl();
            if (foo2.contains(ICONS_ACCEPT_PNG)) {
                miniLabelFirstPasswordLoginStage.setVisible(true);
                miniLabelFirstPasswordLoginStage.setText("Passwprd is OK.  :D");

            } else if (foo2.contains(ICONS_CANCEL_PNG)) {
                miniLabelFirstPasswordLoginStage.setVisible(true);
                miniLabelFirstPasswordLoginStage.setText("Uppercase, lowercase, and numbers - needed  ");

            } else if (foo2.contains(ICONS_LENGTH_PNG)) {
                miniLabelFirstPasswordLoginStage.setVisible(true);
                miniLabelFirstPasswordLoginStage.setText("Password is too short");
            }

        } else if (foo.contains("id=imageSecondPasswordLoginStage") && imageSecondPasswordLoginStage.isVisible()) {
            String foo2 = imageSecondPasswordLoginStage.getImage().getUrl();
            if (foo2.contains(ICONS_ACCEPT_PNG)) {
                miniLabelSecondPasswordLoginStage.setVisible(true);
                miniLabelSecondPasswordLoginStage.setText("Passwprd is OK.  :D");

            } else if (foo2.contains(ICONS_CANCEL_PNG)) {
                miniLabelSecondPasswordLoginStage.setVisible(true);
                miniLabelSecondPasswordLoginStage.setText("Confirm Password must match exactly");
            }


        } else if (foo.contains("id=imageEmailLoginStage") && imageEmailLoginStage.isVisible()) {
            String foo2 = imageEmailLoginStage.getImage().getUrl();
            if (foo2.contains(ICONS_ACCEPT_PNG)) {
                miniLabelEmailLoginStage.setVisible(true);
                miniLabelEmailLoginStage.setText("Email is OK.  :D");

            } else if (foo2.contains(ICONS_CANCEL_PNG)) {
                miniLabelEmailLoginStage.setVisible(true);
                miniLabelEmailLoginStage.setText("Email is not correct");
            }

        }
    }

    public void imageExited(MouseEvent mouseEvent) {
        setAdditionalLabelVisible(false);
        miniLabelUsernameLoginStage.setText("");
        miniLabelFirstPasswordLoginStage.setText("");
        miniLabelSecondPasswordLoginStage.setText("");
        miniLabelEmailLoginStage.setText("");
    }

    private String getPropertyUsername() {
        return propertyUsername.get();
    }

    private String getPropertyFirstPassword() {
        return propertyFirstPassword.get();
    }

    private String getPropertySecondPassword() {
        return propertySecondPassword.get();
    }


}
