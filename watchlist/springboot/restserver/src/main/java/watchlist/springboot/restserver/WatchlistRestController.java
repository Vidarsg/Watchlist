package watchlist.springboot.restserver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import watchlist.core.Movie;


@RestController
public class WatchlistRestController {

  @Autowired
  private WatchlistRestService watchlistRestService;

  @GetMapping("/movies")
  public List<Movie> getMovies(@RequestParam(value = "name", defaultValue = "Whiplash")
      String name) {
    return watchlistRestService.getWatchlist().getList();
  }

  @RequestMapping(value = "user/{username}", method = RequestMethod.GET)
  @ResponseBody
  public List<Movie> getUserList(@PathVariable("username") String username) {
    watchlistRestService.handleLoadUserList(username);
    return watchlistRestService.getUser().getMovies();
  }

  @RequestMapping(value = "user/{username}", method = RequestMethod.PUT)
  @ResponseBody
  public Boolean putUserList(@PathVariable("username") String username, @RequestBody String jsonString) {
    watchlistRestService.handleSaveUserList(jsonString);
    return true;
  }
}
