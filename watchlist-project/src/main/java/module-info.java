module watchlist {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens watchlist to javafx.graphics, javafx.fxml, com.fasterxml.jackson.databind;
}