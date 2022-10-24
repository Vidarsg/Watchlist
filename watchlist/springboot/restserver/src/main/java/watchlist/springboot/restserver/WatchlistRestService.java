package watchlist.springboot.restserver;

import watchlist.core.User;
import watchlist.core.Watchlist;
import watchlist.json.SaveLoadHandler;

public class WatchlistRestService {
  private Watchlist watchlist;
  private User user;
  private SaveLoadHandler saveLoadHandler;

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
}
