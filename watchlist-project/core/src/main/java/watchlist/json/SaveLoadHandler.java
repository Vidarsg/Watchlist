package watchlist.json;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import watchlist.core.Movie;

public class SaveLoadHandler {
    private Path saveFilePath = null;

    private ObjectMapper objectMapper = new ObjectMapper();
    private ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());

    /**
     * Loads a file with the given file name from the "resources" folder and creates a List of Movie objects from said file
     * @param filename the String with the name of the file to load
     * @return a List of Movie objects generated from the given file
     * @throws IOException if the file is invalid
     */
    public List<Movie> loadResourceList(String filename) throws IOException {
        return objectMapper.readValue(getInputStream(filename), new TypeReference<>(){});
    }

    /**
     * Loads a file with the saveFilePath and creates a List of Movie objects from said file
     * @return a List of Movie objects generated from the given file
     * @throws IOException if the file is invalid
     * @throws IllegalStateException if the saveFilePath is null
     * @throws FileNotFoundException if the file does not exist
     */
    public List<Movie> loadUserList() throws IOException, IllegalStateException, FileNotFoundException {
        if (saveFilePath == null) {
            throw new IllegalStateException("The saveFilePath is null");
        }
        return objectMapper.readValue(saveFilePath.toFile(), new TypeReference<>(){});
    }
  
    /**
     * Saves a List of Movie objects to saveFilePath
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

    /**
     * Gets the file with the given filename as InputStream
     * @param filename the String with the name of the file to get
     * @return an InputStream of the file
     */
    public InputStream getInputStream(String filename) {
        return SaveLoadHandler.class.getResourceAsStream(filename + ".json");
    }

    public void setSaveFile(String filename) {
        saveFilePath = Paths.get(System.getProperty("user.home"), filename + ".json");
    }

    public Path getSaveFilePath() {
        return saveFilePath;
    }
}
