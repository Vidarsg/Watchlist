module watchlist {
    requires javafx.controls;
    requires javafx.fxml;

    opens watchlist to javafx.graphics, javafx.fxml;
}