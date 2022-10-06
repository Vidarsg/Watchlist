package watchlist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class WatchlistApp extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(FXMLLoader.load(WatchlistApp.class.getResource("Login.fxml"))));
        primaryStage.setTitle("Watchlist");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("file:src/main/resources/watchlist/images/watchlist-favicon.png")); 
        primaryStage.show();
        
    }
    
    public static void main(String[] args) {
        WatchlistApp.launch(args);
    }
}