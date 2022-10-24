package watchlist.springboot.restserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WatchlistRestController {

  @GetMapping("/movie")
  public String getMovie(@RequestParam(value = "name", defaultValue = "Whiplash") String name) {
    System.out.println("Get request: /movie");
    return "movie";
  }

}
