package watchlist.springboot.restserver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import watchlist.core.User;
import watchlist.core.Watchlist;
import watchlist.json.SaveLoadHandler;

@Service
public class WatchlistRestService {
  private Watchlist watchlist;
  private ObjectMapper objectMapper = new ObjectMapper();
  private User user;
  private SaveLoadHandler saveLoadHandler = new SaveLoadHandler();

  private String movieResource = "movies";

  /**
   * Constructor for WatchlistRestService class.
   *
   * @param watchlist Object of type Watchlist
   * @param user Object of type User
   */
  public WatchlistRestService(Watchlist watchlist, User user) {
    this.watchlist = watchlist;
    this.user = user;
    saveLoadHandler.setSaveFile(user.getName());
    handleLoadResourceList(movieResource);
  }

  /**
   * Default constructor for WatchlistRestService class.
   */
  public WatchlistRestService() {
    //Create default
    this.watchlist = new Watchlist();
    this.user = new User("defaultUser");
    saveLoadHandler.setSaveFile(user.getName());
    handleLoadResourceList(movieResource);
  }

  public Watchlist getWatchlist() {
    return watchlist;
  }

  public void setWatchlist(Watchlist watchlist) {
    this.watchlist = watchlist;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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
  public void handleLoadUserList(String username) {
    if (saveLoadHandler != null) {
      try {
        this.user.setName(username);
        saveLoadHandler.setSaveFile(user.getName());
        System.out.println("in handle load user list");
        user.setMovies(saveLoadHandler.loadUserList());
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Couldn't load user's watchlist.");
      }
    }
  }

  /**
   * Saves the user's watchlist to their local savefile.
   */
  public void handleSaveUserList(String jsonString) {
    System.out.println("Trying to save user list");
    if (saveLoadHandler != null) {
      try {
        System.out.println("in handle save user list");
        saveLoadHandler.saveUserList(objectMapper.readValue(jsonString, new TypeReference<>() {}));
      } catch (IOException e) {
        //e.printStackTrace();
        System.out.println("Couldn't save user's watchlist.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
