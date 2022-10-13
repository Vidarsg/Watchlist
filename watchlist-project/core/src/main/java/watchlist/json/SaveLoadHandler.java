package watchlist.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import watchlist.core.Movie;

public class SaveLoadHandler {
  private Path saveFilePath = null;

  private ObjectMapper objectMapper = new ObjectMapper();
  private ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());

  /**
   * Loads a file with the saveFilePath and creates a List of Movie objects from said file.
   * 
   * @return a List of Movie objects generated from the given file
   * @throws IOException if the file is invalid
   * @throws IllegalStateException if the saveFilePath is null
   * @throws FileNotFoundException if the file does not exist
   */
  public List<Movie> loadUserList() throws IOException,
      IllegalStateException, FileNotFoundException {
    if (saveFilePath == null) {
      throw new IllegalStateException("The saveFilePath is null");
    }
    return objectMapper.readValue(saveFilePath.toFile(), new TypeReference<>() {});
  }

  /**
   * Saves a List of Movie objects to saveFilePath.
   * 
   * @param movieList the List of Movie objects to save
   * @throws IOException if the List is invalid
   * @throws IllegalStateException if the saveFilePath is null
   */
  public void saveUserList(List<Movie> movieList) throws IOException, IllegalStateException {
    if (saveFilePath == null) {
      throw new IllegalStateException("The saveFilePath is null");
    }
    objectWriter.writeValue(saveFilePath.toFile(), movieList);
  }

  public void setSaveFile(String filename) {
    saveFilePath = Paths.get(System.getProperty("user.home"), filename + ".json");
  }

  public Path getSaveFilePath() {
    return saveFilePath;
  }
}
