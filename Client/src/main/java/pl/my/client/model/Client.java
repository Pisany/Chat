package pl.my.client.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static pl.my.client.controller.MainStageController.client;
import static pl.my.client.model.Main.IP;
import static pl.my.client.model.Main.PORT;


public class Client {


    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private Socket socket;

    private String name;
    private String password;

    private String str;

    private Boolean usernameFlag;

    private BooleanProperty propertyCheckingFlag = new SimpleBooleanProperty(this, "checkingFlag", false);
    private StringProperty propertyTextArea = new SimpleStringProperty(this, "chatHistory", "*******************************************Witamy na chacie ********************************************");
    private StringProperty propertyUserList = new SimpleStringProperty(this, "userList");


    public void startKlient() {
        try {
            socket = new Socket(IP, PORT);
            System.out.println("Podłączono do " + socket);

            printWriter = new PrintWriter(socket.getOutputStream());

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            startReceiver();

        } catch (Exception e) {
            System.out.println("Błąd przy starcie klienta");
            //TODO add popup / close app
        }
    }

    private void startReceiver() {
        //tworzymy nowy watek z obiektem odbiorcy odpowiedzialnym za nasłuch
        Thread t1 = new Thread(new Receiver());
        t1.start();
        System.out.println("StartReceiver,");
        printWriter.println("#getUsers# -");
        printWriter.flush();
    }

    public void startPinger() {
        //tworzymy nowy watek z obiektem wysyłającym informacje o aktywnościu
        Thread t2 = new Thread(new Pinger());
        t2.start();
    }

    public void sendCard() {
        printWriter.println(name + " - dołączył do chatu ");
        printWriter.flush();
    }

    public void sendMessage() {
        if (!client.getStr().equalsIgnoreCase("/quit")) {
            printWriter.println(name + ": " + str);
            printWriter.flush();
        } else {
            closeConnection();
        }
    }

    public void checkUserName(String name) {
        //send name to server
        System.out.println("Sprawdz imię : " + name);
        printWriter.println("#checkUsername# -" + name);
        printWriter.flush();
    }

    public void sendNewUser(String username, String password, String email) {
        Encryption encryption = new Encryption();
        System.out.println("#newUser# -" + username + "," + encryption.code(password) + "," + email);
        printWriter.println("#newUser# -" + username + "-" + encryption.code(password) + "-" + email);
        printWriter.flush();
    }

    public void sendLoginCard(String username, String password){
        Encryption encryption = new Encryption();

        System.out.println("#loginUser# -" + username + "-" + encryption.code(password));
        printWriter.println("#loginUser# -" + username + "-" + encryption.code(password));
        printWriter.flush();
    }

    public void haslo(){
    //TODO MD5 - szyfrowanie
        System.out.println();
    }

    void closeConnection() {
        printWriter.println(name + " - rozłonczył się");
        printWriter.flush();
        printWriter.close();

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public StringProperty propertyTextAreaProperty() {
        return propertyTextArea;
    }

    public String getPropertyUserList() {
        return propertyUserList.get();
    }

    public StringProperty propertyUserListProperty() {
        return propertyUserList;
    }

    BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    PrintWriter getPrintWriter() {
        return printWriter;
    }

    public Boolean getUsernameFlag() {
        return usernameFlag;
    }

    void setUsernameFlag(Boolean usernameFlag) {
        this.usernameFlag = usernameFlag;
    }

    public BooleanProperty propertyCheckingFlagProperty() {
        return propertyCheckingFlag;
    }

}

