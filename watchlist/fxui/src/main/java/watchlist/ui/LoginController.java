package watchlist.ui;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Controller for login screen.
 *
 * @author IT1901 gruppe 63
 */
public class LoginController {

  @FXML
  private Button exit;
  @FXML
  private Button login;
  @FXML
  private TextField name;
  @FXML
  private Label invalidInput;
  @FXML
  private Label logo;

  /**
   * Runs login.fxml and sets TextField in focus when the window is opened.
   *
   */
  public void initialize() {

    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        name.requestFocus();
      }
    });
    name.setOnKeyPressed(new EventHandler<KeyEvent>() {

      @Override
      public void handle(KeyEvent key) {
        if (key.getCode().equals(KeyCode.ENTER)) {
          onSubmit();
        }
      }

    });
    Font logoFont = Font.loadFont(WatchlistApp.class.getResourceAsStream(
        "fonts/Haettenschweiler-Regular.ttf"), 96);
    logo.setFont(logoFont);
    invalidInput.setVisible(false);
  }

  /**
   * Opens Watchlist.fxml.
   *
   * @throws IllegalArgumentException if username is invalid
   */
  @FXML
  public void onSubmit() {
    try {
      if (name.getText().matches("[a-zA-ZæøåÆØÅ]+")) {
        try {
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Watchlist.fxml"));
          Parent root = (Parent) fxmlLoader.load();
          Stage stage = new Stage();
          stage.setTitle("Watchlist");
          stage.setResizable(false);
          stage.setScene(new Scene(root));
          stage.getIcons().add(new Image(WatchlistApp.class.getResourceAsStream(
              "images/watchlist-favicon.png")));
          stage.show();
          login.getScene().getWindow().hide();

          WatchlistController appCon = fxmlLoader.getController();
          appCon.setUsername(name.getText());
        } catch (Exception e) {
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

}
