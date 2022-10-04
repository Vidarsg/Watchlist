module watchlist.ui {
    requires javafx.controls;
    requires javafx.fxml;
    
    requires watchlist.core;

    opens watchlist.ui to javafx.graphics, javafx.fxml, watchlist.core;
}