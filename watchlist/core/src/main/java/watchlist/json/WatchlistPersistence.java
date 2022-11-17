package watchlist.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import watchlist.core.Movie;

/**
 * This class handles loading of the movie resource.
 */
public class WatchlistPersistence {
  private String serverUrl = "http://localhost:8080/";

  private ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Method for loading movie resource.
   *
   * @return List of movies in database.
   * @throws IOException           if the file is invalid
   * @throws IllegalStateException if the saveFilePath is null
   * @throws FileNotFoundException if the file does not exist
   */
  public List<Movie> loadMovieList(String movieResource) throws IOException,
      IllegalStateException, FileNotFoundException {
    try (InputStream inputStream = WatchlistPersistence.class
        .getResourceAsStream(movieResource + ".json")) {
      return Arrays.asList(objectMapper.readValue(inputStream, Movie[].class));
    } catch (Exception e) {
      System.out.println("FAILED LOADMOVIELIST");
      System.out.println(WatchlistPersistence.class);
      System.out.println(movieResource);
      throw e;
    }
  }

  /**
   * Attempts to load movie list from REST server.
   *
   * @return List of movies retrieved from the server.
   * @throws Exception if file is not found or the file is invalid
   */
  public List<Movie> loadMovieListHttp() throws Exception {
    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder(new URI(serverUrl + "/movies"))
          .GET()
          .build();
      HttpResponse<String> response = client.send(request,
          HttpResponse.BodyHandlers.ofString());
      return Arrays.asList(objectMapper.readValue(response.body(), Movie[].class));
    } catch (Exception e) {
      throw e;
    }
  }
}