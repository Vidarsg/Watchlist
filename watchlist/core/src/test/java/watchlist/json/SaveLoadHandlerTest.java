package watchlist.json;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import watchlist.core.Movie;
import watchlist.core.User;
import watchlist.core.Watchlist;

public class SaveLoadHandlerTest {

  private User user;
  private Watchlist watchlist;
  private SaveLoadHandler saveLoadHandler = new SaveLoadHandler();
  private ObjectMapper objectMapper = new ObjectMapper();

  private ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());

  private WireMockConfiguration configuration;
  private WireMockServer wireMockServer;

  /**
   * Setup for the tests.
   */
  @BeforeEach
  public void setup() {
    user = new User("TestUser");
    watchlist = new Watchlist();
    try {
      watchlist.setList(objectMapper.readValue(
          SaveLoadHandlerTest.class.getResourceAsStream("test-movies.json"),
          new TypeReference<>() {}));
    } catch (IOException e) {
      e.printStackTrace();
    }

    configuration = WireMockConfiguration.wireMockConfig().port(8080);
    wireMockServer = new WireMockServer(configuration.portNumber());
    wireMockServer.start();
    System.out.println(get(urlEqualTo("/movies").toString()));
  }

  @Test
  @DisplayName("Testing saveUserList")
  public void testSaveUserList() {
    // Testing that exception is thrown when saveFilePath is null
    assertThrows(IllegalStateException.class, () -> {
      saveLoadHandler.saveUserList(user.getMovies());
    }, "An exception should be thrown when the saveFilePath is null");

    // Setting user List equal watchlist, saving to file and testing that no
    // exceptions are thrown
    saveLoadHandler.setSaveFile(user.getName());
    user.setMovies(watchlist.getList());
    try {
      saveLoadHandler.saveUserList(user.getMovies());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  @DisplayName("Save user list HTTP test.")
  public void testSaveUserListHttp() {
    // Testing that it works to save user list with HTTP request
    saveLoadHandler.setSaveFile(user.getName());
    System.out.println(saveLoadHandler.getSaveFilePath());
    try {
      wireMockServer.stubFor(
          put("/user/" + user.getName())
          .willReturn(aResponse()
            .withStatus(200)
          )
      );
      saveLoadHandler.saveUserListHttp(user.getName(), watchlist.getList());
    } catch (Exception e) {
      fail(e.getMessage());
    }

  }

  @Test
  @DisplayName("Testing loadUserList")
  public void testLoadUserList() {
    // Testing that exception is thrown when saveFilePath is null
    assertThrows(IllegalStateException.class, () -> {
      saveLoadHandler.loadUserList();
    }, "An exception should be thrown when the saveFilePath is null");

    // Saving watchlist to users savefile
    saveLoadHandler.setSaveFile(user.getName());
    try {
      saveLoadHandler.saveUserList(watchlist.getList());
    } catch (IOException e) {
      fail(e.getMessage());
    }

    // Testing that exception is thrown when loading from wrong saveFilePath
    saveLoadHandler.setSaveFile("NotUser");
    assertThrows(FileNotFoundException.class, () -> {
      user.setMovies(saveLoadHandler.loadUserList());
    }, "An exception should be thrown when the saveFilePath is wrong");

    // Testing that user list is loaded from file and is equal to watchlist
    saveLoadHandler.setSaveFile(user.getName());
    try {
      user.setMovies(saveLoadHandler.loadUserList());
      assertEquals(watchlist.getList(), user.getMovies());
    } catch (FileNotFoundException e) {
      fail(e.getMessage());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  @DisplayName("Load user list HTTP test.")
  public void testLoadUserListHttp() {
    // Saving watchlist to users savefile
    saveLoadHandler.setSaveFile(user.getName());
    try {
      saveLoadHandler.saveUserList(watchlist.getList());
    } catch (IOException e) {
      fail(e.getMessage());
    }

    // Testing that it works to load user list with HTTP request
    try {
      wireMockServer.stubFor(
          get("/user/" + user.getName())
          .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody(
              objectWriter.writeValueAsString(watchlist.getList())
            )
          )
      );
      List<Movie> list = saveLoadHandler.loadUserListHttp(user.getName());
      assertEquals(3, list.size());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * Deletes the savefile after each test.
   */
  @AfterEach
  public void tearDown() {
    try {
      Files.deleteIfExists(saveLoadHandler.getSaveFilePath());
    } catch (Exception e) {
      e.printStackTrace();
    }
    wireMockServer.stop();
  }
}
