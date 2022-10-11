module watchlist.ui {
    requires com.fasterxml.jackson.databind;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    
    requires watchlist.core;

    opens watchlist.ui to javafx.graphics, javafx.fxml, watchlist.core;
}