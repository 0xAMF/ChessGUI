module com.example.tsssssst {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tsssssst to javafx.fxml;
    exports GUI;
}