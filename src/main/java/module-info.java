module dev.nkkrisz.coffeetype {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.nkkrisz.coffeetype to javafx.fxml;
    exports dev.nkkrisz.coffeetype;
}