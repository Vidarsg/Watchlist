package watchlist.springboot.restserver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
  private SaveLoadHandler saveLoadHandler;

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
    this.saveLoadHandler = new SaveLoadHandler();
    saveLoadHandler.setSaveFile("springboot-watchlist.json");
    try {
      handleLoadResourceList("movies");
      System.out.println("File loaded succesfully");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("File loading failed");
    }
  }

  /**
   * Default constructor for WatchlistRestService class.
   */
  public WatchlistRestService() {
    //Create default
    this.watchlist = new Watchlist();
    this.user = new User("defaultUser");
    this.saveLoadHandler = new SaveLoadHandler();
    saveLoadHandler.setSaveFile("springboot-watchlist.json");
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
    try {
      System.out.println("Trying to load resource: " + filename);
      InputStream inputStream = WatchlistRestService.class.getResourceAsStream(filename + ".json");
      System.out.println(inputStream.toString());
      watchlist.setList(objectMapper.readValue(inputStream, new TypeReference<>() {}));
    } catch (Exception e) {
      //e.printStackTrace();
      System.out.println("Couldn't load resource");
    }
  }


}
