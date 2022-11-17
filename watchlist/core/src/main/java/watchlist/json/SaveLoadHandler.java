package watchlist.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import watchlist.core.Movie;

/**
 * This class saves and loads files for the user.
 *
 * @author IT1901 gruppe 63
 */
public class SaveLoadHandler {
  private Path saveFilePath = null;

  private ObjectMapper objectMapper = new ObjectMapper();
  private ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());

  private String serverUrl = "http://localhost:8080";

  /**
   * Loads a file with the saveFilePath and creates a List of Movie objects from
   * said file.
   *
   * @return a List of Movie objects generated from the given file
   * @throws IOException           if the file is invalid
   * @throws IllegalStateException if the saveFilePath is null
   * @throws FileNotFoundException if the file does not exist
   */
  public List<Movie> loadUserList() throws IOException,
      IllegalStateException, FileNotFoundException {
    if (saveFilePath == null) {
      throw new IllegalStateException("The saveFilePath is null");
    }
    return objectMapper.readValue(saveFilePath.toFile(), new TypeReference<>() {
    });
  }

  /**
   * Requests list of watched movies for a user from the server.
   *
   * @param username The user who's list should be loaded.
   * @return List of movies stored in user's file.
   * @throws Exception if file is not found or the file is invalid
   */
  public List<Movie> loadUserListHttp(String username) throws Exception {
    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder(new URI(serverUrl + "/user/" + username))
              .GET().build();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return new ArrayList<Movie>(Arrays.asList(
        objectMapper.readValue(response.body(), Movie[].class)));
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Saves a List of Movie objects to saveFilePath.
   *
   * @param movieList the List of Movie objects to save
   * @throws IOException           if the List is invalid
   * @throws IllegalStateException if the saveFilePath is null
   */
  public void saveUserList(List<Movie> movieList) throws IOException, IllegalStateException {
    if (saveFilePath == null) {
      throw new IllegalStateException("The saveFilePath is null");
    }
    objectWriter.writeValue(saveFilePath.toFile(), movieList);
  }

  /**
   * Method for saving list of movies to user's file on server.
   *
   * @param username Name of the user
   * @param movieList List of movies to be written to user's list.
   * @throws Exception if file is not found or the file is invalid
   */
  public void saveUserListHttp(String username, List<Movie> movieList) throws Exception {
    try {
      ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
      String jsonString = objectWriter.writeValueAsString(movieList);
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder(new URI(serverUrl + "/user/" + username))
          .PUT(BodyPublishers.ofString(jsonString)).build();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() == 200) {
        System.out.println("Succesfully saved user's list to server.");
      } else {
        System.err.println("Failed to save user file.");
      }
    } catch (Exception e) {
      throw e;
    }

  }

  public void setSaveFile(String filename) {
    saveFilePath = Paths.get(System.getProperty("user.home"), filename + ".json");
  }

  public Path getSaveFilePath() {
    return saveFilePath;
  }
}
