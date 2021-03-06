package com.example.contactschallenge;

import com.example.contactschallenge.datamodel.Contact;
import com.example.contactschallenge.datamodel.ContactData;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    public void handleDoubleClick(){
     //   contactsTable.setEditable(true);

        contactsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        System.out.println("Double clicked");
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.initOwner(mainBorderPane.getScene().getWindow());
                        dialog.setTitle("Edit Contact");
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

                        ContactController contactController = fxmlLoader.getController();
                        contactController.editContact(contactsTable.getSelectionModel().getSelectedItem());

                        Optional<ButtonType> result = dialog.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            contactController.updateSelectedContact(contactsTable.getSelectionModel().getSelectedItem());
                            data.saveContacts();
                        }
                    }
                }
            }
        });
    }

    @FXML
    public void showEditContactDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Edit Contact");
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

        ContactController contactController = fxmlLoader.getController();
        contactController.editContact(contactsTable.getSelectionModel().getSelectedItem());

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            contactController.updateSelectedContact(contactsTable.getSelectionModel().getSelectedItem());
            data.saveContacts();
        }
    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent){
        Contact selectedItem = contactsTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            if (keyEvent.getCode().equals(KeyCode.DELETE)){
                data.deleteSelectedContact(selectedItem);
                data.saveContacts();
            }
        }
    }

    @FXML
    public void handleDeleteContact(){
        data.deleteSelectedContact(contactsTable.getSelectionModel().getSelectedItem());
        data.saveContacts();
    }

    @FXML
    public void showExitContactDialog(){
        Platform.exit();
    }
}