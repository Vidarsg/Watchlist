package watchlist.springboot.restserver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import watchlist.core.Movie;
import watchlist.core.Watchlist;
import watchlist.json.SaveLoadHandler;

@Service
public class WatchlistRestService {
  private Watchlist watchlist;
  private ObjectMapper objectMapper = new ObjectMapper();
  private SaveLoadHandler saveLoadHandler = new SaveLoadHandler();

  private String movieResource = "movies";

  /**
   * Default constructor for WatchlistRestService class.
   */
  public WatchlistRestService() {
    //Create default
    this.watchlist = new Watchlist();
    handleLoadResourceList(movieResource);
  }

  public Watchlist getWatchlist() {
    return watchlist;
  }

  /**
   * Load movie resource list.
   *
   * @param filename name of file to be loaded.
   */
  public void handleLoadResourceList(String filename) {
    try (InputStream inputStream =
        WatchlistRestService.class.getResourceAsStream(filename + ".json")) {
      System.out.println("Trying to load resource: " + filename);
      System.out.println(inputStream.toString());
      watchlist.setList(objectMapper.readValue(inputStream, new TypeReference<>() {}));
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      //e.printStackTrace();
      System.out.println("Couldn't load resource");
    }
  }

  /**
   * Loads the user's watchlist from their local savefile.
   */
  public List<Movie> handleLoadUserList(String username) {
    if (saveLoadHandler != null) {
      try {
        saveLoadHandler.setSaveFile(username);
        System.out.println("in handle load user list");
        return saveLoadHandler.loadUserList();
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Couldn't load user's watchlist.");
      }
    }
    return new ArrayList<>();
  }

  /**
   * Saves the user's watchlist to their local savefile.
   */
  public void handleSaveUserList(String username, String jsonString) {
    System.out.println("Trying to save user list");
    if (saveLoadHandler != null) {
      try {
        saveLoadHandler.setSaveFile(username);
        System.out.println("in handle save user list");
        saveLoadHandler.saveUserList(objectMapper.readValue(jsonString, new TypeReference<>() {}));
        System.out.println(saveLoadHandler.loadUserList().toString());
      } catch (IOException e) {
        //e.printStackTrace();
        System.out.println("Couldn't save user's watchlist.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
