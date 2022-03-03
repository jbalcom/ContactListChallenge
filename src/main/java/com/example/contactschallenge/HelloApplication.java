package com.example.contactschallenge;

import com.example.contactschallenge.datamodel.ContactData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ContactData.getInstance().loadContacts();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        stage.setTitle("My Contacts");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
            ContactData.getInstance().saveContacts();
    }
}