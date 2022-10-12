package watchlist.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginController {

    @FXML private Button exit;
    @FXML private Button login;
    @FXML private TextField name;
    @FXML private Label invalidInput;

    public void initialize() {

        Platform.runLater(new Runnable() {

            // Sets TextField in focus when the app starts

            @Override
            public void run() {		
                name.requestFocus();
            }
        });

        invalidInput.setVisible(false);
    }

    // Opens Watchlist.fxml if username is valid

    @FXML
	public void onSubmit(ActionEvent event) {   
		try {
			if(name.getText().matches("[a-zA-Z æøåÆØÅ]+")) {
		    	try {
			        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Watchlist.fxml"));
			        Parent root = (Parent) fxmlLoader.load();
			        Stage stage = new Stage();
			        stage.setTitle("Watchlist");
			        stage.setResizable(false);
			        stage.setScene(new Scene(root));
                    //stage.getIcons().add(new Image("file:fxui/src/main/resources/watchlist/ui/images/watchlist-favicon.png"));
			        stage.show();
			        login.getScene().getWindow().hide();
			        
			        WatchlistController appCon = fxmlLoader.getController();
			        appCon.setUsername(name.getText());
			    } catch(Exception e) {
			        e.printStackTrace();
			    }
			} else {
				invalidInput.setVisible(true);
				throw new IllegalArgumentException("Invalid username.");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

    // Exits app
    
    public void exitProgram() {	 
        System.exit(0);
    }

}
