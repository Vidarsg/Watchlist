package watchlist.springboot.restserver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
  private User user;
  private Watchlist watchlist;

  private ObjectMapper objectMapper = new ObjectMapper();
  private SaveLoadHandler saveLoadHandler = new SaveLoadHandler();

  @BeforeEach
  public void setup() {
    user = new User("TestUser");
    watchlist = new Watchlist();
    saveLoadHandler.setSaveFile(user.getName());
    try {
      watchlist.setList(objectMapper.readValue(
          WatchlistRestApplicationTest.class.getResourceAsStream("test-movies.json"),
          new TypeReference<>() {}));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGet_watchlist() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/movies")).andExpect(
        MockMvcResultMatchers.status().isOk()).andReturn();
    try {
      Watchlist watchlist = new Watchlist();
      watchlist.setList(objectMapper.readValue(
          result.getResponse().getContentAsString(),
          new TypeReference<>() {}));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGet_userList() throws Exception {
    // Saving watchlist to users savefile
    System.out.println("====Running testGet_userList()====");
    try {
      saveLoadHandler.saveUserList(watchlist.getList());
    } catch (IOException e) {
      fail(e.getMessage());
    }

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/TestUser")).andExpect(
        MockMvcResultMatchers.status().isOk()).andReturn();
    try {
      user.setMovies((objectMapper.readValue(
          result.getResponse().getContentAsString(),
          new TypeReference<>() {})));
      assertEquals(watchlist.getList(), user.getMovies());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testPut_userList() throws Exception {
    System.out.println("====Running testPut_userList()====");
    ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
    String jsonString = objectWriter.writeValueAsString(watchlist.getList());
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/user/TestUser")
        .content(jsonString))
        .andExpect(
            MockMvcResultMatchers.status().isOk()).andReturn();

    try {
      user.setMovies(saveLoadHandler.loadUserList());
      assertEquals(watchlist.getList(), user.getMovies());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @AfterEach
  public void tearDown() {
    try {
      Files.deleteIfExists(saveLoadHandler.getSaveFilePath());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
