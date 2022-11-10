package watchlist.springboot.restserver;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import watchlist.core.Movie;

/** Controller for the Rest server.
 *
 * @author IT1901 gruppe 63
 */
@RestController
public class WatchlistRestController {

  @Autowired
  private WatchlistRestService watchlistRestService;

  @RequestMapping(value = "movies", method = RequestMethod.GET)
  public List<Movie> getMovies() {
    return watchlistRestService.getWatchlist().getList();
  }

  @RequestMapping(value = "user/{username}", method = RequestMethod.GET)
  @ResponseBody
  public List<Movie> getUserList(@PathVariable("username") String username) {
    return watchlistRestService.handleLoadUserList(username);
  }

  @RequestMapping(value = "user/{username}", method = RequestMethod.PUT)
  @ResponseBody
  public Boolean putUserList(@PathVariable("username") String username,
      @RequestBody String jsonString) {
    watchlistRestService.handleSaveUserList(username, jsonString);
    return true;
  }
}
