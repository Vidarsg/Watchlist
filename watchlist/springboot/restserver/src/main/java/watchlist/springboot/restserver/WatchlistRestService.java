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

/** Sevice class for the rest controller.
 *
 * @author IT1901 gruppe 63
 */
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
   * Loads movie resource list.
   *
   * @param filename name of file to be loaded.
   */
  public void handleLoadResourceList(String filename) {
    System.out.println("Trying to load resource: " + filename);
    try (InputStream inputStream =
        WatchlistRestService.class.getResourceAsStream(filename + ".json")) {
      watchlist.setList(objectMapper.readValue(inputStream, new TypeReference<>() {}));
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      System.out.println("Couldn't load resource");
    }
  }

  /**
   * Loads the user's watchlist from their local savefile.
   *
   * @param username name of the user
   * @return a list of Movie objects from the user's savefile
   */
  public List<Movie> handleLoadUserList(String username) {
    System.out.println("Loading user list...");
    if (saveLoadHandler != null) {
      try {
        saveLoadHandler.setSaveFile(username);
        return saveLoadHandler.loadUserList();
      } catch (Exception e) {
        System.out.println("Couldn't load user's watchlist.");
      }
    }
    return new ArrayList<>();
  }

  /**
   * Saves the user's watchlist to their local savefile.
   *
   * @param username name of the user
   * @param jsonString the user's watchlist as a String in json format
   */
  public void handleSaveUserList(String username, String jsonString) {
    System.out.println("Saving user list...");
    if (saveLoadHandler != null) {
      try {
        saveLoadHandler.setSaveFile(username);
        saveLoadHandler.saveUserList(objectMapper.readValue(jsonString, new TypeReference<>() {}));
      } catch (IOException e) {
        //e.printStackTrace();
      } catch (Exception e) {
        System.out.println("Couldn't save user's watchlist.");
      }
    }
  }
}
