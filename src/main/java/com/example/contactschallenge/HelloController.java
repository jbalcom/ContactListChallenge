package com.example.contactschallenge;

import com.example.contactschallenge.datamodel.Contact;
import com.example.contactschallenge.datamodel.ContactData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class HelloController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private ContactData data;

    @FXML
    private TableView<Contact> contactsTable;

    @FXML
    private TableColumn<Contact,String> firstNameCol;

    @FXML
    private TableColumn<Contact,String> lastNameCol;

    @FXML
    private TableColumn<Contact,String> phoneNumberCol;

    @FXML
    private TableColumn<Contact,String> notesCol;

    public void initialize() {
        data = new ContactData();
        contactsTable.setItems(data.getContacts());

        firstNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("phoneNumber"));
        notesCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("notes"));

        contactsTable.getColumns().setAll(firstNameCol, lastNameCol, phoneNumberCol, notesCol);
    }

    @FXML
    public void showAddContactDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add New Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            ContactController contactController = fxmlLoader.getController();
            Contact newContact = contactController.getNewContactInfo();
            data.addContact(newContact);
            data.saveContacts();
        }
    }
    @FXML
    public void showEditContactDialog(){

    }

    @FXML
    public void handleDeleteContact(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact");
        alert.setHeaderText("Delete contact: ");
        alert.setContentText("Are you sure?  Press OK to confirm, or cancel to back out.");
        Optional<ButtonType> result = alert.showAndWait();
    }

    @FXML
    public void showExitContactDialog(){
        Platform.exit();
    }
}