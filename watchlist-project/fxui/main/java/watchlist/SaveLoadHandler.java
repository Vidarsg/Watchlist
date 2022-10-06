package watchlist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class SaveLoadHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    private ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());

    /**
     * Loads a file with the given file name and creates a List of Movie objects from said file
     * @param filename the String with the name of the file to load
     * @return a List of Movie objects generated from the given file
     * @throws FileNotFoundException if the file does not exist
     * @throws IOException if the file is invalid
     */
    public List<Movie> load(String filename) throws FileNotFoundException, IOException {
        File file = getFile(filename);
        List<Movie> movieList = objectMapper.readValue(file, new TypeReference<>(){});
        return movieList;
    }
    
    /**
     * Saves a List of Movie objects to a file with the given file name
     * @param filename the String with the name of the file to save to
     * @param movieList the List of Movie objects to save
     * @throws IOException if the List is invalid
     */
    public void save(String filename, List<Movie> movieList) throws IOException {
        File file = getFile(filename);
        objectWriter.writeValue(file, movieList);
    }
    
    /**
     * Gets the file with the given filename
     * @param filename the String with the name of the file to get
     * @return The file with the given filename
     */
    public static File getFile(String filename) {
        return new File(SaveLoadHandler.class.getResource("savefiles/").getPath() + filename + ".json");
	}
}
