package watchlist.springboot.restserver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

}
