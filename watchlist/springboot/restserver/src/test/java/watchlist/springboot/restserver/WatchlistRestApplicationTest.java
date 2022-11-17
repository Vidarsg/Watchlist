package watchlist.springboot.restserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import watchlist.core.Movie;
import watchlist.core.User;
import watchlist.core.Watchlist;
import watchlist.json.SaveLoadHandler;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { WatchlistRestApplication.class,
    WatchlistRestController.class, WatchlistRestService.class })
@WebMvcTest
public class WatchlistRestApplicationTest {

  @Autowired
  private MockMvc mockMvc;
  private User testUser;
  private Watchlist testWatchlist;

  private ObjectMapper objectMapper = new ObjectMapper();
  private SaveLoadHandler saveLoadHandler = new SaveLoadHandler();

  /**
   * Setup for the tests.
   */
  @BeforeEach
  public void setup() {
    testUser = new User("TestUser");
    testWatchlist = new Watchlist();
    saveLoadHandler.setSaveFile(testUser.getName());
    try {
      testWatchlist.setList(objectMapper.readValue(
          WatchlistRestApplicationTest.class.getResourceAsStream("test-movies.json"),
          new TypeReference<>() {}));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  @DisplayName("Testing getMovies")
  public void testGet_watchlist() throws Exception {
    // Testing that it works to get movies.json through the server
    System.out.println("====Running testGet_watchlist()====");
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/movies")).andExpect(
        MockMvcResultMatchers.status().isOk()).andReturn();
    Watchlist watchlist = new Watchlist();
    try {
      watchlist.setList(objectMapper.readValue(
          result.getResponse().getContentAsString(),
          new TypeReference<>() {}));
    } catch (Exception e) {
      fail(e.getMessage());
    }

    // Testing that the 3 movies in testWatchlist also exist in the watchlist
    // we got through the server
    int counter = 0;
    for (Movie testMovie : testWatchlist.getList()) {
      for (Movie movie : watchlist.getList()) {
        if (testMovie.equals(movie)) {
          counter++;
        }
      }
    }
    assertEquals(3, counter);
  }

  @Test
  @DisplayName("Testing getUserList")
  public void testGet_userList() throws Exception {
    // Saving the testWatchlist to user's savefile without the server
    System.out.println("====Running testGet_userList()====");
    try {
      saveLoadHandler.saveUserList(testWatchlist.getList());
    } catch (IOException e) {
      fail(e.getMessage());
    }

    // Testing that it works to load the user's file through the server
    // and that it is equal to the list we initially saved
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/TestUser")).andExpect(
        MockMvcResultMatchers.status().isOk()).andReturn();
    try {
      testUser.setMovies((objectMapper.readValue(
          result.getResponse().getContentAsString(),
          new TypeReference<>() {})));
      assertEquals(testWatchlist.getList(), testUser.getMovies());
    } catch (Exception e) {
      fail(e.getMessage());
    }

    // Testing that request with wrong user results in empty list
    MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.get("/user/NotUser")).andExpect(
        MockMvcResultMatchers.status().isOk()).andReturn();
    try {
      testUser.setMovies((objectMapper.readValue(
          result1.getResponse().getContentAsString(),
          new TypeReference<>() {})));
      assertNotEquals(testWatchlist.getList(), testUser.getMovies());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  @DisplayName("Testing putUserList")
  public void testPut_userList() throws Exception {
    // Testing that it works to save the user's file through the server
    System.out.println("====Running testPut_userList()====");
    ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
    String jsonString = objectWriter.writeValueAsString(testWatchlist.getList());
    mockMvc.perform(MockMvcRequestBuilders.put("/user/TestUser")
        .content(jsonString))
        .andExpect(
            MockMvcResultMatchers.status().isOk()).andReturn();

    // Loading the file without the server and checking that it was saved correctly
    try {
      testUser.setMovies(saveLoadHandler.loadUserList());
      assertEquals(testWatchlist.getList(), testUser.getMovies());
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
  }

}
