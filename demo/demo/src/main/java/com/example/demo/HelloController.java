package com.example.demo;

import chat.ChatClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField serverIp;
    @FXML
    private TextField serverPortText;
    @FXML
    private TextField textMessage;
    @FXML
    private Button sendMessage;
    @FXML
    private static ListView chat;
    private Object ChatClient;

    @FXML
    public void SendMessage(){
        chat.getItems().add(new Label(textMessage.getText()));
        textMessage.clear();
    }

    @FXML
    public void connect(){
        if (serverIp.getText() != null && serverPortText.getText() != null)
        {

            int serverPort = Integer.parseInt(serverPortText.getText());

            Thread client = new ChatClient(serverIp.getText(), serverPort);

            client.start();
            chat.getItems().add(new Label("ciao"));
            serverIp.clear();
            serverPortText.clear();
        }
    }
    @FXML
    public static void addElementToChat(String message){
        chat.getItems().add(new Label(message));
    }



}