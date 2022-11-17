package watchlist.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class LoginControllerTest extends ApplicationTest {

  private LoginController controller;

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @BeforeEach
  public void setup() throws IOException {
    controller = new LoginController();
  }

  @Test
  @DisplayName("Testing the app setup")
  public void testAppSetup() {
    assertNotNull(this.controller);
  }

  @Test
  @DisplayName("Testing inputfield for valid username")
  public void testValidUsername() {
    TextField tf = lookup("#name").query();
    clickOn(tf).write("TestUser");
    clickOn("#login");
  }

  @Test
  @DisplayName("Testing inputfield for invalid username")
  public void testInvalidUsername() {
    TextField tf = lookup("#name").query();
    clickOn(tf).write("TestUser123");
    clickOn("#login");
  }

}
