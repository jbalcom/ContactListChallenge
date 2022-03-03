module com.example.contactschallenge {
    requires javafx.controls;
    requires javafx.fxml;
    requires jlfgr;
    requires java.xml;


    opens com.example.contactschallenge to javafx.fxml;
    exports com.example.contactschallenge;
}